package com.example.lab12.ui.dashboard;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lab12.R;
import com.example.lab12.databinding.FragmentDashBoardBinding;
import com.example.lab12.ui.viewmodel.SharedViewModel;

public class DashBoardFragment extends Fragment {

    private DashBoardViewModel mViewModel;
    private FragmentDashBoardBinding binding;
    private SharedViewModel sharedViewModel;
    private EditText etNum1, etNum2;

    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashBoardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        etNum1 = root.findViewById(R.id.etNum1);
        etNum2 = root.findViewById(R.id.etNum2);
        View.OnClickListener listener = v -> {
            double num1 = 0, num2 = 0;
            try {
                num1 = Double.parseDouble(etNum1.getText().toString());
                num2 = Double.parseDouble(etNum2.getText().toString());
            } catch (NumberFormatException e) {
                num1 = 0;
                num2 = 0;
            }

            double result = 0;
            String operation = "";
            int id = v.getId();

            if (id == R.id.btnAdd) {
                result = num1 + num2;
                operation = num1 + " + " + num2;
            } else if (id == R.id.btnSub) {
                result = num1 - num2;
                operation = num1 + " - " + num2;
            } else if (id == R.id.btnMul) {
                result = num1 * num2;
                operation = num1 + " × " + num2;
            } else if (id == R.id.btnDiv) {
                if (num2 == 0) result = Double.NaN;
                else result = num1 / num2;
                operation = num1 + " ÷ " + num2;
            }
            sharedViewModel.calculateNumber(result, operation);
        };
        binding.btnAdd.setOnClickListener(listener);
        binding.btnSub.setOnClickListener(listener);
        binding.btnMul.setOnClickListener(listener);
        binding.btnDiv.setOnClickListener(listener);

        sharedViewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double data) {
                binding.textDashboard.setText(String.valueOf(data));
            }
        });
        return root;
//        return inflater.inflate(R.layout.fragment_dash_board, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        // TODO: Use the ViewModel
    }

}
