package kirill.sorokin.ru.calculatorcurrency;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kirill on 13.04.17.
 */
public class PrefManager {

    private static final String PREF_NAME = "MyPref";
    private static final String VAL_CURS = "VAL_CURS";

    private static void setB(Context ctx, String settingName, boolean value) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(settingName, value);
        ed.commit();
    }

    private static void setI(Context ctx, String settingName, int value) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(settingName, value);
        ed.commit();
    }

    private static void setS(Context ctx, String settingName, String value) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(settingName, value);
        ed.commit();
    }

    private static void setL(Context ctx, String settingName, long value) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putLong(settingName, value);
        ed.commit();
    }

    private static boolean getB(Context ctx, String settingName, boolean defaultValue) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sPref.getBoolean(settingName, defaultValue);
    }

    private static String getS(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sPref.getString(settingName, defaultValue);
    }

    private static int getI(Context ctx, String settingName, int defaultValue) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sPref.getInt(settingName, defaultValue);
    }

    private static long getL(Context ctx, String settingName, long defaultValue) {
        SharedPreferences sPref = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sPref.getLong(settingName, defaultValue);
    }
    
    /*************/

    public static String getValCurs(Context context) {
        return getS(context, VAL_CURS, null);
    }

    public static void setValCurs(Context context, String valCurs) {
        setS(context, VAL_CURS, valCurs);
    }
    
}
