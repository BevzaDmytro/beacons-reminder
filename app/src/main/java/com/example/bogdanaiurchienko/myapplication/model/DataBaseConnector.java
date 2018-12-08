package com.example.bogdanaiurchienko.myapplication.model;

import java.util.ArrayList;

public interface DataBaseConnector {

    ArrayList<Note> getNotes();

    Note getNote(int i);

    //створює нову пусту нотатку і повертає її айді
    int getNewNoteId();

    void editNote(int id, String name, String text, String color, String[] beaconsCodes);

    void deleteNote(int id);

    ArrayList<Beacon> getBeacons();

    void setBeacons(ArrayList<Beacon> beacons);

    void updateBeaconsFromServer();

    void updateNotesFromServer();

    void addNote(Note note);
}
