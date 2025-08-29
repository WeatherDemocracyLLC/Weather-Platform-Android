package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import android.view.View
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityAboutUsBinding


class AboutUsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityAboutUsBinding
    private var usertype = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        listener()
    }

    private fun listener() {
        binding.toolbar.imgarrowbackabout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.imgarrowbackabout -> {
                onBackPressed()
            }
        }

    }
}