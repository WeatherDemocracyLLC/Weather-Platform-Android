package com.webmobrilweatherapp.activities.metrologistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityMetrologistAboutUsBinding

class MetrologistAboutUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistAboutUsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_about_us)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.layoutAboutUs.ImgBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutAboutUs.txtLocation.setText("About Us")
    }
}