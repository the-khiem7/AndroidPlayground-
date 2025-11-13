package trinc.com.lab1.lab3;


import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import trinc.com.lab1.R;

public class SachAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Sach> sachList;

    public SachAdapter(Context context, int layout, List<Sach> sachList) {
        this.context = context;
        this.layout = layout;
        this.sachList = sachList;
    }

    @Override
    public int getCount() {
        return sachList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        TextView txtTen = view.findViewById(R.id.textviewTen);
        TextView txtMota = view.findViewById(R.id.textviewMoTa);
        ImageView imageHinh = view.findViewById(R.id.imageviewHinh);

        Sach sach = sachList.get(i);
        txtTen.setText(sach.getTen());
        txtMota.setText(sach.getTheLoai());
        imageHinh.setImageResource(sach.getHinh());
        return view;
    }
}

