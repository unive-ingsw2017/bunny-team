package it.unive.dais.bunnyteam.unfinitaly.lib.experimental.comm;

import java.io.Serializable;

import it.unive.dais.bunnyteam.unfinitaly.lib.util.Function;


public abstract class Reply<T> implements Function<T, Void>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public abstract Void apply(T o);
}
