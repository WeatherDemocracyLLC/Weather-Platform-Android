package com.webmobrilweatherapp.utilise;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationUtils {

    private static final String PREFS_NAME = "notification_prefs";
    private static final String KEY_NOTIFICATION_COUNT = "notification_count";

    public static int getNotificationCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_NOTIFICATION_COUNT, 0);
    }
    public static int getNotificationCountMeterological(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_NOTIFICATION_COUNT, 0);
    }

    public static void incrementNotificationCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int currentCount = prefs.getInt(KEY_NOTIFICATION_COUNT, 0);
        prefs.edit().putInt(KEY_NOTIFICATION_COUNT, currentCount + 1).apply();
    }
    public static void incrementNotificationCountMeterological(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int currentCount = prefs.getInt(KEY_NOTIFICATION_COUNT, 0);
        prefs.edit().putInt(KEY_NOTIFICATION_COUNT, currentCount + 1).apply();
    }

    public static void clearNotificationCount(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_NOTIFICATION_COUNT, 0).apply();
    }
    public static void clearNotificationCountMeterological(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_NOTIFICATION_COUNT, 0).apply();
    }
}
