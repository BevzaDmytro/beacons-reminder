package com.example.bogdanaiurchienko.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

public class BeaconsScanner extends Application implements BeaconConsumer, BootstrapNotifier  {
    private static final String TAG = ".BeaconsScanner";
    private RegionBootstrap regionBootstrap;
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
//        Log.d(TAG, "App started up");
        System.out.println("YESSS");
         beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        // beaconManager.getBeaconParsers().add(new BeaconParser().
        //        setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        // wake up the app when any beacon is seen (you can specify specific id filers in the parameters below)
        Region region = new Region("com.example.myapp.boostrapRegion", null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);
    }

    @Override
    public void onBeaconServiceConnect() {
System.out.println("onBeaconServiceConnect run");
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse("MY_UUID"), Identifier.parse("1"), null));

        } catch (RemoteException e) {
           e.printStackTrace();
        }

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
//                    Log.i(TAG, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
System.out.println("The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");

//                    Log.i(TAG, "Reading…"+"\n"+"proximityUuid:"+" "+ beacons.iterator().next().getId1()+"\n"+
                    System.out.println("Reading…"+"\n"+"proximityUuid:"+" "+ beacons.iterator().next().getId1()+"\n"+
                            "major:"+" "+beacons.iterator().next().getId2()+"\n"+
                            "minor:"+" "+beacons.iterator().next().getId3());}

            }
        });


        beaconManager.setMonitorNotifier(new MonitorNotifier() {

            @Override
            public void didEnterRegion(Region region) {
//                Log.i(TAG, "I just saw an beacon for the first time!");
                System.out.println("I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));

        } catch (RemoteException e) {
        }
    }


    @Override
    public void didEnterRegion(Region region) {
        System.out.println("I just saw an beacon for the first time!");
    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
