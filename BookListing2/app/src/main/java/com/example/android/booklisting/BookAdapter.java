package com.example.android.booklisting;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Activity context, ArrayList<Book> Books) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, Books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        Book w=getItem(position);

        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        /******************* Title **********************/
        TextView Title_TextView=(TextView) convertView.findViewById(R.id.book_title);
        Title_TextView.setText(w.getTitle());

        /******************* Author *****************/
        TextView Author_TextView=(TextView) convertView.findViewById(R.id.author);
        Title_TextView.setText(w.getAuthor());

        /******************** Image ******************/
        ImageView coverImage = (ImageView)convertView.findViewById(R.id.cover_image);
        Picasso.with(getContext()).load(w.getImageUrl()).into(coverImage);



        return convertView;

    }



}