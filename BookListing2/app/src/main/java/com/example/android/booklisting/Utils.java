package com.example.android.booklisting;

import android.text.TextUtils;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link Utils} object.
     */
    private Utils() {

    }

    /**
     * Return an {@link Book} object by parsing out information
     * about the first book from the input bookJSON string.
     */
    private static List<Book> extractBookFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();

        try {

            String title;
            String infoLink;
            String ImgLink;
            ArrayList<String>authors;

            JSONObject jsonObj = new JSONObject(bookJSON);
            JSONArray itemArray=jsonObj.getJSONArray("items");


            for(int i=0;i<itemArray.length();i++)
            {
                JSONObject book=itemArray.getJSONObject(i);

                /************** title **************/
                JSONObject VolumeInfoObj=book.getJSONObject("volumeInfo");
                if(VolumeInfoObj.has("title"))
                {
                    title = VolumeInfoObj.getString("title");
                }
                else
                {
                    title="No Title";
                }

                /**************** Buy( Info ) Link ********/
                JSONObject saleInfo = book.getJSONObject("saleInfo");
                infoLink = (String) saleInfo.get("buyLink");

                /************** Author *************/
                authors=new ArrayList<String>();

                if(VolumeInfoObj.has("authors"))
                {
                    JSONArray authorsArray = VolumeInfoObj.getJSONArray("authors");
                    for (int j=0;j<authorsArray.length();j++)
                    {
                        authors.add(authorsArray.getString(j));
                    }
                }
                else
                {
                    authors.add("Unkown Author");
                }

                /********************** Image Cover ********/
                JSONObject ImageObj=VolumeInfoObj.getJSONObject("imageLinks");
                ImgLink=ImageObj.getString("smallThumbnail");


                Book bookItem = new Book(title, authors, ImgLink, infoLink);

                    // Add the new {@link Book} to the list of booksList.
                    books.add(bookItem);

            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
        }

        // Return the list of books (booksList)
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {

        // To avoid "magic numbers" in code, all numeric values mustn't been used directly in a code
        final int READ_TIMEOUT = 10000;
        final int CONNECT_TIMEOUT = 15000;
        final int CORRECT_RESPONSE_CODE = 200;

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == CORRECT_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the USGS dataset and return a list of {@link Book} objects.
     */
    static List<Book> fetchBookData(String requestUrl) {

        final int SLEEP_TIME_MILLIS = 2000;

        // This action with sleeping is required for displaying circle progress bar
        try {
            Thread.sleep(SLEEP_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            Log.i(LOG_TAG, "HTTP request: OK");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<Book> listBooks = extractBookFromJson(jsonResponse);

        // Return the list of {@link Book}s
        return listBooks;
    }

}