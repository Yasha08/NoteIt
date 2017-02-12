package com.example.android.noteit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by edwin on 31/08/16.
 */
public class Dh extends SQLiteOpenHelper {

    public Dh (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE note(_id INTEGER PRIMARY KEY AUTOINCREMENT, note VARCHAR, Color INTEGER)";
        sqLiteDatabase.execSQL(sql);
       sql = "INSERT INTO note(note) VALUES ('Thanks for installing Note It.\nYou are AWESOME.\n" +
             "If you come across any bugs in the app, do let me know.\n\n"
               + "Features in the next Update:\n* ToDo List\n* Customizable Note Background Colors.')";
       sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
