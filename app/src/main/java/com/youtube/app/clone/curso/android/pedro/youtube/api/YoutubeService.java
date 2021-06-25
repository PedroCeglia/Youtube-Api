package com.youtube.app.clone.curso.android.pedro.youtube.api;

import com.youtube.app.clone.curso.android.pedro.youtube.models.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    /*
    https://www.googleapis.com/youtube/v3/
    search
    ?part=snippet
    &order=date
    &maxResults=20
    &key=AIzaSyCErrVWKCvQuYZTZ7lzyehFnTWU6CucIOw
    &channelId=UC5k4fsA9AhxNLgMQa36CVdg


    https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyCErrVWKCvQuYZTZ7lzyehFnTWU6CucIOw&channelId=UC5k4fsA9AhxNLgMQa36CVdg

     */

    @GET("search")
    Call<Resultado> recuperarVideos(
            @Query ("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults,
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("q") String q);

}
