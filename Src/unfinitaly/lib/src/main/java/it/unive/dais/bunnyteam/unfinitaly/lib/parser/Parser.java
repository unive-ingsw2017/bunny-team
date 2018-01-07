package it.unive.dais.bunnyteam.unfinitaly.lib.parser;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

/**
 * Interfaccia che rappresenta un parser di dati qualsiasi.
 * @param <Data>
 */
public interface Parser<Data> {
    @NonNull List<Data> parse() throws IOException;
    @NonNull String getName();
}
