package com.example.bogdanaiurchienko.myapplication.model;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class ServerConnection  extends AsyncTask<Void, Void, String> {

    private HttpURLConnection conn;
    private String hostName = "http://192.168.0.102/test.php?id=Dima";

    public  void request() throws MalformedURLException {

    }



    @Override
    protected String doInBackground(Void... voids) {
        try {
            this.conn = (HttpURLConnection) new URL(this.hostName).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User.-Agent", "Mozilla/5.0");
            conn.setDoInput(true);
            conn.connect();

            //////
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            System.out.println("полный ответ сервера:\n"+ sb.toString());
            conn.disconnect();
            is.close(); // закроем поток
            br.close(); // закроем буфер

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Success";
    }
}
