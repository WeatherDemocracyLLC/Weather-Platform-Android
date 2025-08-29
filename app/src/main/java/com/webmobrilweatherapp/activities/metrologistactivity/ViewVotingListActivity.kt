package com.webmobrilweatherapp.activities.metrologistactivity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.fragment.DayAfterFragmentMetrologist
import com.webmobrilweatherapp.activities.fragment.DayFragmentMetrologist
import com.webmobrilweatherapp.activities.fragment.TomorrowFragmentMetrologist
import com.webmobrilweatherapp.databinding.ActivityViewVotingListBinding

class ViewVotingListActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewVotingListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_voting_list)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.layoutViewVotingList.imgbackicon.setOnClickListener {
            onBackPressed()
        }
        binding.layoutViewVotingList.txtHeaderName.setText("View Voting List")
        setupViewPager(binding.ViewPagermyaddress)
        binding.simpleTabLayoutmyaddress.setupWithViewPager(binding.ViewPagermyaddress)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DayFragmentMetrologist(), "Day")
        adapter.addFragment(TomorrowFragmentMetrologist(), "Tomorrow")
        adapter.addFragment(DayAfterFragmentMetrologist(), "        Day After Tomorrow")
        viewPager.adapter = adapter
    }

    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

    }
}