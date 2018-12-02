package com.example.bogdanaiurchienko.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;
import com.example.bogdanaiurchienko.myapplication.model.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    DataBaseEmulator db = DataBaseEmulator.getInstance();
    int noteId;
    Note note;
    TextView noteNameView;
    TextView noteTextView;
    TextView noteBeaconsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        noteNameView = findViewById(R.id.noteName);
        noteTextView = findViewById(R.id.noteText);
        noteBeaconsView = findViewById(R.id.noteBeakons);
        View root = noteNameView.getRootView();
        noteId = getIntent().getIntExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", -1);
        note = db.getNote(noteId);
        root.setBackgroundColor(Color.parseColor(note.getColor()));
        noteNameView.setText(note.getName());
        noteTextView.setText(note.getText());
        noteBeaconsView.setText(note.getBeacons());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("onBackPressed    "+noteNameView.getText().toString() + "   " + noteTextView.getText().toString());
        note.setName(noteNameView.getText().toString());
        note.setText(noteTextView.getText().toString());
    }
}
