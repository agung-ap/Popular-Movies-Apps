package com.example.agungaprian.popularmovie.utils;

import com.example.agungaprian.popularmovie.models.Movie;

/**
 * Created by agungaprian on 08/07/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface OnTaskCompleted{
        void onFetchMoviesTaskCompleted(Movie[] movies);
}
