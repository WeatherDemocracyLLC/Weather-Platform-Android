package com.webmobrilweatherapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityForecastScreenBinding

import com.webmobrilweatherapp.fragment.ChallengeByMeFragments
import com.webmobrilweatherapp.fragment.ChallengeByMeFriends

class ForecastScreen : AppCompatActivity() {
    lateinit var binding: ActivityForecastScreenBinding

    var notificationType="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_screen)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_forecast_screen)


        if(intent!=null){
            notificationType= intent.getStringExtra("notificationType").toString();
        }
        Log.e("notificationType", notificationType)


        binding.imgbackicon.setOnClickListener (
            View.OnClickListener {
                //onBackPressed();
                this.startActivity(Intent(this, HomeActivity::class.java))
            })

        if(notificationType == "6"){
            UpdateHomeFragment(ChallengeByMeFriends(), "Home")
            binding.challengebyfriends.setTextColor(resources.getColor(R.color.titelbar_color))
            binding.line2.setBackgroundColor(resources.getColor(R.color.titelbar_color))
            binding.challengebyme.setTextColor(resources.getColor(R.color.black))
            binding.line1.setBackgroundColor(resources.getColor(R.color.black))
        }
        else {
            UpdateHomeFragment(ChallengeByMeFragments(), "Home")
            binding.challengebyme.setTextColor(resources.getColor(R.color.titelbar_color))
            binding.line1.setBackgroundColor(resources.getColor(R.color.titelbar_color))
            binding.challengebyfriends.setTextColor(resources.getColor(R.color.black))
            binding.line2.setBackgroundColor(resources.getColor(R.color.black))
        }


        binding.challengebymeRelative.setOnClickListener(View.OnClickListener {
            UpdateHomeFragment(ChallengeByMeFragments(), "Home")
            binding.challengebyme.setTextColor(resources.getColor(R.color.titelbar_color))
            binding.line1.setBackgroundColor(resources.getColor(R.color.titelbar_color))
            binding.challengebyfriends.setTextColor(resources.getColor(R.color.black))
            binding.line2.setBackgroundColor(resources.getColor(R.color.black))
        })
        binding.challengebyfriendsRelative.setOnClickListener(View.OnClickListener {
            UpdateHomeFragment(ChallengeByMeFriends(), "Home")
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