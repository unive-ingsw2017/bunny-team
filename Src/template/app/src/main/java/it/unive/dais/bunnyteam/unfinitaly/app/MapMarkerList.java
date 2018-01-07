package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by giacomo on 06/01/18.
 */

public class MapMarkerList implements Serializable{
    private static MapMarkerList instance = null;
    private Context context;
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
    public void loadFromCache(Context context) throws IOException, ClassNotFoundException {
        MapsItemIO.readFromCache(context);
    }
    public void loadFromCsv(Context context) throws InterruptedException, ExecutionException, IOException {
        instance.setMapMarkers(MapsItemIO.readFromCsvAsync(context));
        MapsItemIO.saveToCache(instance,context);
    }
    public static void setInstance(MapMarkerList instance){
        MapMarkerList.instance = instance;

    }


}
