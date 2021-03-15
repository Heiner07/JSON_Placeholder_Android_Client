package com.example.jsonplaceholderclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;

    public User(int id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public static User fromJSONObject(JSONObject jsonObject) throws JSONException {
        return new User(
                (int) jsonObject.get("id"),
                (String) jsonObject.get("name"),
                (String) jsonObject.get("username"),
                (String) jsonObject.get("email"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User copy() {
        return new User(this.id, this.name, this.username, this.email);
    }

    public JSONObject toJSONObject(){
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("name", this.name);
            jsonObject.put("username", this.username);
            jsonObject.put("email", this.email);
        } catch (JSONException e) {
            // handle exception
        }
        return jsonObject;
    }
}
