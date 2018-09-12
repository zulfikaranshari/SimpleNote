package com.android.zulfikaranshari.simplenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zulfikaranshari on 02/08/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SimpleNote";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "notes";

    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "tittle";
    public static final String COL_NOTE = "note";
    public static final String COL_DATE = "datecreated";
    public static final String COL_TIME = "timecreated";

    public static final String CREATE_DATABASE = "create table "+TABLE_NAME+"("
            +COL_ID+" integer primary key autoincrement, "+
            ""+COL_TITLE+" text, "+
            ""+COL_NOTE+" text, "+
            ""+COL_DATE+" text, "+
            ""+COL_TIME+" text)";

    public SQLiteDatabase db = this.getWritableDatabase();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertNote(String title, String note, String date, String time ){
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

//        value.put(COL_ID, id);
        value.put(COL_TITLE, title);
        value.put(COL_NOTE, note);
        value.put(COL_DATE, date);
        value.put(COL_TIME, time);

        long result = db.insert(TABLE_NAME, null, value);

        if (result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor selectedNote(String id){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL_ID+" = '"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public void deleteNote(String id){
        String query = "DELETE FROM "+TABLE_NAME+" WHERE "+COL_ID+" = '"+id+"'";
        db.delete(TABLE_NAME, "_id = "+id, null);
    }

    public void updateNote(String id, String tittle, String note){
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, tittle);
        values.put(COL_NOTE, note);
        db.update(TABLE_NAME, values, "_id = "+id, null);
//        String query = "UPDATE "+TABLE_NAME+" SET "+COL_TITLE+" = '"+tittle+"', SET "+COL_NOTE+" = '"+note+"' WHERE "+COL_ID+" = "+id;
//        db.rawQuery(query, null);
    }

    public Cursor allNote(){
        String query = "SELECT * FROM "+TABLE_NAME+ " order by "+COL_DATE+"  DESC, "+COL_TIME+" DESC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
