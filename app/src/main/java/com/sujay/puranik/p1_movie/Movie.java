package com.sujay.puranik.p1_movie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by puranisu on 3/17/2016.
 */
public class Movie implements Parcelable
{

    Movie( String original_title , String movie_poster , String overview , String vote_average , String release_date)
    {
        this.original_title = original_title;
        this.poster_path = movie_poster;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    protected Movie(Parcel in) {
        // constructor for Parcelable object.
        original_title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        release_date = in.readString();
    }

    String original_title ;
    String poster_path;
    String  overview ;
    String vote_average ;
    String release_date;

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }




    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public String toString()
    {
        return original_title ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(vote_average);
        dest.writeString(release_date);
    }
}
