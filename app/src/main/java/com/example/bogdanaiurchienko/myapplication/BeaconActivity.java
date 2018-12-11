package com.example.bogdanaiurchienko.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bogdanaiurchienko.myapplication.model.Beacon;
import com.example.bogdanaiurchienko.myapplication.model.DataBaseConnector;
import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;
import com.example.bogdanaiurchienko.myapplication.model.Note;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class BeaconActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }

        }


        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        // beaconManager.getBeaconParsers().add(new BeaconParser().
        //        setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
         beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(" m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
               System.out.println( "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                System.out.println( "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                System.out.println( "I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
            }
        }
    }


    private void showNotesNotifications(String beaconCode) {
        boolean notificationsOn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications", true);
        boolean notificationsVibrate = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications_vibrate", false);
        boolean notificationsSilent = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications_silent", false);

        DataBaseConnector db  = DataBaseEmulator.getInstance();
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


}
