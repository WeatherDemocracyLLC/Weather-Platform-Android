package com.webmobrilweatherapp.activities.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.activities.metrologistadapter.ProfilesMetrologistAdapter
import com.webmobrilweatherapp.databinding.FragmentProfilesMetrologistBinding

class ProfilesFragmentMetrologist : Fragment() {
    lateinit var binding: FragmentProfilesMetrologistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfilesMetrologistBinding.inflate(layoutInflater)
        initProfileItem()
        return binding.root
    }

    private fun initProfileItem() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.RecylerViewProfiles.layoutManager = layoutManager
        binding.RecylerViewProfiles.adapter = ProfilesMetrologistAdapter(requireContext())

    }
}