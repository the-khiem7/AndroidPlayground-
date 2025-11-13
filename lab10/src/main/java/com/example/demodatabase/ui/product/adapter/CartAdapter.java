package com.example.demodatabase.ui.product.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demodatabase.R;
import com.example.demodatabase.data.model.CartItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH>{

    private final List<CartItem> data = new ArrayList<>();
    private final NumberFormat vn = NumberFormat.getNumberInstance(new Locale("vi","VN"));

    public void submit(List<CartItem> items) {
        data.clear();
        if (items != null) data.addAll(items);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        ShapeableImageView imgThumb;
        TextView tvName, tvPrice, tvQty;
        MaterialButton btnMinus, btnPlus;

        VH(@NonNull View v) {
            super(v);
            imgThumb = v.findViewById(R.id.imgThumb);
            tvName   = v.findViewById(R.id.tvName);
            tvPrice  = v.findViewById(R.id.tvPrice);
            tvQty    = v.findViewById(R.id.tvQty);
            btnMinus = v.findViewById(R.id.btnMinus);
            btnPlus  = v.findViewById(R.id.btnPlus);
        }
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        CartItem item = data.get(pos);
        String name  = (item.product != null && item.product.name != null) ? item.product.name : "";
        long price   = (item.product != null) ? (long) item.product.price : 0L;
        String img   = (item.product != null) ? normalizeDevUrl(item.product.imageUrl) : null;

        h.tvName.setText(name);
        h.tvPrice.setText( vn.format(price)+"đ");
        h.tvQty.setText(String.valueOf(Math.max(1, item.quantity)));

        Glide.with(h.imgThumb.getContext())
                .load(img)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_broken_image_24)
                .into(h.imgThumb);


        h.btnMinus.setOnClickListener(v -> {
            int q = Math.max(1, item.quantity);
            if (q > 1) {
                item.quantity = q - 1;
                h.tvQty.setText(String.valueOf(item.quantity));

            } else {
                Toast.makeText(v.getContext(), "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
            }
        });

        h.btnPlus.setOnClickListener(v -> {
            int q = Math.max(1, item.quantity) + 1;
            item.quantity = q;
            h.tvQty.setText(String.valueOf(item.quantity));
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    private String normalizeDevUrl(String url) {
        if (url == null) return null;

        return url.replace("http://localhost:5140", "https://localhost:7067")
                .replace("https://localhost:7067", "https://localhost:7067");
    }
}
