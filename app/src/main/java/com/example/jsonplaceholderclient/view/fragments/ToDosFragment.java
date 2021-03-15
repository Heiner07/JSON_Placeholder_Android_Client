package com.example.jsonplaceholderclient.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jsonplaceholderclient.R;
import com.example.jsonplaceholderclient.models.ToDo;
import com.example.jsonplaceholderclient.view.adapters.ToDoListItemAdapter;
import com.example.jsonplaceholderclient.view.viewModels.ShowEditUserViewModel;

import java.util.List;

public class ToDosFragment extends Fragment {

    private ShowEditUserViewModel showEditUserViewModel;

    public static ToDosFragment newInstance() {
        return new ToDosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showEditUserViewModel = new ViewModelProvider(requireActivity()).get(ShowEditUserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_to_dos, container, false);
        final RecyclerView toDosListContainer = root.findViewById(R.id.toDosListContainer);
        final ToDoListItemAdapter adapter = new ToDoListItemAdapter();
        toDosListContainer.setAdapter(adapter);
        toDosListContainer.setLayoutManager(new LinearLayoutManager(this.getContext()));
        showEditUserViewModel.getAllToDos().observe(requireActivity(), new Observer<List<ToDo>>() {
            @Override
            public void onChanged(List<ToDo> toDos) {
                adapter.setToDos(toDos);
            }
        });

        final ProgressBar pbLoading = root.findViewById(R.id.pbLoading);
        showEditUserViewModel.getLoadingToDos().observe(requireActivity(), new Observer<ShowEditUserViewModel.StateLoad>() {
            @Override
            public void onChanged(ShowEditUserViewModel.StateLoad stateLoad) {
                if(stateLoad == ShowEditUserViewModel.StateLoad.LOADING){
                    pbLoading.setVisibility(View.VISIBLE);
                }else{
                    pbLoading.setVisibility(View.INVISIBLE);
                }
            }
        });

        return root;
    }
}