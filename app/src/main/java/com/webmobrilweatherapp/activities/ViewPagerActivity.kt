package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import com.webmobrilweatherapp.function.MySingleton
import android.os.Bundle
import android.view.View
import com.webmobrilweatherapp.adapters.HomeInstaAdapter
import com.webmobrilweatherapp.databinding.ActivityViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityViewPagerBinding
    private var usertype = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)

        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
         binding.imgeditbackprofile.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initialization() {

        val profileArray = arrayOf(
            "Home",
            "Posts",
            "Photos",
            "Mayor",
            "About",

            )
        binding.searchViewpager.adapter = HomeInstaAdapter(supportFragmentManager, lifecycle)
        binding.searchViewpager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabSearch, binding.searchViewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->

                tab.text = profileArray[position]

            }).attach()


    }

    override fun onClick(v: View?) {

    }


}