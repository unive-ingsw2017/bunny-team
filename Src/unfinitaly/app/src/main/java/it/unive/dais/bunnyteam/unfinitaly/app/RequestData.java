package it.unive.dais.bunnyteam.unfinitaly.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * Created by Enrico on 30/01/2018.
 */

public class RequestData extends AsyncTask<String, Void, String>{
        Activity requested;
        private double lat;
        private double lng;
        private double percentage;
        private double importo_ultimo_qe;
        private double importo_ultimo_qe_approvato;
        private double importo_sal;
        private String regione;
        private String title;
        private String snippet; //Questo non è presente nel DB
        private String categoria;
        private String sottosettore;
        private String causa;
        private String tipologia_cup;
        private String cup;
        public RequestData(Activity requested, double lat, double lng, double percentage, double qe, double qe_approvato, double sal, String title, String snippet, String categoria, String sottosettore, String regione, String causa, String tipologia_cup, String cup) {
            this.requested = requested;
            this.lat = lat;
            this.lng = lng;
            this.percentage = percentage;
            this.importo_ultimo_qe = qe;
            this.importo_ultimo_qe_approvato = qe_approvato;
            this.importo_sal = sal;
            this.title = title;
            this.snippet = snippet;
            this.categoria = categoria;
            this.sottosettore = sottosettore;
            this.regione = regione;
            this.causa = causa;
            this.tipologia_cup = tipologia_cup;
            this.cup = cup;
        }

        protected void onPreExecute() {
            Log.i("CIAO", "SHOWING PROGRESS BAR!");
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... type) {
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
}
