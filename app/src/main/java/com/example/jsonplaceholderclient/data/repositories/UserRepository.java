package com.example.jsonplaceholderclient.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jsonplaceholderclient.data.services.UserService;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.ServiceResponseListener;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private UserService userService;
    private User userSelected;
    private MutableLiveData<List<User>> users;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<Boolean> loading;

    public UserRepository(UserService userService){
        this.userService = userService;
        this.users = new MutableLiveData<>();
        this.errorMsg = new MutableLiveData<>();
        this.errorMsg.setValue("");
        this.loading = new MutableLiveData<>();
        this.loading.setValue(false);
    }

    private void initializeUsers(){
        if(this.users.getValue() == null){
            loadAll();
        }
    }

    public LiveData<List<User>> getAll(){
        initializeUsers();
        return users;
    }

    public void loadAll(){
        loading.setValue(true);
        users.setValue(new ArrayList<User>());
        userService.getAll(new ServiceResponseListener<List<User>>() {
            @Override
            public void onResponse(List<User> value) {
                users.setValue(value);
                loading.setValue(false);
            }

            @Override
            public void onError(String message) {
                errorMsg.setValue(message);
                loading.setValue(false);
            }
        });
    }

    public void update(final User user, final ServiceResponseListener<User> userServiceResponseListener){
        this.userService.update(user, new ServiceResponseListener<User>() {
            @Override
            public void onResponse(User value) {
                final List<User> tempList = users.getValue();
                if(value != null && tempList != null){
                    final int size = tempList.size();
                    for(int i = 0; i < size; i++){
                        if(tempList.get(i).getId() == user.getId()){
                            tempList.set(i, value);
                            users.setValue(tempList);
                            break;
                        }
                    }
                    userServiceResponseListener.onResponse(value);
                    return;
                }
                userServiceResponseListener.onResponse(null);
            }

            @Override
            public void onError(String message) {
                userServiceResponseListener.onError(message);
            }
        });
    }

    public void delete(final User user, final ServiceResponseListener<User> userServiceResponseListener){
        this.userService.delete(user, new ServiceResponseListener<User>() {
            @Override
            public void onResponse(User value) {
                final List<User> tempList = users.getValue();
                if(value != null && tempList != null){
                    final int size = tempList.size();
                    for(int i = 0; i < size; i++){
                        if(tempList.get(i).getId() == user.getId()){
                            tempList.remove(i);
                            users.setValue(tempList);
                            break;
                        }
                    }
                    userServiceResponseListener.onResponse(value);
                    return;
                }
                userServiceResponseListener.onResponse(null);
            }

            @Override
            public void onError(String message) {
                userServiceResponseListener.onError(message);
            }
        });
    }

    public LiveData<String> getErrorMsg(){
        return this.errorMsg;
    }

    public LiveData<Boolean> isLoading(){
        return this.loading;
    }

    public User getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(User userSelected) {
        this.userSelected = userSelected.copy();
    }
}
