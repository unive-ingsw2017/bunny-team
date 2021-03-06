package it.unive.dais.bunnyteam.unfinitaly.lib.experimental.comm;

import it.unive.dais.bunnyteam.unfinitaly.lib.experimental.IActivity;
import it.unive.dais.bunnyteam.unfinitaly.lib.util.Function;

import java.io.Serializable;

public abstract class Command<Receiver extends IActivity<?>>
        implements Function<Receiver, Void>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public abstract Void apply(Receiver o);

}
