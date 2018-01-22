package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.*;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public abstract class BaseActivity extends AppCompatActivity {
    protected Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);
        //buildDrawer();
    }

    /**
     *
     * @author Giacomo
     *
     */
    protected void buildDrawer(Toolbar toolbar){
        setSupportActionBar(toolbar);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background)
                .addProfiles(
                        new ProfileDrawerItem().withName("Bunny Team").withEmail("bunnyteam@gmail.com").withIcon(getResources().getDrawable(R.drawable.user))
                )
                .build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Impostazioni");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("About");
        item1.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                startSettingsActivity();
                return false;
            }
        });
        item2.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                startInfoActivity();
                return false;
            }
        });
        drawer = new com.mikepenz.materialdrawer.DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1, item2, new DividerDrawerItem()
                )
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }
    public void startSettingsActivity(){
        Intent intent_info = new Intent(this, SettingsActivity.class);
        startActivity(intent_info);
        overridePendingTransition(R.xml.slide_up_info, R.xml.no_change);
    }
    public void startInfoActivity(){
        if(! (this instanceof InfoActivity)) {
            Intent intent_info = new Intent(this, InfoActivity.class);
            startActivity(intent_info);
            overridePendingTransition(R.xml.slide_up_info, R.xml.no_change);
        }
    }

}
