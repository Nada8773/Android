package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import static android.view.View.GONE;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private BookAdapter adapter ;
    private SearchView searchView;
    private TextView emptyView;
    private String mUrlRequestGoogleBooks="";
    boolean Net_isConnected;


    public static final String Book_Listing_URL=
            "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        check_net_connection(connMgr);
        /**************************** Set List Adapter ********************************************/

        adapter=new BookAdapter(this,new ArrayList<Book>());
        ListView listview =(ListView)findViewById(R.id.list);
        listview.setAdapter(adapter);

        /******************************************************************************************/


        /************ Empty View  & loading Spinner  & search View*/
        emptyView =(TextView) findViewById(R.id.empty_view);
        listview.setEmptyView(emptyView);
        View loadingIndicator = findViewById(R.id.loading_spinner);

        searchView = (SearchView) findViewById(R.id.search_view_field); // inititate a search view
        searchView.onActionViewExpanded();
        searchView.setIconified(true);

        /******************* check net connection and set data ********************/

        if(Net_isConnected) {
            getLoaderManager().initLoader(1, null, this);
        }
        else
        {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyView.setText("no internet connection");

        }
        /*************************************************************************************************/



        /************************** Handle Search & Button View for search *************************/

        Button searchButton = (Button) findViewById(R.id.search_button); // Search button



        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                check_net_connection(connMgr);
                if(Net_isConnected) {

                    String searchInput = searchView.getQuery().toString(); // get the query string currently in the text field
                    if (searchInput.contains(" ")) {
                        searchInput = searchInput.replace(" ", "+");
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(Book_Listing_URL).append(searchInput);
                    mUrlRequestGoogleBooks=sb.toString();
                    RestartLoader();
                }
                else
                {
                    adapter.clear();
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("No Internet");
                }


            }

        });

        /********************************************************************************************************/

        /******************************** Handle Press the List ****************************************/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Get the data item for this position
                Book w = adapter.getItem(position);
                //get url
                assert w != null;
                Uri url = Uri.parse(w.getUrlBook());
                Intent web = new Intent(Intent.ACTION_VIEW, url);
                startActivity(web);

            }
        });
        /********************************************************************************************************/



    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i,  Bundle bundle) {

        String searchInput = searchView.getQuery().toString(); // get the query string currently in the text field
        if (searchInput.contains(" ")) {
            searchInput = searchInput.replace(" ", "+");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Book_Listing_URL).append(searchInput);
        mUrlRequestGoogleBooks=sb.toString();

        return new BookLoader(this,mUrlRequestGoogleBooks);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> words) {
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);
        //emptyView.setText("No Books found");

        adapter.clear();

        if (words != null && !words.isEmpty()) {
            adapter.addAll(words);
        }
        else
        {
            emptyView.setText("No Book found");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        adapter.clear();

    }



    public void RestartLoader() {
          emptyView.setVisibility(View.GONE);
         View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.VISIBLE);
        getLoaderManager().restartLoader(1, null, MainActivity.this);
    }


    public void check_net_connection(ConnectivityManager connMgr)
    {

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data

        if (networkInfo != null && networkInfo.isConnected()) {
            Net_isConnected=true;

        }
        else {
            Net_isConnected=false;

        }
    }


}
