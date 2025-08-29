package com.webmobrilweatherapp.activities.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.webmobrilweatherapp.activities.fragment.metrologistsearch.SearchMayorFragmentMetrologist
import com.webmobrilweatherapp.activities.fragment.metrologistsearch.SearchMetrologistFragmentMetrologist
import com.webmobrilweatherapp.activities.fragment.metrologistsearch.SearchProfilesFragmentMetrologist
import com.webmobrilweatherapp.activities.fragment.metrologistsearch.SearchVotersFragmentMetrologist
import com.webmobrilweatherapp.activities.metrologistactivity.MetrilogistHomeActivity
import com.webmobrilweatherapp.databinding.FragmentSearchFragmentmetrologistBinding

class SearchFragmentmetrologist : Fragment() {
    lateinit var binding: FragmentSearchFragmentmetrologistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFragmentmetrologistBinding.inflate(layoutInflater)
        (requireActivity() as MetrilogistHomeActivity).updateBottomBar(3)

        setupViewPager(binding.ViewPagerSearch)
        binding.simpleTabLayoutSearch.setupWithViewPager(binding.ViewPagerSearch)
        return binding.root
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(SearchProfilesFragmentMetrologist(), "Profile")
        adapter.addFragment(SearchVotersFragmentMetrologist(), "Voters")
        adapter.addFragment(SearchMetrologistFragmentMetrologist(), "Meteorologist")
        adapter.addFragment(SearchMayorFragmentMetrologist(), "Mayor")
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