package com.example.user.projectbringback.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.projectbringback.rcv.MusicListAdapter;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private List<Music> musicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        TextView textAlbumName = findViewById(R.id.album_name);
        TextView textSinger = findViewById(R.id.album_singer);
        TextView textDate = findViewById(R.id.album_date);
        TextView textGenre = findViewById(R.id.album_genre);
        Button btnAdd = findViewById(R.id.add_button);
        Button btnPlay = findViewById(R.id.play_button);
        ImageView imageAlbumCover = findViewById(R.id.album_photo);
        String albumName = getIntent().getStringExtra("albumName");
        String singer = getIntent().getStringExtra("singer");
        String date = getIntent().getStringExtra("date");
        String genre = getIntent().getStringExtra("genre");
        String songName = getIntent().getStringExtra("songName");
        int trackNum = getIntent().getIntExtra("trackNum", 1);

        musicList.add(new Music(songName, singer, albumName, genre, date, trackNum));

        textAlbumName.setText(albumName);
        textSinger.setText(singer);
        textDate.setText(date);
        textGenre.setText(genre);
//        imageAlbumCover.setImageDrawable();

        RecyclerView recyclerView = findViewById(R.id.musicList);
        LinearLayoutManager layoutManager = new LinearLayoutManager (this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        MusicListAdapter adapter = new MusicListAdapter(musicList, getApplicationContext());
        recyclerView.setAdapter(adapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddPlaylistDialog();
            }
        });
    }

    private void setAddPlaylistDialog(){
        final CharSequence[] addItems = {"플레이리스트에 추가하기", "보관함에 추가하기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AlbumActivity.this, R.style.AlertDialogTheme);
        builder.setItems(addItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case 0:
                        //add playlist this song
                        Toast.makeText(AlbumActivity.this, "add playlist this song", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        break;
                    case 1:
                        //add home's saved song list
                        Toast.makeText(AlbumActivity.this, "add home's saved song list", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void albumMusic(){
        musicList.add(new Music("음악제목1", "김가수", "앨범제목1", "락", "2019.10.31", 1));
        musicList.add(new Music("음악제목2", "김가수", "앨범제목1", "락", "2019.10.31", 2));
        musicList.add(new Music("음악제목3", "김가수", "앨범제목1", "락", "2019.10.31", 3));
    }

}
