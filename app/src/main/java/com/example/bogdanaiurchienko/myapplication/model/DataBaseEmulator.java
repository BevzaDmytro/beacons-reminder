package com.example.bogdanaiurchienko.myapplication.model;

import java.util.ArrayList;

public class DataBaseEmulator {
    private static final DataBaseEmulator ourInstance = new DataBaseEmulator();
    private ArrayList<Note> notes = new ArrayList<>();

    public static DataBaseEmulator getInstance() {
        return ourInstance;
    }

    private DataBaseEmulator() {

        String[] names = new String[]{"Dishes!!!", "Trash!!!", "some other note",
                "One more", " and again", "again ransom ....", "I got rid of ideas/",
                " but going on"};

        String[] texts = new String[]{"to do the dishes", "don't forget to do it!!", "i don't know what to write",
                "random text",
                "lets pretend there is some sensedfhsfhfghfghfghfdghfg fghf gh fg h fg hfg hf gh fg\n " +
                        "\n\n sdkfjsdhfsdf\n\nasdfhsdjfhsidff",
                "ullalala", "find me sombody to-ou-oo-o-o loooooove",
                "bohemian rhapsody"};

        String[] beacons = new String[]{"kitchen", "kitchen outdoor", "the shop",
                "the school", "kitchen in parents' home", "bathroom", "kitchen outdoor",
                "kitchen bedroom bathroom outdoor"};
        String[] colors = new String[]{ "#d2d4dc", "#dcedc1",
                "#ffd3b6", "#ffaaa5", "#ff8b94", "#a8e6cf",
                "#ffd3b6", "#ff8b94"} ;

        for(int i = 0; i < beacons.length; i++){
            notes.add(new Note(i, names[i], texts[i], beacons[i], colors[i]));

        }
    }


    public ArrayList<Note> getNotes() {
        return notes;
    }

    public Note getNote(int i){
        if(i < notes.size()) return notes.get(i);
        else return null;
    }

    public void addNote(String name, String text, String beacons, String color) {
        notes.add(new Note(notes.size(), name, text, beacons, color));
    }

    public void editNote(int id, String name, String text, String beacons, String color) {
        Note note = notes.get(id);
        note.setId(id);
        note.setName(name);
        note.setText(text);
        note.setBeacons(beacons);
        note.setColor(color);
    }
}
