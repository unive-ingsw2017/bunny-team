package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;

import com.github.paolorotolo.appintro.AppIntroFragment;

import it.unive.dais.bunnyteam.unfinitaly.app.slider.CustomSlider;

public class HelpActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CIAO!", "CIAOAOOOO");
        //addSlide(getSupportFragmentManager().findFragmentById(R.id.fagmentInfo1));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo1));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo2));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo3));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo4));
        addSlide(CustomSlider.newInstance(R.layout.fragmentinfo5));

        setBarColor(Color.parseColor("#303F9F"));
        setBarColor(Color.parseColor("#303F9F"));
        setSeparatorColor(Color.parseColor("#303F9F"));
        showSkipButton(false);
        showDoneButton(false);
        //setFadeAnimation();

    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startMapsActivity();
    }
    public void onBackPressed(){
        /*do nothing!*/
        //dobbiamo farlo perchè di default AppIntro fa tornare indietro
    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startMapsActivity();
    }
    public void startMapsActivity() {
        startActivity(new Intent(this, MapsActivity.class));
    }

}
