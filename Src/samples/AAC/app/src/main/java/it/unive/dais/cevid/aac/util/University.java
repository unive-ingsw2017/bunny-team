package it.unive.dais.cevid.aac.util;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import it.unive.dais.cevid.datadroid.lib.util.MapItem;

/**
 * Created by Fonto on 04/09/17.
 */

public class University extends MapItem implements Serializable{
    private String title;
    private double latitude;
    private double longitude;
    private String description;
    private List<URL> urls;
    private String codiceEnte;
    private static String codiceComparto = "UNI";

    public University(String title, double latitude, double longitude, String description, List<URL> urls, String codiceEnte) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.urls = urls;
        this.codiceEnte = codiceEnte;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(latitude, longitude);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public List<URL> getUrls() {
        return urls;
    }

    public String getCodiceEnte() {
        return codiceEnte;
    }

    public static String getCodiceComparto() {
        return codiceComparto;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrls(List<URL> urls) {
        this.urls = urls;
    }

    public void setCodiceEnte(String codiceEnte) {
        this.codiceEnte = codiceEnte;
    }

    public static void setCodiceComparto(String codiceComparto) {
        University.codiceComparto = codiceComparto;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
