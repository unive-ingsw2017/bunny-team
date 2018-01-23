package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by giacomo on 23/01/18.
 */

public class PercentageClusterRenderer<T extends MapMarker> extends ClusterRenderer {
    public PercentageClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MapMarker item, MarkerOptions markerOptions) {
        /*TO DO: in questo metodo si fa l'algoritmo che calcola il colore in base alla percentuale di completamento.*/
        //quello che c'è qui è quello standard, va cancellato e sistemato il metodo
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        /*la riga sopra colora l'icona del marker di rosso */
        /*quando si clicca su percentuale, va cambiato il ClusterRenderer con questo,
        quando si deseleziona percentuale, va rimesso il ClusterRenderer di default*/

    }
}