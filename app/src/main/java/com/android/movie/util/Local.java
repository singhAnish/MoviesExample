package com.android.movie.util;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.movie.MyApp;


public class Local {

    public static String getString(int string) {
        return MyApp.getContext().getResources().getString(string);
    }

    public static int getDimen(int dimen) {
        return (int) MyApp.getContext().getResources().getDimension(dimen);
    }

    public static int getColor(int color) {
        return ContextCompat.getColor(MyApp.getContext(), color);
    }

    public static Drawable getDrawable(int drawable) {
        return ContextCompat.getDrawable(MyApp.getContext(), drawable);
    }

    public static String getStringFromResourceKey(String resourceKey) {
        int resId;
        try {
            resId = MyApp.getContext().getResources().getIdentifier(resourceKey, "string", MyApp.getContext().getPackageName());
        } catch (Exception e) {
            resId = 0;
        }
        return resId != 0 ? getString(resId) : resourceKey;
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static void logMessage(String msg) {
        Log.d("Anish ", msg);
    }

    public static void toastMessage(int msg) {
        Toast toast = Toast.makeText(MyApp.getContext(), getString(msg), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void toastStringMessage(String msg) {
        Toast toast = Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String getDurationHourMin(final int duration) {
        StringBuilder builder = new StringBuilder();
        if (duration / 60 > 0) {
            builder.append(String.valueOf(duration / 60)).append(" ");
            if (duration % 60 > 0) {
                if (duration / 60 > 1) {
                    builder.append("hrs ");
                } else {
                    builder.append("hr ");
                }
                builder.append(" ").append(String.valueOf(duration % 60)).append(" ").append("min");
            } else {
                if (duration / 60 > 1) {
                    builder.append("hrs ");
                } else {
                    builder.append("hr ");
                }
            }
        } else {
            builder.append(String.valueOf(duration % 60)).append(" ").append("min");
        }
        return builder.toString();
    }
}