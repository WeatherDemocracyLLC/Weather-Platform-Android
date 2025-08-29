package com.webmobrilweatherapp.activities;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Sharedpref file name
    private static final String PREF_NAME = "WeatherPlatForm";

    // All Shared Preferences Keys
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS = "pass";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_USERID = "userId";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_MOBILE = "mobile";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_TOKEN = "accessToken";
    public static final String KEY_IS_VERIFIED = "1";
    public static final String KEY_IS_TRIAL = "is_trial";
    public static final String KEY_EXPIRY_DATE = "expiry_date";
    public static final String KEY_EMERGENCY_NO_1 = "emergency_no_1";
    public static final String KEY_EMERGENCY_NO_2 = "emergency_no_2";
    public static final String KEY_USER_PIN = "user_pin";
    public static final String KEY_USER_IMG = "user_img";
    public static final String KEY_FIREBASE_ID = "Firebasetoken";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        /* editor.commit();*/
        editor.apply();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        clearDeviceToken();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
      /*  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
        _context.startActivity(i);

    }
    public void clearDeviceToken() {
        // Clearing the device token from Shared Preferences
        editor.remove(KEY_TOKEN);
        editor.apply();
        //editor.commit();
    }

    public void createUserDetailsSession(String id) {
        // Storing login value as TRUE
        editor.putString(KEY_USERID, id);
        //editor.putString(KEY_PASS, password);
        editor.commit();
    }

    public void updateUserProfile(String name, String mobile, String email) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE, mobile);

        editor.commit();
    }

    public void createNumbersSession(String no1, String no2) {
        editor.putString(KEY_EMERGENCY_NO_1, no1);
        editor.putString(KEY_EMERGENCY_NO_2, no2);
        editor.commit();
    }

    public HashMap<String, String> getEmergencyNumbers() {

        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_EMERGENCY_NO_1, pref.getString(KEY_EMERGENCY_NO_1, null));
        user.put(KEY_EMERGENCY_NO_2, pref.getString(KEY_EMERGENCY_NO_2, null));
        return user;
    }

    public void createLatLongSession(String lat, String longi) {
        editor.putString(KEY_LATITUDE, lat);
        editor.putString(KEY_LONGITUDE, longi);
        editor.commit();
    }

    public HashMap<String, String> getLatLong() {

        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_LATITUDE, pref.getString(KEY_LATITUDE, null));
        user.put(KEY_LONGITUDE, pref.getString(KEY_LONGITUDE, null));
        return user;
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USERID, pref.getString(KEY_USERID, "0"));
        return user;
    }


    public void createTokenSession(String access_token) {

        // Storing Token in pref
        editor.putString(KEY_TOKEN, access_token);
        // commit changes
        editor.commit();
    }

    public void updateUserDetail(String key,String value) {

        // Storing Token in pref
        editor.putString(key, value);
        // commit changes
        editor.commit();
    }

    public String isVerified(){
        String isVerified=pref.getString(KEY_IS_VERIFIED, "0");
        return isVerified;
    }


    /**
     * Get stored token data
     */
    public HashMap<String, String> getAccessToken() {
        HashMap<String, String> token = new HashMap<>();
        token.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        return token;
    }
    public HashMap<String, String> getuserid() {
        HashMap<String, String> userid = new HashMap<>();
        userid.put(KEY_USERID, pref.getString(KEY_USERID, null));
        return userid;
    }

    public void createUserImgSession(String logo) {
        editor.putString(KEY_USER_IMG, logo);
        editor.commit();
    }
    public static void setPreference(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }


    public HashMap<String, String> getUserImg() {
        HashMap<String, String> logo = new HashMap<>();
        logo.put(KEY_USER_IMG, pref.getString(KEY_USER_IMG, null));
        return logo;
    }

    public void createUserPinSession(String pin) {
        editor.putString(KEY_USER_PIN, pin);
        editor.commit();
    }

    public HashMap<String, String> getUserPin() {
        HashMap<String, String> logo = new HashMap<>();
        logo.put(KEY_USER_PIN, pref.getString(KEY_USER_PIN, null));
        return logo;
    }

}
