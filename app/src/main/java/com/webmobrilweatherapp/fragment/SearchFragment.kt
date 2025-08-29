package com.webmobrilweatherapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.webmobrilweatherapp.activities.HomeActivity
import com.webmobrilweatherapp.databinding.FragmentSearchBinding
import com.webmobrilweatherapp.fragment.usersearch.MayorSearchFragment
import com.webmobrilweatherapp.fragment.usersearch.MeterologistSearchFragment
import com.webmobrilweatherapp.fragment.usersearch.ProfileSearchkFragment
import com.webmobrilweatherapp.fragment.usersearch.VotersSearchFragment


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        (requireActivity() as HomeActivity).updateBottomBars(3)
        setupViewPager(binding.viewPager)
        binding.tabProfile.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ProfileSearchkFragment(), "Profile")
        adapter.addFragment(VotersSearchFragment(), "Voters")
        adapter.addFragment(MeterologistSearchFragment(), "Meteorologist")
        adapter.addFragment(MayorSearchFragment(), "Mayor")
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