package com.example.bogdanaiurchienko.myapplication.model;


import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String name;
    private String text;
    private String beacons;
    private String color;
    private int colorId;

    public Note(int id, String name, String text, String beacons, String color, int colorId) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.beacons = beacons;
        this.color = color;
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getBeacons() {
        return beacons;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setBeacons(String beacons) {
        this.beacons = beacons;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }
}
