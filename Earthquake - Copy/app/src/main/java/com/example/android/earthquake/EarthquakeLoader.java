package com.example.android.earthquake;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<word>> {

    private String url;

    public EarthquakeLoader(Context context,String murl) {
        super(context);
        url=murl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<word> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (url == null) {
            return null;
        }
        List<word> al = QueryUtils.fetchEarthquakeData(url);
        return al;

    }
}
