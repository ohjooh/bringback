package com.example.user.projectbringback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder> {
    private List<Music> musicList;
    private Context context;

    public MusicListAdapter(List<Music> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    public static class MusicListViewHolder extends RecyclerView.ViewHolder {
        TextView album_songName;
        TextView album_songNum;

        public MusicListViewHolder(@NonNull View itemView) {
            super(itemView);
            album_songName = itemView.findViewById(R.id.album_songName);
            album_songNum= itemView.findViewById(R.id.album_songNum);
        }
    }


    @NonNull
    @Override
    public MusicListAdapter.MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.musiclist, viewGroup, false);
        return new MusicListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListAdapter.MusicListViewHolder holder, int i) {
        holder.album_songName.setText(musicList.get(i).getName());
        holder.album_songNum.setText(String.valueOf(musicList.get(i).getTrack_num()));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

}

