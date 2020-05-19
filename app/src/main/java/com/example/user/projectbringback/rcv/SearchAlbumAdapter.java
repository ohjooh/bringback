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

public class SearchAlbumAdapter extends RecyclerView.Adapter<SearchAlbumAdapter.SearchAlbumViewHolder> {
    private Context context;
    private List<Music> musics;
    private List<Music> filterList = new ArrayList<>();

    public SearchAlbumAdapter(Context context, List<Music> musics) {
        this.context = context;
        this.musics = musics;
        filterList.addAll(musics);
    }

    class SearchAlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView albumImage;
        TextView albumName;
        TextView albumArtist;
        TextView albumDate;

        SearchAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            albumImage = itemView.findViewById(R.id.albumImage);
            albumName = itemView.findViewById(R.id.albumName);
            albumArtist = itemView.findViewById(R.id.albumArtist);
            albumDate = itemView.findViewById(R.id.albumDate);
        }
    }

    public void filter(String input){
        input = input.toLowerCase(Locale.getDefault());
        musics.clear();

        if(input.length() == 0){
            musics.clear();
        }else{
            for(Music music : filterList){
                String album = music.getAlbum();
                if(album.toLowerCase().contains(input)){
                    musics.add(music);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAlbumAdapter.SearchAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.search_album, parent, false);
        return new SearchAlbumViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAlbumAdapter.SearchAlbumViewHolder holder, int position) {
        Glide.with(context)
                .asDrawable()
                .load(musics.get(position).getBitmapResource())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.albumImage);
        holder.albumName.setText(musics.get(position).getAlbum());
        holder.albumArtist.setText(musics.get(position).getSinger());
        holder.albumDate.setText(musics.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }
}
