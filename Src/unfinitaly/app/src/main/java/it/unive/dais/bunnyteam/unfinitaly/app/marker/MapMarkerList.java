package it.unive.dais.bunnyteam.unfinitaly.app.marker;

import android.content.Context;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import it.unive.dais.bunnyteam.unfinitaly.app.LoadingActivity;
import it.unive.dais.bunnyteam.unfinitaly.app.MapsItemIO;


/**
 * Created by giacomo on 06/01/18.
 */

public class MapMarkerList implements Serializable{
    private static MapMarkerList instance = null;
    private ArrayList<MapMarker> mapMarkers = null;
    public static MapMarkerList getInstance(){
        if (instance == null)
            instance = new MapMarkerList();
        return instance;
    }
    private MapMarkerList(){
        mapMarkers = new ArrayList<MapMarker>();
    }
    private MapMarkerList(ArrayList<MapMarker> list){
        mapMarkers = list;
    }
    public ArrayList<MapMarker> getMapMarkers(){
        return instance.mapMarkers;
    }
    public void setMapMarkers(ArrayList<MapMarker> mapMarkers) { instance.mapMarkers = mapMarkers; }
    public boolean loadFromCache(Context context) throws IOException, ClassNotFoundException {
        return MapsItemIO.readFromCache(context);
    }
    public void loadFromCsv(LoadingActivity loadAct) throws InterruptedException, ExecutionException, IOException {
        //instance.setMapMarkers(MapsItemIO.readFromCsvAsync(loadAct));
        new MapsItemIO().loadFromCsv(loadAct);
    }
    public static void setInstance(MapMarkerList instance){
        MapMarkerList.instance = instance;
    }


}
