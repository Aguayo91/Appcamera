package com.camarografia.elektra.caguayo.servicios;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONTask extends AsyncTask<String, String,String> {

    String xml;
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection=null;
        BufferedReader reader=null;
        OutputStreamWriter wr=null;
        BufferedReader rd;


        try{
            URL url=new URL(params[0]);
            connection=(HttpURLConnection)url.openConnection();
            connection.connect();

         rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//

            InputStream stream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer=new StringBuffer();
            String line="";
            while((line=reader.readLine()) !=null){
                buffer.append(line);
            }
            return buffer.toString();
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (connection != null) {
                connection.disconnect();
            }
            try{
                if(reader!=null){
                    reader.close();
                }

                if(wr != null)
                    wr.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        return null;
    }

    Context context;

    public JSONTask(){

    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String resultado=result;
       TextView tvData = ((Activity)context).findViewById(R.id.textView2);
       tvData.setText( result.toString());

       Log.i("Resultado: " , result.toString());


    }
}