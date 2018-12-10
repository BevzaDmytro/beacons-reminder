package com.example.bogdanaiurchienko.myapplication.select.beacons;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bogdanaiurchienko.myapplication.R;
import com.example.bogdanaiurchienko.myapplication.model.Beacon;

import java.util.ArrayList;
import java.util.List;

public class ListViewItemCheckboxBaseAdapter extends BaseAdapter {
    private List<ListViewItemDTO> listViewItemDtoList;
    private ArrayList<Beacon> noteBeacons;

    private Context ctx;

    ListViewItemCheckboxBaseAdapter(Context ctx, List<ListViewItemDTO> listViewItemDtoList, ArrayList<Beacon> beacons) {
        this.ctx = ctx;
        this.listViewItemDtoList = listViewItemDtoList;
        this.noteBeacons = beacons;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(listViewItemDtoList!=null)
        {
            ret = listViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        ListViewItemViewHolder viewHolder;

        if(convertView!=null)
        {
            viewHolder = (ListViewItemViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.activity_list_view_with_checkbox_item, null);
            CheckBox listItemCheckbox = convertView.findViewById(R.id.list_view_item_checkbox);
            TextView beaconNameView = convertView.findViewById(R.id.beaconName);
            TextView beaconAddressView = convertView.findViewById(R.id.beaconAddress);
            TextView beaconCodeView = convertView.findViewById(R.id.beaconCode);

            viewHolder = new ListViewItemViewHolder(convertView);
            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setBeaconName(beaconNameView);
            viewHolder.setBeaconAddress(beaconAddressView);
            viewHolder.setBeaconCode(beaconCodeView);

            convertView.setTag(viewHolder);
        }

        ListViewItemDTO listViewItemDto = listViewItemDtoList.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(listViewItemDto.isChecked());
        viewHolder.getBeaconName().setText(listViewItemDto.getBeacon().getName());
        viewHolder.getBeaconAddress().setText(listViewItemDto.getBeacon().getAddress());
        viewHolder.getBeaconCode().setText(listViewItemDto.getBeacon().getCode());

        return convertView;
    }
}
