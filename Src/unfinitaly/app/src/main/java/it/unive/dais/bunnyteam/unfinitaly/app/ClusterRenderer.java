package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by giacomo on 08/01/18.
 */

public class ClusterRenderer<T extends MapMarker> extends DefaultClusterRenderer{
    public ClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }
    @Override
    protected int getBucket(Cluster cluster) {
        return cluster.getSize();
    }
    protected String getClusterText(int bucket){
        return ""+bucket;
    }



}
