package com.example.user.projectbringback.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.projectbringback.rcv.PlaylistEditAdapter;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;

import java.util.ArrayList;
import java.util.List;

public class ModifyPlaylistInfoActivity extends AppCompatActivity {
    private List<Music> musicList = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private PlaylistEditAdapter adapter;
    private Button btnDelete;
    private TextView textSelectedOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_playlist_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        textSelectedOptions = findViewById(R.id.textSelectedOptions);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setVisibility(View.GONE);
        String playlistName = getIntent().getStringExtra("name");
        int numberOfSong = getIntent().getIntExtra("numberOfSong", 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(playlistName);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        RecyclerView playlistView = findViewById(R.id.playlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        playlistView.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < numberOfSong; i++)
            musicList.add(new Music("노래제목" + (i + 1), "가수", "앨범명", "장르", "2019-09-23", i + 1));

        adapter = new PlaylistEditAdapter(this, musicList);
        playlistView.setAdapter(adapter);

        textSelectedOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedItems.size() <= 0 || mSelectedItems.size() < musicList.size()) {
                    textSelectedOptions.setText("선택 해제");
                    for (int i = 0; i < musicList.size(); i++) {
                        mSelectedItems.put(i, true);
                    }
                    btnDelete.setVisibility(View.VISIBLE);
                    showDeleteButton(btnDelete);
                } else {
                    textSelectedOptions.setText("전체 선택");
                    mSelectedItems.clear();
                    hideDeleteButton(btnDelete);
                }
            }
        });

        adapter.setOnItemClickListener(new PlaylistEditAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(PlaylistEditAdapter.PlaylistEditViewHolder holder, View view, int position) {
                if (mSelectedItems.get(position, false)) {
                    mSelectedItems.delete(position);
                    view.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    mSelectedItems.put(position, true);
                    view.setBackgroundColor(getColor(R.color.colorOpaque));
                }

                if (mSelectedItems.size() <= 0) {
                    hideDeleteButton(btnDelete);
                } else {
                    btnDelete.setVisibility(View.VISIBLE);
                    showDeleteButton(btnDelete);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedOption = textSelectedOptions.getText().toString();
                if (selectedOption.equals("선택 해제")) {
                    musicList.clear();
                    textSelectedOptions.setText("전체 선택");
                } else {
                    for (int i = 0; i < musicList.size(); i++) {
                        if (mSelectedItems.get(i)) {
                            deleteMusicList(i);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                hideDeleteButton(btnDelete);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_playlist_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.submit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteMusicList(int i) {
        musicList.remove(i);
    }

    private void showDeleteButton(Button view) {
        view.animate().translationY(0);
    }

    private void hideDeleteButton(Button view) {
        view.animate().translationY(view.getHeight());
    }
}
