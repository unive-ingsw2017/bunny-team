package it.unive.dais.bunnyteam.unfinitaly.app.view;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

/**
 * Created by BunnyTeam on 27/01/18.
 */

public class ProgressBarAnimation extends Animation {
    private RoundCornerProgressBar progressBar;
    private int to;
    private int from;
    private long stepDuration;

    public ProgressBarAnimation(RoundCornerProgressBar progressBar, long fullDuration) {
        super();
        this.progressBar = progressBar;
        stepDuration = (long)(fullDuration / progressBar.getMax());
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }

        if (progress > progressBar.getMax()) {
            progress = (int) progressBar.getMax();
        }

        to = progress;

        from = (int) progressBar.getProgress();
        setDuration(Math.abs(to - from) * stepDuration);
        progressBar.startAnimation(this);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }
}
