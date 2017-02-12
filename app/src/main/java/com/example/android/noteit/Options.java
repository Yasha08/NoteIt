package com.example.android.noteit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Options extends AppCompatActivity {
    Dh dh = new Dh(this, "New", null, 1);
    String note;
    int id;
    private ArrayList<String> options = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        note = intent.getStringExtra("string");
        options.add("Share");
        options.add("Delete");

        final ArrayAdapter<String> optionsAdapter =
                new ArrayAdapter<String>(Options.this, android.R.layout.simple_list_item_1, options);
        ListView optionsList = (ListView) findViewById(R.id.optionsList);
        optionsList.setAdapter(optionsAdapter);


        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(optionsAdapter.getItem(i) == "Share")
                {
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_TEXT, note + "\n\n-Shared from Note It App");
                    share.setType("text/plain");
                    startActivity(share);
                }
                else
                if(optionsAdapter.getItem(i) == "Delete"){
                    SQLiteDatabase db = dh.getWritableDatabase();
                    db.execSQL("DELETE FROM note WHERE _id='"+ id +"'");
                    Intent main = new Intent(Options.this, MainActivity.class);
                    startActivity(main);

                }
            }
        });
    }
}
