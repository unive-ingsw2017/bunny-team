package it.unive.dais.cevid.aac;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.unive.dais.cevid.aac.util.University;
import it.unive.dais.cevid.datadroid.lib.parser.AppaltiParser;
import it.unive.dais.cevid.datadroid.lib.parser.AsyncParser;
import it.unive.dais.cevid.datadroid.lib.parser.SoldipubbliciParser;
import it.unive.dais.cevid.datadroid.lib.util.DataManipulation;
import it.unive.dais.cevid.datadroid.lib.util.Function;
import it.unive.dais.cevid.datadroid.lib.util.ProgressStepper;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    public static final String BUNDLE_UNI = "UNI";
    private static final String BUNDLE_LIST = "LIST";

    private University university;
    private SoldipubbliciParser soldiPubbliciParser;
    private AppaltiParser appaltiParser;
    private LinearLayout mainView;
    private String soldiPubbliciText = " ";
    private String appaltiText = " ";
    private ProgressBar progressBar1, progressBar2;

    public class MyActivity extends AppCompatActivity {
        private ProgressBar progressBar1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            progressBar1 = (ProgressBar) findViewById(R.id.progress_bar1);
        }
    }


    // simple progress management for both parsers
    //

    protected class MyAppaltiParser extends AppaltiParser {
        private static final String TAG = "MyAppaltiParser";

        public MyAppaltiParser(List<URL> urls) {
            super(urls);
            progressBar1.setIndeterminate(false);
            progressBar1.setMax(100);
        }

        @Override
        public void onProgressUpdate(ProgressStepper p) {
            int progress = (int) (p.getPercent() * progressBar1.getMax());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar1.setProgress(progress, true);
            }
            else {
                progressBar1.setProgress(progress);
            }
        }

        @Override
        protected void onPreExecute() {
            progressBar1.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Data> r) {
            progressBar1.setVisibility(View.GONE);
        }
    }

    protected class MySoldipubbliciParser extends SoldipubbliciParser {
        private static final String TAG = "MySoldipubbliciParser";

        public MySoldipubbliciParser(String a, String b) {
            super(a, b);
            progressBar2.setIndeterminate(false);
            progressBar2.setMax(100);
        }

        @Override
        public void onProgressUpdate(ProgressStepper p) {
            int progress = (int) (p.getPercent() * progressBar2.getMax());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar2.setSecondaryProgress(progress);
            }
            else {
                progressBar2.setProgress(progress);
            }
        }

        @Override
        protected void onPreExecute() {
            progressBar2.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<Data> r) {
            progressBar2.setVisibility(View.GONE);
        }
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(BUNDLE_UNI, university);
//        saveParserState(savedInstanceState, appaltiParser);
//        saveParserState(savedInstanceState, soldiPubbliciParser);
        super.onSaveInstanceState(savedInstanceState);
    }

    // TODO: finire questo
    private <T> void saveParserState(Bundle savedInstanceState, AsyncParser<T, ?> parser) {
        try {
            AsyncTask<Void, ?, List<T>> p = parser.getAsyncTask();
            switch (p.getStatus()) {
                case FINISHED:
                    savedInstanceState.putSerializable(BUNDLE_LIST, new ArrayList<T>(p.get()));
                    break;
                default:
                    break;
            }
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, String.format("parser %s failed", parser.getClass().getSimpleName()));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mainView = (LinearLayout) findViewById(R.id.search_activity);

        progressBar1 = (ProgressBar) findViewById(R.id.progress_bar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress_bar2);

        if (savedInstanceState == null) {
            // crea l'activity da zero
            university = (University) getIntent().getSerializableExtra(BUNDLE_UNI);
        } else {
            // ricrea l'activity deserializzando alcuni dati dal bundle
            university = (University) savedInstanceState.getSerializable(BUNDLE_UNI);
        }
        TextView title = (TextView) findViewById(R.id.univeristy_name);
        title.setText(university.getTitle());

        // TODO: salvare lo stato dei parser con un proxy serializzabile
        soldiPubbliciParser = new MySoldipubbliciParser(University.getCodiceComparto(), university.getCodiceEnte());
        appaltiParser = new MyAppaltiParser(university.getUrls());
        soldiPubbliciParser.getAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        appaltiParser.getAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        SearchView appaltiSearch = (SearchView) findViewById(R.id.ricerca_appalti);
        SearchView soldipubbliciSearch = (SearchView) findViewById(R.id.ricerca_soldipubblici);
        appaltiSearch.onActionViewExpanded();
        soldipubbliciSearch.onActionViewExpanded();

        setSingleListener(appaltiSearch, appaltiParser, UniversityActivity.LIST_APPALTI, Appalti_getText, Appalti_getCode, new Function<String, Void>() {
            @Override
            public Void eval(String x) {
                SearchActivity.this.appaltiText = x;
                return null;
            }
        });
        setSingleListener(soldipubbliciSearch, soldiPubbliciParser, UniversityActivity.LIST_SOLDIPUBBLICI, Soldipubblici_getText, Soldipubblici_getCode, new Function<String, Void>() {
            @Override
            public Void eval(String x) {
                SearchActivity.this.soldiPubbliciText = x;
                return null;
            }
        });

        Button combineButton = (Button) findViewById(R.id.button_combine_data);
        combineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, UniversityActivity.class);
                boolean r1 = processQuery(appaltiParser, appaltiText, intent, UniversityActivity.LIST_APPALTI, Appalti_getText, Appalti_getCode),
                        r2 = processQuery(soldiPubbliciParser, soldiPubbliciText, intent, UniversityActivity.LIST_SOLDIPUBBLICI, Soldipubblici_getText, Soldipubblici_getCode);
                if (r1 && r2)
                    startActivity(intent);
                else
                    alert("Compilare entrambi i campi di testo ed assicurarsi che diano risultati per richiedere una ricerca combinata.");
            }
        });

        mainView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    private void alert(String msg) {
        Snackbar.make(mainView, msg, Snackbar.LENGTH_SHORT).show();
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private <T> boolean processQuery(AsyncParser<T, ?> parser, String text, Intent intent, String label,
                                     Function<T, String> getText, Function<T, Integer> getCode) {
        try {
            List<T> l = new ArrayList<>(parser.getAsyncTask().get());   // clona la lista per poterla manipolare in sicurezza
            if (!text.isEmpty()) {
                if (text.matches("[0-9]+"))
                    DataManipulation.filterByCode(l, Integer.parseInt(text), getCode);
                else
                    DataManipulation.filterByWords(l, text.split(" "), getText, false);
                if (l.size() == 0) {
                    return false;
                } else {
                    intent.putExtra(label, (Serializable) l);
                    return true;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            alert(String.format("Errore inatteso: %s. Riprovare.", e.getMessage()));
            Log.e(TAG, String.format("exception caught during parser %s", parser.getName()));
            e.printStackTrace();
        }
        return false;
    }

    private <T> void setSingleListener(final SearchView v, final AsyncParser<T, ?> parser, final String label,
                                       final Function<T, String> getText, final Function<T, Integer> getCode, final Function<String, Void> setText) {
        v.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                Intent intent = new Intent(SearchActivity.this, UniversityActivity.class);
                if (processQuery(parser, text, intent, label, getText, getCode))
                    startActivity(intent);
                else
                    alert("La ricerca non ha prodotto nessun risultato. Provare con altri valori.");
                v.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setText.eval(newText);
                return false;
            }
        });
    }

    // higher-order functions
    //
    //

    private static final Function<AppaltiParser.Data, String> Appalti_getText = new Function<AppaltiParser.Data, String>() {
        @Override
        public String eval(AppaltiParser.Data x) {
            return x.oggetto;
        }
    };

    private static final Function<AppaltiParser.Data, Integer> Appalti_getCode = new Function<AppaltiParser.Data, Integer>() {
        @Override
        public Integer eval(AppaltiParser.Data x) {
            return Integer.parseInt(x.cig);
        }
    };

    private static final Function<SoldipubbliciParser.Data, String> Soldipubblici_getText = new Function<SoldipubbliciParser.Data, String>() {
        @Override
        public String eval(SoldipubbliciParser.Data x) {
            return x.descrizione_codice;
        }
    };

    private static final Function<SoldipubbliciParser.Data, Integer> Soldipubblici_getCode = new Function<SoldipubbliciParser.Data, Integer>() {
        @Override
        public Integer eval(SoldipubbliciParser.Data x) {
            return Integer.parseInt(x.codice_siope);
        }
    };

}
