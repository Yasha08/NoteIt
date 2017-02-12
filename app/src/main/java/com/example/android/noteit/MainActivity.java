package com.example.android.noteit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    Cursor c;
    CustomCursorAdapter ca;
    GridView gridView;
    Dh dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        gridView = (GridView) findViewById(R.id.gridView);


        dh = new Dh(this, "New", null, 1);
        final SQLiteDatabase db = dh.getReadableDatabase();
        c = db.rawQuery("SELECT _id, note, Color FROM NOTE ORDER BY _id DESC", null);

        //String[] from = {"note"};
        //int[] to = {R.id.grid};
        //gridItem = (TextView) findViewById(R.id.grid);
        //ca = new SimpleCursorAdapter(this, R.layout.grid_item_layout, c, from, to, 0);

        ca = new CustomCursorAdapter(this, c);
        gridView.setAdapter(ca);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditNote.class);
                intent.putExtra("string", c.getString(c.getColumnIndex("note")));
                intent.putExtra("id", c.getInt(c.getColumnIndex("_id")));
                intent.putExtra("color", c.getInt(c.getColumnIndex("Color")));
                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, Options.class);
                intent.putExtra("id", c.getInt(0));
                intent.putExtra("string", c.getString(c.getColumnIndex("note")));
                startActivity(intent);

                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewNote.class));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.about);
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
