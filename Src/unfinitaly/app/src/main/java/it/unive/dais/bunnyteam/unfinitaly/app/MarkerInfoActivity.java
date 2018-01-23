package it.unive.dais.bunnyteam.unfinitaly.app;


import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.mikepenz.materialdrawer.Drawer;


public class MarkerInfoActivity extends BaseActivity {
    MapMarker thisMapMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);
        RoundCornerProgressBar rc = (RoundCornerProgressBar) findViewById(R.id.roundcorner);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        buildDrawer(toolbar);
        toolbar.setTitle("Informazioni opera");
        thisMapMarker = (MapMarker)getIntent().getSerializableExtra("MapMarker");
        TextView tv = (TextView) findViewById(R.id.tv_markerInfoAcitivity);
        String opera = "PUBBLICATA DA:\n "+thisMapMarker.getRegione();
        opera+="\n\nCATEGORIA:\n"+thisMapMarker.getCategoria();
        opera+="\n\nSOTTOSETTORE:\n "+thisMapMarker.getSottosettore();
        opera+="\n\nCUP:\n "+thisMapMarker.getCup();
        opera+="\n\nTIPOLOGIA CUP:\n "+thisMapMarker.getTipologia_cup();
        opera+="\n\nDESCRIZIONE:\n"+thisMapMarker.getTitle();
        opera+="\n\nCAUSA DEL FALLIMENTO:\n"+thisMapMarker.getCausa();
        opera+="\n\nPERCENTUALE AVANZAMENTO DEI LAVORI: "+thisMapMarker.getPercentage()+" %";
        tv.setText(opera);
        rc.setMax(100);
        rc.setProgress((int)thisMapMarker.getPercentage());
        final LatLng coordMapM = thisMapMarker.getPosition();
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                onBackPressed();
                return false;
            }
        });
        final StreetViewPanoramaFragment streetViewPanoramaFragment =
                (StreetViewPanoramaFragment) getFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        getFragmentManager().beginTransaction().hide(streetViewPanoramaFragment).commit();
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
                @Override
                public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                    panorama.setOnStreetViewPanoramaChangeListener(new StreetViewPanorama.OnStreetViewPanoramaChangeListener() {
                            @Override
                            public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                                if (streetViewPanoramaLocation != null && streetViewPanoramaLocation.links != null) {
                                    getFragmentManager().beginTransaction().show(streetViewPanoramaFragment).commit();
                                }
                            }
                        });
                    panorama.setPosition(coordMapM);
                }
        });

    }

}

