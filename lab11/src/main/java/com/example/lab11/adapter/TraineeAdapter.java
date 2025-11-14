package com.example.lab11.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab11.R;
import com.example.lab11.model.Trainee;

import java.util.ArrayList;
import java.util.List;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.VH> {

    public interface OnItemAction {
        void onEdit(Trainee t);
        void onDelete(Trainee t);
    }

    private final List<Trainee> items = new ArrayList<>();
    private final OnItemAction listener;

    public TraineeAdapter(OnItemAction listener) {
        this.listener = listener;
    }

    public void setItems(List<Trainee> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trainee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Trainee t = items.get(position);
        holder.tvName.setText(t.getName());
        holder.tvEmail.setText(t.getEmail());
        holder.tvPhone.setText(t.getPhone());
        holder.tvPosition.setText(t.getPosition() == null ? "" : t.getPosition());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(t);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onDelete(t);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone, tvPosition;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvPosition = itemView.findViewById(R.id.tvPosition);
        }
    }
}

