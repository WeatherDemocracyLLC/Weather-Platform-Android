package com.webmobrilweatherapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.webmobrilweatherapp.fragment.*

class HomeInstaAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun createFragment(position: Int): Fragment {


        /*  when (position) {
              0 -> {
                  return HomeinstaFragment.newInstance()
              }
              1 -> {
                  return PostsinstaFragment.newInstance()
              }
              2 -> {
                  return PhotosinstaFragment.newInstance()
              }
              3 -> {
                  return MayorinstaFragment.newInstance()
              }
              4 -> {
                  return AboutinstaFragment.newInstance()
              }
          }*/
        return HomeinstaFragment.newInstance()
    }

    override fun getItemCount(): Int {
        return TAB_COUNT
    }

    companion object {
        private const val TAB_COUNT = 5
    }
}