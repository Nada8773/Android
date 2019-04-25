package com.example.android.miwok;

public class Word {
    private String Get_Default_Word;
    private String Get_Miwok_Word;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int Get_Icon_Image=NO_IMAGE_PROVIDED;
    private int Get_Sound;

    /* constructor take 3 input parameter used to handle that phrase activity don't need icon_image*/
    public Word(String Default_Word ,String Miwok_Word,int sound)
    {
        Get_Default_Word=Default_Word;
        Get_Miwok_Word=Miwok_Word;
        Get_Sound=sound;
    }

    /* constructor take 3 input parameter used for Number ,family , color activity */



    public Word(String Default_Word ,String Miwok_Word,int Icon_Image,int sound)
    {
        Get_Default_Word=Default_Word;
        Get_Miwok_Word=Miwok_Word;
        Get_Icon_Image=Icon_Image;
        Get_Sound=sound;
    }



    /* Get Default words */
    public String GetDefaultWord()
    {
        return Get_Default_Word;
    }

    /* Get Miwok words */
    public String GetMiwokWord()
    {
        return Get_Miwok_Word;
    }

    /* Get Icon Image */
    public int GetIconImage(){
        return Get_Icon_Image;
    }

    public boolean hasImage()
    {
        return (Get_Icon_Image!=NO_IMAGE_PROVIDED);

    }
    public int GetSound(){
        return Get_Sound;
    }
}
