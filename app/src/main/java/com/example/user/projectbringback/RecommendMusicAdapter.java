package com.example.user.projectbringback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecommendMusicAdapter extends RecyclerView.Adapter<RecommendMusicAdapter.RecommendViewHolder> {
    private Context context;
    private List<Music> musicList;

    public RecommendMusicAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView albumName;
        TextView singer;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.imageAlbumCover);
            albumName = itemView.findViewById(R.id.textAlbumName);
            singer = itemView.findViewById(R.id.textSinger);
        }
    }

    @NonNull
    @Override
    public RecommendMusicAdapter.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.album_large, parent, false);
        return new RecommendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendMusicAdapter.RecommendViewHolder holder, int position) {
//        holder.albumCover.setImageDrawable();
        holder.albumName.setText(musicList.get(position).getAlbum());
        holder.singer.setText(musicList.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }


}
