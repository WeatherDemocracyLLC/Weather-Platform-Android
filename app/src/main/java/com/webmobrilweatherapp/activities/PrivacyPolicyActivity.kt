package com.webmobrilweatherapp.activities

import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import android.view.View
import com.webmobrilweatherapp.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityPrivacyPolicyBinding
    private var usertype = "0"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
        listener()

    }
    private fun initialization() {
        if (usertype == "3") {
            binding.relativeprivacypolicy.setBackgroundResource(R.drawable.ic_metrobackcolor)
            binding.toolbar.setBackgroundResource(R.color.metroappback)
        }

    }
    private fun listener() {
        binding.imgarrowprivacy.setOnClickListener(this)
        binding.RemainingEssentiallgraph.setOnClickListener(this)

    }
    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.imgarrowprivacy -> {

                onBackPressed()
            }

            /* R.id.RemainingEssentiallgraph->{

                 val i = Intent(this@PrivacyPolicyActivity, TermsandConditionActivity::class.java)
                 startActivity(i)
             }*/
        }

    }
}