package com.example.bogdanaiurchienko.myapplication.model;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class ServerConnection  extends AsyncTask<String, Void, String> {

    private HttpURLConnection conn;
    private String hostName = "http://192.168.0.102/test.php";

    public ServerConnection(){

    }

    public void getRequest(String id){

    }



    @Override
    protected String doInBackground(String... strings) {
        String adress = this.hostName;
        StringBuilder sb = new StringBuilder();

        if(strings[0].equals("get")){
            switch (strings[1]){
                case "beacons":
                    adress =this.hostName+"?action=select&id=beacons";
                    break;

                case "notes":
                    adress =this.hostName+"?action=select&id=notes";
                    break;

                default:
                    adress =this.hostName+"?action=select&id="+strings[1];
                    break;
            }
        }
       else if(strings[0].equals("insert")){
            adress =this.hostName+"?action=insert&id="+strings[1];
        }
        else if(strings[0].equals("delete")){
            adress =this.hostName+"?action=delete&id="+strings[1];
        }
        else if(strings[0].equals("update")){
            adress =this.hostName+"?action=update&id="+strings[1];
        }

        try {
            this.conn = (HttpURLConnection) new URL(adress).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
//            conn.setRequestProperty("User.-Agent", "Mozilla/5.0");
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, "UTF-8"));
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            conn.disconnect();
            is.close(); // закроем поток
            br.close(); // закроем буфер

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



}
