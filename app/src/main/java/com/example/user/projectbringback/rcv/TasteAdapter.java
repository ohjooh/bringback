package com.example.user.projectbringback.rcv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.projectbringback.R;

import java.util.List;

public class TasteAdapter extends RecyclerView.Adapter<TasteAdapter.TasteViewHolder> {
    private Context context;
    private List<String> tastes;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TasteViewHolder holder, View view, int position);
    }

    public TasteAdapter(Context context, List<String> taste) {
        this.context = context;
        this.tastes = taste;
    }

    public class TasteViewHolder extends RecyclerView.ViewHolder {
        public TextView tasteName;
        OnItemClickListener listener;

        TasteViewHolder(@NonNull View itemView) {
            super(itemView);
            tasteName = itemView.findViewById(R.id.tasteName);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null)
                    listener.onItemClick(TasteViewHolder.this, v, position);
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }

    @NonNull
    @Override
    public TasteAdapter.TasteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.taste, parent, false);
        return new TasteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TasteAdapter.TasteViewHolder holder, int position) {
        holder.tasteName.setText(tastes.get(position));
        holder.setOnItemClickListener(listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return tastes.size();
    }

}
