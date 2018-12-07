package com.example.bogdanaiurchienko.myapplication.select.beacons;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.bogdanaiurchienko.myapplication.R;
import com.example.bogdanaiurchienko.myapplication.model.Beacon;
import com.example.bogdanaiurchienko.myapplication.model.DataBaseConnector;
import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;

import java.util.ArrayList;
import java.util.List;

public class ListViewWithCheckboxActivity extends AppCompatActivity {

    int noteId;
    DataBaseConnector db = DataBaseEmulator.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_with_checkbox);

        setTitle("Select All needed beacons");

        noteId = getIntent().getIntExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", -1);
        final ListView listViewWithCheckbox = findViewById(R.id.list_view_with_checkbox);
        final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList(db.getBeacons(),
                db.getNote(noteId).getBeacons());

        // Create a custom list view adapter with checkbox control.
        final ListViewItemCheckboxBaseAdapter listViewDataAdapter =
                new ListViewItemCheckboxBaseAdapter(getApplicationContext(),
                        initItemList, db.getNote(noteId).getBeacons());

        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listViewWithCheckbox.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);
                // Translate the selected item to DTO object.
                ListViewItemDTO itemDto = (ListViewItemDTO)itemObject;
                // Get the checkbox.
                CheckBox itemCheckbox = view.findViewById(R.id.list_view_item_checkbox);
                // Reverse the checkbox and clicked item check state.
                if(itemDto.isChecked()) {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                }else {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }
            }
        });

        Button selectNoneButton = findViewById(R.id.list_select_none);
        selectNoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = initItemList.size();
                for(int i=0;i<size;i++) {
                    ListViewItemDTO dto = initItemList.get(i);
                    dto.setChecked(false);
                }

                listViewDataAdapter.notifyDataSetChanged();
            }
        });

        selectNoneButton.getRootView().setBackgroundColor(Color.parseColor(
                getIntent().getStringExtra("com.example.bogdanaiurchienko.myapplication.BG_COLOR")));

        Button cancelButton = findViewById(R.id.list_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });


        Button okButton = findViewById(R.id.list_ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Beacon> beacons = new ArrayList<>();
                int size = initItemList.size();
                for(int i=0;i<size;i++) {
                    ListViewItemDTO dto = initItemList.get(i);
                    if(dto.isChecked()){
                        beacons.add(dto.getBeacon());
                    }
                }
                db.getNote(noteId).setBeacons(beacons);
                finish();
            }
        });


    }


    // Return an initialize list of ListViewItemDTO.
    private List<ListViewItemDTO> getInitViewItemDtoList(ArrayList<Beacon> beacons, ArrayList<Beacon> noteBeacons)
    {
        List<ListViewItemDTO> ret = new ArrayList<>();
        for (Beacon beacon : beacons) {
            ListViewItemDTO dto = new ListViewItemDTO();
            if(noteBeacons.contains(beacon)) {
                dto.setChecked(true);
            }  else{
                dto.setChecked(false);
            }
            dto.setBeacon(beacon);
            ret.add(dto);
        }
        return ret;
    }
}
