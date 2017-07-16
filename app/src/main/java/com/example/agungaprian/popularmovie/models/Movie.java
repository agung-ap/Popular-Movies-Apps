package com.example.agungaprian.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by agungaprian on 04/07/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class Movie implements Parcelable {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private String mOriginalTitle;
    private String mPosterPath;
    private String mOverview;
    private Double mVoteAverage;
    private String mReleaseDate;

    //contructor
    public Movie(){

    }

    /**
     * set original title for the movie
     * @param originalTitle
     */
    public void setOriginalTitle(String originalTitle){
        mOriginalTitle = originalTitle;
    }

    /**
     * set the path of movie poster
     * @param posterPath
     */
    public void setPosterPath(String posterPath){
        mPosterPath = posterPath;
    }

    /**
     * sets overview of the movie, if overview is 'null' it will default to null
     * @param overView
     */
    public void setOverview(String overView){
        if (!overView.equals(null)){
            mOverview = overView;
        }
    }


    /**
     * set the vote average of the movie
     *
     * @param voteAverage
     */
    public void setVoteAverage(Double voteAverage){
        mVoteAverage = voteAverage;
    }

    /**
     * Sets the release date of the movie.
     *
     * @param releaseDate Release date of the movie. If value is "null" the release date will remain
     *                    null
     */
    public void setReleaseDate(String releaseDate) {
        if(!releaseDate.equals("null")) {
            mReleaseDate = releaseDate;
        }
    }

    /**
     * Gets the original title of the movie
     *
     * @return Original title of the movie
     */
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * Returns URL string to where the poster can be loaded
     *
     * @return URL string to where the poster can be loaded
     */
    public String getPosterPath() {
        final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

        return TMDB_POSTER_BASE_URL + mPosterPath;
    }

    /**
     * Gets the TMDb movie description (plot)
     *
     * @return TMDb movie description (plot)
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Gets the TMDb vote average score
     *
     * @return TMDb vote average score
     */
    private Double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Gets the release date of the movie
     *
     * @return Release date of the movie
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Gets the TMDb way of scoring the movie: <score>/10
     *
     * @return TMDb way of scoring the movie: <score>/10
     */
    public String getDetailedVoteAverage() {
        return String.valueOf(getVoteAverage()) + " / 10";
    }

    /**
     * Returns the format of the date.
     *
     * @return Format of the date
     */
    public String getDateFormat() {
        return DATE_FORMAT;
    }


    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
    }

    private Movie(Parcel in) {
        mOriginalTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
    }
}
