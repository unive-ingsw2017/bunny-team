package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.coreutils.BuildConfig;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;


/**
 * Activity per la schermata di crediti e about.
 *
 * @author Alvise Spanò, Università Ca' Foscari
 */
public class InfoActivity extends BaseActivity {
    WebView webview;
    FloatingActionButton fab;
    Toolbar toolbar;
    ImageView imV;
    TextView mail;
    TextView site;
    /**
     * Produce la stringa completa coi crediti.
     *
     * @param ctx oggetto Context, tipicamente {@code this} se chiamato da un'altra Activity.
     * @return ritorna la stringa completa.
     */
    public static String credits(Context ctx) {
        return "Email\nunfinitaly.app@gmail.com\n\nSito web:\nunfinitaly.@altervista.org";
    }

    /**
     * Metodo di creazione dell'activity che imposta il layout e la text view con la stringa con i crediti.
     *
     * @param saveInstanceState
     */
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_info);
        webview = (WebView)findViewById(R.id.infowebview);
        webview.loadUrl("http://unfinitaly.altervista.org/");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        mail = (TextView)findViewById(R.id.textViewmail);
        site = (TextView)findViewById(R.id.textViewsite);
        mail.setText("Email\nunfinitaly.app@gmail.com\n");
        site.setText("Sito web:\nunfinitaly.@altervista.org\n");
        imV = (ImageView) findViewById(R.id.bunnyLogo);
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        buildDrawer(toolbar);
        toolbar.setTitle("Informazioni");

        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.setOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
            @Override
            public boolean onNavigationClickListener(View clickedView) {
                onBackPressed();
                return true;
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"unfinitaly.app@gmail.com"});
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "[UNFINITALY] info");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "");
                startActivity(i);
            }
        });
        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView();
            }
        });
    }

    public void showWebView() {
        webview.setVisibility(View.VISIBLE);
        //invisible tutti gli altri, toolbar rimane sempre visibile
        imV.setVisibility(View.INVISIBLE);
        mail.setVisibility(View.INVISIBLE);
        site.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);

    }

    public void unshowWebView() {
        webview.setVisibility(View.INVISIBLE);
        imV.setVisibility(View.VISIBLE);
        mail.setVisibility(View.VISIBLE);
        site.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (webview.getVisibility() == View.VISIBLE)
            unshowWebView();
        else
            super.onBackPressed();
    }
}
