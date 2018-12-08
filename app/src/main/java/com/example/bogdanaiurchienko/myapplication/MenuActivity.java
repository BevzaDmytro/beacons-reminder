package com.example.bogdanaiurchienko.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bogdanaiurchienko.myapplication.model.DataBaseConnector;
import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;
<<<<<<< HEAD
import com.example.bogdanaiurchienko.myapplication.model.ServerConnection;

import java.net.MalformedURLException;
=======
import com.example.bogdanaiurchienko.myapplication.model.Note;

import java.util.ArrayList;
>>>>>>> dev

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final DataBaseConnector db = DataBaseEmulator.getInstance();
    ListView notesView;
    NoteItemAdapter noteItemAdapter;
    int lastNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //кнопка створення нової нотатки
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastNote = 0;
                Intent showNoteDetail = new Intent(getApplicationContext(), NoteDetailsActivity.class);
                showNoteDetail.putExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", db.getNewNoteId());
                startActivity(showNoteDetail);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

<<<<<<< HEAD



=======
        //відображаємо список усіх нотаток, вішаємо на них екшен - відкрити актівіті нотатки
>>>>>>> dev
        notesView = findViewById(R.id.notesView) ;
        noteItemAdapter = new NoteItemAdapter(this, db.getNotes());
        notesView.setAdapter(noteItemAdapter);
        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showNoteDetail = new Intent(getApplicationContext(), NoteDetailsActivity.class);
                lastNote = i;
                showNoteDetail.putExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", noteItemAdapter.getCount() -1 - i);
                startActivity(showNoteDetail);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.add_note) {
            lastNote = 0;
            Intent showNoteDetail = new Intent(getApplicationContext(), NoteDetailsActivity.class);
            showNoteDetail.putExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", db.getNewNoteId());
            startActivity(showNoteDetail);
        } else if (id == R.id.all_beacons) {
            Intent showAllBeacons = new Intent(getApplicationContext(), AllBeaconsActivity.class);
            startActivity(showAllBeacons);
        } else if (id == R.id.settings) {


        } else if (id == R.id.help) {


        } else if (id == R.id.feedback) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notesView = findViewById(R.id.notesView) ;
        noteItemAdapter = new NoteItemAdapter(this, db.getNotes());
        notesView.setAdapter(noteItemAdapter);
        notesView.post(new Runnable() {
            @Override
            public void run() {
                notesView.smoothScrollToPosition(lastNote+1);
            }
        });
    }

    class NoteItemAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private ArrayList<Note> notes;
        private int size;

        NoteItemAdapter(Context context, ArrayList<Note> notes) {
            this.size = notes.size();
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.notes = notes;
        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Object getItem(int i) {
            return notes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return notes.get(i).getId();
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Note note = notes.get(size - 1 - i);
            view = layoutInflater.inflate(R.layout.note_item_layout, null);
            view.setBackgroundColor(Color.parseColor(note.getColor()));

            TextView noteName = view.findViewById(R.id.noteName);
            TextView noteText = view.findViewById(R.id.noteText);
            TextView noteBeacons = view.findViewById(R.id.noteBeacons);

            noteName.setText(note.getName());
            noteText.setText(note.getText());
            noteBeacons.setText(note.getBeaconsNames());
            return view;
        }
    }
}
