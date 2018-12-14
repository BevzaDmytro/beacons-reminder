package com.example.bogdanaiurchienko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;

public class AddBeaconActivity extends AppCompatActivity {
    TextView newBeaconName;
    TextView newBeaconAddress;
    TextView newBeaconCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beacon);

        newBeaconName = findViewById(R.id.beaconName);
        newBeaconAddress = findViewById(R.id.beaconAddress);
        newBeaconCode = findViewById(R.id.beaconCode);

        Button addButton = findViewById(R.id.addNewBeaconButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newBeaconName.getText().toString();
                String address = newBeaconAddress.getText().toString();
                String code = newBeaconCode.getText().toString();

                DataBaseEmulator.getInstance().addBeacon(name, address, code);
                finish();
            }
        });


    }
}
