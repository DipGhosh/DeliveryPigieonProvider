package com.dev.pigeonproviderapp.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

public class SharedPreferenceUtils {
    private static SharedPreferences sharedPreferences = null;

    private static SharedPreferences.Editor editor = null;


    public static void init(Context mcontext) {

        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(mcontext);
            editor = sharedPreferences.edit();
            editor.apply();
        }
        Log.v("shared", String.valueOf(sharedPreferences.getAll().size()));
    }

    public static void clear() {
        editor.clear();
        save();
    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, String value) {
        if (editor != null) {
            editor.putString(key, value);
            save();
        }

    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, int value) {
        if (editor != null) {
            editor.putInt(key, value);
            save();
        }

    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, long value) {
        if (editor != null) {
            editor.putLong(key, value);
            save();
        }

    }

    /**
     * Puts new Key and its Values into SharedPreference map.
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, boolean value) {
        if (editor != null) {
            editor.putBoolean(key, value);
            save();
        }
    }

    /**
     * saves the values from the editor to the SharedPreference
     */
    public static void save() {
        editor.commit();
        editor.apply();
    }

    /**
     * returns a values associated with a Key default value ""
     *
     * @return String
     */
    public static String getString(String key, String defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, defValue);
        } else
            return null;
    }


    /**
     * returns a values associated with a Key default value -1
     *
     * @return String
     */
    public static int getInt(String key, int defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defValue);
        } else
            return 0;
    }

    /**
     * returns a values associated with a Key default value -1
     *
     * @return String
     */
    public static long getLong(String key, long defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, defValue);
        } else
            return 0;
    }

    /**
     * returns a values associated with a Key default value false
     *
     * @return String
     */
    public static boolean getBoolean(String key, boolean defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, defValue);
        } else
            return false;
    }

    /**
     * Checks if key is exist in SharedPreference
     *
     * @param key
     * @return boolean
     */
    public static boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    /**
     * returns map of all the key value pair available in SharedPreference
     *
     * @return Map<String, ?>
     */
    public static Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    /**
     * store object class in shared
     */
    public static void putObjectClass(String jsonObjectName, JSONObject jsonObject) {
        Gson gson = new Gson();
        String json = gson.toJson(jsonObject);
        editor.putString(jsonObjectName, json);
        save();
    }

    /**
     * get object form shared preference
     */
    public static JSONObject getJsonObject(String jsonObjectName) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(jsonObjectName, "");
        JSONObject obj = gson.fromJson(json, JSONObject.class);
        return obj;
    }

    /**
     * store join response model in
     */
}
