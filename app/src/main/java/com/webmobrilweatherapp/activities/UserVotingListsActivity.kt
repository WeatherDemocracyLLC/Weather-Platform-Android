package com.webmobrilweatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityUserVotingListsBinding
import com.webmobrilweatherapp.fragment.uservotinglist.UserDayAfterFragmentMetrologist
import com.webmobrilweatherapp.fragment.uservotinglist.UserDayFragmentMetrologist
import com.webmobrilweatherapp.fragment.uservotinglist.UserTomorrowFragmentMetrologist

class UserVotingListsActivity : AppCompatActivity() {
    lateinit var binding:ActivityUserVotingListsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_user_voting_lists)
        setupViewPager(binding.ViewPagermyaddress)
        binding.simpleTabLayoutmyaddress.setupWithViewPager(binding.ViewPagermyaddress)
        binding.imgarrowbackvoting.setOnClickListener {
            onBackPressed()
        }
     }
    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(UserDayFragmentMetrologist(), "Day")
        adapter.addFragment(UserTomorrowFragmentMetrologist(), "Tomorrow")
        adapter.addFragment(UserDayAfterFragmentMetrologist(), "        Day After Tomorrow")
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