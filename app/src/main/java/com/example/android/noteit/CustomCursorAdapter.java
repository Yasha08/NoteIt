package com.example.android.noteit;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by edwin on 07/09/16.
 */
public class CustomCursorAdapter extends CursorAdapter {
    Cursor c;

    public CustomCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
        c = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.grid_item_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView grid = (TextView) view.findViewById(R.id.grid);
        String note = c.getString(c.getColumnIndex("note"));
        grid.setText(note);
        int noteColor = c.getInt(c.getColumnIndex("Color"));

        if (noteColor == 0)
        {
            noteColor = Color.parseColor("#FFEB3B");
        }

        CardView noteCard = (CardView)view.findViewById(R.id.grid_item_layout);
        noteCard.setCardBackgroundColor(noteColor);
    }
}
