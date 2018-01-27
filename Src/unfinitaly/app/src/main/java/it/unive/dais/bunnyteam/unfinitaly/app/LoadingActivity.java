package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;
import it.unive.dais.bunnyteam.unfinitaly.app.memory.MapsItemIO;


public class LoadingActivity extends AppCompatActivity {
    private WebView webview;
    private TextView tv_status;
    private TextView tvCountLoad;
    private ProgressBar progressBar;
    int status = 0;
    private FloatingActionButton fab;

    @Override
    public void onBackPressed() {
        if(webview.getVisibility()==View.VISIBLE)
            unshowWebView();
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            System.exit(0);
        }

        setContentView(R.layout.activity_loading);
        tv_status = (TextView)findViewById(R.id.tv_status);
        tvCountLoad =(TextView)findViewById(R.id.tvCountLoad);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        webview = (WebView)findViewById(R.id.webview);
        webview.loadUrl("http://unfinitaly.altervista.org/");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView();
            }
        });
        if(MapMarkerList.getInstance().getMapMarkers().size() == 0) {
            /*non ci sono markers*/
            try {
            if (MapsItemIO.isCached(this)) {

                    Log.i("loading", "is on cache!");
                    if(!(MapMarkerList.getInstance().loadFromCache(this)))
                        MapMarkerList.getInstance().loadFromCsv(this);
                    else{
                        Log.i("loading", "starting app!");
                        startMapsActivity();
                    }
            }else
                MapMarkerList.getInstance().loadFromCsv(this);
            } catch (InterruptedException | IOException | ExecutionException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void showWebView(){
        webview.setVisibility(View.VISIBLE);
        tv_status.setVisibility(View.INVISIBLE);
        tvCountLoad.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);
    }
    public void unshowWebView(){
        webview.setVisibility(View.INVISIBLE);
        tv_status.setVisibility(View.VISIBLE);
        tvCountLoad.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        if(status == 1)
            startMapsActivity();
    }
    public void startMapsActivity() {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public WebView getWebview(){
        return webview;
    }
}
