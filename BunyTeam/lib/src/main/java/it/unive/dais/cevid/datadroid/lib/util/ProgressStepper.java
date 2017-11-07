package it.unive.dais.cevid.datadroid.lib.util;

/**
 * Created by spano on 30/10/2017.
 */

public class ProgressStepper {

    private final int size;
    private int cnt = 0;
    private final double base, scale;

    public ProgressStepper(int size) {
        this(size, 0.0, 1.0);
    }

    public ProgressStepper(int size, double base, double scale) {
        this.size = size;
        this.base = base;
        this.scale = scale;
    }

    public ProgressStepper getSubProgressStepper(int newSize) {
        return new ProgressStepper(newSize, getPercent(), 1.0 / (double) size);
    }

    public void step() {
        ++cnt;
    }

    public double getPercent() {
        double p = (double) cnt / (double) size;
        return base + p * scale;
    }
}

