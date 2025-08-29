package com.webmobrilweatherapp.activities

import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.webmobrilweatherapp.databinding.ActivityWinnerBinding

class WinnerActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityWinnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWinnerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
        listener()

    }

    private fun initialization() {


    }

    private fun listener() {
        binding.winnerimage.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.winnerimage -> {
                val i = Intent(this@WinnerActivity, ContactActivity::class.java)
                startActivity(i)
            }
        }

    }
}