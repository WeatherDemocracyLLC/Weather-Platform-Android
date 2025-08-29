package com.webmobrilweatherapp.activities

import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.activities.metrologistactivity.MetrologistLogInActivity
import com.webmobrilweatherapp.databinding.ActivitySelectOptionBinding


class SelectOptionActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivitySelectOptionBinding
    private var pressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        usertype = SharedPreferenceManager.getInstance(this).getUserType(PrefConstant.USER_TYPE)
        MySingleton.handleTheme(this, "2")

        binding = ActivitySelectOptionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
        Glide.with(this).load(R.drawable.butterflywhite).into(binding.imgsplashiconeuser)
        listener()


    }

    private fun initialization() {


    }

    private fun listener() {
        binding.constrintoption.setOnClickListener(this)
        binding.relativemetrogists.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.constrintoption -> {
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)

            }

            R.id.relativemetrogists -> {

                val i = Intent(this, MetrologistLogInActivity::class.java)
                startActivity(i)

            }
        }

    }

    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }

}