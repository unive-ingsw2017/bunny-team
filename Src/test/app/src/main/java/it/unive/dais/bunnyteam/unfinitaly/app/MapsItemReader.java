package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


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

public class MapsItemReader{
    Context context;
    public MapsItemReader(Context context){
        this.context = context;
    }

    public MapMarkerList read() throws ExecutionException, InterruptedException, IOException {
        MapMarkerList mmL = null;
        if(isCached()) {
            try {
                Log.i("ItemReader", "read from cache");
                mmL = readFromCache();
                if (mmL == null){
                    mmL = readFromCsv();
                    saveToCache(mmL);
                }
                Log.i("ItemReader", "done.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
            mmL = readFromCsv();
        return mmL;
    }

    private MapMarkerList readFromCsv() throws ExecutionException, InterruptedException, IOException {
        Log.i("ItemReader", "starting reading...");
        ArrayList<MapMarker> items = new ArrayList<MapMarker>();
        InputStream is = context.getResources().openRawResource(R.raw.csv_ok);
        CsvRowParser p = new CsvRowParser(new InputStreamReader(is), true, ";");
        List<CsvRowParser.Row> rows = p.getAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        for (final CsvRowParser.Row r : rows) {
            Log.i("ItemReader", "adding elements");
            items.add(new MapMarker(Double.parseDouble(r.get("lat")), Double.parseDouble(r.get("lon")), r.get("titolo"), r.get("descrizione")));
        }
        Log.i("ItemReader", "ending reading.. Readed : "+items.size());
        MapMarkerList mmL = new MapMarkerList(items);
        Log.i("ItemReader", "saving to cache!!!!");
        saveToCache(mmL);
        return mmL;
    }
    private boolean isCached(){
        File cacheDir = new File(context.getCacheDir(), "files");
        cacheDir.mkdirs();
        File cacheFile = new File(cacheDir, "mapMarkers.obj");
        if (cacheFile.exists())
            return true;
        else
            return false;
    }

    private MapMarkerList readFromCache() throws IOException, ClassNotFoundException {
        MapMarkerList mmL = null;
        File cacheDir = new File(context.getCacheDir(), "files");
        Log.i("ReadFromCache", "start");
        if (!cacheDir.exists())
            throw new IOException();
        File cachedFile = new File(cacheDir, "mapMarkers.obj");
        FileInputStream is = new FileInputStream(cachedFile);
        ObjectInputStream oIs = new ObjectInputStream(is);
        Log.i("ReadFromCache", "reading");
        Object readed = oIs.readObject();
        Log.i("ReadFromCache", "okk");
        if (readed instanceof MapMarkerList)
            mmL = (MapMarkerList) readed;
        Log.i("ReadFromCache", "mmL size: "+mmL.getMapMarkers().size());
        return mmL;
    }

    private void saveToCache(MapMarkerList mmL) throws IOException {
        Log.i("Save to Cache", "start: size = "+mmL.getMapMarkers().size());
        if(mmL != null){
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
