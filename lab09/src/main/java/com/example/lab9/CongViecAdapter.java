package com.example.lab9;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CongViecAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CongViec> list;
    private LayoutInflater inflater;

    public interface ItemListener {
        void onEdit(CongViec cv);
        void onDelete(CongViec cv);
    }

    private ItemListener listener;

    public CongViecAdapter(Context context, ArrayList<CongViec> list, ItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return list.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dong_cong_viec, parent, false);
            h = new ViewHolder();
            h.tvTen = convertView.findViewById(R.id.tvTen);
            h.tvNoiDung = convertView.findViewById(R.id.tvNoiDung);
            h.btnSua = convertView.findViewById(R.id.btnSua);
            h.btnXoa = convertView.findViewById(R.id.btnXoa);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        final CongViec cv = list.get(position);
        h.tvTen.setText(cv.getTen());
        h.tvNoiDung.setText(cv.getNoiDung());

        // Nút sửa
        h.btnSua.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(cv);
        });

        // Nút xóa
        h.btnXoa.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa công việc")
                    .setMessage("Bạn có chắc muốn xóa '" + cv.getTen() + "' không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        if (listener != null) listener.onDelete(cv);
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvTen, tvNoiDung;
        Button btnSua, btnXoa;
    }


    public void setList(ArrayList<CongViec> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

}