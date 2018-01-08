package it.unive.dais.bunnyteam.unfinitaly.app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

public class MarkerInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_info);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        buildDrawer(toolbar);
        TextView tv = (TextView) findViewById(R.id.tv_markerInfoAcitivity);
        String title = "MARKER CORRENTE: "+getIntent().getStringExtra("TITLE");
        tv.setText(title);
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
