package com.practice.sample.database;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
    private RecyclerViewAdapter adapter;

    private Button buttonModeList;
    private Button buttonModeGrid;
    private Button newMemo;

    private static final int REQUEST_USED_PERMISSION = 200;

    private static final String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean permissionToFileAccepted = true;

        switch (requestCode){
            case REQUEST_USED_PERMISSION:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionToFileAccepted = false;
                        break;
                    }
                }
                break;
        }
        if (permissionToFileAccepted == false){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (String permission : needPermissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, needPermissions, REQUEST_USED_PERMISSION);
                break;
            }
        }

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
        adapter = new RecyclerViewAdapter(this, new RecyclerViewAdapter.RecyclerViewAdapterEventListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);

                Memo memo = adapter.getMemo(position);

                if (memo != null) {
                    Intent intent = new Intent(DatabaseActivity.this, MemoWriteActivity.class);
                    intent.putExtra("fileName" , memo.fileName);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        newMemo = (Button) findViewById(R.id.memo_write);
        newMemo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatabaseActivity.this, MemoWriteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.refreshMemoList();
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
