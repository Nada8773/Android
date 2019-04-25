package com.example.android.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Book {

    private String Get_Image;
    private String Get_Title;
    private List<String> Get_Author;
    private String Get_InfoLink;




    Book(String title , List<String>author,String image,String infoLink)
    {
        Get_Image=image;
        Get_Title=title;
        Get_Author=author;
        Get_InfoLink=infoLink;
    }

    public String getImageUrl(){
        return Get_Image;
    }
    public String  getTitle(){
        return Get_Title;
    }

    public String getAuthor(){
        String author=Get_Author.get(0);
        if(Get_Author.size()>1)
        {
            for(int i=1;i<Get_Author.size();i++)
            {
                author+="\n"+Get_Author.get(i);
            }

        }

        return author;
    }

    public String getUrlBook()
    {
        return Get_InfoLink;
    }


}
