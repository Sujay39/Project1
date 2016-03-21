package com.sujay.puranik.p1_movie;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by puranisu on 3/19/2016.
 */
public class MovieDetailFragment extends android.support.v4.app.Fragment
{
    Movie movieItem;

    public  MovieDetailFragment()
    {
        movieItem = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_detail, container , false);

        Bundle bundle = getArguments();
        movieItem = bundle.getParcelable("movie");

        if(movieItem == null)
        {
            Toast.makeText(getContext(), "Could not retrieve the movie", Toast.LENGTH_LONG).show();
        }
        else {


            String movie_poster = movieItem.getPoster_path();

            TextView textView = (TextView) (view.findViewById(R.id.titleTextview));
            textView.setText(movieItem.getOriginal_title());

            textView = (TextView)view.findViewById(R.id.RatingTextView);
            textView.setText(movieItem.getVote_average());

            textView = (TextView)view.findViewById(R.id.ReleaseDate);
            textView.setText(movieItem.getRelease_date());

            textView = (TextView)view.findViewById(R.id.OverviewTexview);
            textView.setText(movieItem.getOverview());




            ImageView imageView = (ImageView) view.findViewById(R.id.movieThumbnail);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/" + movie_poster).into(imageView);
        }

        return view;


    }
}
