package com.example.mahmoud.movieapp.MovieDetail.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mahmoud on 9/7/2016.
 */

public class Review implements Serializable{
    @SerializedName("content")
    private String content;
    @SerializedName("name")
    private String name;

    public Review(String name, String content) {
        this.content = content;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
}
