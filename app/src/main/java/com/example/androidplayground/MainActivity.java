package com.example.androidplayground;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidplayground.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LabItemAdapter adapter = new LabItemAdapter(buildLabItems(), item ->
                startActivity(item.buildIntent(this)));
        binding.labsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.labsRecycler.setAdapter(adapter);
    }

    private List<LabItem> buildLabItems() {
        List<LabItem> labs = new ArrayList<>();
        labs.add(new LabItem(
                "Lab 1 – Layouts",
                "Linear, Relative and Constraint layout samples.",
                ctx -> new Intent(ctx, trinc.com.lab1.Lab1ShowcaseActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 2 – Random & Calculator",
                "Random number generator plus basic calculator.",
                ctx -> new Intent(ctx, trinc.com.lab1.Lab2Activity.class)
        ));
        labs.add(new LabItem(
                "Lab 3 – List & CRUD",
                "Custom adapter and simple CRUD samples.",
                ctx -> new Intent(ctx, trinc.com.lab1.lab3.Lab3ShowcaseActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 4 – Order Flow",
                "Food & drink ordering with custom activities.",
                ctx -> new Intent(ctx, trinc.com.lab1.lab4.Lab4Activity.class)
        ));
        labs.add(new LabItem(
                "Lab 5 – Modules & Users",
                "Recycler/Grid exercises with dialogs.",
                ctx -> new Intent(ctx, trinc.com.lab1.lab5.Lab5ShowcaseActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 6 – Option Menus",
                "Three exercises using app/menu variations.",
                ctx -> new Intent(ctx, com.example.lab6.Lab6MenuActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 7 – Permission Dialog",
                "Camera permission checks and rationale dialog.",
                ctx -> new Intent(ctx, com.example.lab7.MainActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 8 – Notifications",
                "Android 13 notification permission and samples.",
                ctx -> new Intent(ctx, com.example.lab8.MainActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 9 – Task List",
                "Task list CRUD with in-memory storage.",
                ctx -> new Intent(ctx, com.example.lab9.MainActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 10 – Database + API",
                "Room, Retrofit and Glide powered catalog.",
                ctx -> new Intent(ctx, com.example.demodatabase.SignInActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 11 – REST API",
                "RecyclerView with Retrofit and swipe refresh.",
                ctx -> new Intent(ctx, com.example.prm392_lab_11_api.MainActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 12 – Bottom Navigation",
                "Fragments, Navigation component and ViewModel.",
                ctx -> new Intent(ctx, com.example.fragmentbottomnavigation.MainActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 13 – Firebase Auth",
                "Email/password auth with verification flow.",
                ctx -> new Intent(ctx, com.example.trancongtuong_se181735_lab13_authbyemail.MainActivity.class)
        ));
        labs.add(new LabItem(
                "Lab 14 – Maps & Location",
                "OpenStreetMap with location permissions.",
                ctx -> new Intent(ctx, com.example.khiemnvd_androidlab14_google_map.MainActivity.class)
        ));
        return labs;
    }
}
