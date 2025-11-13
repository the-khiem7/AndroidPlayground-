package trinc.com.lab1.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import trinc.com.lab1.R;

public class DrinkAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MenuItem> list;

    public DrinkAdapter(Context context, ArrayList<MenuItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_drink, parent, false);
        }

        MenuItem item = list.get(position);

        ImageView img = convertView.findViewById(R.id.imgDrink);
        TextView name = convertView.findViewById(R.id.tvDrinkName);
        TextView desc = convertView.findViewById(R.id.tvDrinkDesc);
        TextView price = convertView.findViewById(R.id.tvDrinkPrice);

        img.setImageResource(item.getImage());
        name.setText(item.getName());
        desc.setText(item.getDesc());
        price.setText(item.getPrice() + " VNĐ");


        return convertView;
    }
}
