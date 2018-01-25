package it.unive.dais.bunnyteam.unfinitaly.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.MarkerManager;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by giacomo on 23/01/18.
 */

public class CustomClusterManager<T extends ClusterItem> extends ClusterManager<MapMarker>{

    private MapMarkerList mapMarkers = null;
    private Context context;
    private GoogleMap map;
    public CustomClusterManager(final Context context, GoogleMap map) {
        super(context, map);
        this.context=context;
        this.map = map;
        setOnClusterClickListener(getDefaultOnClusterClickListener());
        setOnClusterItemClickListener(getDefaultOnClusterItemClickListener());
        setOnClusterItemInfoWindowClickListener(getDefaultOnClusterItemInfoWindowClickListener());
        setRenderer(new it.unive.dais.bunnyteam.unfinitaly.app.ClusterRenderer<>(context, map, this));
    }
    protected void resetMarkers(){
        clearItems();
        addItems(mapMarkers.getMapMarkers());
        cluster();
    }
    protected void clearMarkers(){
        clearItems();
        cluster();
    }

    protected void showRegions(ArrayList<String> regions) {
        /*qui devo far vedere le regioni.*/
        clearItems();
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(regions.contains(mM.getRegione()))
                addItem(mM);
        cluster();
    }
    protected int countMarkerByRegion(String region){
        int i=0;
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(mM.getRegione().equals(region))
                i++;
        return i;
    }
    protected ArrayList<String> getAllMarkerCategory(){
        ArrayList<String> allCategory = new ArrayList<>();
        for(MapMarker mM: mapMarkers.getMapMarkers())
            if(!allCategory.contains(mM.getCategoria()))
                allCategory.add(mM.getCategoria());
        return allCategory;
    }

    protected int countMarkerByCategory(String category) {
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
                final String[] stringclusterlista = new String[cluster.getSize()];
                final Collection<MapMarker> clusterlist = cluster.getItems();
                Log.d("Grandezza",""+cluster.getSize());
                for(int i=0;i<clusterlist.size();i++){
                    stringclusterlista[i]= (String)"CUP:"+((MapMarker)clusterlist.toArray()[i]).getCup()+"\n"+((MapMarker)clusterlist.toArray()[i]).getTipologia_cup();
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
    public OnClusterItemClickListener<MapMarker> getDefaultOnClusterItemClickListener(){
        return new OnClusterItemClickListener<MapMarker>() {
            @Override
            public boolean onClusterItemClick(MapMarker mapMarker) {
                View v = ((Activity)context).getLayoutInflater().inflate(R.layout.marker_layout,null);
                TextView titolo = (TextView)v.findViewById(R.id.titleMarker);
                TextView snippet = (TextView)v.findViewById(R.id.snippetMarker);
                titolo.setText(mapMarker.getTitle());
                snippet.setText(mapMarker.getSnippet());
                Log.d("marker",""+mapMarker.getSnippet());
                return false;
            }
        };
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
