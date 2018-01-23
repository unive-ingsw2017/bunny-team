package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.google.maps.android.ui.SquareTextView;


/**
 * Created by giacomo on 08/01/18.
 */

public class ClusterRenderer<T extends MapMarker> extends DefaultClusterRenderer<MapMarker> {
    private final float mDensity;
    private ShapeDrawable mColoredCircleBackground;
    private SparseArray<BitmapDescriptor> mIcons = new SparseArray();
    private int mMinClusterSize = 4;
    private IconGenerator mIconGenerator;

    public ClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mIconGenerator = new IconGenerator(context);
        this.mIconGenerator.setBackground(this.makeClusterBackground());
        this.mIconGenerator.setContentView(this.makeSquareTextView(context));
        this.mIconGenerator.setTextAppearance(com.google.maps.android.R.style.amu_ClusterIcon_TextAppearance);
    }

    public int getMinClusterSize(){
        return mMinClusterSize;
    }
    @Override
    protected void onBeforeClusterRendered(Cluster<MapMarker> cluster, MarkerOptions markerOptions) {
        //Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        int bucket = this.getBucket(cluster);
        BitmapDescriptor descriptor = (BitmapDescriptor)this.mIcons.get(bucket);
        if(descriptor == null) {
            this.mColoredCircleBackground.getPaint().setColor(this.getColor(bucket));
            //descriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(String.valueOf(cluster.getSize()))); //real numbers
            descriptor = BitmapDescriptorFactory.fromBitmap(this.mIconGenerator.makeIcon(this.getClusterText(bucket))); //number +
            this.mIcons.put(bucket, descriptor);
        }

        markerOptions.icon(descriptor);
    }
/*
    protected void onBeforeClusterItemRendered(MapMarker item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

    }
    */
    private LayerDrawable makeClusterBackground() {
        this.mColoredCircleBackground = new ShapeDrawable(new OvalShape());
        ShapeDrawable outline = new ShapeDrawable(new OvalShape());
        outline.getPaint().setColor(-2130706433);
        LayerDrawable background = new LayerDrawable(new Drawable[]{outline, this.mColoredCircleBackground});
        int strokeWidth = (int)(this.mDensity * 3.0F);
        background.setLayerInset(1, strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        return background;
    }

    private SquareTextView makeSquareTextView(Context context) {
        SquareTextView squareTextView = new SquareTextView(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        squareTextView.setLayoutParams(layoutParams);
        squareTextView.setId(com.google.maps.android.R.id.amu_text);
        int twelveDpi = (int)(12.0F * this.mDensity);
        squareTextView.setPadding(twelveDpi, twelveDpi, twelveDpi, twelveDpi);
        return squareTextView;
    }

    protected boolean shouldRenderAsCluster(Cluster<MapMarker> cluster) {
        return cluster.getSize() > this.mMinClusterSize;
    }
}
