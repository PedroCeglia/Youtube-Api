package com.youtube.app.clone.curso.android.pedro.youtube.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.youtube.app.clone.curso.android.pedro.youtube.R;
import com.youtube.app.clone.curso.android.pedro.youtube.adapter.AdapterVideos;
import com.youtube.app.clone.curso.android.pedro.youtube.api.YoutubeService;
import com.youtube.app.clone.curso.android.pedro.youtube.helper.RecyclerItemClickListener;
import com.youtube.app.clone.curso.android.pedro.youtube.helper.RetrofitConfig;
import com.youtube.app.clone.curso.android.pedro.youtube.helper.YoutubeConfig;
import com.youtube.app.clone.curso.android.pedro.youtube.models.Item;
import com.youtube.app.clone.curso.android.pedro.youtube.models.Resultado;
import com.youtube.app.clone.curso.android.pedro.youtube.models.Video;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvVideos;
    private AdapterVideos adapterVideos;
    private List<Item> listaDeItens = new ArrayList<>();
    private MaterialSearchView searchView;
    private Retrofit retrofit;
    private Resultado resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurações Iniciais
        configuracoesIniciais();

        // Configurando Toolbar
        configurandoToolbar();

        // Configurando Retrofit
        recuperarVideos("");

        // Configurando SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recuperarVideos("");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }


    private void recuperarVideos(String q){

        String pesquisaQ = q.replaceAll(" ", "+");
        YoutubeService youtubeService = retrofit.create(YoutubeService.class);

        youtubeService.recuperarVideos(
                "snippet",
                "date",
                "20",
                YoutubeConfig.CHAVE_YOUTUBE_API,
                YoutubeConfig.CANAL_ID,
                pesquisaQ
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {

                Log.i("Resultado", "resultado : " + response.toString());
                if (response.isSuccessful()){
                    resultado = response.body();
                    listaDeItens = resultado.items;
                    configurandoRecyvlerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });

    }

    public void configuracoesIniciais(){
        retrofit = RetrofitConfig.getRetrofit();
        rvVideos = findViewById(R.id.rvVideos);
    }
    public void configurandoToolbar(){
        // Configurando Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_principal);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);
    }
    public void configurandoRecyvlerView(){
        // Configurando recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVideos.setLayoutManager(layoutManager);
        rvVideos.setHasFixedSize(true);
        adapterVideos = new AdapterVideos(this, listaDeItens);
        rvVideos.setAdapter(adapterVideos);

        rvVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        rvVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Item video = listaDeItens.get(position);
                                String idVideo = video.id.videoId;


                                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                                i.putExtra("idVideo", idVideo);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    /*
    Log.i("Resultado", "resultado regionCode : " + resultado.regionCode);
                    Log.i("Resultado", "resultado totalResults : " + resultado.pageInfo.totalResults);
                    Log.i("Resultado", "resultado resultsPerPage : " + resultado.pageInfo.resultsPerPage);
                    Log.i("Resultado", "resultado size : " + resultado.items.size());
                    Log.i("Resultado", "resultado channelId : " + resultado.items.get(1).snippet.channelId);
                    Log.i("Resultado", "resultado channelTitle : " + resultado.items.get(1).snippet.channelTitle);
                    Log.i("Resultado", "resultado description : " + resultado.items.get(1).snippet.description);
                    Log.i("Resultado", "resultado liveBroadcastContent : " + resultado.items.get(1).snippet.liveBroadcastContent);
                    Log.i("Resultado", "resultado publishedAt : " + resultado.items.get(1).snippet.publishedAt);
                    Log.i("Resultado", "resultado title : " + resultado.items.get(1).snippet.title);
                    Log.i("Resultado", "resultado kind : " + resultado.items.get(1).id.kind);
                    Log.i("Resultado", "resultado videoId : " + resultado.items.get(1).id.videoId);
                    Log.i("Resultado", "resultado url : " + resultado.items.get(1).snippet.thumbnails.high.url);

     */
}