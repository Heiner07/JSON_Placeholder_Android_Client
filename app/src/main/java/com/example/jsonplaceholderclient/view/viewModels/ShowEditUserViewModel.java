package com.example.jsonplaceholderclient.view.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jsonplaceholderclient.data.repositories.UserRepository;
import com.example.jsonplaceholderclient.data.services.AlbumService;
import com.example.jsonplaceholderclient.data.services.ToDoService;
import com.example.jsonplaceholderclient.models.Album;
import com.example.jsonplaceholderclient.models.ToDo;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.ServiceResponseListener;

import java.util.ArrayList;
import java.util.List;

public class ShowEditUserViewModel extends ViewModel {
    public enum StateEditUser {
        NO_EDITING,
        EDITING,
        EDITING_STARTED,
        ERROR_EDITING,
        EDITING_COMPLETED,
        DELETING_COMPLETED
    }

    public enum StateLoad {
        LOADING,
        LOADED,
        LOAD_ERROR
    }

    private final UserRepository userRepository;
    private final ToDoService toDoService;
    private final AlbumService albumService;

    private User userSelected;
    private MutableLiveData<List<ToDo>> toDos;
    private MutableLiveData<StateLoad> loadingToDos;
    private MutableLiveData<List<Album>> albums;
    private MutableLiveData<StateLoad> loadingAlbums;
    private MutableLiveData<StateEditUser> stateEditUser;
    private MutableLiveData<String> errorMsg;

    public ShowEditUserViewModel(
            UserRepository userRepository,
            ToDoService toDoService,
            AlbumService albumService) {
        this.toDoService = toDoService;
        this.albumService = albumService;
        this.userRepository = userRepository;
        userSelected = this.userRepository.getUserSelected();

        toDos = new MutableLiveData<>();
        loadingToDos = new MutableLiveData<>();
        loadingToDos.setValue(StateLoad.LOADING);
        albums = new MutableLiveData<>();
        loadingAlbums = new MutableLiveData<>();
        loadingAlbums.setValue(StateLoad.LOADING);

        stateEditUser = new MutableLiveData<>();
        stateEditUser.setValue(StateEditUser.NO_EDITING);
        errorMsg = new MutableLiveData<>();
        errorMsg.setValue("");
    }

    public void loadAllToDos(){
        loadingToDos.setValue(StateLoad.LOADING);
        toDos.setValue(new ArrayList<ToDo>());
        toDoService.getAllByUser(userSelected, new ServiceResponseListener<List<ToDo>>() {
            @Override
            public void onResponse(List<ToDo> value) {
                toDos.setValue(value);
                loadingToDos.setValue(StateLoad.LOADED);
            }

            @Override
            public void onError(String message) {
                errorMsg.setValue(message);
                loadingToDos.setValue(StateLoad.LOAD_ERROR);
            }
        });
    }

    private void initializeToDos(){
        if(this.toDos.getValue() == null){
            loadAllToDos();
        }
    }

    public LiveData<List<ToDo>> getAllToDos(){
        initializeToDos();
        return toDos;
    }

    public void loadAllAlbums(){
        loadingAlbums.setValue(StateLoad.LOADING);
        albums.setValue(new ArrayList<Album>());
        albumService.getAllByUser(userSelected, new ServiceResponseListener<List<Album>>() {
            @Override
            public void onResponse(List<Album> value) {
                albums.setValue(value);
                loadingAlbums.setValue(StateLoad.LOADED);
            }

            @Override
            public void onError(String message) {
                errorMsg.setValue(message);
                loadingAlbums.setValue(StateLoad.LOAD_ERROR);
            }
        });
    }

    public LiveData<StateLoad> getLoadingToDos() {
        return loadingToDos;
    }

    public MutableLiveData<StateLoad> getLoadingAlbums() {
        return loadingAlbums;
    }

    private void initializeAlbums(){
        if(this.albums.getValue() == null){
            loadAllAlbums();
        }
    }

    public LiveData<List<Album>> getAllAlbums(){
        initializeAlbums();
        return albums;
    }

    public void editUser(String name, String username, String email){
        this.errorMsg.setValue("");
        this.stateEditUser.setValue(StateEditUser.EDITING_STARTED);
        User userToEdit = userSelected.copy();
        userToEdit.setName(name);
        userToEdit.setUsername(username);
        userToEdit.setEmail(email);
        this.userRepository.update(userToEdit, new ServiceResponseListener<User>() {
            @Override
            public void onResponse(User value) {
                userSelected = value;
                stateEditUser.setValue(StateEditUser.EDITING_COMPLETED);
            }

            @Override
            public void onError(String message) {
                errorMsg.setValue(message);
                stateEditUser.setValue(StateEditUser.ERROR_EDITING);
            }
        });
    }

    public void deleteUser(){
        this.errorMsg.setValue("");
        this.stateEditUser.setValue(StateEditUser.EDITING_STARTED);
        this.userRepository.delete(userSelected, new ServiceResponseListener<User>() {
            @Override
            public void onResponse(User value) {
                stateEditUser.setValue(StateEditUser.DELETING_COMPLETED);
            }

            @Override
            public void onError(String message) {
                errorMsg.setValue(message);
                stateEditUser.setValue(StateEditUser.ERROR_EDITING);
            }
        });
    }

    public User getUserSelected() {
        return userSelected;
    }

    public LiveData<StateEditUser> getStateEditUser() {
        return stateEditUser;
    }

    public void startEditingUser(){
        stateEditUser.setValue(StateEditUser.EDITING);
    }

    public void endEditingUser(){
        stateEditUser.setValue(StateEditUser.NO_EDITING);
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }
}
