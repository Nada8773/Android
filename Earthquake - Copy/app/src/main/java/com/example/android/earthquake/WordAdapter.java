package com.example.android.earthquake;

import android.content.Context;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;



public class WordAdapter extends ArrayAdapter<word> {

        public WordAdapter(Context context, ArrayList<word> words) {
            super(context, 0, words);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            word w = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            View ListItemView= convertView;
            if (ListItemView == null) {
                ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

           /******************************************* Magnitude ************************/
            // Lookup view for data population
            TextView Magnitude = (TextView) ListItemView.findViewById(R.id.Magnitude_TextView);
            /** put the magnitude in this form 0:0
             * format() method takes a double value as input*/
            DecimalFormat formatter = new DecimalFormat("0.0");
            String Mag_DecimalFormat= formatter.format(w.GetMagnitude());
            Magnitude.setText(Mag_DecimalFormat);

            /******************************************* Magnitude color circle ************************/

            int Mag_CircleColor;
            // Set the proper background color on the magnitude circle.
            // Fetch the background from the TextView, which is a GradientDrawable.
            /**
             * GraidentDrawable which is set on the background of the magnitude TextView.
             *                  And there you go! The proper color is automatically set on the magnitude circle in the UI!
             */
            GradientDrawable magnitudeCircle = (GradientDrawable) Magnitude.getBackground();

            // Get the appropriate background color based on the current earthquake magnitude
            /**Math.floor()
             *   finding the closest integer less than the decimal value
             * ContextCompat getColor()
             *       to convert the color resource ID into an actual integer color value,
             *       and return the result as the return value
             */
            int magnitudeFloor = (int) Math.floor(w.GetMagnitude());
            switch (magnitudeFloor)
            {
                case 0:
                case 1:
                    Mag_CircleColor = R.color.magnitude1;
                    break;
                case 2:
                    Mag_CircleColor = R.color.magnitude2;
                    break;
                case 3:
                    Mag_CircleColor = R.color.magnitude3;
                    break;
                case 4:
                    Mag_CircleColor = R.color.magnitude4;
                    break;
                case 5:
                    Mag_CircleColor = R.color.magnitude5;
                    break;
                case 6:
                    Mag_CircleColor = R.color.magnitude6;
                    break;
                case 7:
                    Mag_CircleColor = R.color.magnitude7;
                    break;
                case 8:
                    Mag_CircleColor = R.color.magnitude8;
                    break;
                case 9:
                    Mag_CircleColor = R.color.magnitude9;
                    break;
                default:
                    Mag_CircleColor = R.color.magnitude10plus;
                    break;
            }

            int magnitudeColor = ContextCompat.getColor(getContext(), Mag_CircleColor);

            // Set the color on the magnitude circle
            magnitudeCircle.setColor(magnitudeColor);


            /******************************************* Location ************************/

            TextView PrimaryLocation = (TextView) ListItemView.findViewById(R.id.PrimaryLocation_TextView);
            TextView OffsetLocation = (TextView) ListItemView.findViewById(R.id.OffsetLocation_TextView);

            if(w.GetLocation().contains("of")) {
                /** Split location into 2 text view  for example “74km NW of Rumoi, Japan*/
                String[] separated = w.GetLocation().split("of");
                /** Primary location  ->  Rumoi, Japan*/
                PrimaryLocation.setText(separated[1]);
                /** Offset location  -> 74km NW*/
                OffsetLocation.setText(separated[0] + "of");
            }
            else
            {
                OffsetLocation.setText("Near the");
                PrimaryLocation.setText(w.GetLocation());
            }

            /******************************************* Date ************************/


            Date dateObject = new Date(w.GetDate());

            // convert time unix to date  -> Mar 3, 1984
            /**
             * SimpleDateFormat
             *    allows you to start by choosing any user-defined patterns for date-time formatting.
             * DateFormat
             *    is an abstract class extand from format class for date/time formatting subclasses
             *    which formats and parses dates or time in a language-independent manner.
             *    format() constructor
             */
            TextView Date_textView = (TextView) ListItemView.findViewById(R.id.Date_TextView);
            SimpleDateFormat DateFormatter = new SimpleDateFormat("LLL dd, yyyy");
            String DateToDisplay = DateFormatter.format(dateObject);
            Date_textView.setText(DateToDisplay);

            /******************************************* Time ************************/

            // convert time unix to time  -> 3:25PM
            TextView Time_textView = (TextView) ListItemView.findViewById(R.id.Time_TextView);
            SimpleDateFormat TimeFormatter = new SimpleDateFormat("h:mm a");
            String TimeToDisplay = TimeFormatter.format(dateObject);
            Time_textView.setText(TimeToDisplay );



            /******************************************* return ************************/



            // Return the completed view to render on screen
            return ListItemView;
        }

}
