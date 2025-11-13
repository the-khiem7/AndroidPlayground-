package com.example.demodatabase.ui.product.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.demodatabase.R;
import com.example.demodatabase.data.remote.ApiClient;
import com.example.demodatabase.data.remote.dto.AddToCartRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCartSheet extends BottomSheetDialogFragment {
    private static final String ARG_ID   = "id";
    private static final String ARG_NAME = "name";
    private static final String ARG_PRICE= "price";
    private static final String ARG_IMG  = "img";

    public static AddToCartSheet newInstance(int id, String name, double price, @Nullable String imgUrl) {
        Bundle b = new Bundle();
        b.putInt(ARG_ID, id);
        b.putString(ARG_NAME, name);
        b.putDouble(ARG_PRICE, price);
        b.putString(ARG_IMG, imgUrl);
        AddToCartSheet f = new AddToCartSheet();
        f.setArguments(b);
        return f;
    }

    private int qty = 1;
    private long price;
    private NumberFormat vn;

    @Nullable
    @Override
    public View onCreateView(@NonNull android.view.LayoutInflater inflater,
                             @Nullable android.view.ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_add_to_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        vn = NumberFormat.getNumberInstance(new Locale("vi","VN"));
        vn.setMinimumFractionDigits(0); vn.setMaximumFractionDigits(0);

        int productId   = getArguments() != null ? getArguments().getInt(ARG_ID, -1) : -1;
        String name     = getArguments() != null ? getArguments().getString(ARG_NAME, "") : "";
        price           = getArguments() != null ? getArguments().getLong(ARG_PRICE, 0L) : 0L;
        String imgUrl   = getArguments() != null ? getArguments().getString(ARG_IMG, null) : null;

        ShapeableImageView imgThumb = v.findViewById(R.id.imgThumb);
        TextView tvName  = v.findViewById(R.id.tvNameBS);
        TextView tvPrice = v.findViewById(R.id.tvPriceBS);
        TextView tvQty   = v.findViewById(R.id.tvQty);
        MaterialButton btnMinus = v.findViewById(R.id.btnMinus);
        MaterialButton btnPlus  = v.findViewById(R.id.btnPlus);
        MaterialButton btnConfirm = v.findViewById(R.id.btnConfirmAdd);

        tvName.setText(name);
        tvPrice.setText( vn.format(price)+"đ");
        tvQty.setText(String.valueOf(qty));

        Glide.with(requireContext())
                .load(imgUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_broken_image_24)
                .into(imgThumb);

        btnMinus.setOnClickListener(_v -> {
            if (qty > 1) { qty--; tvQty.setText(String.valueOf(qty)); }
        });
        btnPlus.setOnClickListener(_v -> {
            qty++; tvQty.setText(String.valueOf(qty));
        });

        btnConfirm.setOnClickListener(_v -> {
            if (productId <= 0) {
                Toast.makeText(requireContext(), "Thiếu mã sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }
            setLoading(btnConfirm, true);

            // Use MockApiWrapper - automatically switches between mock and real API
            com.example.demodatabase.data.mock.MockApiWrapper.addToCart(new AddToCartRequest(productId, qty))
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> resp) {
                            setLoading(btnConfirm, false);

                            if (!resp.isSuccessful()) {
                                int code = resp.code();
                                if (code == 401) {
                                    Toast.makeText(requireContext(), "Phiên đăng nhập đã hết hạn", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(requireContext(), "Lỗi: HTTP " + code, Toast.LENGTH_LONG).show();
                                }
                                return;
                            }

                            // Server trả text/plain -> body có thể là message
                            Toast.makeText(requireContext(),
                                    "Đã thêm " + qty + " \"" + name + "\" vào giỏ", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            setLoading(btnConfirm, false);
                            Toast.makeText(requireContext(), "FAIL: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    private void setLoading(MaterialButton btn, boolean loading) {
        btn.setEnabled(!loading);
        btn.setText(loading ? "Đang thêm…" : "Thêm vào giỏ");
    }
}
