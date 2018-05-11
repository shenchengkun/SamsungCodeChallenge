package com.example.cheng.helloworld.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.helloworld.MovieDetailModel.MovieDetail;
import com.example.cheng.helloworld.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailActivity extends Activity {
    private final String APIKey="75243f11239a95d952cb6e0b6466d536";
    private HttpService httpService;
    private int movieID;
    private TextView overView,movieTitle,voteCount,voteAverage;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        initView();
        fetchData();
    }

    private void fetchData() {
        httpService.getMovieDetail(movieID,APIKey).enqueue(new Callback<MovieDetail>(){
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                MovieDetail movieDetail = response.body();
                Picasso.get().load("https://image.tmdb.org/t/p/w780"+movieDetail.getBackdropPath()).into(imageView);
                overView.setText(movieDetail.getOverview());
                movieTitle.setText(movieDetail.getTitle()+"\n\nOverview");
                voteAverage.setText("\n"+"Vote average: "+String.valueOf(movieDetail.getVoteAverage()));
                voteCount.setText("Vote count: "+String.valueOf(movieDetail.getVoteCount()));
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        overView=findViewById(R.id.overview);
        movieTitle=findViewById(R.id.title_detail);
        voteAverage=findViewById(R.id.vote_average);
        voteCount=findViewById(R.id.vote_count);
        imageView=findViewById(R.id.movie_poster);
        movieID=getIntent().getIntExtra("MovieID",0);
        httpService = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MainActivity.BaseUrl)
                .build().create(HttpService .class);
    }
}
