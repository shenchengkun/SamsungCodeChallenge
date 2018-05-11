package com.example.cheng.helloworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cheng.helloworld.MovieListModel.MovieListDataResults;

import java.util.List;

/**
 * Created by cheng on 5/10/2018.
 */

public class MovieListAdapter extends BaseAdapter {
    private Context context;
    private final List<MovieListDataResults> dataList;

    public MovieListAdapter(Context context, List<MovieListDataResults> dataArray) {
        this.context=context;
        this.dataList =dataArray;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView title,popularity,genres;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.movie_item, parent,false);
            viewHolder=new ViewHolder();
            viewHolder.title=convertView.findViewById(R.id.title);
            viewHolder.popularity=convertView.findViewById(R.id.popularity);
            viewHolder.genres=convertView.findViewById(R.id.genres);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(dataList.get(position).getTitle());
        viewHolder.popularity.setText("popularity: "+String.valueOf(dataList.get(position).getPopularity()));

        int[] genre_ids= dataList.get(position).getGenre_ids();
        StringBuilder stringBuilder=new StringBuilder("");
        for (int i=0;i<genre_ids.length;i++){
            stringBuilder.append(" ");
            stringBuilder.append(genre_ids[i]);
        }
        viewHolder.genres.setText("genres: "+stringBuilder.toString());
        return convertView;
    }
}
