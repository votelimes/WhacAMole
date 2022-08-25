package com.votelimes.whacamole.util;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScore {
    private static final String PREFS_HIGHSCORE_TAG = "whacamole_highscore";
    public static int getLast(Context context){
        SharedPreferences prefs = context.getSharedPreferences(
                "com.votelimes.whacamole", Context.MODE_PRIVATE);

        return prefs.getInt(PREFS_HIGHSCORE_TAG, 0);
    }
    public static void put(Context context, int highscore){
        SharedPreferences prefs = context.getSharedPreferences(
                "com.votelimes.whacamole", Context.MODE_PRIVATE);
        prefs.edit().putInt(PREFS_HIGHSCORE_TAG, highscore).apply();
    }
}
