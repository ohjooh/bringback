package com.example.user.projectbringback;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private List<Music> musicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        albumMusic();

        RecyclerView recyclerView = findViewById(R.id.musicList);
        LinearLayoutManager layoutManager = new LinearLayoutManager (this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        MusicListAdapter adapter = new MusicListAdapter(musicList, getApplicationContext());
        recyclerView.setAdapter(adapter);


    }

    private void albumMusic(){
        musicList.add(new Music("음악제목1", "김가수", "앨범제목1", "락", "2019.10.31", 1));
        musicList.add(new Music("음악제목2", "김가수", "앨범제목1", "락", "2019.10.31", 2));
        musicList.add(new Music("음악제목3", "김가수", "앨범제목1", "락", "2019.10.31", 3));
    }

}
