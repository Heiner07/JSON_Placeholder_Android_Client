package com.example.jsonplaceholderclient.view.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jsonplaceholderclient.data.repositories.UserRepository;
import com.example.jsonplaceholderclient.models.User;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<List<User>> getUsers(){
        return userRepository.getAll();
    }

    public LiveData<Boolean> isLoading(){
        return userRepository.isLoading();
    }

    public LiveData<String> getErrorMessage(){
        return userRepository.getErrorMsg();
    }

    public void setUserSelected(User user){
        this.userRepository.setUserSelected(user);
    }
}
