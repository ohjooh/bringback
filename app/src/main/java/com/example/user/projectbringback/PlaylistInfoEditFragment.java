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

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//TODO Edit Playlist 화면을 Activity로 변경하기. 이 코드는 Activity로 옮긴 후 삭제하기
public class PlaylistInfoEditFragment extends Fragment {
    private List<Music> musicList = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private PlaylistEditAdapter adapter;


    public PlaylistInfoEditFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_info_edit, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && getArguments() != null) {
            String playlistName = getArguments().getString("name");
            int numberOfSong = getArguments().getInt("numberOfSong");
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setTitle(playlistName);
            activity.getSupportActionBar().setHomeButtonEnabled(false);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            RecyclerView playlistView = view.findViewById(R.id.playlist);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
            playlistView.setLayoutManager(linearLayoutManager);
            for (int i = 0; i < numberOfSong; i++)
                musicList.add(new Music("노래제목"+(i+1), "가수", "앨범명", "장르", "2019-09-23", i + 1));

            adapter = new PlaylistEditAdapter(activity, musicList);
            playlistView.setAdapter(adapter);

            adapter.setOnItemClickListener(new PlaylistEditAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(PlaylistEditAdapter.PlaylistEditViewHolder holder, View view, int position) {
                    if (mSelectedItems.get(position, false)) {
                        mSelectedItems.delete(position);
                    } else {
                        mSelectedItems.put(position, true);
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_playlist_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.submit) {
            if (mSelectedItems.size() == 0) {
                loadFragment(new PlaylistInfoFragment());
            } else {
                for(int i=0;i<musicList.size();i++){
                    if(mSelectedItems.get(i)){
                        deleteMusicList(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteMusicList(int i) {
        musicList.remove(i);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


}
