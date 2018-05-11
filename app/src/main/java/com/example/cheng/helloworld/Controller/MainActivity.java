package com.example.cheng.helloworld.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cheng.helloworld.MovieListModel.MovieListData;
import com.example.cheng.helloworld.MovieListModel.MovieListDataResults;
import com.example.cheng.helloworld.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String title;
    public final static String BaseUrl="https://api.themoviedb.org/";
    private final String APIKey="75243f11239a95d952cb6e0b6466d536";
    private HttpService httpService;
    private ListView listView;
    private boolean flag_loading=false;
    private List<MovieListDataResults> nowPlayingData;
    private MovieListAdapter nowPlayingAdapter;
    private int nowPlayingPage=0;
    private List<MovieListDataResults> upComingData;
    private MovieListAdapter upComingAdapter;
    private int upComingPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setListViewListener();
    }

    private void setListViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),MovieDetailActivity.class);
                if(title.equals("Now Playing")) {
                    intent.putExtra("MovieID",nowPlayingData.get(i).getId());
                }else intent.putExtra("MovieID",upComingData.get(i).getId());
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(flag_loading == false)
                    {
                        flag_loading = true;
                        Call<MovieListData> call;
                        if(title.equals("Now Playing")) call=httpService.getNowPlaying(APIKey,nowPlayingPage+1);
                        else call=httpService.getUpComing(APIKey,upComingPage+1);
                        call.enqueue(new Callback<MovieListData>(){
                            @Override
                            public void onResponse(Call<MovieListData> call, Response<MovieListData> response) {
                                MovieListData movieListData = response.body();
                                if(title.equals("Now Playing")) {
                                    nowPlayingPage++;
                                    Toast.makeText(getApplicationContext(),"Page "+String.valueOf(nowPlayingPage)+" Loaded",Toast.LENGTH_SHORT).show();
                                    nowPlayingData.addAll(Arrays.asList(movieListData.getResults()));
                                    nowPlayingAdapter.notifyDataSetChanged();
                                }else{
                                    upComingPage++;
                                    Toast.makeText(getApplicationContext(),"Page "+String.valueOf(upComingPage)+" Loaded",Toast.LENGTH_SHORT).show();
                                    upComingData.addAll(Arrays.asList(movieListData.getResults()));
                                    upComingAdapter.notifyDataSetChanged();
                                }
                                flag_loading=false;
                                Log.i("成功",movieListData.getResults()[0].getTitle());
                            }

                            @Override
                            public void onFailure(Call<MovieListData> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void initData() {
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

                Toast.makeText(getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT).show();
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

                Toast.makeText(getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        setTitle("Now Playing");
        title="Now Playing";
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
                title="Now Playing";
                listView.setAdapter(nowPlayingAdapter);
            }
        });
        findViewById(R.id.upcoming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Upcoming Movies");
                title="Upcoming Movies";
                listView.setAdapter(upComingAdapter);
            }
        });
    }
}
