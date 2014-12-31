package com.example.osdevlab.simpletutorial;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by osdevlab on 12/30/14.
 */


public class JsonHelperClass {
    Context context;
    private String result = "default result";

    //JsonHelperClass Constructor
    public JsonHelperClass(Context context) {
        // context is passed from FragmentOne.java; to be used in JsonHelperClass
        this.context = context;
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String getResult() {
        //only fetch json if there is network connection
        if (isConnectedToNetwork()) {
            fetchMeJson();
        }
        return result;
    }

    private boolean isConnectedToNetwork() {
        boolean connection;
        //ConnectivityManager required context
        //context is passed from FragmentOne.java
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // small popup to show the status of network connection
            Toast.makeText(context, "Internet is connected",
                    Toast.LENGTH_SHORT).show();
            connection = true;
        } else {
            Toast.makeText(context, "Internet is not connected",
                    Toast.LENGTH_SHORT).show();
            connection = false;
        }
        return connection;
    }

    private void fetchMeJson() {

        //Ref website
        // http://stackoverflow.com/questions/8706464/defaulthttpclient-to-androidhttpclient
        //this is just for the simplicity
        //it is always recommended to move network operation off the UI thread, for example, using AsyncTask.
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {

            //Ref from http://www.tutorialspoint.com/android/android_json_parser.htm
            URL url = new URL("http://date.jsontest.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 ); // in milliseconds
            conn.setConnectTimeout(15000); // in milliseconds
            //below two line are default themselves; no need to set for getting data
            //but need to set explicitly if we want to post data
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            InputStream stream = conn.getInputStream();

            String data = convertStreamToString(stream);

            readAndParseJSON(data);
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readAndParseJSON(String in) {
        try {
            JSONObject reader = new JSONObject(in);
            //time is expressed using the GMT time zone.
            result = reader.getString("time");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
