package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import java.lang.reflect.Field;

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

    public static boolean isCached(Context context) {
        File cacheDir = new File(context.getCacheDir(), "files");
        cacheDir.mkdirs();
        File cacheFile = new File(cacheDir, "mapMarkers.obj");
        if (cacheFile.exists())
            return true;
        else
            return false;
    }

    public void loadFromCsv(LoadingActivity loadingActivity){
        new CSVReader(loadingActivity).execute();
    }
    public static boolean readFromCache(Context context) throws IOException, ClassNotFoundException {
        File cacheDir = new File(context.getCacheDir(), "files");
        Log.i("ReadFromCache", "start");
        if (!cacheDir.exists())
            throw new IOException();
        File cachedFile = new File(cacheDir, "mapMarkers.obj");
        FileInputStream is = new FileInputStream(cachedFile);
        ObjectInputStream oIs = new ObjectInputStream(is);
        Log.i("ReadFromCache", "reading");
        Object readed = oIs.readObject();

        Log.i("ciao", "instance of: "+readed.getClass());
        if (readed instanceof MapMarkerList) {
            MapMarkerList.setInstance((MapMarkerList) readed);
            Log.i("ReadFromCache", "readed "+MapMarkerList.getInstance().getMapMarkers().size());
            /*Controllo che il dato nella cache sia uguale a quello da creare come MapMarker in caso di cambiamenti
            Campi classe di base MapMarker
            Field[] nuovo = Class.forName("MapMaker").getDeclaredFields();
            Campi dell'oggetto MapMarker presente nella cache
            Field[] esistente = MapMarkerList.getInstance().getMapMarkers().get(0).getClass().getDeclaredFields();
            if(nuovo.equals(esistente)){
                Log.d("Controllo campi","ok");
            }
            else{
                Log.d("Controllo campi","non ok");
            }*/
            return true;
        }
        else
            return false;
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

