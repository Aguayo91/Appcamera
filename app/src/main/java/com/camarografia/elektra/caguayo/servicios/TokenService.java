package com.camarografia.elektra.caguayo.servicios;


import android.content.Context;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.TimeZone;

import static com.camarografia.elektra.caguayo.servicios.Constantes.BASE_URL_SERVICES;
import static com.camarografia.elektra.caguayo.servicios.Constantes.IDAPP;
import static com.camarografia.elektra.caguayo.servicios.Constantes.URL_TOKEN;

public class TokenService {

    private final String TAG = "TokenService";

    public void getToken(String nameService) {

        HttpURLConnection con = null;
        Context context;
        String token = null;
        String fecha = "";
        String uri = "";
        String ip = "";
        String idApp = "";
        boolean error = false;

        try {

            SimpleDateFormat fechaSDF = new SimpleDateFormat("ddMMyyyyHHmmss");
            fechaSDF.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
            fecha = "fecha=" + fechaSDF.format(new Date());
            Log.v("fecha", "fecha mexico: " + fecha);

            uri = "uri=" + BASE_URL_SERVICES + nameService;
            ip = "ip=" + getLocalIpAddress();
            idApp = "idapl=" + IDAPP;

            String cadena = idApp + "&" + UtilCryptoGS.encrypt(ip + "&" + fecha + "&" + uri).replace("\n", "");
            Log.v(TAG, "CADENA = " + cadena);

            URL url = new URL(URL_TOKEN + "?" + cadena); // execute()

            Log.v(TAG, "URL = " + URL_TOKEN + "?" + cadena);

            new JSONTask().execute(url.toString());

        } catch (Exception e) {
            Log.v(TAG, "Error al convertir Token JSON " + e.getMessage());
            token = null;

        }
       // return token;


    }

    public String getLocalIpAddress() {

        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ip = getIpAsString(inetAddress);

                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ip;
    }

    private String getIpAsString(InetAddress address) {
        byte[] ipAddress = address.getAddress();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ipAddress.length; i++) {
            if (i > 0) str.append('.');
            str.append(ipAddress[i] & 0xFF);
        }
        return str.toString();
    }


    protected void onPostExecute(String mensaje) {


    }
}
