package it.unive.dais.bunnyteam.unfinitaly.app.marker;

/**
 * Created by giacomo on 29/01/18.
 */

public class MapMarkerListVersioningHelper {
    protected static int STATIC_ID_VERSION = 5;
    protected int ID_VERSION;
    public MapMarkerListVersioningHelper(){
        ID_VERSION = STATIC_ID_VERSION;
    }
    public static int getStaticIdVersion() {
        return STATIC_ID_VERSION;
    }

    public int getIdVersion() {
        return ID_VERSION;
    }
}
