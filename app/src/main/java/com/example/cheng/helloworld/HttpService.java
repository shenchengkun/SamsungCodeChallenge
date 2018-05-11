package com.example.cheng.helloworld;

import com.example.cheng.helloworld.MovieListModel.MovieListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cheng on 5/10/2018.
 */

public interface HttpService {

    @GET("3/movie/now_playing")
    Call<MovieListData> getNowPlaying(@Query("api_key") String APIKey,@Query("page") int page);

    @GET("3/movie/upcoming")
    Call<MovieListData> getUpComing(@Query("api_key") String APIKey,@Query("page") int page);
}
