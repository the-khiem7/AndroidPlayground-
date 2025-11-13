package trinc.com.lab1.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import trinc.com.lab1.R;

public class OrderAdapter extends ArrayAdapter<OrderItem> {
    private Context context;
    private ArrayList<OrderItem> orderList;

    public OrderAdapter(Context context, ArrayList<OrderItem> list) {
        super(context, 0, list);
        this.context = context;
        this.orderList = list;
    }
    public interface OnOrderChangeListener {
        void onOrderChanged();
    }
    private OnOrderChangeListener listener;

    public void setOnOrderChangeListener(OnOrderChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        }

        OrderItem item = orderList.get(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        ImageButton btnPlus = convertView.findViewById(R.id.btnPlus);
        ImageButton btnMinus = convertView.findViewById(R.id.btnMinus);

        tvName.setText(item.getName());
        tvQuantity.setText("x" + item.getQuantity());
        tvPrice.setText(item.getPrice() * item.getQuantity() + " VNĐ");

        btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyDataSetChanged();
            if (listener != null) listener.onOrderChanged(); // báo MainActivity
        });

        btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                orderList.remove(position);
            }
            notifyDataSetChanged();
            if (listener != null) listener.onOrderChanged(); // báo MainActivity
        });


        return convertView;
    }
}
