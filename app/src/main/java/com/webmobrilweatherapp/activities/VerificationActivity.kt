package com.webmobrilweatherapp.activities

import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.webmobrilweatherapp.databinding.ActivityVerificationBinding


class VerificationActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityVerificationBinding
    private var usertype = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)

        binding = ActivityVerificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
        listener()


    }

    private fun initialization() {

        if (usertype == "3") {
            binding.constraintverification.setBackgroundResource(R.drawable.backimagemetro)
            binding.btnsendverification.setBackgroundResource(R.drawable.metroshapebutton)
            binding.imagebackvery.setBackgroundResource(R.drawable.ic_metroblueicon)
            binding.emailiconverify.setBackgroundResource(R.drawable.ic_metroemail)
            binding.imgbackscreen.setBackgroundResource(R.drawable.backimagemetro)
        }


    }

    private fun listener() {
        binding.btnsendverification.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btnsendverification -> {

                val i = Intent(this@VerificationActivity, OtpScreenActivity::class.java)
                startActivity(i)
            }

        }
    }
}