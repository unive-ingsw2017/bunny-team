package it.unive.dais.bunnyteam.unfinitaly.app.cluster;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarker;

/**
 * Created by giacomo on 23/01/18.
 */

public class PercentageClusterRenderer<T extends MapMarker> extends ClusterRenderer {
    public PercentageClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }

    /* test, colorare il cluster in base alle percentuali medie @Override
    protected void onBeforeClusterRendered(Cluster cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
        int size = cluster.getSize();
        double sum = 0;
        double media = 0;

        int bucket = this.getBucket(cluster);
        Collection<MapMarker> mM = cluster.getItems();
        BitmapDescriptor descriptor = (BitmapDescriptor)this.mIcons.get(bucket);
        if(descriptor == null) {

            //descriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(String.valueOf(cluster.getSize()))); //real numbers
            descriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(this.getClusterText(bucket))); //number +
            this.mIcons.put(bucket, descriptor);
        }
        for(MapMarker m : mM)
            sum += m.getPercentage();
        media = sum / size;
        if(media >= 0 && media < 25){
            //Tra 0 e 25
            this.mColoredCircleBackground.getPaint().setColor(this.getColor(Color.RED));
        }else{
            if(media >= 25 && media < 50){
                //Tra 25 e 50
                this.mColoredCircleBackground.getPaint().setColor(this.getColor(Color.RED));
            }else{
                if(media >= 50 && media < 75){
                    //Tra 50 e 75
                    this.mColoredCircleBackground.getPaint().setColor(this.getColor(Color.YELLOW));
                }
                else{
                    //Tra 75 e 100
                    this.mColoredCircleBackground.getPaint().setColor(this.getColor(Color.GREEN));
                }
            }
        }
    }*/
    @Override
    protected void onBeforeClusterItemRendered(MapMarker item, MarkerOptions markerOptions) {
        double percentage =  item.getPercentage();
        if(percentage >= 0 && percentage < 25){
            //Tra 0 e 25
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }else{
            if(percentage >= 25 && percentage < 50){
                //Tra 25 e 50
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            }else{
                if(percentage >= 50 && percentage < 75){
                    //Tra 50 e 75
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                }
                else{
                    //Tra 75 e 100
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
            }
        }
    }


}