package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int SetColor;
    public  WordAdapter(Context context, ArrayList<Word> words,int color)
    {

        super(context,0,words);
        SetColor=color;

    }

    /* getView() is the method that returns the actual view used
                 as a row within the ListView at a particular position*/

    /*
        position -> the position in the list of data this layout should present
        converView -> recycle view that should populated
        parent  ->the list item will added to it as a child

     */
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        View ListItemView= convertView;

        if(ListItemView==null) // if there's no view can reuse
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word CurrentWord=getItem(position); // Get the Word object located at this position


        // Find the TextView in the list_item.xml layout
        TextView Miwok_TextView =(TextView) ListItemView.findViewById(R.id.Miwok_TextView);
        Miwok_TextView.setText(CurrentWord.GetMiwokWord());


        TextView Default_TextView =(TextView) ListItemView.findViewById(R.id.Default_TextView);
        Default_TextView.setText(CurrentWord.GetDefaultWord());


        // find the imageView
        ImageView Icon_ImageView =(ImageView) ListItemView.findViewById(R.id.Icon_ImageView);

        if(CurrentWord.hasImage())
        {
            Icon_ImageView.setImageResource(CurrentWord.GetIconImage());
            Icon_ImageView.setVisibility(View.VISIBLE);
        }
        else  // hide Image View
        {
            Icon_ImageView.setVisibility(View.GONE);
        }


        View textContainer = ListItemView.findViewById(R.id.Background_TextView);
        // ContextCompat-> class && getColor()-> method return the color that the resource ID maps to
        // getContext()->method Returns the context the view is currently running in
        int color = ContextCompat.getColor(getContext(), SetColor);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);



        return ListItemView;

    }
}
