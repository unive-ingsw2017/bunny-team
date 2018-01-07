package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;


import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unive.dais.bunnyteam.unfinitaly.lib.parser.CsvRowParser;

/**
 * Created by giacomo on 06/01/18.
 *
 */

public class MapsItemIO {
    /**
     * per ora non è asincrono, ma andrebbe fatto in modo async. LoadingActivity chiama readFromCsvAsync (che sarà asincrono).
     *      TODO: Async pre: chiama metodo showStatusBar() di LoadingActivity
     *      TODO: Async post: chiama metodo startMapsActivity da LoadingActivity
     *      TODO: Async durante: chiama metodo showProgress(int progress, int total) di LoadingActivity che aggiorna testo su TextView di LoadingActivity
     *                              es. Caricamento... 34/634 (oppure fare percentuale tipo Caricamento 56%)
     * @param context
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public static ArrayList<MapMarker> readFromCsvAsync(Context context) throws ExecutionException, InterruptedException, IOException {
        /*VA FATTO ASYNCRONAMENTE*/
        Log.i("ItemReader", "starting reading...");
        ArrayList<MapMarker> items = new ArrayList<MapMarker>();
        InputStream is = context.getResources().openRawResource(R.raw.csv_ok);
        CsvRowParser p = new CsvRowParser(new InputStreamReader(is), true, ";");
        List<CsvRowParser.Row> rows = p.getAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        for (final CsvRowParser.Row r : rows) {
            Log.i("ItemReader", "adding elements");
            items.add(new MapMarker(Double.parseDouble(r.get("lat")), Double.parseDouble(r.get("lon")), r.get("titolo"), r.get("descrizione")));
        }
        Log.i("ItemReader", "ending reading.. Readed : " + items.size());
        return items;
    }

    public static boolean isCached(Context context) {
        File cacheDir = new File(context.getCacheDir(), "files");
        cacheDir.mkdirs();
        File cacheFile = new File(cacheDir, "mapMarkers.obj");
        if (cacheFile.exists())
            return true;
        else
            return false;
    }

    public static void readFromCache(Context context) throws IOException, ClassNotFoundException {
        File cacheDir = new File(context.getCacheDir(), "files");
        Log.i("ReadFromCache", "start");
        if (!cacheDir.exists())
            throw new IOException();
        File cachedFile = new File(cacheDir, "mapMarkers.obj");
        FileInputStream is = new FileInputStream(cachedFile);
        ObjectInputStream oIs = new ObjectInputStream(is);
        Log.i("ReadFromCache", "reading");
        Object readed = oIs.readObject();
        if (readed instanceof MapMarkerList)
            MapMarkerList.setInstance((MapMarkerList) readed);
    }

    public static void saveToCache(MapMarkerList mmL, Context context) throws IOException {
        Log.i("Save to Cache", "start: size = " + mmL.getMapMarkers().size());
        if (mmL != null) {
            File cacheDir = new File(context.getCacheDir(), "files");
            if (!cacheDir.exists())
                cacheDir.mkdir();
            Log.i("Save to Cache", "saving");
            File cachedFile = new File(cacheDir, "mapMarkers.obj");
            ObjectOutputStream Oos = new ObjectOutputStream(new FileOutputStream(cachedFile));
            Oos.writeObject(mmL);
        }
    }
}
