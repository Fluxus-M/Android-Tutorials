package com.practice.sample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MemoListDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MemoListDB.db";

    public static class MemoEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";

        public static final String COLUMN_NAME_FILENAME = "filename";
        public static final String COLUMN_NAME_DATE = "date";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MemoEntry.TABLE_NAME + " (" +
                    MemoEntry._ID + " INTEGER PRIMARY KEY," +
                    MemoEntry.COLUMN_NAME_FILENAME + "  TEXT," +
                    MemoEntry.COLUMN_NAME_DATE + " TEXT )";

    public MemoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveMemo(File file) {
        if (file.exists() == false) {
            return;
        }

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MemoEntry.COLUMN_NAME_FILENAME, file.getName());
        values.put(MemoEntry.COLUMN_NAME_DATE, getDate());

        db.insert(MemoEntry.TABLE_NAME, null, values);
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.KOREA);

        String date = simpleDateFormat.format(new Date());

        return date;
    }

    public ArrayList<Memo> loadMemoList() {
        ArrayList<Memo> memoArrayList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                MemoEntry._ID,
                MemoEntry.COLUMN_NAME_FILENAME,
                MemoEntry.COLUMN_NAME_DATE
        };

        Cursor cursor = db.query(MemoEntry.TABLE_NAME, projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Memo memo = new Memo();

            memo.fileName = cursor.getString(cursor.getColumnIndex(MemoEntry.COLUMN_NAME_FILENAME));

            memo.date = cursor.getString(cursor.getColumnIndex(MemoEntry.COLUMN_NAME_DATE));

            memoArrayList.add(memo);
        }

        return memoArrayList;
    }

    public void removeMemo(File file) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = MemoEntry.COLUMN_NAME_FILENAME + " LIKE ?";
        String[] selectionArgs = { file.getName() };

        db.delete(MemoEntry.TABLE_NAME, selection, selectionArgs);
    }
}

