package com.example.user.projectbringback.rcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;

import java.util.List;

public class PlaylistEditAdapter extends RecyclerView.Adapter<PlaylistEditAdapter.PlaylistEditViewHolder> {
    private Context context;
    private List<Music> musicList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void OnItemClick(PlaylistEditViewHolder holder, View view, int position);
    }

    public PlaylistEditAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    public class PlaylistEditViewHolder extends RecyclerView.ViewHolder {
        public TextView songName;
        public TextView singer;
        public ImageView songImage;
        public ImageButton btnItemMove;
        OnItemClickListener listener;

        public PlaylistEditViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.textPlaylistName);
            singer = itemView.findViewById(R.id.textNumberOfSong);
            songImage = itemView.findViewById(R.id.playlistImage);
            btnItemMove = itemView.findViewById(R.id.btnDeletePlaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null)
                        listener.OnItemClick(PlaylistEditViewHolder.this, v, position);
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    @NonNull
    @Override
    public PlaylistEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.playlist, parent, false);
        return new PlaylistEditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistEditViewHolder holder, int position) {
        holder.songName.setText(musicList.get(position).getName());
        holder.singer.setText(musicList.get(position).getSinger());
//        holder.songImage.setImageDrawable();
        holder.btnItemMove.setImageDrawable(null);
        holder.setOnItemClickListener(listener);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

}
