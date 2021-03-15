package com.example.jsonplaceholderclient.data.services;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jsonplaceholderclient.models.Album;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.GlobalValues;
import com.example.jsonplaceholderclient.utilities.ServiceResponseListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AlbumService {
    private final String endpoint = GlobalValues.baseURL + "/albums";
    private final RequestQueue requestQueue;

    public AlbumService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void getAllByUser(User user, final ServiceResponseListener<List<Album>> serviceResponseListener) {
        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        endpoint + "?userId=" + user.getId(),
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                final List<Album> albumsToReturn = new ArrayList<>();
                                final int length = response.length();
                                for (int i = 0; i < length; i++) {
                                    try {
                                        albumsToReturn.add(Album.fromJSONObject(response.getJSONObject(i)));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        serviceResponseListener.onError("Error retrieving the albums");
                                        return;
                                    }
                                }
                                serviceResponseListener.onResponse(albumsToReturn);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceResponseListener.onError("Error retrieving the albums");
                    }
                });
        requestQueue.add(request);
    }
}
