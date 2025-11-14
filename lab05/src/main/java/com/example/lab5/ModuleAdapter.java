package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.lab5.R;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    private List<Module> moduleList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ModuleAdapter(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.imgModule.setImageResource(module.getImageResId());
        holder.tvTitle.setText(module.getTitle());
        holder.tvDescription.setText(module.getDescription());
        holder.tvPlatform.setText(module.getPlatform());
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        ImageView imgModule;
        TextView tvTitle, tvDescription, tvPlatform;

        public ModuleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            imgModule = itemView.findViewById(R.id.imgModule);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPlatform = itemView.findViewById(R.id.tvPlatform);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                    listener.onItemClick(getAdapterPosition());
            });
        }
    }
}
