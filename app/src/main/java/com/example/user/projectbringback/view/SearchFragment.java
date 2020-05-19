package com.example.user.projectbringback.view;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;
import com.example.user.projectbringback.rcv.SearchAlbumAdapter;
import com.example.user.projectbringback.rcv.SearchArtistAdapter;
import com.example.user.projectbringback.rcv.SearchSongAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.example.user.projectbringback.data.Music.MUSICS;

public class SearchFragment extends Fragment {
    private List<Music> musicList = new ArrayList<>();
    private EditText editSearch;
    private TextView textSong;
    private TextView textAlbum;
    private TextView textArtist;
    private RecyclerView songView;
    private RecyclerView albumView;
    private RecyclerView artistView;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(view);
        hideViews();

        initMusicList();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        LinearLayoutManager songLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        songView.setLayoutManager(songLayoutManager);
        final SearchSongAdapter songAdapter = new SearchSongAdapter(activity, musicList);
        songView.setAdapter(songAdapter);
        LinearLayoutManager albumLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        albumView.setLayoutManager(albumLayoutManager);
        final SearchAlbumAdapter albumAdapter = new SearchAlbumAdapter(activity, musicList);
        albumView.setAdapter(albumAdapter);
        LinearLayoutManager artistLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        artistView.setLayoutManager(artistLayoutManager);
        final SearchArtistAdapter artistAdapter = new SearchArtistAdapter(activity, musicList);
        artistView.setAdapter(artistAdapter);


        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = editSearch.getText().toString().toLowerCase(Locale.getDefault());
                if(searchText.trim().equals("")){
                    hideViews();
                }else{
                    songAdapter.filter(searchText);
                    if(songAdapter.getItemCount() == 0){
                        albumAdapter.filter(searchText);
                        if(albumAdapter.getItemCount() == 0){
                            artistAdapter.filter(searchText);
                        }
                    }
                    showViews();
                }
            }
        });

        return view;
    }


    private void initViews(View view) {
        editSearch = view.findViewById(R.id.editSearch);
        textSong = view.findViewById(R.id.textSong);
        textAlbum = view.findViewById(R.id.textAlbum);
        textArtist = view.findViewById(R.id.textArtist);
        songView = view.findViewById(R.id.songView);
        albumView = view.findViewById(R.id.albumView);
        artistView = view.findViewById(R.id.artistView);
    }

    private void hideViews() {
        textSong.setVisibility(View.GONE);
        textAlbum.setVisibility(View.GONE);
        textArtist.setVisibility(View.GONE);
        songView.setVisibility(View.GONE);
        albumView.setVisibility(View.GONE);
        artistView.setVisibility(View.GONE);
    }

    private void showViews(){
        textSong.setVisibility(View.VISIBLE);
        textAlbum.setVisibility(View.VISIBLE);
        textArtist.setVisibility(View.VISIBLE);
        songView.setVisibility(View.VISIBLE);
        albumView.setVisibility(View.VISIBLE);
        artistView.setVisibility(View.VISIBLE);
    }

    private void initMusicList(){
        musicList.addAll(Arrays.asList(MUSICS));
    }

}
