package com.example.android.miwok;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandleButton();
    }

    private void HandleButton()
    {


        /* Handle Numbers Button and use intent to open another layer */
        TextView numbers =(TextView)findViewById(R.id.numbers_TextView);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*  start Numbers activity via an intent. */
                Intent myIntent = new Intent(MainActivity.this, NumberActivity.class);
                startActivity(myIntent);
            }
        });



        /* Handle Family Button and use intent to open another layer */
        TextView family =(TextView)findViewById(R.id.family_TextView);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*  start family activity via an intent. */
                Intent myIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(myIntent);
            }
        });



        /* Handle COLOR Button and use intent to open another layer*/
        TextView color =(TextView)findViewById(R.id.color_TextView);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*  start color activity via an intent. */
                Intent myIntent = new Intent(MainActivity.this, ColorActivity.class);
                startActivity(myIntent);
            }
        });




        /* Handle phrase Button and use intent to open another layer*/
        TextView phrase =(TextView)findViewById(R.id.phrase_TextView);
        phrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*  start phrase activity via an intent. */
                Intent myIntent = new Intent(MainActivity.this, PhraseActivity.class);
                startActivity(myIntent);
            }
        });

    }





}
