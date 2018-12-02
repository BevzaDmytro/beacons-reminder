package com.example.bogdanaiurchienko.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;

public class MainActivity extends AppCompatActivity {

    final DataBaseEmulator db = DataBaseEmulator.getInstance();
    ListView notesView;
    NoteItemAdapter noteItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesView = findViewById(R.id.notesView) ;
        noteItemAdapter = new NoteItemAdapter(this, db.getNotes());
        notesView.setAdapter(noteItemAdapter);
        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showNoteDetail = new Intent(getApplicationContext(), NoteDetailsActivity.class);
                showNoteDetail.putExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", i);
                startActivity(showNoteDetail);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notesView = findViewById(R.id.notesView) ;
        noteItemAdapter = new NoteItemAdapter(this, db.getNotes());
        notesView.setAdapter(noteItemAdapter);
        System.out.println("main onRestart");
    }
}
