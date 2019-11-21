package com.example.user.projectbringback;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlbumStorageAdapter extends RecyclerView.Adapter<AlbumStorageAdapter.AlbumStorageViewHolder> {
    private Context context;
    private List<Music> albums;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        public void onItemClick(AlbumStorageViewHolder holder, View view, int position);
    }

    public AlbumStorageAdapter(Context context, List<Music> albums) {
        this.context = context;
        this.albums = albums;
    }

    public class AlbumStorageViewHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView albumName;
        TextView singer;
        OnItemClickListener listener;

        public AlbumStorageViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.imageAlbumCover);
            albumName = itemView.findViewById(R.id.textAlbumName);
            singer = itemView.findViewById(R.id.textSinger);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!=null)
                        listener.onItemClick(AlbumStorageViewHolder.this, view, position);
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }

    @NonNull
    @Override
    public AlbumStorageAdapter.AlbumStorageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.album_storage, parent, false);
        return new AlbumStorageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumStorageAdapter.AlbumStorageViewHolder holder, int position) {
        holder.albumCover.setImageDrawable(null);
        holder.albumCover.setBackgroundColor(Color.WHITE);
        holder.albumName.setText(albums.get(position).getAlbum());
        holder.singer.setText(albums.get(position).getSinger());
        holder.setOnItemClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

}
