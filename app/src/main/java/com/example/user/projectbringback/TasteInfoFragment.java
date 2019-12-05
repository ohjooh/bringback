package com.example.user.projectbringback;


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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            Objects.requireNonNull(activity.getSupportActionBar()).setTitle(tasteName); //TODO Taste Name 받아오기
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_24px);
            RecyclerView albumView = view.findViewById(R.id.albumList);
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
            albumView.setLayoutManager(horizontalLayoutManager);

            albumList.add(new Music("Pop Song", "Pop Singer", "Pop Album", "팝", "2019", 1));
            albumList.add(new Music("Hip Song", "Hip Singer", "Hip Album", "랩/힙합", "2019", 1));
            albumList.add(new Music("Ballad Song", "Ballad Singer", "Ballad Album", "발라드", "2019", 1));
            albumList.add(new Music("Classic Song", "Classic Composer", "Classic Album", "클래식", "2019", 1));

            TasteInfoAdapter tasteInfoAdapter = new TasteInfoAdapter(getActivity(), albumList);
            albumView.setAdapter(tasteInfoAdapter);

            RecyclerView recommendedMusicView = view.findViewById(R.id.recommendedMusicView);
            LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, true);
            recommendedMusicView.setLayoutManager(verticalLayoutManager);
            RecommendMusicAdapter recommendMusicAdapter = new RecommendMusicAdapter(activity, albumList);
            recommendedMusicView.setAdapter(recommendMusicAdapter);

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
        fragmentManager.popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
