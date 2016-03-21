package com.sujay.puranik.p1_movie;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by puranisu on 3/17/2016.
 */
public class MovieCustomAdapter extends ArrayAdapter<Movie>
{

    public MovieCustomAdapter(Activity context, List<Movie> movies)
    {
        super(context, 0 , movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Movie currentMovie = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater
                    .from(getContext())
                        .inflate(R.layout.movie_list_item, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_list_item_imageview);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        String movie_poster = currentMovie.poster_path;
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+movie_poster).into(imageView);

        return convertView;
    }
}
