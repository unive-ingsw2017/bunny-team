package it.unive.dais.bunnyteam.unfinitaly.app.cluster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarker;
import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;
import it.unive.dais.bunnyteam.unfinitaly.app.MapsActivity;
import it.unive.dais.bunnyteam.unfinitaly.app.R;

/**
 * Created by giacomo on 23/01/18.
 */

public class CustomClusterManager<T extends ClusterItem> extends ClusterManager<MapMarker>{

    private MapMarkerList mapMarkers = null;
    private Context context;
    private GoogleMap map;
    private boolean flagregione = false;
    private boolean flagtipo = false;
    public CustomClusterManager(final Context context, GoogleMap map) {
        super(context, map);
        this.context=context;
        this.map = map;
        setOnClusterClickListener(getDefaultOnClusterClickListener());
        setOnClusterItemClickListener(new OnClusterItemClickListener<MapMarker>() {
            @Override
            public boolean onClusterItemClick(final MapMarker mapMarker) {
                if(((Activity)context).findViewById(R.id.marker_window).getVisibility()==View.VISIBLE)
                    ((Activity)context).findViewById(R.id.marker_window).setVisibility(View.INVISIBLE);
                ((MapsActivity)context).getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(mapMarker.getPosition(), 13), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        String title = mapMarker.getCategoria();
                        String snippet = mapMarker.getTitle();
                        if(title.length()>100){
                            title = title.substring(0,99) + "...";
                        }
                        if(snippet.length()>100){
                            snippet = snippet.substring(0,99) + "...";
                        }
                        ((TextView)((Activity)context).findViewById(R.id.titleMarker)).setText(title);
                        ((TextView)((Activity)context).findViewById(R.id.snippetMarker)).setText(snippet);
                        ((Activity)context).findViewById(R.id.marker_window).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancel() {
                        String title = mapMarker.getCategoria();
                        String snippet = mapMarker.getTitle();
                        if(title.length()>100){
                            title = title.substring(0,99) + "...";
                        }
                        if(snippet.length()>100){
                            snippet = snippet.substring(0,99) + "...";
                        }
                        ((TextView)((Activity)context).findViewById(R.id.titleMarker)).setText(title);
                        ((TextView)((Activity)context).findViewById(R.id.snippetMarker)).setText(snippet);
                        ((Activity)context).findViewById(R.id.marker_window).setVisibility(View.VISIBLE);
                    }
                });

                        View info;
                        info = ((Activity)context).findViewById(R.id.marker_window);
                        info.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                ((MapsActivity) context).showMarkerInfo(mapMarker);
                            }
                        });
                        View navigate;
                        navigate = ((Activity)context).findViewById(R.id.navigate);
                        navigate.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                ((MapsActivity)context).updateCurrentPosition();
                                LatLng app = ((MapsActivity)context).getCurrentPosition();
                                if (app != null)
                                    ((MapsActivity)context).navigate(app,mapMarker.getPosition());
                                else
                                    Toast.makeText(context, "Errore durante la ricezione della posizione. Riprova tra poco.", Toast.LENGTH_SHORT).show();
                            }
                        });
                ((Activity)context).findViewById(R.id.position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MapsActivity)context).getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(mapMarker.getPosition(), 13));
                    }
                });
                return true;
            };
            });
        setOnClusterItemInfoWindowClickListener(getDefaultOnClusterItemInfoWindowClickListener());
        setRenderer(new ClusterRenderer<>(context, map, this));
    }


    public void resetMarkers(){
        clearItems();
        addItems(mapMarkers.getMapMarkers());
        cluster();
    }
    protected void clearMarkers(){
        clearItems();
        cluster();
    }

    public void resetFlags(){
        flagregione = false;
        flagtipo = false;
    }

    public void setFlagRegion(boolean status){
        flagregione = status;
    }

    public void setFlagTipo(boolean status){
        flagtipo = status;
    }

    public void showRegions(ArrayList<String> regions) {
        /*qui devo far vedere le regioni.*/
        clearItems();
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(regions.contains(mM.getRegione()))
                addItem(mM);
        flagregione = true;
        cluster();
    }
    public int countMarkerByRegion(String region){
        int i=0;
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(mM.getRegione().equals(region))
                i++;
        return i;
    }
    public ArrayList<String> getAllMarkerCategory(){
        ArrayList<String> allCategory = new ArrayList<>();
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(!allCategory.contains(mM.getCategoria()))
                allCategory.add(mM.getCategoria());
        return allCategory;
    }

    public int countMarkerByCategory(String category) {
        int i=0;
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(mM.getCategoria().equals(category))
                i++;
        return i;
    }

    public void showCategory(ArrayList<String> selectedCategory) {
       /*qui devo far vedere le categorie.*/
        clearItems();
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(selectedCategory.contains(mM.getCategoria()))
                addItem(mM);
        cluster();
    }
    public OnClusterClickListener<MapMarker> getDefaultOnClusterClickListener(){
        return new ClusterManager.OnClusterClickListener<MapMarker>() {
            @Override
            public boolean onClusterClick(Cluster<MapMarker> cluster) {
                ((Activity)context).findViewById(R.id.marker_window).setVisibility(View.INVISIBLE);
                Log.i("CIAO", "CIAO!");
                final String[] stringclusterlista = new String[cluster.getSize()];
                final Collection<MapMarker> clusterlist = cluster.getItems();
                Log.d("Grandezza",""+cluster.getSize());
                for(int i=0;i<clusterlist.size();i++){
                    stringclusterlista[i]= (String)"Categoria: "+((MapMarker)clusterlist.toArray()[i]).getCategoria()+"\n"+((MapMarker)clusterlist.toArray()[i]).getTipologia_cup();
                }
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Elementi presenti: "+cluster.getSize())
                        .setSingleChoiceItems(stringclusterlista, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if(context instanceof MapsActivity)
                                    ((MapsActivity)context).showMarkerInfo((MapMarker)clusterlist.toArray()[id]);
                                //Toast.makeText(getApplicationContext(), "cliccato Cluster!"+id, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Indietro", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();
                dialog.show();
                return false;
            }
        };
    }

    @Override
    public void setOnClusterItemClickListener(OnClusterItemClickListener<MapMarker> listener) {
        super.setOnClusterItemClickListener(listener);
    }
    public OnClusterItemInfoWindowClickListener<MapMarker> getDefaultOnClusterItemInfoWindowClickListener() {
        return new ClusterManager.OnClusterItemInfoWindowClickListener<MapMarker>() {
            @Override
            public void onClusterItemInfoWindowClick(MapMarker mapMarker) {
                if (context instanceof MapsActivity)
                    ((MapsActivity) context).showMarkerInfo(mapMarker);
            }
        };
    }
    public void setMapMarkerList(MapMarkerList mM){
        this.mapMarkers = mM;
        addItems(mM.getMapMarkers());
        cluster();
    }
    public void setPercentageRenderer(){
        setRenderer(new PercentageClusterRenderer<>(context,map,this));
        resetMarkers();
    }
    public void unsetPercentageRender(){
        setRenderer(new ClusterRenderer<>(context,map,this));
        resetMarkers();
    }
    public List<LatLng> getCoordList(){
        ArrayList<LatLng> lL = new ArrayList<>();
        for(MapMarker mM: mapMarkers.getMapMarkers())
            lL.add(mM.getPosition());
        return lL;
    }
}
