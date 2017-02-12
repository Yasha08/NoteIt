package com.example.android.noteit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewNote extends AppCompatActivity {
    EditText note;
    boolean newNoteEmpty;
    String newNote;
    String share;
    int noteColor = Color.parseColor("#FFEB3B");
    Dh dh = new Dh(NewNote.this, "New", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        //Receiving and handling Intent from other Apps
        Intent receivedIntent = getIntent();
        String action = receivedIntent.getAction();
        String type = receivedIntent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null){
            String receivedData = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
            EditText newNote = (EditText)findViewById(R.id.note);
            newNote.setText(receivedData);
        }
    }

    @Override
    protected void onStop() {
        addToDatabase();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        goToMainActivity();
    }

    @Override
    public void onBackPressed() {
        addToDatabase();
        goToMainActivity();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.color) {
            View view = findViewById(R.id.color);

            PopupMenu popupMenu = new PopupMenu(NewNote.this, view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.colors, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int Id = item.getItemId();
                    setColor(Id);
                    return true;
                }
            });
        }

        if (id == R.id.share){
            EditText newNote = (EditText)findViewById(R.id.note);
            share = newNote.getText().toString();

            if (share.isEmpty()){
                Toast.makeText(NewNote.this, "Your Note is Empty.", Toast.LENGTH_SHORT).show();
            }
            else {
                shareNote(share);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void shareNote(String note){
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, note + "\n\n-Shared from Note It App");
        share.setType("text/plain");
        startActivity(share);
    }

    public void setColor(int id){
        CardView noteCard = (CardView) findViewById(R.id.newNoteCard);

        switch (id) {

            case R.id.green:
                noteColor = Color.parseColor("#B2FF59");
                noteCard.setCardBackgroundColor(noteColor);
                break;
            case R.id.blue:
                noteColor = Color.parseColor("#4FC3F7");
                noteCard.setCardBackgroundColor(noteColor);
                break;
            case R.id.yellow:
                noteColor = Color.parseColor("#FFEB3B");
                noteCard.setCardBackgroundColor(noteColor);
                break;
            case R.id.pink:
                noteColor = Color.parseColor("#F48FB1");
                noteCard.setCardBackgroundColor(noteColor);
        }
    }

    public void addToDatabase() {
       note = (EditText) findViewById(R.id.note);
        SQLiteDatabase db = dh.getWritableDatabase();
        newNote = note.getText().toString();
        newNoteEmpty = newNote.isEmpty();
        if (!newNoteEmpty) {
            ContentValues cv = new ContentValues();
            cv.put("note", note.getText().toString());
            cv.put("Color", noteColor);
            db.insert("note", null, cv);
            note.setText("");
        }
    }

    public void goToMainActivity() {
        Intent i = new Intent(NewNote.this, MainActivity.class);
        startActivity(i);
    }
}


