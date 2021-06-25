package com.youtube.app.clone.curso.android.pedro.youtube.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youtube.app.clone.curso.android.pedro.youtube.R;
import com.youtube.app.clone.curso.android.pedro.youtube.models.Item;
import com.youtube.app.clone.curso.android.pedro.youtube.models.Video;

import java.util.List;

public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.MyViewHolderVideos> {

    private Context c;
    private List<Item> listadeVideos;

    public AdapterVideos(Context c, List<Item> listadeVideos) {
        this.c = c;
        this.listadeVideos = listadeVideos;
    }

    @NonNull
    @Override
    public MyViewHolderVideos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_videos_layout, parent, false);
        return new MyViewHolderVideos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderVideos holder, int position) {

        Item videoModel = listadeVideos.get(position);
        String url = videoModel.snippet.thumbnails.high.url;
        String title = videoModel.snippet.title;
        String descricao = videoModel.snippet.description;


        holder.tvTitulo.setText(title);
        if (descricao != null){
            if (!descricao.isEmpty()){
                holder.tvDescricao.setText(descricao);
            }
        }


        if (url != null){
            if (!url.isEmpty()){
                Glide.with(c).load(url).into(holder.ivCapa);
            }
        }



    }

    @Override
    public int getItemCount() {
        return listadeVideos.size();
    }

    public class MyViewHolderVideos extends RecyclerView.ViewHolder {

        private TextView tvTitulo;
        private TextView tvDescricao;
        private TextView tvData;
        private ImageView ivCapa;

        public MyViewHolderVideos(@NonNull View itemView) {
            super(itemView);

            tvDescricao = itemView.findViewById(R.id.tvAdapterDescrição);
            tvTitulo = itemView.findViewById(R.id.tvAdapterTitulo);
            ivCapa = itemView.findViewById(R.id.ivCapa);
        }
    }
}
