package com.example.jsonplaceholderclient.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsonplaceholderclient.R;
import com.example.jsonplaceholderclient.models.User;
import com.example.jsonplaceholderclient.utilities.InjectorHelper;
import com.example.jsonplaceholderclient.view.adapters.SectionsPagerAdapter;
import com.example.jsonplaceholderclient.view.viewModels.ShowEditUserViewModel;
import com.google.android.material.tabs.TabLayout;

public class ShowEditUserActivity extends AppCompatActivity {
    private EditText edtName, edtUsername, edtEmail;
    private TextView textName, textUsername, textEmail;
    private Button btnSave, btnCancel, btnEdit, btnHideUserDetails, btnShowUserDetails;
    private ImageView btnDelete;
    private ProgressBar pbLoading;
    private RelativeLayout buttonsContainer, formContainer, textsContainer, editUserSection;

    private ShowEditUserViewModel showEditUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_edit_user);

        initializeUIComponents();
        bindUIEvents();

        initializeTapSection();
    }

    private void initializeUIComponents(){
        edtName = findViewById(R.id.edtName);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);

        textName = findViewById(R.id.textName);
        textUsername = findViewById(R.id.textUsername);
        textEmail = findViewById(R.id.textEmail);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnHideUserDetails = findViewById(R.id.btnHideUserDetails);
        btnShowUserDetails = findViewById(R.id.btnShowUserDetails);

        formContainer = findViewById(R.id.formContainer);
        textsContainer = findViewById(R.id.textsContainer);
        buttonsContainer = findViewById(R.id.buttonsContainer);
        editUserSection = findViewById(R.id.editUserSection);

        pbLoading = findViewById(R.id.pbLoading);
    }

    private void bindUIEvents(){
        final InjectorHelper injectorHelper = InjectorHelper.getInstance(getApplicationContext());
        showEditUserViewModel = new ViewModelProvider(
                this,
                injectorHelper.getShowEditUserViewModelFactory())
                .get(ShowEditUserViewModel.class);

        setFormFieldsValues();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditUserViewModel.editUser(
                        edtName.getText().toString(),
                        edtUsername.getText().toString(),
                        edtEmail.getText().toString());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditUserViewModel.endEditingUser();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditUserViewModel.deleteUser();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditUserViewModel.startEditingUser();
            }
        });

        btnHideUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserSection.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,220));
                editUserSection.setVisibility(View.INVISIBLE);
                btnShowUserDetails.setVisibility(View.VISIBLE);
            }
        });

        btnShowUserDetails.setVisibility(View.INVISIBLE);
        btnShowUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserSection.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                editUserSection.setVisibility(View.VISIBLE);
                btnShowUserDetails.setVisibility(View.INVISIBLE);
            }
        });

        showEditUserViewModel.getStateEditUser().observe(this, new Observer<ShowEditUserViewModel.StateEditUser>() {
            @Override
            public void onChanged(ShowEditUserViewModel.StateEditUser stateEditUser) {
                manageStateEditUser(stateEditUser);
            }
        });
    }

    private void setFormFieldsValues() {
        final User userSelected = showEditUserViewModel.getUserSelected();
        edtName.setText(userSelected.getName());
        edtUsername.setText(userSelected.getUsername());
        edtEmail.setText(userSelected.getEmail());

        textName.setText(userSelected.getName());
        textUsername.setText(userSelected.getUsername());
        textEmail.setText(userSelected.getEmail());
    }

    private void manageStateEditUser(ShowEditUserViewModel.StateEditUser stateEditUser){
        switch (stateEditUser){
            case NO_EDITING:
                formContainer.setVisibility(View.INVISIBLE);
                textsContainer.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.VISIBLE);
                btnHideUserDetails.setVisibility(View.VISIBLE);
                buttonsContainer.setVisibility(View.INVISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
                setFormFieldsValues();
                break;
            case EDITING:
                formContainer.setVisibility(View.VISIBLE);
                textsContainer.setVisibility(View.INVISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
                btnHideUserDetails.setVisibility(View.INVISIBLE);
                buttonsContainer.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
                break;
            case EDITING_STARTED:
                btnEdit.setVisibility(View.INVISIBLE);
                buttonsContainer.setVisibility(View.INVISIBLE);
                pbLoading.setVisibility(View.VISIBLE);
                break;
            case EDITING_COMPLETED:
                Toast.makeText(this, "Edited successfully", Toast.LENGTH_SHORT).show();
                showEditUserViewModel.endEditingUser();
                break;
            case ERROR_EDITING:
                Toast.makeText(this, showEditUserViewModel.getErrorMsg().getValue(), Toast.LENGTH_SHORT).show();
                btnEdit.setVisibility(View.INVISIBLE);
                buttonsContainer.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.INVISIBLE);
                break;
            case DELETING_COMPLETED:
                Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                onBackPressed();
            default:
                break;
        }
    }

    private void initializeTapSection() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}