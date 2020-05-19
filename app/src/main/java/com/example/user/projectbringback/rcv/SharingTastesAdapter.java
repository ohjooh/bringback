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
import com.example.user.projectbringback.data.Taste;

import java.util.List;

public class SharingTastesAdapter extends RecyclerView.Adapter<SharingTastesAdapter.SharingTastesViewHolder> {
    private Context context;
    private List<Taste> tastes;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SharingTastesViewHolder holder, View view, int position);
    }

    public SharingTastesAdapter(Context context, List<Taste> tastes) {
        this.context = context;
        this.tastes = tastes;
    }

    public class SharingTastesViewHolder extends RecyclerView.ViewHolder {
        ImageView tasteImage;
        public TextView tasteName;
        OnItemClickListener listener;

        SharingTastesViewHolder(@NonNull View itemView) {
            super(itemView);
            tasteImage = itemView.findViewById(R.id.tasteImage);
            tasteName = itemView.findViewById(R.id.tasteName);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null)
                    listener.onItemClick(SharingTastesViewHolder.this, v, position);
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    @NonNull
    @Override
    public SharingTastesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.card_taste, parent, false);
        return new SharingTastesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SharingTastesViewHolder holder, int position) {
        holder.tasteName.setText(tastes.get(position).getName());
        holder.setOnItemClickListener(listener);
        Glide.with(context)
                .asDrawable()
                .load(tastes.get(position).getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.tasteImage);
        holder.tasteImage.setClipToOutline(true);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return tastes.size();
    }
}
