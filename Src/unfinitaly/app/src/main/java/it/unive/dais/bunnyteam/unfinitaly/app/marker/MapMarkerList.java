package it.unive.dais.bunnyteam.unfinitaly.app.marker;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import it.unive.dais.bunnyteam.unfinitaly.app.LoadingActivity;
import it.unive.dais.bunnyteam.unfinitaly.app.memory.MapsItemIO;


/**
 * Created by giacomo on 06/01/18.
 */

public class MapMarkerList implements Serializable{
    private static MapMarkerList instance = null;
    private ArrayList<MapMarker> mapMarkers = null;
    /*INCREMENTARE VERSION_ID SE SI EFFETTUANO MODIFICHE ALLA CLASSE
    * Modificare entrambi gli int: VERSION_ID serve per controllare il num. di versione salvato in memoria,
    * STATIC_VERSION_ID invece per controllare la versione della classe.
    * */
    private int VERSION_ID;
    private static int STATIC_VERSION_ID=5;
    public static MapMarkerList getInstance(){
        if (instance == null) {
            instance = new MapMarkerList();
            instance.VERSION_ID = STATIC_VERSION_ID;
        }
        return instance;
    }
    private MapMarkerList(){
        mapMarkers = new ArrayList<MapMarker>();
    }
    private MapMarkerList(ArrayList<MapMarker> list){
        mapMarkers = list;
    }

    public static int getStaticVersionId() {
        return STATIC_VERSION_ID;
    }

    public ArrayList<MapMarker> getMapMarkers(){
        return instance.mapMarkers;
    }
    public void setMapMarkers(ArrayList<MapMarker> mapMarkers) { instance.mapMarkers = mapMarkers; }
    public boolean loadFromCache(Context context) throws IOException, ClassNotFoundException {
        return MapsItemIO.readFromCache(context);
    }
    public void loadFromCsv(LoadingActivity loadAct, TextView tv_status, TextView tvCountLoad, ProgressBar progressBar) throws InterruptedException, ExecutionException, IOException {
        //instance.setMapMarkers(MapsItemIO.readFromCsvAsync(loadAct));
        new MapsItemIO().loadFromCsv(loadAct, tv_status, tvCountLoad, progressBar);
    }
    public static void setInstance(MapMarkerList instance){
        MapMarkerList.instance = instance;
    }

    public int getVersionId(){
        return this.VERSION_ID;
    }

}
