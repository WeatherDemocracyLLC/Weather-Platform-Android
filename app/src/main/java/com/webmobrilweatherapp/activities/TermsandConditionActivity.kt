package com.webmobrilweatherapp.activities

import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.webmobrilweatherapp.databinding.ActivityTermsandConditionBinding

class TermsandConditionActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityTermsandConditionBinding
    private var usertype = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()

        binding = ActivityTermsandConditionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
        listener()
    }

    private fun initialization() {

        if (usertype == "3") {
            binding.relativetermscondition.setBackgroundResource(R.drawable.ic_metrobackcolor)
            binding.toolbar.setBackgroundResource(R.color.metroappback)
        }
    }

    private fun listener() {
        binding.imgtermcondition.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.imgtermcondition -> {

                onBackPressed()
            }

        }
    }
}