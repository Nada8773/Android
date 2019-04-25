package com.example.android.earthquake;

public class word {

    private double Get_Magnitude ;
    private String Get_Location ;
    private long Get_Date ;
    private String Get_Url ;

    word(double Magnitude,String Location,long Date,String url)
    {
        Get_Magnitude=Magnitude;
        Get_Location=Location;
        Get_Date=Date;
        Get_Url=url;
    }

    public double GetMagnitude()
    {
        return Get_Magnitude;
    }

    public String GetLocation()
    {
        return Get_Location;
    }

    public long GetDate()
    {
        return Get_Date;
    }

    public String GetUrl()
    {
        return Get_Url;
    }
}
