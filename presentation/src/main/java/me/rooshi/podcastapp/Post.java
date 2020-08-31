package me.rooshi.podcastapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {
    public String date;
    public int likes;
    public String text;
    public String user;
    public String key;

    public Post() {

    }

    public Post(String date, int likes, String text, String user) {
        this.date = date;
        this.likes = likes;
        this.text = text;
        this.user  = user;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("likes", likes);
        result.put("text", text);
        result.put("user", user);
        result.put("key", key);

        return result;
    }
}
