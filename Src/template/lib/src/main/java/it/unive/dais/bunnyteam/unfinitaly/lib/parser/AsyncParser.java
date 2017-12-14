package it.unive.dais.bunnyteam.unfinitaly.lib.parser;

import android.os.AsyncTask;

import java.util.List;

/**
 * Interfaccia che rappresenta un parser asincrono.
 * @param <Data>
 * @param <Progress>
 */
public interface AsyncParser<Data, Progress> extends Parser<Data> {
    AsyncTask<Void, Progress, List<Data>> getAsyncTask();
}
