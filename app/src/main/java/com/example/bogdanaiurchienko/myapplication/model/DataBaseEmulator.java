package com.example.bogdanaiurchienko.myapplication.model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import java.net.NetworkInterface;

import java.util.Collections;
import java.util.List;


public class DataBaseEmulator implements DataBaseConnector {
    private static final DataBaseConnector ourInstance = new DataBaseEmulator();
    private ArrayList<Note> notes = new ArrayList<>();
    private ArrayList<Beacon> beacons = new ArrayList<>();
    private String mac;

    public static DataBaseConnector getInstance() {
        return ourInstance;
    }

    public void updateBeaconsFromServer(){
        String jsonBeacons = "";
        ServerConnection con = (ServerConnection) new ServerConnection().execute("get", "beacons");
        try {
            jsonBeacons = con.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        TypeToken<ArrayList<Beacon>> token = new TypeToken<ArrayList<Beacon>>() {};
        this.beacons = gson.fromJson(jsonBeacons, token.getType());
    }

    public void updateNotesFromServer(){
        String jsonNotes = "";
        ServerConnection con = (ServerConnection) new ServerConnection().execute("get", "notes");
        try {
            jsonNotes = con.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        TypeToken<ArrayList<Note>> token = new TypeToken<ArrayList<Note>>() {};
        this.notes = gson.fromJson(jsonNotes, token.getType());
        System.out.println();
    }

    private DataBaseEmulator() {
//        String[] beaconsNames = new String[]{"kitchen", "outdoor", "the shop", "the school",
//                "in parents' home", "bathroom", "bedroom"};
//        String[] beaconsLocations = new String[]{"Poltava, Ukraine", "USA, Boston",
//                "Poltava, Ukraine", "USA, Boston", "Poltava, Ukraine", "USA, Boston", "Kiev, Ukraine"};
//        String[] beaconsCodes = new String[]{"sdkjfsjf;skdjfs;df", "dfkjhlifhuasdfasdfas", "sldfhaiuefwe",
//                "sdlfjaw;iofh;woef", "sljfa;oifhowi","sldfjhaiudf;f",
//                "ldskjf;isodh;fosduf"};
//
//        for(int i = 0; i < beaconsNames.length; i++){
//            beacons.add(new Beacon(i, beaconsNames[i], beaconsLocations[i], beaconsCodes[i]));
//        }

this.updateNotesFromServer();
this.updateBeaconsFromServer();

//        String[] names = new String[]{"Dishes!!!", "Trash!!!", "some other note",
//                "One more", " and again", "again ransom ....", "I got rid of ideas/",
//                " but going on"};
//
//        String[] texts = new String[]{"to do the dishes", "don't forget to do it!!", "i don't know what to write",
//                "random text",
//                "lets pretend there is some sensedfhsfhfghfghfghfdghfg fghf gh fg h fg hfg hf gh fg\n " +
//                        "\n\n sdkfjsdhfsdf\n\nasdfhsdjfhsidff",
//                "ullalala", "find me sombody to-ou-oo-o-o loooooove",
//                "bohemian rhapsody"};
//
//
//        String[] colors = new String[]{ "#d2d4dc", "#dcedc1",
//                "#ffd3b6", "#ffaaa5", "#ff8b94", "#a8e6cf",
//                "#ffd3b6", "#ff8b94"} ;
//
//        for(int i = 0; i < names.length; i++){
//            notes.add(new Note(i, names[i], texts[i], beacons, colors[i]));
//
//        }

        this.mac = getMac();

    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public Note getNote(int i){
        //dv ?
        this.updateNotesFromServer();
        for ( Note note : this.notes) {
            if(note.getId() == i) return note;
        }
//        if(i < notes.size()) return notes.get(i);
         return null;
    }

    public ArrayList<Note> getNotesToBeacon(String beaconCode){
        String jsonNotes = "";
        ServerConnection con = (ServerConnection) new ServerConnection().execute("get", "beacon", beaconCode);
        try {
            jsonNotes = con.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        TypeToken<ArrayList<Note>> token = new TypeToken<ArrayList<Note>>() {};
        return  gson.fromJson(jsonNotes, token.getType());
    }

    public void addNote(Note note) {

    }

    public int getNewNoteId(){
       new ServerConnection().execute("insert", "note");

       int lastNoteID = this.notes.get(this.notes.size()-1).getId();
        return lastNoteID;
    }

    public void editNote(int id, String name, String text, String color, ArrayList<String> beaconsCodes) {

//        int noteId = this.notes.get(id).getId();
        String jsonResult = "note={id:"+String.valueOf(id)+",name:"+name+",text="+text+",color:"+color
                +"}&beacons={";
        int i =0;
        for(String code: beaconsCodes){
            jsonResult += String.valueOf(i)+":"+code+",";
            i++;
        }
        jsonResult = jsonResult.substring(0, jsonResult.length() - 1);
        jsonResult +="}";
        ServerConnection con = (ServerConnection) new ServerConnection().execute("update", jsonResult);

//        Note note = notes.get(id);
////        note.setId(id);
//        note.setName(name);
//        note.setText(text);
//        ArrayList<Beacon> b = new ArrayList<>();
//        for(String code: beaconsCodes){
//            b.add(beacons.get(beacons.indexOf(new Beacon(code))));
//        }
//        note.setBeacons(b);
//        note.setColor(color);
    }

    public void deleteNote(int id){
        String response = "";
        ServerConnection con = (ServerConnection) new ServerConnection().execute("delete", String.valueOf(id));
        try {
            response = con.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if(!response.equals("Success")){
            return;
        }

        notes.remove(new Note(id));
//        this.updateNotesFromServer();
    }

    public ArrayList<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(ArrayList<Beacon> beacons) {
        this.beacons = beacons;
    }

    static private String getMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif: all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b: macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

}
