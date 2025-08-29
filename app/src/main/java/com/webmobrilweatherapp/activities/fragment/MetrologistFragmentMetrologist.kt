package com.webmobrilweatherapp.activities.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistMetrologistAdapter
import com.webmobrilweatherapp.databinding.FragmentMetrologistMetrologistBinding


class MetrologistFragmentMetrologist : Fragment() {
    lateinit var binding: FragmentMetrologistMetrologistBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMetrologistMetrologistBinding.inflate(layoutInflater)
        initmetrologistItem()
        return binding.root
    }

    private fun initmetrologistItem() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.RecylerViewMetrologist.layoutManager = layoutManager
        binding.RecylerViewMetrologist.adapter = MetrologistMetrologistAdapter(requireContext())

    }
}