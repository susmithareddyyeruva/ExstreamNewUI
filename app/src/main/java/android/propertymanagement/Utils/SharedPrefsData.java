package android.propertymanagement.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsData {

   private static SharedPrefsData instance = null;
    private static final String DEFAULTVALUESTRING = "N/A";
    private static final int DEFAULTVALUINT = 0;
    private static final String POULTRY_DATA = "exstreamapp";
    private SharedPreferences PoultrySharedPrefs = null;

    public static SharedPrefsData getInstance(Context context) {

        if (instance == null) {
            instance = new SharedPrefsData();
            instance.getPitchItSharedPrefs(context);
        }
        return instance;
    }

    private void getPitchItSharedPrefs(Context context) {
        if (this.PoultrySharedPrefs == null) {
            this.PoultrySharedPrefs = context.getSharedPreferences(POULTRY_DATA, Context.MODE_PRIVATE);
        }
    }

    private void loadFreshPitchItSharedPrefs(Context context) {
        this.PoultrySharedPrefs = context.getSharedPreferences(POULTRY_DATA, Context.MODE_PRIVATE);
    }

    public String getStringFromSharedPrefs(String key) {
        return PoultrySharedPrefs.getString(key, DEFAULTVALUESTRING);
    }

    public int getIntFromSharedPrefs(String key) {
        return PoultrySharedPrefs.getInt(key, DEFAULTVALUINT);
    }
    public void updateStringValue(Context context, String key, String value) {
        //getPitchItSharedPrefs(context);
        SharedPreferences.Editor editor = this.PoultrySharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void updateIntValue(Context context, String key, int value) {
        //getPitchItSharedPrefs(context);
        SharedPreferences.Editor editor = this.PoultrySharedPrefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void ClearData(Context context) {
        getPitchItSharedPrefs(context);
        SharedPreferences profilePref = context.getSharedPreferences(POULTRY_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = profilePref.edit();
        editor.clear();
        editor.apply();
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }
    public static void putString(Context context, String key, String value, String pref) {
        if (context != null && key != null) {
            if (pref != null && !pref.isEmpty()) {
                context.getSharedPreferences(pref, 0).edit().putString(key, value).apply();
            } else {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
            }

        }
    }

    public static String getString(Context context, String key, String pref) {
        return context != null && key != null ? (pref != null && !pref.isEmpty() ?
                context.getSharedPreferences(pref, 0).getString(key, (String) null)
                : PreferenceManager.getDefaultSharedPreferences(context).getString(key, (String) null)) : null;
    }

    public static void putInt(Context context, String key, int value, String pref) {
        if (context == null || key == null) {
            return;
        }
        if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
        }
    }

    public static int getInt(Context context, String key, String pref) {
        if (context == null || key == null) {
            return 0;
        }
        if (pref == null || pref.isEmpty()) {
            return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0);
        } else {
            return context.getSharedPreferences(pref, Context.MODE_PRIVATE).getInt(key, 0);
        }
    }

    public static void putBool(Context context, String key, boolean value, String pref) {
        if (context == null || key == null) {
            return;
        }
        if (pref == null || pref.isEmpty()) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
        } else {
            context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
        }
    }

    public static boolean getBool(Context context, String key, String pref) {
        if (context == null || key == null) {
            return false;
        }
        if (pref == null || pref.isEmpty()) {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
        } else {
            return context.getSharedPreferences(pref, Context.MODE_PRIVATE).getBoolean(key, false);
        }
    }

}
