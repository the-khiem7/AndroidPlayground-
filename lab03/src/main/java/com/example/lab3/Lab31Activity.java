package com.example.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.lab3.R;


public class Lab31Activity extends AppCompatActivity {
    ListView dataView;
    ArrayList<String> data = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    EditText inputText;
    int SelectedIndex;
    Button add;
    Button delete;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_1);

        dataView = (ListView)findViewById(R.id.dataView);
        inputText = (EditText) findViewById(R.id.inputText);
        add = (Button) findViewById(R.id.addBtn);
        delete = (Button) findViewById(R.id.deleteBtn);
        update = (Button) findViewById(R.id.updateBtn);
        data.add("Android 2017");
        data.add("PHP");
        data.add("iOS");
        data.add("Unity");

        // Load data
        arrayAdapter = new ArrayAdapter(
                Lab31Activity.this, android.R.layout.simple_list_item_1,
                data
        );

        dataView.setAdapter(arrayAdapter);

        dataView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Lab31Activity.this, data.get(position), Toast.LENGTH_SHORT).show();
                SelectedIndex = position;
                inputText.setText(data.get(position));
            }
        });

        // Button Event
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleAdd();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleDelete();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleUpdate();
            }
        });
    }

    private void Delete(int id, ArrayAdapter arrayAdapter){
        data.remove(id);
        arrayAdapter.notifyDataSetChanged();
    }

    private void HandleDelete(){
        if (SelectedIndex == -1){
            return;
        }
        data.remove(SelectedIndex);
        arrayAdapter.notifyDataSetChanged();
        SelectedIndex = -1;
    }
    private void HandleAdd(){
        SelectedIndex = -1;
        data.add(inputText.getText().toString());
        arrayAdapter.notifyDataSetChanged();
    }
    private void HandleUpdate(){
        if (SelectedIndex == -1){
            return;
        }
        data.set(SelectedIndex, inputText.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        SelectedIndex = -1;
    }
}
