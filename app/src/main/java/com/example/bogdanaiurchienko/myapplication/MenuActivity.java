package com.example.bogdanaiurchienko.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final DataBaseEmulator db = DataBaseEmulator.getInstance();
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

        //відображаємо список усіх нотаток, вішаємо на них екшен - відкрити актівіті нотатки
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


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
}
