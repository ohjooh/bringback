package com.example.user.projectbringback.rcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecommendMusicAdapter extends RecyclerView.Adapter<RecommendMusicAdapter.RecommendViewHolder> {
    private Context context;
    private List<Music> musicList;
    private List<Music> filterList = new ArrayList<>();

    public RecommendMusicAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
        filterList.addAll(musicList);
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView albumName;
        TextView singer;

        RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.imageAlbumCover);
            albumName = itemView.findViewById(R.id.textAlbumName);
            singer = itemView.findViewById(R.id.textSinger);
        }
    }

    public void filter(String input){
        input = input.toLowerCase(Locale.getDefault());
        musicList.clear();

        if(input.length() == 0){
            musicList.clear();
        }else{
            for(Music music : filterList){
                String genre = music.getGenre();
                if(genre.toLowerCase().contains(input)){
                    musicList.add(music);
                }
            }
        }
        notifyDataSetChanged();
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
        Glide.with(context)
                .asDrawable()
                .load(musicList.get(position).getBitmapResource())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.albumCover);
        holder.albumName.setText(musicList.get(position).getAlbum());
        holder.singer.setText(musicList.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }


}
