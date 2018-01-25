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
        ((TextView)findViewById(R.id.tv_categoria)).setText(thisMapMarker.getCategoria());
        ((TextView)findViewById(R.id.tv_pubblicata_da)).setText(thisMapMarker.getRegione());
        ((TextView)findViewById(R.id.tv_sottosettore)).setText(thisMapMarker.getSottosettore());
        ((TextView)findViewById(R.id.tv_cup)).setText(thisMapMarker.getCup());
        ((TextView)findViewById(R.id.tv_tipo_cup)).setText(thisMapMarker.getTipologia_cup());
        ((TextView)findViewById(R.id.tv_descrizione)).setText(thisMapMarker.getTitle());
        ((TextView)findViewById(R.id.tv_fallimento)).setText(thisMapMarker.getCausa());
        String percentage = thisMapMarker.getPercentage()+"%";
        ((TextView)findViewById(R.id.tv_percentuale)).setText(percentage);
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

