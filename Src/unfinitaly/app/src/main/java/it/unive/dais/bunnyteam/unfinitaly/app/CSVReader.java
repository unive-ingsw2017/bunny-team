package it.unive.dais.bunnyteam.unfinitaly.app;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unive.dais.bunnyteam.unfinitaly.lib.parser.CsvRowParser;

/**
 * Created by giacomo on 17/01/18.
 */

public class CSVReader extends AsyncTask<Void, Integer, Void> {
    LoadingActivity loadingAct;
    ArrayList<MapMarker> items;
    List<CsvRowParser.Row> rows;
    InputStream is;
    public CSVReader(LoadingActivity loadingAct) {
        this.loadingAct = loadingAct;
        items = new ArrayList<>();
        is = loadingAct.getResources().openRawResource(R.raw.csv_ok);
    }

    protected void onPreExecute() {
            /*qui dobbiamo mostrare la progress bar */
        Log.i("CIAO", "SHOWING PROGRESS BAR!");
        ((AVLoadingIndicatorView)loadingAct.findViewById(R.id.avi)).smoothToShow();
        ((ProgressBar)loadingAct.findViewById(R.id.progressBar)).setMax(100);
        ((TextView)loadingAct.findViewById(R.id.tv_status)).setText("Parsing del CSV...");
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        CsvRowParser p = new CsvRowParser(new InputStreamReader(is), true, ";");
        try {
                /*troviamo le rows*/
            rows = p.getAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        int i = 0;
        for (final CsvRowParser.Row r : rows) {
            Log.i("ItemReader", "adding elements");
            items.add(new MapMarker(Double.parseDouble(r.get("lat")), Double.parseDouble(r.get("lon")), Double.parseDouble(r.get("perc_avanzamento").replaceAll(",",".").replaceAll("%","")), r.get("titolo"), r.get("descrizione"), r.get("categoria"), r.get("sottosettore")));
            i++;
            publishProgress(rows.size(), i);
        }
        Log.i("ItemReader", "ending reading.. Readed : " + items.size());
        return null;
    }


    protected void onProgressUpdate(Integer... ints) {
        int max = ints[0].intValue();
        int cur = ints[1].intValue();
        String count = cur + "/" + max;
        ((TextView)loadingAct.findViewById(R.id.tv_status)).setText("Creazione Markers...");
        ((TextView) loadingAct.findViewById(R.id.tvCountLoad)).setText(count);
        ((ProgressBar) loadingAct.findViewById(R.id.progressBar)).setProgress(cur * 100 / max);
    }
    @Override
    protected void onPostExecute(Void v) {
            /*terminiamo la LoadingActivity*/
            /*aggiorniamo i marker e salviamoli in cache -> va fatto qui perchè devono essere salvati quando ho finito di leggerli*/
        MapMarkerList.getInstance().setMapMarkers(items);
        try {
            MapsItemIO.saveToCache(MapMarkerList.getInstance(),loadingAct);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(loadingAct.webview.getVisibility()== View.INVISIBLE)
            loadingAct.startMapsActivity();
        else
            loadingAct.setStatus(1);
    }
}