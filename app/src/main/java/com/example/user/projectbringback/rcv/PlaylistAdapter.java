package com.example.user.projectbringback.rcv;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Playlist;

import java.text.MessageFormat;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<Playlist> playlist;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PlaylistViewHolder holder, View view, int position);
    }

    public PlaylistAdapter(List<Playlist> playlist, Context context) {
        this.playlist = playlist;
        this.context = context;
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView playlistName;
        TextView numberOfSong;
        ImageView playlistImage;
        ImageButton btnDeletePlaylist;
        OnItemClickListener listener;

        PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.textPlaylistName);
            numberOfSong = itemView.findViewById(R.id.textNumberOfSong);
            playlistImage = itemView.findViewById(R.id.playlistImage);
            btnDeletePlaylist = itemView.findViewById(R.id.btnDeletePlaylist);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(PlaylistViewHolder.this, view, position);
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    @NonNull
    @Override
    public PlaylistAdapter.PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.playlist, parent, false);
        return new PlaylistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.PlaylistViewHolder holder, final int position) {
        holder.playlistName.setText(playlist.get(position).getName());
        holder.numberOfSong.setText(MessageFormat.format("{0}곡", playlist.get(position).getNumberOfSong()));
        holder.setOnItemClickListener(listener);

        holder.btnDeletePlaylist.setOnClickListener(v -> RemovePlaylistDialog(position));
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private void RemovePlaylistDialog(final int position) {
        final Dialog removePlaylistDialog = new Dialog(context);
        removePlaylistDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        removePlaylistDialog.setContentView(R.layout.dialog_remove_playlist);
        Button btnRemovePlaylist = removePlaylistDialog.findViewById(R.id.btnRemovePlaylist);
        Button btnCancel = removePlaylistDialog.findViewById(R.id.btnCancel);

        btnRemovePlaylist.setOnClickListener(view -> {
            Toast.makeText(context, playlist.get(position).getName()+" 을(를) 삭제했습니다.", Toast.LENGTH_SHORT).show();
            playlist.remove(position);
            notifyDataSetChanged();
            removePlaylistDialog.dismiss();
        });

        btnCancel.setOnClickListener(view -> removePlaylistDialog.cancel());

        removePlaylistDialog.show();
    }
}
