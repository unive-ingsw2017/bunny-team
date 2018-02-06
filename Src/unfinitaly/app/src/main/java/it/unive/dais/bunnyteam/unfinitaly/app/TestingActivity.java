package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarker;
import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;
import it.unive.dais.bunnyteam.unfinitaly.app.memory.JsonIO;

public class TestingActivity extends AppCompatActivity {
    String versionLocal = "0";
    boolean outdated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_testing);
        if (!isOnline()) {
            setContentView(R.layout.error_layout);
            ((Button) findViewById(R.id.buttonExit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    System.exit(0);
                }
            });
        }
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            System.exit(0);
        }
        new RequestVersion().execute();

    }

    public void startMapsActivity() {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    class RequestVersion extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            Log.i("CIAO", "getting version from server");
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... type) {
            StringBuffer htmlCode = new StringBuffer();
            try {
                Log.i("CIAO", "reading version from URL");
                URL url = new URL("http://unfinitaly.altervista.org/api/get_version.php");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                Log.i("CIAO", "url -> " + url.toString());
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
            //result contiene il JSON delle domande.
            //bisogna estrarre domanda e risposta e salvarle su questions
            if (!result.equals("")) {

                try {
                    Log.i("CIAO", "readed version from url, check local version");
                    Log.i("CIAO", "isVersionCached: "+JsonIO.isVersionCached(getApplicationContext()));
                    if(!JsonIO.isVersionCached(getApplicationContext())){
                        Log.i("CIAO", "Is NOT cached: saving new version");
                        JsonIO.saveVersionJSON(getApplicationContext(), result);
                        requestJSON();
                    }
                    else{
                        /*check if equals*/
                        Log.i("CIAO", "Is cached: CHECK VERSION");

                        JSONArray arr = null;
                        arr = new JSONArray(result);
                        JSONObject jObj = arr.getJSONObject(0);
                        String versionServer = jObj.getString("version");
                        Log.i("CIAO", "Online version: "+versionServer);
                        String localVersion = JsonIO.readVersion(getApplicationContext());
                        Log.i("CIAO", "Local version: "+versionServer);
                        if(versionServer.equals(localVersion) && JsonIO.isVersionCached(getApplicationContext())){
                            Log.i("CIAO", "version is the same. Reading from JSON");
                            JsonIO.readJSON(getApplicationContext());
                            startMapsActivity();
                        }
                        else{
                            Log.i("CIAO", "outdated :( reading from URL");
                            requestJSON();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                super.onPostExecute(result);
            }
        }
    }

    private void requestJSON() {
        new RequestData(this).execute();
    }

}
