package com.example.androidplayground;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.example.androidplayground.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int[] ACCENT_COLORS = new int[]{
            R.color.lab_accent_1,
            R.color.lab_accent_2,
            R.color.lab_accent_3,
            R.color.lab_accent_4,
            R.color.lab_accent_5,
            R.color.lab_accent_6
    };

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.topAppBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        loadLogo();

        List<LabItem> labs = buildLabItems();
        binding.chipLabsCount.setText(getString(R.string.playground_lab_count, labs.size()));

        LabItemAdapter adapter = new LabItemAdapter(labs, item ->
                startActivity(item.buildIntent(this)));
        binding.labsRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.labsRecycler.setAdapter(adapter);
    }

    private void loadLogo() {
        try (InputStream stream = getResources().openRawResource(R.raw.android_playground_logo)) {
            SVG svg = SVG.getFromInputStream(stream);
            PictureDrawable drawable = new PictureDrawable(svg.renderToPicture());
            binding.toolbarLogo.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            binding.toolbarLogo.setImageDrawable(drawable);
        } catch (IOException | SVGParseException e) {
            binding.toolbarLogo.setVisibility(View.GONE);
        }
    }

    private List<LabItem> buildLabItems() {
        List<LabItem> labs = new ArrayList<>();
        labs.add(createLab(
                "Lab 1 – Layouts",
                "Linear, Relative and Constraint layout samples.",
                1,
                ctx -> new Intent(ctx, trinc.com.lab1.Lab1ShowcaseActivity.class)
        ));
        labs.add(createLab(
                "Lab 2 – Random & Calculator",
                "Random number generator plus basic calculator.",
                2,
                ctx -> new Intent(ctx, trinc.com.lab2.Lab2Activity.class)
        ));
        labs.add(createLab(
                "Lab 3 – List & CRUD",
                "Custom adapter and simple CRUD samples.",
                3,
                ctx -> new Intent(ctx, trinc.com.lab3.Lab3ShowcaseActivity.class)
        ));
        labs.add(createLab(
                "Lab 4 – Order Flow",
                "Food & drink ordering with custom activities.",
                4,
                ctx -> new Intent(ctx, trinc.com.lab4.Lab4Activity.class)
        ));
        labs.add(createLab(
                "Lab 5 – Modules & Users",
                "Recycler/Grid exercises with dialogs.",
                5,
                ctx -> new Intent(ctx, trinc.com.lab5.Lab5ShowcaseActivity.class)
        ));
        labs.add(createLab(
                "Lab 6 – Option Menus",
                "Three exercises using app/menu variations.",
                6,
                ctx -> new Intent(ctx, com.example.lab6.Lab6MenuActivity.class)
        ));
        labs.add(createLab(
                "Lab 7 – Permission Dialog",
                "Camera permission checks and rationale dialog.",
                7,
                ctx -> new Intent(ctx, com.example.lab7.MainActivity.class)
        ));
        labs.add(createLab(
                "Lab 8 – Notifications",
                "Android 13 notification permission and samples.",
                8,
                ctx -> new Intent(ctx, com.example.lab8.MainActivity.class)
        ));
        labs.add(createLab(
                "Lab 9 – Task List",
                "Task list CRUD with in-memory storage.",
                9,
                ctx -> new Intent(ctx, com.example.lab9.MainActivity.class)
        ));
        labs.add(createLab(
                "Lab 10 – Database + API",
                "Room, Retrofit and Glide powered catalog.",
                10,
                ctx -> new Intent(ctx, com.example.demodatabase.SignInActivity.class)
        ));
        labs.add(createLab(
                "Lab 11 – REST API",
                "RecyclerView with Retrofit and swipe refresh.",
                11,
                ctx -> new Intent(ctx, com.example.prm392_lab_11_api.MainActivity.class)
        ));
        labs.add(createLab(
                "Lab 12 – Bottom Navigation",
                "Fragments, Navigation component and ViewModel.",
                12,
                ctx -> new Intent(ctx, com.example.fragmentbottomnavigation.MainActivity.class)
        ));
        labs.add(createLab(
                "Lab 13 – Firebase Auth",
                "Email/password auth with verification flow.",
                13,
                ctx -> new Intent(ctx, com.example.trancongtuong_se181735_lab13_authbyemail.MainActivity.class)
        ));
        labs.add(createLab(
                "Lab 14 – Maps & Location",
                "OpenStreetMap with location permissions.",
                14,
                ctx -> new Intent(ctx, com.example.khiemnvd_androidlab14_google_map.MainActivity.class)
        ));
        return labs;
    }

    private LabItem createLab(String title, String description, int badgeNumber, LabItem.IntentFactory factory) {
        int color = ACCENT_COLORS[(badgeNumber - 1) % ACCENT_COLORS.length];
        String badge = String.format("LAB %02d", badgeNumber);
        return new LabItem(title, description, badge, color, factory);
    }
}
