package com.example.jsonplaceholderclient.data.services;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jsonplaceholderclient.models.ToDo;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.GlobalValues;
import com.example.jsonplaceholderclient.utilities.ServiceResponseListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ToDoService {
    private final String endpoint = GlobalValues.baseURL + "/todos";
    private final RequestQueue requestQueue;

    public ToDoService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void getAllByUser(User user, final ServiceResponseListener<List<ToDo>> serviceResponseListener) {
        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        endpoint + "?userId=" + user.getId(),
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                final List<ToDo> toDosToReturn = new ArrayList<>();
                                final int length = response.length();
                                for (int i = 0; i < length; i++) {
                                    try {
                                        toDosToReturn.add(ToDo.fromJSONObject(response.getJSONObject(i)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        serviceResponseListener.onError("Error retrieving the ToDos");
                                        return;
                                    }
                                }
                                serviceResponseListener.onResponse(toDosToReturn);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceResponseListener.onError("Error retrieving the ToDos");
                    }
                });
        requestQueue.add(request);
    }
}
