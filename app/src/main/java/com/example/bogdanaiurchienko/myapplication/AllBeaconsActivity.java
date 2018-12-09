package com.example.bogdanaiurchienko.myapplication;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bogdanaiurchienko.myapplication.model.Beacon;
import com.example.bogdanaiurchienko.myapplication.model.DataBaseConnector;
import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;
import com.example.bogdanaiurchienko.myapplication.model.Note;

import java.util.ArrayList;

public class AllBeaconsActivity extends AppCompatActivity {
    DataBaseConnector db = DataBaseEmulator.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_beacond_activity);
        //відображаємо список усіх біконів
        ListView allBeaconsView =  findViewById(R.id.all_beacons_list) ;
        BeaconItemAdapter beaconItemAdapter = new BeaconItemAdapter(this, db.getBeacons());
        allBeaconsView.setAdapter(beaconItemAdapter);
        allBeaconsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showNotesNotifications(db.getBeacons().get(i).getCode());
            }
        });
    }

    private void showNotesNotifications(String beaconCode) {
        boolean notificationsOn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications", true);
        boolean notificationsVibrate = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications_vibrate", false);
        boolean notificationsSilent = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications_silent", false);

        for (Note note : db.getNotes()) {
            if (note.getBeacons().contains(new Beacon(beaconCode)) && notificationsOn) {
                Intent backIntent = new Intent(this, MenuActivity.class);
                backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = new Intent(this, NoteDetailsActivity.class);
                intent.putExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", note.getId());
                final PendingIntent pendingIntent = PendingIntent.getActivities(this, note.getId()*17,
                        new Intent[] {backIntent, intent}, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                        .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                        .setContentTitle(note.getName())
                        .setContentText(note.getText())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                if(notificationsVibrate){
                    mBuilder.setVibrate(new long[] { 1000, 1000});
                }
                if(!notificationsSilent){
                    mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                }
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(note.getId()*17, mBuilder.build());
            }
        }
    }

    class BeaconItemAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private ArrayList<Beacon> beacons;

        BeaconItemAdapter(Context context, ArrayList<Beacon> beacons) {
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.beacons = beacons;
        }


        @Override
        public int getCount() {
            return beacons.size();
        }

        @Override
        public Object getItem(int i) {
            return beacons.get(i);
        }

        @Override
        public long getItemId(int i) {
            return beacons.get(i).getId();
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = layoutInflater.inflate(R.layout.beacon_list_item_layout, null);

            TextView beaconName = view.findViewById(R.id.beaconNameView);
            TextView beaconAddress = view.findViewById(R.id.beaconAddressView);
            TextView beaconCode = view.findViewById(R.id.beaconCodeView);

            Beacon beacon =  beacons.get(i);
            beaconName.setText(beacon.getName());
            beaconAddress.setText(beacon.getAddress());
            beaconCode.setText(beacon.getCode());
            return view;
        }
    }
}




