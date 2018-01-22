package it.unive.dais.bunnyteam.unfinitaly.app;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import it.unive.dais.bunnyteam.unfinitaly.lib.util.MapItem;

/**
 * Created by giacomo on 06/01/18.
 */

public class MapMarker extends MapItem implements ClusterItem, Serializable {
    private double lat;
    private double lng;
    private double percentage;
    private String title;
    private String snippet;
    private String categoria;
    private String sottosettore;


    public MapMarker(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        this.percentage = percentage;
        title = "";
        snippet = "";
    }

    public MapMarker(double lat, double lng, double percentage, String title, String snippet, String categoria, String sottosettore) {
        this.lat = lat;
        this.lng = lng;
        this.percentage = percentage;
        this.title = title;
        this.snippet = snippet;
        this.categoria = categoria;
        this.sottosettore = sottosettore;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(lat, lng);
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getSnippet() { return snippet; }

    /**
     * Set the title of the marker
     * @param title string to be set as title
     */
    public void setTitle(String title) {this.title = title;
    }

    /**
     * Set the description of the marker
     * @param snippet string to be set as snippet
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getPercentage(){
        return this.percentage;
    }

    public String getCategoria(){
        return this.categoria;
    }

    public String getSottosettore(){
        return this.sottosettore;
    }
}
