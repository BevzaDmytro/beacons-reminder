package com.example.bogdanaiurchienko.myapplication.select.beacons;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListViewItemViewHolder extends ViewHolder {
    private CheckBox itemCheckbox;

    private TextView beaconName;
    private TextView beaconAddress;
    private TextView beaconCode;

    ListViewItemViewHolder(View itemView) {
        super(itemView);
    }

    CheckBox getItemCheckbox() {
        return itemCheckbox;
    }

    void setItemCheckbox(CheckBox itemCheckbox) {
        this.itemCheckbox = itemCheckbox;
    }

    TextView getBeaconName() {
        return beaconName;
    }

    void setBeaconName(TextView beaconName) {
        this.beaconName = beaconName;
    }

    public TextView getBeaconAddress() {
        return beaconAddress;
    }

    public void setBeaconAddress(TextView beaconAddress) {
        this.beaconAddress = beaconAddress;
    }

    public TextView getBeaconCode() {
        return beaconCode;
    }

    public void setBeaconCode(TextView beaconCode) {
        this.beaconCode = beaconCode;
    }
}
