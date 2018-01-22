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
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

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
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.background)
                .addProfiles(
                        new ProfileDrawerItem().withName("Bunny Team").withEmail("unfinitaly.app@gmail.com").withIcon(getResources().getDrawable(R.drawable.bunnylogo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        startInfoActivity();
                        return false;
                    }
                })
                .build();
        //Creazione voci di menu
        PrimaryDrawerItem regione = new PrimaryDrawerItem().withIdentifier(1).withName("Filtro per regione").withIcon(R.drawable.regione);
        PrimaryDrawerItem categoria = new PrimaryDrawerItem().withIdentifier(1).withName("Filtro per categoria").withIcon(R.drawable.categoria);
        PrimaryDrawerItem percentuale = new PrimaryDrawerItem().withIdentifier(1).withName("Filtro per percentuale").withIcon(R.drawable.percentage);
        PrimaryDrawerItem informazioni = new PrimaryDrawerItem().withIdentifier(1).withName("Informazioni").withIcon(R.drawable.info);
        PrimaryDrawerItem impostazioni = new PrimaryDrawerItem().withIdentifier(1).withName("Impostazioni").withIcon(R.drawable.settings);
        //Associazione listener alle varie voci
        regione.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Toast.makeText(getApplicationContext(),"Pulsante regione",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        categoria.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Toast.makeText(getApplicationContext(),"Pulsante categoria", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        percentuale.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                Toast.makeText(getApplicationContext(),"Pulsante %",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        informazioni.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                startInfoActivity();
                return false;
            }
        });
        impostazioni.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                startSettingsActivity();
                return false;
            }
        });
        drawer = new com.mikepenz.materialdrawer.DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        regione,categoria,percentuale, new DividerDrawerItem(), informazioni, impostazioni, new DividerDrawerItem()
                )
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }
    public void startSettingsActivity(){
        Intent intent_info = new Intent(this, SettingsActivity.class);
        startActivity(intent_info);
        //overridePendingTransition(R.xml.slide_up_info, R.xml.no_change);
    }
    public void startInfoActivity(){
        if(! (this instanceof InfoActivity)) {
            Intent intent_info = new Intent(this, InfoActivity.class);
            startActivity(intent_info);
            //overridePendingTransition(R.xml.slide_up_info, R.xml.no_change);
        }
    }

}
