package com.example.user.projectbringback.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.projectbringback.helper.SpaceItemDecoration;
import com.example.user.projectbringback.rcv.AlbumStorageAdapter;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.user.projectbringback.data.Music.MUSICS;

public class BrowserFragment extends Fragment {
    private List<Music> albumList = new ArrayList<>();

    public BrowserFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        RecyclerView albumView = view.findViewById(R.id.albumView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        albumView.setLayoutManager(gridLayoutManager);
        albumView.addItemDecoration(new SpaceItemDecoration(30));

        initAlbumList();
        final AlbumStorageAdapter adapter = new AlbumStorageAdapter(getActivity(), albumList);
        albumView.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view1, position) -> {
            Music item = albumList.get(position);
            Intent albumIntent = new Intent(getActivity(), AlbumActivity.class);
            albumIntent.putExtra("albumName", item.getAlbum());
            albumIntent.putExtra("singer", item.getSinger());
            albumIntent.putExtra("date", item.getDate());
            albumIntent.putExtra("genre",item.getGenre());
            albumIntent.putExtra("songName", item.getName());
            albumIntent.putExtra("trackNum", item.getTrack_num());
            albumIntent.putExtra("albumCover",item.getBitmapResource());
            startActivity(albumIntent);
        });
        return view;
    }

    private void initAlbumList(){
        albumList.addAll(Arrays.asList(MUSICS));
    }
}
