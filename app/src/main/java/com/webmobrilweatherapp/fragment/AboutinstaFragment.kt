package com.webmobrilweatherapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.webmobrilweatherapp.databinding.FragmentAboutinstaBinding

class AboutinstaFragment : Fragment() {
    lateinit var binding: FragmentAboutinstaBinding

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance() =
            AboutinstaFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutinstaBinding.inflate(layoutInflater)
        return binding.root
    }

}