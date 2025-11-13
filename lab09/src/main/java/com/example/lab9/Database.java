package com.example.lab9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "congviec_db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "CongViec";
    public static final String COL_ID = "id";
    public static final String COL_TEN = "ten";
    public static final String COL_NOIDUNG = "noidung";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEN + " TEXT, " +
                COL_NOIDUNG + " TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert
    public long addCongViec(CongViec cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TEN, cv.getTen());
        values.put(COL_NOIDUNG, cv.getNoiDung());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Get all
    public ArrayList<CongViec> getAllCongViec() {
        ArrayList<CongViec> list = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(select, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndexOrThrow(COL_ID));
                String ten = c.getString(c.getColumnIndexOrThrow(COL_TEN));
                String noidung = c.getString(c.getColumnIndexOrThrow(COL_NOIDUNG));
                list.add(new CongViec(id, ten, noidung));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    // Update
    public int updateCongViec(CongViec cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TEN, cv.getTen());
        values.put(COL_NOIDUNG, cv.getNoiDung());
        int rows = db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(cv.getId())});
        db.close();
        return rows;
    }

    // Delete
    public int deleteCongViec(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}