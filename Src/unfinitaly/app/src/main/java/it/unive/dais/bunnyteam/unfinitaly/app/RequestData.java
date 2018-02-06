package it.unive.dais.bunnyteam.unfinitaly.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarker;
import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;

/**
 * Created by Enrico on 30/01/2018.
 */

public class RequestData extends AsyncTask<Void, Void, String>{
        Activity requested;

        public RequestData(Activity requested) {
            this.requested = requested;
        }

        protected void onPreExecute() {
            Log.i("CIAO", "SHOWING PROGRESS BAR!");
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... type) {
            StringBuffer htmlCode = new StringBuffer();
            try {
                URL url = new URL("http://unfinitaly.altervista.org/api/get_opere.php");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                Log.i("CIAO", "url -> "+url.toString());
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    htmlCode.append(inputLine);
                    Log.d("CIAO", "html: " + inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("CIAO", "Error: " + e.getMessage());
                Log.d("CIAO", "HTML CODE: " + htmlCode);
                Log.i("CIAO", "Errore: non posso ricevere il file!");
            }
            return htmlCode.toString();
        }
    protected void onPostExecute(String result) {
        ArrayList<MapMarker> mmList;
        //result contiene il JSON delle domande.
        //bisogna estrarre domanda e risposta e salvarle su questions
        if(!result.equals("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<MapMarker>>() {
            }.getType();
            Log.i("CIAO", "JSOOON" + result);
            mmList = gson.fromJson(result, type);
            Log.i("CIAO", "SIZE -> "+mmList.size());
            Log.i("CIAO",mmList.get(0).getCup());
            for(MapMarker marker : mmList){
                Log.i("CIAO", marker.getSnippet());
            }
            MapMarkerList.getInstance().setMapMarkers(mmList);
            ((TestingActivity)requested).startMapsActivity();
        //updateServerGroup();
        //progressDialog.dismiss();
        super.onPostExecute(result);
    }
}
}
