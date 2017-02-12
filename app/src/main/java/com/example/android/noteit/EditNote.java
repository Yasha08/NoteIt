package com.example.android.noteit;

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

public class EditNote extends AppCompatActivity {
    EditText edit;
    String sql;
    int id;
    Intent intent;
    String note;
    int noteColor;
    CardView editNoteCard;
    Dh dh = new Dh(this, "New", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = getIntent();
        note = intent.getStringExtra("string");
        id = intent.getIntExtra("id", 0);
        noteColor = intent.getIntExtra("color", 0);
        if (noteColor == 0){
            noteColor = Color.parseColor("#FFEB3B");
        }
        edit = (EditText) findViewById(R.id.edit);
        editNoteCard = (CardView) findViewById(R.id.editNoteCard);
        editNoteCard.setCardBackgroundColor(noteColor);
        if (note == "\0"){
            edit.setHint("Type Here");
        }
        else {
            edit.setText(note);
            edit.setSelection(edit.getText().length());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateNote();
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateNote();

    }

    @Override
    public void onBackPressed() {
        updateNote();
        intent = new Intent(EditNote.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            note = edit.getText().toString();
            shareNote(note);
            return true;
        }
        if (id == R.id.color){
            //Toast.makeText(this, "Colors", Toast.LENGTH_SHORT).show();
            View view = findViewById(R.id.color);
            PopupMenu popupMenu = new PopupMenu(EditNote.this, view);
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
        return super.onOptionsItemSelected(item);
    }

    public void setColor(int id){
        switch (id) {

            case R.id.green:
                noteColor = Color.parseColor("#B2FF59");
                editNoteCard.setCardBackgroundColor(noteColor);
                break;
            case R.id.blue:
                noteColor = Color.parseColor("#4FC3F7");
                editNoteCard.setCardBackgroundColor(noteColor);
                break;
            case R.id.yellow:
                noteColor = Color.parseColor("#FFEB3B");
                editNoteCard.setCardBackgroundColor(noteColor);
                break;
            case R.id.pink:
                noteColor = Color.parseColor("#F48FB1");
                editNoteCard.setCardBackgroundColor(noteColor);

        }
    }

    public void updateNote(){
        String updateNote = edit.getText().toString();
        SQLiteDatabase db = dh.getWritableDatabase();
        if (updateNote.length() > note.length() || updateNote.length() < note.length()) {
            sql = "UPDATE note SET note = '" + updateNote + "' WHERE _id='" + id + "'";
            db.execSQL(sql);
        }
        sql = "UPDATE note SET Color = '"+ noteColor + "' WHERE _id='" + id + "'";
        db.execSQL(sql);
    }

    public void shareNote(String note){
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, note + "\n\n-Shared from Note It App");
        share.setType("text/plain");
        startActivity(share);
    }
}
