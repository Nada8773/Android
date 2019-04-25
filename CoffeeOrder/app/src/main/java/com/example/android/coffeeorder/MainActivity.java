package com.example.android.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int number_of_coffee=0;
    public void submitOrder(View view) {
       /* displayPrice(number_of_coffee * 5);*/
        displayMsg();
    }

    /**
     * This method increament the quantity
     */
    public void Increament(View view) {
        if(number_of_coffee>=100)return;
        number_of_coffee++;
        display(number_of_coffee);


    }


    /**
     * This method decreament the quantity
     */
    public void Decreament(View view) {
        if(number_of_coffee<=1)return;
        number_of_coffee--;
        display(number_of_coffee);

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */

    private void displayMsg()
    {
        /* check box*/
        CheckBox check_Whipped_cream =(CheckBox) findViewById(R.id.Whipped_cream_CheckBox);
        boolean has_whipped_cream =check_Whipped_cream.isChecked();

        CheckBox check =(CheckBox) findViewById(R.id.Chocolate_CheckBox);
        boolean has_Chocolate =check.isChecked();

        int price=0;

        if(has_whipped_cream)price+=1; // add Whipped cream price increase by 1
        if(has_Chocolate)price+=2;   // add Chocolate price increase by 2

        /* edit text */

        EditText name =(EditText) findViewById(R.id.Name_EditText);
        String text_name =name.getText().toString();


        /* name & price*/
        String PriceMessage="Name: "+text_name;
               PriceMessage+="\nAdd Whipped_cream? "+has_whipped_cream;
               PriceMessage+="\nAdd Chocolate? "+has_Chocolate ;
               PriceMessage+= "\nQuentity "+number_of_coffee;
               PriceMessage+="\nTotal: "+"$"+(number_of_coffee * (5+price)) ;
               PriceMessage+="\nThank you ^_^";


               /* Send the data to E-mail */
        Email_Intent(text_name,PriceMessage);

    }

 /* send text and subject to mail Using intent */
    private void Email_Intent(String name,String info)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "order for "+name); // subject
        intent.putExtra(Intent.EXTRA_TEXT, info); // email body
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}