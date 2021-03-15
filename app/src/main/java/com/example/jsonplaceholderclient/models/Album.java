package com.example.jsonplaceholderclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Album {
    private int id;
    private int userId;
    private String title;

    public Album(int id, int userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public static Album fromJSONObject(JSONObject jsonObject) throws JSONException {
        return new Album(
                (int) jsonObject.get("id"),
                (int) jsonObject.get("userId"),
                (String) jsonObject.get("title"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                '}';
    }

    public Album copy(){
        return new Album(this.id, this.userId, this.title);
    }
}
