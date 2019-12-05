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

public class TasteInfoAdapter extends RecyclerView.Adapter<TasteInfoAdapter.TasteInfoViewHolder> {
    private Context context;
    private List<Music> albums;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        public void onItemClick(TasteInfoViewHolder holder, View view, int position);
    }

    public TasteInfoAdapter(Context context, List<Music> albums) {
        this.context = context;
        this.albums = albums;
    }

    public class TasteInfoViewHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView albumName;
        TextView albumSinger;
        OnItemClickListener listener;

//imageAlbumCover textAlbumName textSinger
        public TasteInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.imageAlbumCover);
            albumName = itemView.findViewById(R.id.textAlbumName);
            albumSinger = itemView.findViewById(R.id.textSinger);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null)
                        listener.onItemClick(TasteInfoViewHolder.this, v, position);
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener){this.listener = listener;}
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
//        holder.albumCover.setImageDrawable();
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
