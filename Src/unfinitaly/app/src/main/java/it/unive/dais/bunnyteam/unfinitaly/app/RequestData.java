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
import it.unive.dais.bunnyteam.unfinitaly.app.memory.JsonIO;

/**
 * Created by Enrico on 30/01/2018.
 */

public class RequestData extends AsyncTask<Void, Void, String>{
        Activity requested;

        public RequestData(Activity requested) {
            this.requested = requested;
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... type) {
            StringBuffer htmlCode = new StringBuffer();
            try {
                Log.i("CIAO","Readding opere from URL");
                URL url = new URL("http://unfinitaly.altervista.org/api/get_opere.php");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                Log.i("CIAO", "url -> "+url.toString());
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    htmlCode.append(inputLine);
                    //Log.d("CIAO", "html: " + inputLine);
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
        if(!result.equals("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<MapMarker>>() {}.getType();
            mmList = gson.fromJson(result, type);
            Log.i("CIAO","Readed opere From URL, save to JSON");
            JsonIO.saveJSON(requested.getApplicationContext(), result);
            MapMarkerList.getInstance().setMapMarkers(mmList);
            ((TestingActivity)requested).startMapsActivity();

        super.onPostExecute(result);
    }
}
}
