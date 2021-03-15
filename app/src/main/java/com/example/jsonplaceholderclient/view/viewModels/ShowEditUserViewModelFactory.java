package com.example.jsonplaceholderclient.view.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.jsonplaceholderclient.data.repositories.UserRepository;
import com.example.jsonplaceholderclient.data.services.AlbumService;
import com.example.jsonplaceholderclient.data.services.ToDoService;

public class ShowEditUserViewModelFactory implements ViewModelProvider.Factory {
    private final UserRepository userRepository;
    private final ToDoService toDoService;
    private final AlbumService albumService;

    public ShowEditUserViewModelFactory(
            UserRepository userRepository,
            ToDoService toDoService,
            AlbumService albumService) {
        this.userRepository = userRepository;
        this.toDoService = toDoService;
        this.albumService = albumService;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShowEditUserViewModel(userRepository, toDoService, albumService);
    }
}
