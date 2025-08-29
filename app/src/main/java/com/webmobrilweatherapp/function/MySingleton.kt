package com.webmobrilweatherapp.function

import android.content.Context
import com.webmobrilweatherapp.R

object MySingleton {

    fun handleTheme(mContext: Context, type: String) {
        if (type == "3") {
            mContext.theme.applyStyle(R.style.OverlayThemeRed, true)
        } else {
            mContext.theme.applyStyle(R.style.OverlayThemeBlue, true)
        }
    }

}