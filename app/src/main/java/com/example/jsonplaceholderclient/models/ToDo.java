package com.example.jsonplaceholderclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ToDo {
    private int id;
    private int userId;
    private String title;
    private Boolean completed;

    public ToDo(int id, int userId, String title, Boolean completed) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.completed = completed;
    }

    public static ToDo fromJSONObject(JSONObject jsonObject) throws JSONException {
        return new ToDo(
                (int) jsonObject.get("id"),
                (int) jsonObject.get("userId"),
                (String) jsonObject.get("title"),
                (Boolean) jsonObject.get("completed"));
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }

    public ToDo copy(){
        return new ToDo(this.id, this.userId, this.title, this.completed);
    }
}
