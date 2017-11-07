package it.unive.dais.cevid.aac;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.coreutils.BuildConfig;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import it.unive.dais.cevid.aac.R;


/**
 * Activity per la schermata di crediti e about.
 *
 * @author Alvise Spanò, Università Ca' Foscari
 *         Fork creato per ingegneria del SW. Bunny Team 2017/2018
 */
public class InfoActivity extends AppCompatActivity {

    /**
     * Produce la stringa completa coi crediti.
     *
     * @param ctx oggetto Context, tipicamente {@code this} se chiamato da un'altra Activity.
     * @return ritorna la stringa completa.
     */
    public static String credits(Context ctx) {
        ApplicationInfo ai = ctx.getApplicationInfo();
        StringBuffer buf = new StringBuffer();
        buf.append("\n\tEMAIL 857763@stud.unive.it");
        buf.append("\n\tFAQ\thttps://www.google.it");
        return String.format(
                "--- APP ---\n" +
                        "%s v%s [%s]\n" +
                        "(c) %s %s @ %s - %s \n\n" +
                        "--- INFORMAZIONI ---\n%s",
                ctx.getString(ai.labelRes),
                BuildConfig.VERSION_NAME,
                BuildConfig.BUILD_TYPE,
                R.string.credits_year, R.string.credits_project, R.string.credits_company, R.string.credits_authors,
                buf);
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
        TextView tv_1 = (TextView) findViewById(R.id.textView_1);
        tv_1.setText(credits(this));
    }

}
