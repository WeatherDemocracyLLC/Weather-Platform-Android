package com.webmobrilweatherapp.utilise

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern

object CommonUtil {

    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val REGEX_MOBILE   = "^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[6789]\\d{9}|(\\d[ -]?){10}\\d\$"
    val passwordpattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$"



    fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun isTextValue(editText: TextView): Boolean {
        val getValue = editText.text.toString().trim { it <= ' ' }
        return getValue != ""
    }


    fun isStringValue(value: String?): Boolean {
        return !(value == null || value == "" || value == " ")
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && target!!.matches(emailPattern.toRegex())

    }

    fun isValidPassword(password: String): Boolean {

        val pattern = Pattern.compile(passwordpattern)
        val matcher = pattern.matcher(password)

        return matcher.matches()
    }


    fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network?.isConnected) ?: false
    }



    fun isValidMobile(phone: String): Boolean {
        return !TextUtils.isEmpty(phone) &&  phone.matches(REGEX_MOBILE.toRegex())
    }


    fun checkValidation(target: String): Boolean {

        return if (target.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        } else {
            //Patterns.PHONE.matcher(target).matches()
            target.matches(REGEX_MOBILE.toRegex())
        }
    }



    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun generateSSHKey(context: Context){
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.getEncoder().encode(md.digest()))
                Log.i("AppLog", "key:$hashKey")
            }
        } catch (e: Exception) {
            Log.e("AppLog", "error:", e)
        }

    }

    // url = file path or whatever suitable URL you want.
    fun getMimeType(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

}