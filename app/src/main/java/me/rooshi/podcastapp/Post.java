package me.rooshi.podcastapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post {
    public String date;
    public int likes;
    public String text;
    public String user;

    public Post() {

    }

    public Post(String date, int likes, String text, String user) {
        this.date = date;
        this.likes = likes;
        this.text = text;
        this.user  = user;
    }
}
