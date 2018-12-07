package com.example.bogdanaiurchienko.myapplication.select.beacons;

import com.example.bogdanaiurchienko.myapplication.model.Beacon;

public class ListViewItemDTO {
    private boolean checked;
    private Beacon beacon;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    Beacon getBeacon() {
        return beacon;
    }

    void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }


}
