package com.example.user.projectbringback.rcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchArtistAdapter extends RecyclerView.Adapter<SearchArtistAdapter.SearchArtistViewHolder> {
    private Context context;
    private List<Music> musics;
    private List<Music> filterList = new ArrayList<>();

    public SearchArtistAdapter(Context context, List<Music> musics) {
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
                String artist = music.getSinger();
                if(artist.toLowerCase().contains(input)){
                    musics.add(music);
                }
            }
        }
        notifyDataSetChanged();
    }

    class SearchArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView artistImage;
        TextView artistName;

        SearchArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistImage = itemView.findViewById(R.id.artistImage);
            artistName = itemView.findViewById(R.id.artistName);
        }
    }

    @NonNull
    @Override
    public SearchArtistAdapter.SearchArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.search_artist, parent, false);
        return new SearchArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchArtistAdapter.SearchArtistViewHolder holder, int position) {
        holder.artistImage.setImageDrawable(null);
        holder.artistName.setText(musics.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }
}
