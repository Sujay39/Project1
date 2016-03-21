package com.sujay.puranik.p1_movie;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by puranisu on 3/18/2016.
 */
public class MovieDetail extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        //Toast.makeText(getApplicationContext(), String.valueOf(getIntent().hasExtra("movie")) , Toast.LENGTH_LONG).show();

        Movie currentMovieItem =(Movie) getIntent().getParcelableExtra("movie");
        // get the Movie (Parcelable) object

        MovieDetailFragment detailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie" , currentMovieItem);
        detailFragment.setArguments(bundle);


        if(savedInstanceState == null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                        .add(R.id.detailcontainer , detailFragment)
                            .commit();
        }


    }

}
