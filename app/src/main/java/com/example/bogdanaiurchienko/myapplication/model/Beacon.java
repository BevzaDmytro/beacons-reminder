package com.example.bogdanaiurchienko.myapplication.model;

import java.util.Objects;

public class Beacon {
    private long id;
    private String name;
    private String address;
    private String code;


    Beacon(long id, String name, String address, String code) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.code = code;
    }

    public Beacon(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beacon beacon = (Beacon) o;
        return Objects.equals(code, beacon.code);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(code);
    }
    
}
