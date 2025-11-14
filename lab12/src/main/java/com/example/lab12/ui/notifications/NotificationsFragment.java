package com.example.lab12.ui.notifications;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab12.R;
import com.example.lab12.databinding.FragmentNotificationsBinding;
import com.example.lab12.ui.viewmodel.SharedViewModel;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;
    private FragmentNotificationsBinding binding;
    private SharedViewModel sharedViewModel;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double data) {
                // binding.textNotifications.setText(String.valueOf(data));
            }
        });
        return root;
//        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        // TODO: Use the ViewModel
    }

}
