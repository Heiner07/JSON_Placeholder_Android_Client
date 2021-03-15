package com.example.jsonplaceholderclient.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jsonplaceholderclient.R;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.InjectorHelper;
import com.example.jsonplaceholderclient.utilities.OnUserClick;
import com.example.jsonplaceholderclient.view.adapters.UserListItemAdapter;
import com.example.jsonplaceholderclient.view.viewModels.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView userListContainer;
    private ProgressBar pbLoading;
    private TextView textErrorMsg;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIComponents();
        bindUIEvents();
    }

    private void initializeUIComponents(){
        userListContainer = findViewById(R.id.userListContainer);
        pbLoading = findViewById(R.id.pbLoading);
        textErrorMsg = findViewById(R.id.textErrorMsg);
    }

    private void bindUIEvents(){
        InjectorHelper injectorHelper = InjectorHelper.getInstance(getApplicationContext());
        userViewModel = new ViewModelProvider(
                this,
                injectorHelper.getUserViewModelFactory())
                .get(UserViewModel.class);

        final UserListItemAdapter adapter = new UserListItemAdapter(new OnUserClick() {
            @Override
            public void onClick(User user) {
                userViewModel.setUserSelected(user);
                Intent intent = new Intent(MainActivity.this, ShowEditUserActivity.class);
                startActivity(intent);
            }
        });
        userListContainer.setAdapter(adapter);
        userListContainer.setLayoutManager(new LinearLayoutManager(this));

        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsers(users);
            }
        });

        userViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    userListContainer.setVisibility(View.INVISIBLE);
                    pbLoading.setVisibility(View.VISIBLE);
                }else{
                    userListContainer.setVisibility(View.VISIBLE);
                    pbLoading.setVisibility(View.INVISIBLE);
                }
            }
        });

        userViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.isEmpty()){
                    textErrorMsg.setVisibility(View.INVISIBLE);
                }else{
                    textErrorMsg.setVisibility(View.VISIBLE);
                }
                textErrorMsg.setText(s);
            }
        });
    }
}