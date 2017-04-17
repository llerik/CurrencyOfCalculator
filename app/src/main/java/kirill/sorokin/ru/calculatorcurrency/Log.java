package kirill.sorokin.ru.calculatorcurrency;

import android.text.TextUtils;

/**
 * Created by Kirill on 12.04.2017.
 */
public class Log {
    private static String tag = "CalculatorCurrency";
    private static int magicNumber = -1;

    public static void v(String message) {
        android.util.Log.v(tag, getLocation() + message);
    }

    public static void w(String message) {
        android.util.Log.w(tag, getLocation() + message);
    }

    public static void e(String message, Exception e) {
        android.util.Log.e(tag, getLocation() + message, e);
    }

    public static void e(String message) {
        android.util.Log.e(tag, getLocation() + message);
    }

    public static void i(String message) {
        android.util.Log.i(tag, getLocation() + message);
    }

    public static void d(String message) {
        android.util.Log.d(tag, getLocation() + message);
    }

    public static void v() {
        android.util.Log.v(tag, getLocation());
    }

    public static void w() {
        android.util.Log.w(tag, getLocation());
    }

    public static void e() {
        android.util.Log.e(tag, getLocation());
    }

    public static void i() {
        android.util.Log.i(tag, getLocation());
    }

    public static void d() {
        android.util.Log.d(tag, getLocation());
    }

    public static String getLocation() {
        if (magicNumber > -1) {
            return getLocationFast();
        }
        return getLocationSlow();
    }

    private static String getLocationSlow() {
        final String className = Log.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;
        for (int i = 0; i < traces.length; i++) {
            StackTraceElement trace = traces[i];
            try {
                if (found) {
                    if (!trace.getClassName().startsWith(className)) {
                        magicNumber = i;
                        Class<?> clazz = Class.forName(trace.getClassName());
                        return "[" + getClassName(clazz) + ":" + trace.getMethodName() + ":" + trace.getLineNumber() + "]: ";
                    }
                } else {
                    if (trace.getClassName().startsWith(className)) {
                        found = true;
                    }
                }
            } catch (ClassNotFoundException e) {
            }
        }
        return "[]: ";
    }

    private static String getLocationFast() {
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        StackTraceElement trace = traces[magicNumber];
        try {
            Class<?> clazz = Class.forName(trace.getClassName());
            return "[" + getClassName(clazz) + ":" + trace.getMethodName() + ":" + trace.getLineNumber() + "]: ";
        } catch (ClassNotFoundException e) {
        }
        return "[]: ";
    }


    private static String getClassName(Class<?> clazz) {
        if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.getSimpleName())) {
                return clazz.getSimpleName();
            }

            return getClassName(clazz.getEnclosingClass());
        }

        return "";
    }
}
