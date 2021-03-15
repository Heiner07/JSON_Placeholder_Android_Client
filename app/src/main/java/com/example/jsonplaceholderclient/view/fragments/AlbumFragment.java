package com.example.jsonplaceholderclient.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonplaceholderclient.R;
import com.example.jsonplaceholderclient.models.Album;
import com.example.jsonplaceholderclient.view.adapters.AlbumListItemAdapter;
import com.example.jsonplaceholderclient.view.viewModels.ShowEditUserViewModel;

import java.util.List;

public class AlbumFragment extends Fragment {

    private ShowEditUserViewModel showEditUserViewModel;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showEditUserViewModel = new ViewModelProvider(requireActivity()).get(ShowEditUserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_album, container, false);
        final RecyclerView albumsListContainer = root.findViewById(R.id.albumsListContainer);
        final AlbumListItemAdapter adapter = new AlbumListItemAdapter();
        albumsListContainer.setAdapter(adapter);
        albumsListContainer.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        showEditUserViewModel.getAllAlbums().observe(requireActivity(), new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                adapter.setAlbums(albums);
            }
        });

        final ProgressBar pbLoading = root.findViewById(R.id.pbLoading);
        showEditUserViewModel.getLoadingAlbums().observe(requireActivity(), new Observer<ShowEditUserViewModel.StateLoad>() {
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