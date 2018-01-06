package it.unive.dais.bunnyteam.unfinitaly.lib.util;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Classe astratta che rappresenta una entità visualizzabile su una mappa.
 * I metodi presenti sono il minimo indispensabile per creare un Marker in una GoogleMap, dotato di posizione, titolo e descrizione.
 *
 * @author Alvise Spanò, Università Ca' Foscari
 */
public abstract class MapItem implements Serializable {

    /**
     * Ritorna la posizione.
     *
     * @return la posizione in un oggetto di tipo LatLng
     */
    public abstract LatLng getPosition();

    /**
     * Ritorna il titolo.
     *
     * @return la stringa col titolo.
     */
    public String getTitle() {
        return toString();
    }

    /**
     * Ritorna la descrizione.
     *
     * @return la stringa con la descrizione.
     */
    public String getDescription() {
        return getTitle();
    }

    @NonNull
    public static <I extends MapItem> Collection<Marker> putMarkersFromMapItems(GoogleMap gMap, List<I> l) {
        Collection<Marker> r = new ArrayList<>();
        for (MapItem i : l) {
            MarkerOptions opts = new MarkerOptions().title(i.getTitle()).position(i.getPosition()).snippet(i.getDescription());
            r.add(gMap.addMarker(opts));
        }
        return r;
    }
}
