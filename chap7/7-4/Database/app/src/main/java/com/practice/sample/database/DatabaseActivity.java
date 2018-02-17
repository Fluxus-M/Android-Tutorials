package com.practice.sample.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class DatabaseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private Button buttonModeList;
    private Button buttonModeGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        buttonModeList = (Button) findViewById(R.id.mode_list);
        buttonModeList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeModeList();
            }
        });

        buttonModeGrid = (Button) findViewById(R.id.mode_grid);
        buttonModeGrid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeModeGrid();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        if (isSaveModeList()) {
            changeModeList();
        } else {
            changeModeGrid();
        }

        layoutManager.setAutoMeasureEnabled(false);
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void changeModeList() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        saveRecyclerViewMode(true);
    }

    private void changeModeGrid() {
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        saveRecyclerViewMode(false);
    }

    private void saveRecyclerViewMode(boolean modeList) {
        SharedPreferences sharedPreferences = getSharedPreferences("memo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is list mode", modeList);

        editor.commit();
    }

    private boolean isSaveModeList() {
        SharedPreferences sharedPreferences = getSharedPreferences("memo", Context.MODE_PRIVATE);

        boolean isListMode = sharedPreferences.getBoolean("is list mode", true);
        return isListMode;
    }
}
