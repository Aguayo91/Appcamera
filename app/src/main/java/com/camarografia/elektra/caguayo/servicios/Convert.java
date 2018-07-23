package com.camarografia.elektra.caguayo.servicios;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Convert {

    private static final String TAG = "Convert";

    public String getStringFromInputStream(InputStream is) {

        //Log.v("Convert","Entro a getStringFromInputStream");

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                Log.v("Convert","Line = "+line);
            }

        } catch (Exception e) {
            //e.printStackTrace();
            Log.v(TAG,"ERROR AL CONVERTIR: "+e.getMessage());

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //Log.v("Convert","Fin del getString");

        return sb.toString();

    }
}
