package com.example.cheng.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.cheng.helloworld.MovieListModel.MovieListData;
import com.example.cheng.helloworld.MovieListModel.MovieListDataResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final static String BaseUrl="https://api.themoviedb.org/";
    private final String APIKey="75243f11239a95d952cb6e0b6466d536";
    private HttpService httpService;
    private ListView listView;
    private List<MovieListDataResults> nowPlayingData;
    private MovieListAdapter nowPlayingAdapter;
    private List<MovieListDataResults> upComingData;
    private MovieListAdapter upComingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Now Playing");

        listView=findViewById(R.id.movie_list);
        nowPlayingData=new ArrayList<>();
        nowPlayingAdapter=new MovieListAdapter(this,nowPlayingData);
        listView.setAdapter(nowPlayingAdapter);
        setTitle("Now Playing");
        upComingData=new ArrayList<>();
        upComingAdapter=new MovieListAdapter(this,upComingData);
        httpService = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BaseUrl).build().create(HttpService .class);
        findViewById(R.id.now_playing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Now Playing");
                listView.setAdapter(nowPlayingAdapter);
            }
        });
        findViewById(R.id.upcoming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Upcoming Movies");
                listView.setAdapter(upComingAdapter);
            }
        });

        httpService.getNowPlaying(APIKey,1).enqueue(new Callback<MovieListData>(){
            @Override
            public void onResponse(Call<MovieListData> call, Response<MovieListData> response) {
                MovieListData movieListData = response.body();
                nowPlayingData.addAll(Arrays.asList(movieListData.getResults()));
                nowPlayingAdapter.notifyDataSetChanged();
                Log.i("成功",movieListData.getResults()[0].getTitle());
            }

            @Override
            public void onFailure(Call<MovieListData> call, Throwable t) {

                Log.i("失败","啊啊啊啊啊啊啊啊");
            }
        });
        httpService.getUpComing(APIKey,1).enqueue(new Callback<MovieListData>(){
            @Override
            public void onResponse(Call<MovieListData> call, Response<MovieListData> response) {
                MovieListData movieListData = response.body();
                upComingData.addAll(Arrays.asList(movieListData.getResults()));
                upComingAdapter.notifyDataSetChanged();
                Log.i("成功",movieListData.getResults()[0].getTitle());
            }

            @Override
            public void onFailure(Call<MovieListData> call, Throwable t) {

                Log.i("失败","啊啊啊啊啊啊啊啊");
            }
        });
    }

}
