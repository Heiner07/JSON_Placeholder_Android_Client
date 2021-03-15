package com.example.jsonplaceholderclient.utilities;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.example.jsonplaceholderclient.data.repositories.UserRepository;
import com.example.jsonplaceholderclient.data.services.AlbumService;
import com.example.jsonplaceholderclient.data.services.ToDoService;
import com.example.jsonplaceholderclient.data.services.UserService;
import com.example.jsonplaceholderclient.view.viewModels.ShowEditUserViewModelFactory;
import com.example.jsonplaceholderclient.view.viewModels.UserViewModelFactory;

public class InjectorHelper {
    private static InjectorHelper instance;
    private UserService userService;
    private ToDoService toDoService;
    private AlbumService albumService;
    private UserRepository userRepository;

    private InjectorHelper(RequestQueue requestQueue){
        this.userService = new UserService(requestQueue);
        this.userRepository = new UserRepository(this.userService);

        this.toDoService = new ToDoService(requestQueue);
        this.albumService = new AlbumService(requestQueue);
    }

    public static InjectorHelper getInstance(Context context){
        if(instance == null){
            instance = new InjectorHelper(RequestQueueSingleton.getInstance(context).getRequestQueue());
        }
        return instance;
    }

    public UserService getUserService(){
        return this.userService;
    }

    public UserRepository getUserRepository(){
        return this.userRepository;
    }

    public ToDoService getToDoService() {
        return toDoService;
    }

    public AlbumService getAlbumService() {
        return albumService;
    }

    public UserViewModelFactory getUserViewModelFactory(){
        return new UserViewModelFactory(getUserRepository());
    }

    public ShowEditUserViewModelFactory getShowEditUserViewModelFactory(){
        return new ShowEditUserViewModelFactory(getUserRepository(), toDoService, albumService);
    }
}
