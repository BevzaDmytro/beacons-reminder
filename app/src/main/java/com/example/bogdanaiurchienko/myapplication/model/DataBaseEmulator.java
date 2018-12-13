package com.example.bogdanaiurchienko.myapplication.model;

import java.net.NetworkInterface;
import java.util.ArrayList;
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

    private DataBaseEmulator() {
        String[] beaconsNames = new String[]{"kitchen", "Andriy'sBeacon", "the shop", "the school",
                "in parents' home", "bathroom", "bedroom", "new beacon"};
        String[] beaconsLocations = new String[]{"Poltava, Ukraine", "USA, Boston",
                "Poltava, Ukraine", "USA, Boston", "Poltava, Ukraine", "USA, Boston", "Kiev, Ukraine",  "Kiev, Ukraine"};
        String[] beaconsCodes = new String[]{"1260a1f0-c1c3-466c-8cd9-88c2881b6225", "28fb2f6b-f574-40c1-afd3-34832145537d", "sldfhaiuefwe",
                "sdlfjaw;iofh;woef", "sljfa;oifhowi","sldfjhaiudf;f",
                "ldskjf;isodh;fosduf", "dfkja;dsoifja;odijf"};

        for(int i = 0; i < beaconsNames.length; i++){
            beacons.add(new Beacon(i, beaconsNames[i], beaconsLocations[i], beaconsCodes[i]));
        }

        String[] names = new String[]{"Dishes!!!", "Trash!!!", "some other note",
                "One more", " and again", "again ransom ....", "I got rid of ideas/",
                " but going on"};

        String[] texts = new String[]{"to do the dishes", "don't forget to do it!!", "i don't know what to write",
                "random text",
                "lets pretend there is some sensedfhsfhfghfghfghfdghfg fghf gh fg h fg hfg hf gh fg\n " +
                        "sdkfjsdhfsdf\nasdfhsdjfhsidff",
                "ullalala", "find me sombody to-ou-oo-o-o loooooove",
                "bohemian rhapsody"};


        String[] colors = new String[]{"#baa896", "#e6ccb5", "#eae2d6", "#d5c3aa", "#d6c6b9",
                "#eae2d6", "#d5c3aa", "#d6c6b9"} ;

        for(int i = 0; i < names.length; i++){
            notes.add(new Note(i, names[i], texts[i], new ArrayList<>(beacons.subList(i, (i < names.length -2) ?  i+2 : i+1)), colors[i]));

        }



        this.mac = getMac();

    }


    public ArrayList<Note> getNotes() {
        return notes;
    }

    public Note getNote(int i){
        if(i < notes.size()) return notes.get(i);
        else return null;
    }

    public int getNewNoteId() {
        notes.add(new Note());
        return notes.size() - 1;
    }

    public void editNote(int id, String name, String text, String color, String[] beaconsCodes) {
        Note note = notes.get(id);
        note.setId(id);
        note.setName(name);
        note.setText(text);
        ArrayList<Beacon> b = new ArrayList<>();
        for(String code: beaconsCodes){
            b.add(beacons.get(beacons.indexOf(new Beacon(code))));
        }
        note.setBeacons(b);
        note.setColor(color);
    }

    public void deleteNote(int id){
        notes.remove(id);
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
