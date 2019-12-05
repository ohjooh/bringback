package com.example.user.projectbringback;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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

        adapter.setOnItemClickListener(new AlbumStorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AlbumStorageAdapter.AlbumStorageViewHolder holder, View view, int position) {
                Music item = albumList.get(position);
                Intent albumIntent = new Intent(getActivity(), AlbumActivity.class);
                albumIntent.putExtra("albumName", item.getAlbum());
                albumIntent.putExtra("singer", item.getSinger());
                albumIntent.putExtra("date", item.getDate());
                albumIntent.putExtra("genre",item.getGenre());
                albumIntent.putExtra("songName", item.getName());
                albumIntent.putExtra("trackNum", item.getTrack_num());
                startActivity(albumIntent);
            }
        });
        return view;
    }

    private void initAlbumList(){
        albumList.add(new Music("Good Thing", "Zedd, Kehlani", "Good Thing", "팝", "2019-09-27", 1));
        albumList.add(new Music("Done For Me", "Charlie Puth,\nKehlani", "Voicenotes", "팝", "2018-05-11", 5));
        albumList.add(new Music("Futures", "PREP", "Futures", "팝", "2017-10-19", 1));
        albumList.add(new Music("Sunburnt Though The Glass", "PREP", "Futures", "팝", "2017-10-19", 2));
        albumList.add(new Music("Cheapest Flight", "PREP", "Futures", "팝", "2017-10-19", 3));
        albumList.add(new Music("단감", "팝콘", "1집 먹기", "팝", "2019-08-16", 1));
        albumList.add(new Music("고기", "팝콘", "1집 먹기", "팝", "2019-08-16", 2));
        albumList.add(new Music("멍멍", "호진", "1집 왈왈", "발라드", "2019-10-01", 1));
        albumList.add(new Music("컹컹", "호진", "1집 왈왈", "발라드", "2019-10-01", 2));
        albumList.add(new Music("코딩은 즐거워", "조수연", "세뇌", "국악", "2019-11-18", 1));
        albumList.add(new Music("크로크무슈", "조수연", "세뇌", "국악", "2019-11-18", 2));
        albumList.add(new Music("구글은 내 친구", "조수연", "세뇌", "국악", "2019-11-18", 3));
        albumList.add(new Music("무엇을 먹일까", "오유림", "배식", "힙합", "2019-11-15", 1));
        albumList.add(new Music("무엇을 먹을까", "오유림", "배식", "힙합", "2019-11-15", 2));
        albumList.add(new Music("무얼 해치울까", "오유림", "배식", "힙합", "2019-11-15", 3));
        albumList.add(new Music("저녁 뭐먹어", "오유림", "배식", "힙합", "2019-11-15", 4));
        albumList.add(new Music("점심 뭐먹어", "오유림", "배식", "힙합", "2019-11-15", 5));
    }
}
