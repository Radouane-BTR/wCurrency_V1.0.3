package apiServices;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getResponses extends AsyncTask<String, Void, String> {
    Handler handler = null;
    public getResponses(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected String doInBackground(String... param) {
        URL url = null;
        try {
            url = new URL(param[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String resultat = null;
        //Ouvrir connection
        BufferedReader in = null;
        HttpURLConnection urlConn = null;
        try {
            urlConn = (HttpURLConnection) url.openConnection();
            //lire le fichier
            in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            // Recuperer le text
            resultat = getJson(in);
            //Log.v(" ** redOne ** ",resultat);
            //fermer la connexion
            urlConn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultat;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("REPONSE" , s);
        msg.setData(data);
        handler.sendMessage(msg);
    }

    private String getJson(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        //Log.v(" ** myJSON ** : ","BufferedReader : "+in.toString());
        String ligne =  null ;
        while ((ligne = in.readLine())!=null){
            sb.append("\n" + ligne);
        }
        return sb.toString();
    }
}
