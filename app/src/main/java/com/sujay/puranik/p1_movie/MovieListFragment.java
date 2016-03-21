
package com.sujay.puranik.p1_movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by puranisu on 3/16/2016.
 */

public class MovieListFragment  extends Fragment
{
    private MovieCustomAdapter movieListAdapter;
    static  String sort_param ="popular";       // By Default it sorts by Popularity.
    Movie MovieList[];
    public MovieListFragment()
    {
        MovieList = null;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        updateList(sort_param);
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_list_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.sortByPopularity)
        {
            sort_param = "popular";
            updateList(sort_param);
         //   Toast.makeText(getContext() , "Sorted the list based on Popularity" , Toast.LENGTH_LONG).show();
            return  true;
        }
        else
        if( id == R.id.sortByRating)
        {
            sort_param = "top_rated";
            updateList(sort_param);
           // Toast.makeText(getContext() , "Sorted the list based on Rating" , Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateList(String param)
    {   // Accepts a string - for sorting parameter - (popular or rating)
        MovieListFetcher movieListFetcher = new MovieListFetcher();
        movieListFetcher.execute(param);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        View rootView = inflater.inflate(R.layout.fragment_main , container , false);
        try
        {
            movieListAdapter = new MovieCustomAdapter(getActivity(), new ArrayList<Movie>());
            GridView gridView = (GridView)  rootView.findViewById(R.id.movie_gridview);
            gridView.setAdapter(movieListAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent intent = new Intent(getActivity() , MovieDetail.class)

                    Movie current = movieListAdapter.getItem(position);

                    Intent intent = new Intent(getActivity() , MovieDetail.class);

                    intent.putExtra("movie", current);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            Log.e("MovieListFragment",e.toString() + "\n Could not set the movie adapter ");
        }

        return  rootView;
    }


    public  class  MovieListFetcher extends AsyncTask<String,Void,Movie[]>
    {
        @Override
        protected Movie[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String resultJSONString = null;

            try
            {
                // IMPORTANT: PLACE YOUR API_KEY AT values/strings.xml as a value to api_key.
                String key = getResources().getString(R.string.api_key);
                String link ="http://api.themoviedb.org/3/movie/"+params[0]+"?api_key="+key;
                //Uri uri = Uri.parse("http://api.themoviedb.org/3/movie/"+params+"?api_key"+R.string.api_key);
                //URL url = new URL(uri.toString());

                URL url = new URL(link);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();



                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String singleResult ;
                StringBuffer resultBuffer = new StringBuffer();
                int i = 0 ;
                singleResult= reader.readLine();

                resultBuffer.append(singleResult+'\n');


                if(resultBuffer.length()==0)
                {
                    return  null;
                }
                resultJSONString = resultBuffer.toString();
            }
            catch (IOException e)
            {
                Log.e("MOVIELIST", "CONNECTION ERROR", e);
                return  null;
            }
            finally {
                if(urlConnection!=null)
                {
                    urlConnection.disconnect();
                }
                if(reader!=null)
                {
                    try {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        Log.e("MovieListFragment","Could not close the reader");
                    }
                }
            }

            try
            {
                JSONObject movieJSONObject = new JSONObject(resultJSONString);
                JSONArray movieListArray = movieJSONObject.getJSONArray("results");


                Movie movie ;
                MovieList = new Movie[movieListArray.length()];

                String original_title ;
                String  poster_path;
                String  overview ;
                String vote_average ;
                String release_date;

                for (int i = 0; i < movieListArray.length(); i++)
                {

                    original_title = movieListArray.getJSONObject(i).getString("original_title");
                    poster_path = movieListArray.getJSONObject(i).getString("poster_path");
                    overview = movieListArray.getJSONObject(i).getString("overview");
                    vote_average = movieListArray.getJSONObject(i).getString("vote_average");
                    release_date = movieListArray.getJSONObject(i).getString("release_date");

                    movie = new Movie(original_title, poster_path, overview, vote_average , release_date);
                    MovieList[i] = movie;
                }
                return  MovieList;

            }
            catch ( JSONException e)
            {
                Log.e("JSONConverterr","ERROR while parsing JSON " +e.toString());
            }
            return  null;
        }



        @Override
        protected void onPostExecute(Movie movies[])
        {

            if (movies!= null)
            {
                movieListAdapter.clear();
                for(Movie movie : movies)
                {
                    //Log.i("onPPOSTEXECUTE -", movie.toString() );
                    movieListAdapter.add(movie);
                }
            }
        }
    }

}
