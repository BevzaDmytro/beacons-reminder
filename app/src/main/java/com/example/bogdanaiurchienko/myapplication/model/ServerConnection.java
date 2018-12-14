package com.example.bogdanaiurchienko.myapplication.model;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class ServerConnection  extends AsyncTask<String, Void, String> {

    private HttpURLConnection conn;
    private String hostName = "http://192.168.0.103/beacons-server/api/";
//    private String hostName = "http://10.241.128.107/beacons-server/api/";

    public ServerConnection(){

    }

    public void getRequest(String id){

    }



    @Override
    protected String doInBackground(String... strings) {
        String adress = this.hostName;
        StringBuilder sb = new StringBuilder();
        String method = "GET";
        if(strings[0].equals("get")){

            switch (strings[1]){
                case "beacons":
                    adress =this.hostName+"beacons";
                    break;

                case "beacon":
                    adress = this.hostName +"beacon/"+strings[2];
                    break;

                case "notes":
                    adress =this.hostName+"notes";
                    break;

                default:
                    adress =this.hostName+"note/"+strings[1];
                    break;
            }
        }
       else if(strings[0].equals("insert")){
            adress =this.hostName+"note";
            method = "POST";
        }
        else if(strings[0].equals("delete")){
            adress =this.hostName+"note/"+strings[1];
            method = "DELETE";
        }
        else if(strings[0].equals("update")){
            adress =this.hostName+"edit";
            method = "POST";
        }

        try {
            this.conn = (HttpURLConnection) new URL(adress).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(method);
//            conn.setRequestProperty("User.-Agent", "Mozilla/5.0");
            conn.setDoInput(true);
            conn.connect();
            if(method.equals("POST") && strings[0].equals("update")) {
                byte[] postData       = strings[1].getBytes( StandardCharsets.UTF_8 );
                try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                    wr.write(postData);
                }
            }

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
