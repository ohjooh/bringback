package com.example.user.projectbringback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PlaylistInfoAdapter extends RecyclerView.Adapter<PlaylistInfoAdapter.PlaylistInfoViewHolder> implements PlaylistItemTouchHelper.OnItemMoveListener {
    private List<Music> musicList;
    private Context context;
    private OnItemClickListener listener;
    private ArrayList<Music> filterList;

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(fromPosition < toPosition){
            for(int i=fromPosition;i<toPosition;i++){
                Collections.swap(musicList, i, i+1);
            }
        }else{
            for(int i=fromPosition;i>toPosition;i--){
                Collections.swap(musicList, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public static interface OnItemClickListener {
        public void onItemClick(PlaylistInfoViewHolder holder, View view, int position);
    }

    public PlaylistInfoAdapter(List<Music> musicList, Context context) {
        this.musicList = musicList;
        this.context = context;
    }

    public void filter(String input){
        input = input.toLowerCase(Locale.getDefault());
        musicList.clear();
        if(input.length() == 0){
//            musicList.addAll();
        }
    }

    public static class PlaylistInfoViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView singer;
        ImageView songImage;
        ImageButton btnItemMove;
        OnItemClickListener listener;

        public PlaylistInfoViewHolder(@NonNull final View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.textPlaylistName);
            singer = itemView.findViewById(R.id.textNumberOfSong);
            songImage = itemView.findViewById(R.id.playlistImage);
            btnItemMove = itemView.findViewById(R.id.btnDeletePlaylist);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    @NonNull
    @Override
    public PlaylistInfoAdapter.PlaylistInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.playlist, parent, false);
        return new PlaylistInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistInfoAdapter.PlaylistInfoViewHolder holder, int position) {
        holder.songName.setText(musicList.get(position).getName());
        holder.singer.setText(musicList.get(position).getSinger());
        holder.singer.setTextColor(Color.WHITE);
        holder.songImage.setImageDrawable(null);
        holder.btnItemMove.setImageResource(R.drawable.ic_sort);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

}
