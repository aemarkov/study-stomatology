package com.example.stomatologyclient.models;

import com.example.stomatologyclient.api.Models;

/**
 * Created by Garrus on 15.05.2016.
 */
public abstract class NamedModel
{
    //public float Cost;
    public String Image;
    public int Id;

    public abstract String Name();

    public  float Cost()
    {
        return  0;
    }

}
