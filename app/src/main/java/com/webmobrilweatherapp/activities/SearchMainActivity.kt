package com.webmobrilweatherapp.activities

import com.webmobrilweatherapp.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.webmobrilweatherapp.adapters.ViewPagerAdapterss
import com.webmobrilweatherapp.fragment.usersearch.ProfileSearchkFragment
import com.webmobrilweatherapp.fragment.usersearch.VotersSearchFragment
import com.webmobrilweatherapp.fragment.usersearch.MeterologistSearchFragment
import com.webmobrilweatherapp.fragment.usersearch.MayorSearchFragment
import android.view.View
import com.webmobrilweatherapp.databinding.ActivitySearchMainBinding

class SearchMainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivitySearchMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialization()
        listener()


    }

    private fun initialization() {

        setUpAdapter()


    }

    private fun listener() {
        binding.imgarrowbacksearch.setOnClickListener(this)

    }

    override fun onClick(v: View?) {


        when (v?.id) {

            R.id.imgarrowbacksearch -> {

                onBackPressed()

            }

        }
    }

    private fun setUpAdapter() {

        val adapter = ViewPagerAdapterss(supportFragmentManager)
        adapter.addFragment(ProfileSearchkFragment(), "Abc")
        adapter.addFragment(VotersSearchFragment(), "A")
        adapter.addFragment(MeterologistSearchFragment(), "BHHH")
        adapter.addFragment(MayorSearchFragment(), "abg")
        binding.viewpagersearch.adapter = adapter
    }
}