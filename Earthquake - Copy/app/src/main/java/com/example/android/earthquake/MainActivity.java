package com.example.android.earthquake;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.content.AsyncTaskLoader;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<word>>
{

    private WordAdapter adapter ;
    private TextView EmptyTextView;
    public static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /************* Create the adapter to convert the array to views **/


        adapter = new WordAdapter(this, new ArrayList<word>());
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(adapter);

        // Text View to display the empty view
        EmptyTextView=(TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(EmptyTextView);


        /***************** Using implicity Intent with ListView to handle if i Pressed on the List open
         *                       web browser for more information about this earthquake***************/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                                                // Get the data item for this position
                                                word w = adapter.getItem(position);
                                                //get url
                                                Uri url = Uri.parse(w.GetUrl());
                                                Intent web = new Intent(Intent.ACTION_VIEW, url);
                                                startActivity(web);


                                            }
                                        }


        );


// Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.

            getLoaderManager().initLoader(1, null, this);
        }
        else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            EmptyTextView.setText("no internet connection");
        }
    }


        @Override
        public Loader<List<word>> onCreateLoader(int id, Bundle args) {
            return new EarthquakeLoader(this,USGS_REQUEST_URL);
        }

        @Override
        public void onLoadFinished(Loader<List<word>> loader, List<word> words) {

            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            EmptyTextView.setText("No earthquake found");

         // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
          if (words != null && !words.isEmpty()) {
            adapter.addAll(words);
          }
        }


        @Override
        public void onLoaderReset(Loader<List<word>> loader) {
        adapter.clear();
        }


}
