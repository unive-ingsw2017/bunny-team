package it.unive.dais.bunnyteam.unfinitaly.app.slider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import it.unive.dais.bunnyteam.unfinitaly.app.LoadingActivity;
import it.unive.dais.bunnyteam.unfinitaly.app.R;
import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;
import it.unive.dais.bunnyteam.unfinitaly.app.memory.MapsItemIO;

/**
 * Created by giacomo on 28/01/18.
 */

public class CustomSliderLoading extends CustomSlider {
    private TextView tv_status;
    private TextView tvCountLoad;
    private ProgressBar progressBar;
    private LoadingActivity loadAct;
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private View v;

    public static CustomSliderLoading newInstance(int layoutResId, LoadingActivity loadAct) {
        CustomSliderLoading sampleSlide = new CustomSliderLoading();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);
        sampleSlide.loadAct = loadAct;
        return sampleSlide;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("CIAO", "ONCREATEVIEW!");
        if(v!=null)
            return v;
        else
            return inflater.inflate(layoutResId, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            v = view;
            loadAct.setLoadingView(v);
            Log.d("CIAO", "ON VIEW CREATED!!!!");
            super.onViewCreated(view, savedInstanceState);
            //qui possiamo inserire i metodi del loading.
            tv_status = (TextView) view.findViewById(R.id.tv_status2);
            tv_status.setText("Parsing del CSV");
            tvCountLoad = (TextView) view.findViewById(R.id.tvCountLoad2);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
            if (MapMarkerList.getInstance().getMapMarkers().size() == 0) {
                /*non ci sono markers*/
                try {
                    if (MapsItemIO.isCached(loadAct)) {

                        Log.i("loading", "is on cache!");
                        if (!(MapMarkerList.getInstance().loadFromCache(loadAct)))
                            MapMarkerList.getInstance().loadFromCsv(loadAct, tv_status, tvCountLoad, progressBar);
                        else {
                            Log.i("loading", "starting app!");
                            loadAct.startMapsActivity();
                        }
                    } else
                        MapMarkerList.getInstance().loadFromCsv(loadAct, tv_status, tvCountLoad, progressBar);
                } catch (InterruptedException | IOException | ExecutionException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
