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
    //VARIABILI
    private double lat;
    private double lng;
    private double percentage;
    private double importo_ultimo_qe;
    private double importo_ultimo_qe_approvato;
    private double importo_sal;
    private String regione;
    private String title;
    private String snippet;
    private String categoria;
    private String sottosettore;
    private String causa;
    private String tipologia_cup;
    private String cup;

    //COSTRUTTORI
    public MapMarker(){
        this.lat = 0;
        this.lng = 0;
        this.percentage = 0;
        this.importo_ultimo_qe = 0;
        this.importo_ultimo_qe_approvato = 0;
        this.importo_sal = 0;
        title = "";
        snippet = "";
        regione = "";
        causa = "";
        tipologia_cup = "";
        cup = "";
    }

    public MapMarker(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        this.percentage = 0;
        this.importo_ultimo_qe = 0;
        this.importo_ultimo_qe_approvato = 0;
        this.importo_sal = 0;
        title = "";
        snippet = "";
        regione = "";
        causa = "";
        tipologia_cup = "";
        cup = "";
    }

    public MapMarker(double lat, double lng, double percentage, double qe, double qe_approvato, double sal, String title, String snippet, String categoria, String sottosettore, String regione, String causa, String tipologia_cup, String cup) {
        this.lat = lat;
        this.lng = lng;
        this.percentage = percentage;
        this.importo_ultimo_qe = qe;
        this.importo_ultimo_qe_approvato = qe_approvato;
        this.importo_sal = sal;
        this.title = title;
        this.snippet = snippet;
        this.categoria = categoria;
        this.sottosettore = sottosettore;
        this.regione = regione;
        this.causa = causa;
        this.tipologia_cup = tipologia_cup;
        this.cup = cup;
    }

    //METODI GET
    public String getRegione(){ return this.regione; }
    public double getPercentage(){
        return this.percentage;
    }
    public String getCategoria(){
        return this.categoria;
    }
    public String getSottosettore(){
        return this.sottosettore;
    }
    public String getCausa(){
        return this.causa;
    }
    public String getTipologia_cup(){
        return this.tipologia_cup;
    }
    public String getCup(){return this.cup;}
    public String getTitle() { return this.title; }
    public LatLng getPosition() {
        return new LatLng(lat, lng);
    }
    public String getSnippet() { return this.snippet; }

    //METODI SET
    public void setTitle(String title) {this.title = title;}
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getImporto_ultimo_qe() {
        return importo_ultimo_qe;
    }

    public double getImporto_ultimo_qe_approvato() {
        return importo_ultimo_qe_approvato;
    }

    public double getImporto_sal() {
        return importo_sal;
    }
}
