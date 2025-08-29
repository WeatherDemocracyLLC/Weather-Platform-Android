package com.webmobrilweatherapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class CommonMethod constructor(context: Context) {
    private val sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor? = null

    init {
        val prefsFile = context?.packageName
        sharedPreferences = context!!.getSharedPreferences(prefsFile, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply()
    }


    fun deletePreference(key: String) {
        if (sharedPreferences.contains(key)) {
            sharedPreferences.edit().remove(key).apply()
        }
    }


    //don't delete device token, device token will be restored at the time of
    //login
    fun deleteAllPreference() {
        try {
            // val deviceToken = getPreference<String>(AppConstants.DEVICE_TOKEN)
            sharedPreferences.edit().clear().apply()
            //savePreference(AppConstants.DEVICE_TOKEN,deviceToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun savePreference(key: String, value: Any) {
        deletePreference(key)

        when (value) {
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            is Int -> sharedPreferences.edit().putInt(key, value).apply()
            is Float -> sharedPreferences.edit().putFloat(key, value).apply()
            is Long -> sharedPreferences.edit().putLong(key, value).apply()
            is String -> sharedPreferences.edit().putString(key, value).apply()
            is Double -> sharedPreferences.edit().putString(key, value.toString()).apply()
            is Enum<*> -> sharedPreferences.edit().putString(key, value.toString()).apply()
            else -> throw RuntimeException("Attempting to save non-primitive preference")
        }
    }

    fun <T> getPreference(key: String): T {
        return sharedPreferences.all[key] as T
    }

    fun <T> getPreference(key: String, defValue: T): T {
        val returnValue = sharedPreferences.all[key] as T
        return returnValue ?: defValue
    }

    fun isPreferenceExists(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun getPreferenceClear(context: Context) {
        val prefs = context.getSharedPreferences(AppConstant.MY_PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    var deviceToken: String?
        get() = sharedPreferences!!.getString("deviceToken", "")
        @SuppressLint("CommitPrefEdits")
        set(fcmToken) {
            editor = sharedPreferences!!.edit()
            editor!!.putString("deviceToken", fcmToken)
            editor!!.apply()
        }

    fun removeData(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)


        editor.apply()
    }


    companion object {
        @JvmStatic
        var sInstance: CommonMethod? = null

        @Synchronized
        @JvmStatic
        fun getInstance(context: Context?): CommonMethod {
            if (sInstance == null) {
                sInstance = context?.let { CommonMethod(it) }
            }
            return sInstance as CommonMethod
        }
    }
}