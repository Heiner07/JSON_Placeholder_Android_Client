package com.example.jsonplaceholderclient.data.services;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.GlobalValues;
import com.example.jsonplaceholderclient.utilities.ServiceResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final String endpoint = GlobalValues.baseURL + "/users";
    private final RequestQueue requestQueue;

    public UserService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void getAll(final ServiceResponseListener<List<User>> serviceResponseListener) {
        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        endpoint,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                final List<User> usersToReturn = new ArrayList<>();
                                final int length = response.length();
                                for (int i = 0; i < length; i++) {
                                    try {
                                        usersToReturn.add(User.fromJSONObject(response.getJSONObject(i)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        serviceResponseListener.onError("Error retrieving the data");
                                        return;
                                    }
                                }
                                serviceResponseListener.onResponse(usersToReturn);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceResponseListener.onError("Error retrieving the data");
                    }
                });
        requestQueue.add(request);
    }

    public void update(User element, final ServiceResponseListener<User> serviceResponseListener) {
        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.PUT,
                        endpoint + "/" + element.getId(),
                        element.toJSONObject(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    final User userUpdated = User.fromJSONObject(response);
                                    serviceResponseListener.onResponse(userUpdated);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    serviceResponseListener.onError("Error updating the user");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceResponseListener.onError("Error updating the user");
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };
        requestQueue.add(request);
    }

    public void delete(final User element, final ServiceResponseListener<User> serviceResponseListener) {
        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.DELETE,
                        endpoint + "/" + element.getId(),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                serviceResponseListener.onResponse(element);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceResponseListener.onError("Error deleting the user");
                    }
                });
        requestQueue.add(request);
    }
}
