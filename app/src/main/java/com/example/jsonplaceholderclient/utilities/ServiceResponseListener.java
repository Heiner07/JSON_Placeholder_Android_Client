package com.example.jsonplaceholderclient.utilities;

public interface ServiceResponseListener<T> {
    void onResponse(T value);
    void onError(String message);
}
