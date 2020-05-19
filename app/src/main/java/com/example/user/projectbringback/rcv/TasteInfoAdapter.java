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

public class TasteInfoAdapter extends RecyclerView.Adapter<TasteInfoAdapter.TasteInfoViewHolder> {
    private Context context;
    private List<Music> albums;
    private OnItemClickListener listener;
    private List<Music> filterList = new ArrayList<>();

    public interface OnItemClickListener{
        void onItemClick(TasteInfoViewHolder holder, View view, int position);
    }

    public TasteInfoAdapter(Context context, List<Music> albums) {
        this.context = context;
        this.albums = albums;
        filterList.addAll(albums);
    }

    public class TasteInfoViewHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView albumName;
        TextView albumSinger;
        OnItemClickListener listener;

        TasteInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.imageAlbumCover);
            albumName = itemView.findViewById(R.id.textAlbumName);
            albumSinger = itemView.findViewById(R.id.textSinger);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener != null)
                    listener.onItemClick(TasteInfoViewHolder.this, v, position);
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener){this.listener = listener;}
    }

    public void filter(String input){
        input = input.toLowerCase(Locale.getDefault());
        albums.clear();

        if(input.length() == 0){
            albums.clear();
        }else{
            for(Music music : filterList){
                String genre = music.getGenre();
                if(genre.toLowerCase().contains(input)){
                    albums.add(music);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TasteInfoAdapter.TasteInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.album_small, parent, false);
        return new TasteInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TasteInfoAdapter.TasteInfoViewHolder holder, int position) {
        Glide.with(context)
                .asDrawable()
                .load(albums.get(position).bitmapResource)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.albumCover);
        holder.albumName.setText(albums.get(position).getAlbum());
        holder.albumSinger.setText(albums.get(position).getSinger());
        holder.setOnItemClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener){this.listener = listener;}

    @Override
    public int getItemCount() {
        return albums.size();
    }

}
