package com.webmobrilweatherapp.activities.metrologistactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.fragment.MetrologistChallengeByFriendsFragment
import com.webmobrilweatherapp.activities.fragment.MetrologistChallengeByMeFragment
import com.webmobrilweatherapp.databinding.ActivityMetrologistForecastChallengeBinding

class MetrologistForecastChallengeActivity : AppCompatActivity() {
    lateinit var binding: ActivityMetrologistForecastChallengeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metrologist_forecast_challenge)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_forecast_challenge)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))


        UpdateHomeFragment(MetrologistChallengeByMeFragment(), "Home")
        binding.challengebyme.setTextColor(resources.getColor(R.color.titelbar_color))
        binding.line1.setBackgroundColor(resources.getColor(R.color.titelbar_color))
        binding.challengebyfriends.setTextColor(resources.getColor(R.color.black))
        binding.line2.setBackgroundColor(resources.getColor(R.color.black))


        binding.imgbackicon.setOnClickListener (
            View.OnClickListener {
                //onBackPressed();
                this?.startActivity(Intent(this, MetrilogistHomeActivity::class.java))
            })


        binding.challengebymeRelative.setOnClickListener(View.OnClickListener {
            UpdateHomeFragment(MetrologistChallengeByMeFragment(), "Home")
            binding.challengebyme.setTextColor(resources.getColor(R.color.titelbar_color))
            binding.line1.setBackgroundColor(resources.getColor(R.color.titelbar_color))
            binding.challengebyfriends.setTextColor(resources.getColor(R.color.black))
            binding.line2.setBackgroundColor(resources.getColor(R.color.black))
        })


        binding.challengebyfriendsRelative.setOnClickListener(View.OnClickListener {
            UpdateHomeFragment(MetrologistChallengeByFriendsFragment(), "Home")
            binding.challengebyfriends.setTextColor(resources.getColor(R.color.titelbar_color))
            binding.line2.setBackgroundColor(resources.getColor(R.color.titelbar_color))
            binding.challengebyme.setTextColor(resources.getColor(R.color.black))
            binding.line1.setBackgroundColor(resources.getColor(R.color.black))
        })
    }

    private fun UpdateHomeFragment(fragment: Fragment, title: String) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.nav_fragment, fragment)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commit()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()

    }
    }