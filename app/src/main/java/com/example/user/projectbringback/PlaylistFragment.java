package com.example.user.projectbringback;


import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment {
    private List<Playlist> playlists = new ArrayList<>();


    public PlaylistFragment() {
        this.setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        final RecyclerView playlist = view.findViewById(R.id.playlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        playlist.setLayoutManager(linearLayoutManager);
        playlists.add(new Playlist("팝송", 2, null));
        playlists.add(new Playlist("노동요", 4, null));
        playlists.add(new Playlist("레드벨벳", 3, null));
        playlists.add(new Playlist("드럼", 1, null));
        final PlaylistAdapter playlistAdapter = new PlaylistAdapter(playlists, getActivity());
        playlist.setAdapter(playlistAdapter);

        playlistAdapter.setOnItemClickListener(new PlaylistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlaylistAdapter.PlaylistViewHolder holder, View view, int position) {
                Playlist item = playlists.get(position);
                loadFragment(item);
               //fragment로 화면 전환해서 플레이리스트 수정하기
            }
        });

        ImageButton mBtnAddPlaylist = view.findViewById(R.id.btnAddPlaylist);

        mBtnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPlaylistDialog();
                playlistAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void loadFragment(Playlist item) {
        String playlistName = item.getName();
        int numberOfSong = item.getNumberOfSong();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, newInstance(playlistName, numberOfSong));
        transaction.commit();
    }

    private static Fragment newInstance(String name, int numberOfSong){
        PlaylistInfoFragment fragment = new PlaylistInfoFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putInt("numberOfSong", numberOfSong);
        fragment.setArguments(args);
        return fragment;
    }


    private void AddPlaylistDialog() {
        final Dialog addPlaylistDialog = new Dialog(getContext());
        addPlaylistDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addPlaylistDialog.setContentView(R.layout.dialog_add_playlist);
        final EditText editPlaylistName = addPlaylistDialog.findViewById(R.id.editPlaylistName);
        Button btnAddPlaylist = addPlaylistDialog.findViewById(R.id.btnAddPlaylist);
        Button btnCancel = addPlaylistDialog.findViewById(R.id.btnCancel);

         btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editPlaylistName.getText().toString().trim();
                if(name.isEmpty()){
                    Toast.makeText(getContext(), "플레이리스트명을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else{
                    playlists.add(new Playlist(name, 0, null));
                    Log.d("new Playlist Info:", playlists.size()+"번째 플레이리스트명? "+playlists.get(playlists.size()-1).getName());
                    addPlaylistDialog.dismiss();
                }
            }
        });

         btnCancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 addPlaylistDialog.cancel();
             }
         });

         addPlaylistDialog.show();
    }

}
