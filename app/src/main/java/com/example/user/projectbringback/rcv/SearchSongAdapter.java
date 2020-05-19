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

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.SearchSongViewHolder> {
    private Context context;
    private List<Music> musics;
    private List<Music> filterList = new ArrayList<>();

    public SearchSongAdapter(Context context, List<Music> musics) {
        this.context = context;
        this.musics = musics;
        filterList.addAll(musics);
    }

    public void filter(String input){
        input = input.toLowerCase(Locale.getDefault());
        musics.clear();

        if(input.length() == 0){
            musics.clear();
        }else{
            for(Music music : filterList){
                String name = music.getName();
                if(name.toLowerCase().contains(input)){
                    musics.add(music);
                }
            }
        }
        notifyDataSetChanged();
    }

    class SearchSongViewHolder extends RecyclerView.ViewHolder {
        ImageView songImage;
        TextView title;
        TextView artist;

        SearchSongViewHolder(@NonNull View itemView) {
            super(itemView);
            songImage = itemView.findViewById(R.id.songImage);
            title = itemView.findViewById(R.id.title);
            artist = itemView.findViewById(R.id.artist);
        }
    }

    @NonNull
    @Override
    public SearchSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.search_song, parent, false);
        return new SearchSongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSongViewHolder holder, int position) {
        Glide.with(context)
                .asDrawable()
                .load(musics.get(position).getBitmapResource())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.songImage);
        holder.title.setText(musics.get(position).getName());
        holder.artist.setText(musics.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

}
