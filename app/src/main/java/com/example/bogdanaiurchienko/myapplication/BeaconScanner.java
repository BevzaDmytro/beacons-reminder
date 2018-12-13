package com.example.bogdanaiurchienko.myapplication;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;
import com.example.bogdanaiurchienko.myapplication.model.Note;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class BeaconScanner extends Service implements BeaconConsumer {
    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;
    private Region region;

    public BeaconScanner() {
        region = new Region("myMonitoringUniqueId", null, null, null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
        
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        // beaconManager.getBeaconParsers().add(new BeaconParser().
        //        setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(" m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onBeaconServiceConnect() {
        Log.i(TAG,"onBeaconServiceConnect     :  ");

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
                try {
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG,"I no longer see a beacon");
                try {
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG,"I have just switched from seeing/not seeing beacons: " + state);

            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                for(org.altbeacon.beacon.Beacon oneBeacon : collection) {
                    Log.i(TAG,"distance: " + oneBeacon.getDistance() + " id:" + oneBeacon.getId1() + "/" + oneBeacon.getId2() + "/" + oneBeacon.getId3());
                    Log.i(TAG,"oneBeacon.getId1().toUuid().toString():  " + oneBeacon.getId1().toUuid().toString());
                    showNotesNotifications(oneBeacon.getId1().toUuid().toString());
                }
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy     :  ");

        try {
            beaconManager.stopMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        beaconManager.unbind(this);
    }



    private void showNotesNotifications(String beaconCode) {
        boolean notificationsOn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications", true);
        boolean notificationsVibrate = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications_vibrate", false);
        boolean notificationsSilent = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications_silent", false);

        for (Note note : DataBaseEmulator.getInstance().getNotes()) {
            if (note.getBeacons().contains(new com.example.bogdanaiurchienko.myapplication.model.Beacon(beaconCode)) && notificationsOn) {
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
