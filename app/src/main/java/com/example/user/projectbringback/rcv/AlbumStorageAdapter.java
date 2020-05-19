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

import java.util.List;

public class AlbumStorageAdapter extends RecyclerView.Adapter<AlbumStorageAdapter.AlbumStorageViewHolder> {
    private Context context;
    private List<Music> albums;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(AlbumStorageViewHolder holder, View view, int position);
    }

    public AlbumStorageAdapter(Context context, List<Music> albums) {
        this.context = context;
        this.albums = albums;
    }

    public class AlbumStorageViewHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView albumName;
        public TextView singer;
        OnItemClickListener listener;

        AlbumStorageViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.imageAlbumCover);
            albumName = itemView.findViewById(R.id.textAlbumName);
            singer = itemView.findViewById(R.id.textSinger);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(listener!=null)
                    listener.onItemClick(AlbumStorageViewHolder.this, view, position);
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
        Glide.with(context)
                .asDrawable()
                .load(albums.get(position).bitmapResource)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.albumCover);
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
