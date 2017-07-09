package com.example.agungaprian.popularmovie.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.example.agungaprian.popularmovie.R;
import com.example.agungaprian.popularmovie.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by agungaprian on 07/07/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ImageAdapter extends BaseAdapter {
    private final Context mContext;
    private final Movie[] mMovies;

    public ImageAdapter(Context mContext, Movie[] mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
    }

    @Override
    public int getCount() {
        if (mMovies == null || mMovies.length == 0){
            return -1;
        }
        return mMovies.length;
    }

    @Override
    public Object getItem(int i) {
        if (mMovies ==null || mMovies.length ==0){
            return null;
        }
        return mMovies[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // Will be null if it's not recycled. Will initialize ImageView if new.
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mMovies[position].getPosterPath())
                .resize(mContext.getResources().getInteger(R.integer.tmdb_poster_w185_width),
                        mContext.getResources().getInteger(R.integer.tmdb_poster_w185_height))
                .error(R.drawable.example_movie_poster)
                .placeholder(R.drawable.searching)
                .into(imageView);

        return imageView;
    }
}
