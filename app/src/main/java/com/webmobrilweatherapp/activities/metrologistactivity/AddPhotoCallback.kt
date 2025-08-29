package com.webmobrilweatherapp.activities.metrologistactivity

import android.net.Uri
import java.io.File

interface AddPhotoCallback {
    fun getImageFile(imageFile : File, imageUri : Uri)
}