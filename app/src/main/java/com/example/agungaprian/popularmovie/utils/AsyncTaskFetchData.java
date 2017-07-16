package com.example.agungaprian.popularmovie.utils;

import com.example.agungaprian.popularmovie.models.Movie;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by agungaprian on 04/07/17.
 */

@SuppressWarnings({"ALL", "JavaDoc"})
public class AsyncTaskFetchData extends AsyncTask<String,Void,Movie[]>{
    //logging purpose
    private final String LOG_TAG = AsyncTaskFetchData.class.getSimpleName();
    //API key
    private final String mApiKey;
    //URLParameter
    private String URLParameter;
    //listener
    private final OnTaskCompleted mListener;

    /**
     * constructor
     * @param listener
     * @param apiKey
     */
    @SuppressWarnings("JavaDoc")
    public AsyncTaskFetchData(OnTaskCompleted listener, String apiKey ,String parameter) {
        super();
        mListener = listener;
        mApiKey = apiKey;
        URLParameter = parameter;
    }

    /**
     * create and return an URL
     *
     */
    private URL getApiUrl(String [] parameters) throws MalformedURLException {
        final String BASE_URL = URLParameter;
        final String API_KEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM,mApiKey)
                .build();
        return new URL(builtUri.toString());
    }

    /**
     * Extracts data from the JSON object and returns an Array of movie objects.
     * @param moviesJsonString
     * @throws JSONException
     */
    private Movie[] getDataFromJson (String moviesJsonString) throws JSONException {
        //json tag
        final String TAG_RESULTS = "results";
        final String TAG_ORIGINAL_TITLE = "original_title";
        final String TAG_POSTER_PATH = "poster_path";
        final String TAG_OVERVIEW = "overview";
        final String TAG_VOTE_AVERAGE = "vote_average";
        final String TAG_RELEASE_DATE = "release_date";

        // Get the array containing the movies found
        JSONObject moviesJson = new JSONObject(moviesJsonString);
        JSONArray resultsArray = moviesJson.getJSONArray(TAG_RESULTS);

        // Create array of Movie objects that stores data from the JSON string
        Movie[] movies = new Movie[resultsArray.length()];

        //Traverse Through movie one by one and get data
        for (int i = 0; i <resultsArray.length();  i++){
            // Initialize each object before it can be used
            movies[i] = new Movie();

            // Object contains all tags we're looking for
            JSONObject movieInfo = resultsArray.getJSONObject(i);

            // Store data in movie object
            movies[i].setOriginalTitle(movieInfo.getString(TAG_ORIGINAL_TITLE));
            movies[i].setPosterPath(movieInfo.getString(TAG_POSTER_PATH));
            movies[i].setOverview(movieInfo.getString(TAG_OVERVIEW));
            movies[i].setVoteAverage(movieInfo.getDouble(TAG_VOTE_AVERAGE));
            movies[i].setReleaseDate(movieInfo.getString(TAG_RELEASE_DATE));
        }
        return movies;
    }

    //do in background process when fetch data from TMDB api
    @Override
    protected Movie[] doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        //hold data return to the api
        String moviesJsonString = null;
        try {
            URL url = getApiUrl(params);

            // connecting to get JSON
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Adds '\n' at last line if not already there.
                // This supposedly makes it easier to debug.
                builder.append(line).append("\n");
            }

            if (builder.length() == 0) {
                // No data found. Nothing more to do here.
                return null;
            }

            moviesJsonString = builder.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG,"Eror "+ e);
            return null;
        } finally {
            // Tidy up: release url connection and buffered reader
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            //make sense of the json data
            return getDataFromJson(moviesJsonString);
        }catch (JSONException e){
            Log.e(LOG_TAG, e.getMessage(),e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);

        //notif ui
        mListener.onFetchMoviesTaskCompleted(movies);
    }
}
