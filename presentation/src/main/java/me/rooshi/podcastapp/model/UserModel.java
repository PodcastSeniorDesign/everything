package me.rooshi.podcastapp.model;


import java.util.HashMap;
import java.util.Map;

public class UserModel {
    private String uid;
    public String name;
    public String email;
    public String profilePictureUri;

    public UserModel() {}

    public UserModel(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("email", email);
        result.put("profilePictureUri", profilePictureUri);

        return result;
    }
}
