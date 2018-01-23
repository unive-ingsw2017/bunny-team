package it.unive.dais.bunnyteam.unfinitaly.app;


import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
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
        String opera = "CATEGORIA:\n "+thisMapMarker.getCategoria();
        opera+="\n\nSOTTOSETTORE:\n "+thisMapMarker.getSottosettore();
        opera+="\n\nDESCRIZIONE:\n"+thisMapMarker.getTitle();
        opera+="\n\nPUBBLICATA DA:\n"+thisMapMarker.getRegione();
        opera+="\n\nPercentuale avanzamento dei lavori: "+thisMapMarker.getPercentage()+" %";
        tv.setText(opera);
        rc.setMax(100);
        rc.setProgress((int)thisMapMarker.getPercentage());
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                onBackPressed();
                return false;
            }
        });
    }

}

