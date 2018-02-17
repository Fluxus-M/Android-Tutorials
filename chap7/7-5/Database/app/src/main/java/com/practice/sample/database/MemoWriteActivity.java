package com.practice.sample.database;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MemoWriteActivity extends Activity {
    private Button save;
    private Button delete;
    private EditText memoWrite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_write);

        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
        memoWrite = (EditText) findViewById(R.id.memo_write);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveMemo();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMemo();
            }
        });

        loadMemo();
    }

    private void saveMemo() {
        String memo = memoWrite.getText().toString();

        if (memo.length() > 0) {
            FileOutputStream fileOutputStream;

            try {
                fileOutputStream = new FileOutputStream(getMemoFile());
                fileOutputStream.write(memo.getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getMemoFile(){
        File memoFile = new File(Environment.getExternalStorageDirectory(), "memo.txt");

        return memoFile;
    }

    private void loadMemo() {
        File memoFile = getMemoFile();

        if (memoFile.exists()) {
            FileInputStream inputStream;

            try {
                inputStream = new FileInputStream(memoFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            byte[] memoData = null;

            try {
                memoData = new byte[inputStream.available()];
                inputStream.read(memoData);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if (memoData != null) {
                String memoString = new String(memoData);
                memoWrite.setText(memoString);
            }
        }
    }

    private void deleteMemo() {
        File memoFile = getMemoFile();
        if (memoFile.exists()) {
            memoFile.delete();
        }
    }
}
