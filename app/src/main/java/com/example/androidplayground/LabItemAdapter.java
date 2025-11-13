package com.example.androidplayground;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidplayground.databinding.ItemLabBinding;

import java.util.List;

class LabItemAdapter extends RecyclerView.Adapter<LabItemAdapter.LabViewHolder> {

    interface OnLabClickListener {
        void onLabClick(LabItem item);
    }

    private final List<LabItem> items;
    private final OnLabClickListener listener;

    LabItemAdapter(List<LabItem> items, OnLabClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemLabBinding binding = ItemLabBinding.inflate(inflater, parent, false);
        return new LabViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabViewHolder holder, int position) {
        LabItem item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class LabViewHolder extends RecyclerView.ViewHolder {
        private final ItemLabBinding binding;

        LabViewHolder(ItemLabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(LabItem item, OnLabClickListener listener) {
            binding.tvLabTitle.setText(item.getTitle());
            binding.tvLabDescription.setText(item.getDescription());
            binding.tvLabBadge.setText(item.getBadge());

            int accentColor = ContextCompat.getColor(binding.getRoot().getContext(), item.getAccentColorRes());
            binding.stripAccent.setBackgroundColor(accentColor);
            ViewCompat.setBackgroundTintList(binding.tvLabBadge, ColorStateList.valueOf(accentColor));
            binding.ivArrow.setImageTintList(ColorStateList.valueOf(accentColor));
            binding.getRoot().setRippleColor(ColorStateList.valueOf(accentColor));

            binding.getRoot().setOnClickListener(v -> listener.onLabClick(item));
        }
    }
}
