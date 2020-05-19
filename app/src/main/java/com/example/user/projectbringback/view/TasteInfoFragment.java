package com.example.user.projectbringback.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.rcv.RecommendMusicAdapter;
import com.example.user.projectbringback.rcv.TasteInfoAdapter;
import com.example.user.projectbringback.data.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.user.projectbringback.data.Music.MUSICS;

public class TasteInfoFragment extends Fragment {
    private List<Music> albumList = new ArrayList<>();

    public TasteInfoFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taste_info, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && getArguments() != null) {
            String tasteName = getArguments().getString("tasteName");
            activity.setSupportActionBar(toolbar);
            Objects.requireNonNull(activity.getSupportActionBar()).setTitle(tasteName);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_24px);
            RecyclerView albumView = view.findViewById(R.id.albumList);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
            albumView.setLayoutManager(horizontalLayoutManager);

            initAlbumList();
            TasteInfoAdapter tasteInfoAdapter = new TasteInfoAdapter(getActivity(), albumList);
            albumView.setAdapter(tasteInfoAdapter);
            tasteInfoAdapter.filter(tasteName);

            RecyclerView recommendedMusicView = view.findViewById(R.id.recommendedMusicView);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, true);
            recommendedMusicView.setLayoutManager(verticalLayoutManager);
            RecommendMusicAdapter recommendMusicAdapter = new RecommendMusicAdapter(activity, albumList);
            recommendedMusicView.setAdapter(recommendMusicAdapter);
            recommendMusicAdapter.filter(tasteName);

        }
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            loadFragment(new SharingTastesFragment());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        Objects.requireNonNull(fragmentManager).popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void initAlbumList(){
        albumList.addAll(Arrays.asList(MUSICS));
    }
}
