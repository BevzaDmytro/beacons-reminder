package com.example.bogdanaiurchienko.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bogdanaiurchienko.myapplication.model.Note;

import java.util.ArrayList;


public class NoteItemAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Note[] notes;

    NoteItemAdapter(Context context, ArrayList<Note> notes) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.notes = new Note[notes.size()];
        for(int i = 0; i < this.notes.length; i++){
            this.notes[i] = notes.get(i);
        }
    }

    @Override
    public int getCount() {
        return notes.length;
    }

    @Override
    public Object getItem(int i) {
        return notes[i];
    }

    @Override
    public long getItemId(int i) {
        return notes[i].getId();
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.note_item_layout, null);
        view.setBackgroundColor(Color.parseColor(notes[i].getColor()));
        TextView noteName = view.findViewById(R.id.noteName);
        TextView noteText = view.findViewById(R.id.noteText);
        TextView noteBeacons = view.findViewById(R.id.noteBeakons);
        noteName.setText(notes[i].getName());
        noteText.setText(notes[i].getText());
        noteBeacons.setText(notes[i].getBeacons());
        return view;
    }
}
