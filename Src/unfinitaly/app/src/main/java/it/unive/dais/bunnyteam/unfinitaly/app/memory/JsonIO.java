package it.unive.dais.bunnyteam.unfinitaly.app.memory;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarker;
import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;

/**
 * Created by giacomo on 06/02/18.
 */

public class JsonIO {
    public static boolean isVersionCached(Context context) {
        File cacheDir = new File(context.getCacheDir(), "files");
        cacheDir.mkdirs();
        File cacheFile = new File(cacheDir, "version.json");
        if (cacheFile.exists())
            return true;
        else
            return false;
    }
    public static boolean isJSONCached(Context context) {
        File cacheDir = new File(context.getCacheDir(), "files");
        cacheDir.mkdirs();
        File cacheFile = new File(cacheDir, "opere.json");
        if (cacheFile.exists())
            return true;
        else
            return false;
    }
    public static String readVersion(Context context) throws IOException {
        String version = "";
        File cacheDir = new File(context.getCacheDir(), "files");
        Log.i("ReadFromCache", "start");
        if (!cacheDir.exists())
            throw new IOException();
        File cachedFile = new File(cacheDir, "version.json");
        FileInputStream is = new FileInputStream(cachedFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String jsonVersion = br.readLine();
        JSONArray arrLocal = null;

        try {
            arrLocal = new JSONArray(jsonVersion);
            JSONObject jObjLocal = arrLocal.getJSONObject(0);
            version =  jObjLocal.getString("version");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return version;
    }
    public static void readJSON(Context context) throws IOException {
        ArrayList<MapMarker> mmList;
        String version = "";
        File cacheDir = new File(context.getCacheDir(), "files");
        Log.i("ReadFromCache", "start");
        if (!cacheDir.exists())
            throw new IOException();
        File cachedFile = new File(cacheDir, "opere.json");
        FileInputStream is = new FileInputStream(cachedFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String json= br.readLine();

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MapMarker>>() {}.getType();

        mmList = gson.fromJson(json, type);
        Log.i("CIAO", "SIZE -> "+mmList.size());
        Log.i("CIAO",mmList.get(0).getCup());
        MapMarkerList.getInstance().setMapMarkers(mmList);
        /*done!*/
    }
    public static void saveJSON(Context context, String json){
        File cacheDir = new File(context.getCacheDir(), "files");
        Log.i("saveToCache", "start");
        if (!cacheDir.exists())
            cacheDir.mkdir();
        File cachedFile = new File(cacheDir, "opere.json");
        try {
            FileOutputStream os = new FileOutputStream(cachedFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(json);
            bw.flush();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveVersionJSON(Context context, String json){
        File cacheDir = new File(context.getCacheDir(), "files");
        Log.i("saveToCache", "start");
        if (!cacheDir.exists())
            cacheDir.mkdir();
        File cachedFile = new File(cacheDir, "version.json");
        try {
            FileOutputStream os = new FileOutputStream(cachedFile);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(json);
            bw.flush();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
