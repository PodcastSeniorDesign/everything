package me.rooshi.podcastapp;

public class PostModel {
    public String username;
    public String dateString;
    public String postText;
    public int likeCount;

    public PostModel(String username, String dateString, String postText) {
        this.username = username;
        this.dateString = dateString;
        this.postText = postText;
        likeCount = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getDateString() {
        return dateString;
    }

    public String getPostText() {
        return postText;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
