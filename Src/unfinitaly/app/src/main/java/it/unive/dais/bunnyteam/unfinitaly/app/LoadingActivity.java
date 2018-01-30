package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;
import it.unive.dais.bunnyteam.unfinitaly.app.memory.MapsItemIO;
import it.unive.dais.bunnyteam.unfinitaly.app.slider.CustomSlider;
import it.unive.dais.bunnyteam.unfinitaly.app.slider.CustomSliderLoading;

/**
 *
 * @author BunnyTeam, Università Ca' Foscari
 */
public class LoadingActivity extends AppIntro {
    private WebView webview;
    private TextView tv_status;
    private TextView tvCountLoad;
    private ProgressBar progressBar;
    CustomSliderLoading csl;
    Fragment curFragment;
    private View v;
    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    int status = 0;
    private FloatingActionButton fab;
    private View loadingView;
    private boolean ready = false;
    @Override
    public void onBackPressed() {
        /*if(webview.getVisibility()==View.VISIBLE)
            unshowWebView();
        else*/
            super.onBackPressed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            System.exit(0);
        }
        csl = CustomSliderLoading.newInstance(R.layout.fragmentinfo1, this);

        addSlide(csl);
        ((TextView)findViewById(com.github.paolorotolo.appintro.R.id.done)).setText("CONTINUA");
        if(getSlides().get(0) instanceof CustomSliderLoading)
            Log.d("CIAO", "È un customsliderloading!!!");
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo2));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo3));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo4));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo5));
        setBarColor(Color.parseColor("#66000000"));
        setSeparatorColor(Color.parseColor("#66000000"));
        curFragment = fragments.get(0);
        setProgressButtonEnabled(true);
        showSkipButton(false);
        setZoomAnimation();
        //setContentView(R.layout.activity_loading);
        /*tv_status = (TextView)loadingView.findViewById(R.id.tv_status);
        tvCountLoad =(TextView)loadingView.findViewById(R.id.tvCountLoad);
        progressBar = (ProgressBar)loadingView.findViewById(R.id.progressBar);*/
       /*webview = (WebView)loadingView.findViewById(R.id.webview);
        webview.loadUrl("http://unfinitaly.altervista.org/");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        fab = (FloatingActionButton)loadingView.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWebView();
            }
        });*/
        /*if(MapMarkerList.getInstance().getMapMarkers().size() == 0) {
            /*non ci sono markers
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
        }*/
    }
    public void showWebView(){
        webview.setVisibility(View.VISIBLE);
        tv_status.setVisibility(View.INVISIBLE);
        tvCountLoad.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        //fab.setVisibility(View.INVISIBLE);
    }
    public void unshowWebView(){
        webview.setVisibility(View.INVISIBLE);
        tv_status.setVisibility(View.VISIBLE);
        tvCountLoad.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        //fab.setVisibility(View.VISIBLE);
        //if(status == 1)
            //startMapsActivity();
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


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        startMapsActivity();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        if(isReady())
            startMapsActivity();
        else {
            Snackbar snack = Snackbar.make(currentFragment.getView(), "Mappa non ancora pronta. Attendi!", Snackbar.LENGTH_SHORT);
            View view = snack.getView();

            view.setBackgroundColor(getResources().getColor(R.color.md_red_900));
            snack.show();
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        Log.d("CIAOO", "set new Fragment");
        curFragment = newFragment;

    }

    public void showSkip(){
        showSkipButton(true);
    }


    public void showFinishSnackbar() {
        Snackbar snack = Snackbar.make(curFragment.getView(), "Mappa disponibile!", Snackbar.LENGTH_SHORT);
        View view = snack.getView();
        view.setBackgroundColor(getResources().getColor(R.color.md_green_700));

        snack.show();
        snack.setActionTextColor(getResources().getColor(R.color.md_white_1000));
        snack.setAction("CONTINUA", new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startMapsActivity();
            }
        });
    }
}

