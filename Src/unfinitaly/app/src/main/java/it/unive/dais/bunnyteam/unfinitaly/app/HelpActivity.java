package it.unive.dais.bunnyteam.unfinitaly.app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class HelpActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("CIAO!", "CIAOAOOOO");
        addSlide(AppIntroFragment.newInstance("Benvenuto!", "Prima di iniziare, vogliamo spiegarti in breve il funzionamento di Unfinitaly ", R.color.colorPrimary, getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("Accesso al menù", "Effettua uno swipe dal lato sinistro verso il centro per accedere al menù! Dal menù puoi attivare i filtri e accedere alle informazioni. ", R.drawable.help_menu, getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("Informazioni opera incompiuta", "Se vuoi vedere le informazioni di un'opera incompiute, prima devi selezionarla dalla mappa, e in seguito cliccare sulla finestra in sovraimpressione. ", R.drawable.help_marker, getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("Rintracciare l'opera", "Puoi usare i pulsanti a destra e a sinistra del titolo dell'opera rispettivamente per trovare l'opera sulla mappa e per impostare il navigatore. ", R.drawable.help_marker, getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("Aiuto? Suggerimenti? Richieste? ", "Puoi contattarci dal menu informazioni! ", R.drawable.help_info, getResources().getColor(R.color.colorPrimaryDark)));
        setBarColor(Color.parseColor("#303F9F"));
        setBarColor(Color.parseColor("#303F9F"));
        setSeparatorColor(Color.parseColor("#303F9F"));
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
