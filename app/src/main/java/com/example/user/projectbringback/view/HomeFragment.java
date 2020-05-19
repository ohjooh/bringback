package com.example.user.projectbringback.view;


import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.projectbringback.helper.SpaceItemDecoration;
import com.example.user.projectbringback.rcv.AlbumStorageAdapter;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.user.projectbringback.data.Music.MUSICS;

public class HomeFragment extends Fragment {
    private List<Music> albumList = new ArrayList<>();

    public HomeFragment() {
        this.setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView mProfile = view.findViewById(R.id.profile);
        ImageView btnSetting = view.findViewById(R.id.btnSetting);
        TextView mNickName = view.findViewById(R.id.nickname);
        TextView mTastes = view.findViewById(R.id.tastes);
        mProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mProfile.setClipToOutline(true);

        String userId = getArguments().getString("Id");
        String userTaste = getArguments().getString("Taste");

        mNickName.setText(userId);
        mTastes.setText(userTaste);

        final RecyclerView albums = view.findViewById(R.id.albums);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        albums.setLayoutManager(gridLayoutManager);
        albums.addItemDecoration(new SpaceItemDecoration(30));

        initAlbumList();
        final AlbumStorageAdapter adapter = new AlbumStorageAdapter(getActivity(), albumList);
        albums.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view2, position) -> {
            Music item = albumList.get(position);
            Intent albumIntent = new Intent(getActivity(), AlbumActivity.class);
            albumIntent.putExtra("albumName", item.getAlbum());
            albumIntent.putExtra("singer", item.getSinger());
            albumIntent.putExtra("date", item.getDate());
            albumIntent.putExtra("genre",item.getGenre());
            albumIntent.putExtra("songName", item.getName());
            albumIntent.putExtra("trackNum", item.getTrack_num());
            albumIntent.putExtra("albumCover", item.getBitmapResource());
            startActivity(albumIntent);
        });


        btnSetting.setOnClickListener(view1 -> {
            Intent homeSettingIntent = new Intent(getActivity(), HomeSettingActivity.class);
            homeSettingIntent.putExtra("Id", userId);
            startActivity(homeSettingIntent);
        });

        return view;
    }

    private void initAlbumList(){
        albumList.addAll(Arrays.asList(MUSICS));
    }

}
