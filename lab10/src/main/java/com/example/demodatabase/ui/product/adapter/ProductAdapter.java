package com.example.demodatabase.ui.product.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demodatabase.R;
import com.example.demodatabase.data.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.VH>{
    private final Context ctx;
    private final List<Product> data = new ArrayList<>();
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    private final NumberFormat vn = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    {
        vn.setMaximumFractionDigits(0);
        vn.setMinimumFractionDigits(0);
    }

    public interface OnItemClick { void onProductClick(Product p); }
    private OnItemClick listener;
    public void setOnItemClick(OnItemClick l) { this.listener = l; }

    public ProductAdapter(Context ctx) { this.ctx = ctx; }

    public void submit(List<Product> items) {
        data.clear();
        if (items != null) data.addAll(items);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvPrice;
        VH(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.imgProduct);
            tvName = v.findViewById(R.id.tvName);
            tvPrice = v.findViewById(R.id.tvPrice);
        }
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Product p = data.get(pos);
        h.tvName.setText(p.name == null ? "" : p.name);
        h.tvPrice.setText( vn.format(p.price) +"đ" );


        String url = p.imageUrl;
        if (url != null) {
            url = url.replace("http://localhost:5140", "https://localhost:7067");
            url = url.replace("https://localhost:7067", "https://localhost:7067"); // ép về http cho dev
        }

        Glide.with(h.img.getContext())
                .load(url)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_broken_image_24)
                .into(h.img);


        h.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(p);
        });
    }

    @Override
    public int getItemCount() { return data.size(); }


}
