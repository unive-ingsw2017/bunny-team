package it.unive.dais.bunnyteam.unfinitaly.app;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by giacomo on 06/01/18.
 */

public class MapMarkerList implements Serializable{
    private ArrayList<MapMarker> mapMarkers;

    public MapMarkerList(){
        mapMarkers = new ArrayList<MapMarker>();
    }
    public MapMarkerList(ArrayList<MapMarker> list){
        mapMarkers = list;
    }
    public ArrayList<MapMarker> getMapMarkers(){
        return mapMarkers;
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

}
