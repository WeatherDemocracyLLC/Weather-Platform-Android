package com.webmobrilweatherapp.fragment

import com.webmobrilweatherapp.model.InstaPostData
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.adapters.InstaPostAdapter
import com.webmobrilweatherapp.databinding.FragmentHomeinstaBinding
import java.util.ArrayList

class HomeinstaFragment : Fragment() {
    private var listModelsdata: MutableList<InstaPostData> = arrayListOf()
    lateinit var binding: FragmentHomeinstaBinding


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SearchFragment.
         */
        @JvmStatic
        fun newInstance() =
            HomeinstaFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding = FragmentHomeinstaBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listModelsdata = ArrayList()


        //usertype = SharedPreferenceManager.getInstance(activity).getUserType()!!
//        if (usertype == 2) {
//            constraintchat.setBackgroundResource(R.drawable.ic_metrobackcolor)
//        }
        val linearLayoutManager1 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recycleviewhomeinsta.layoutManager = linearLayoutManager1

        listModelsdata.add(InstaPostData())
        listModelsdata.add(InstaPostData())
        listModelsdata.add(InstaPostData())
        listModelsdata.add(InstaPostData())
        listModelsdata.add(InstaPostData())
        listModelsdata.add(InstaPostData())
        binding.recycleviewhomeinsta.adapter = InstaPostAdapter(requireContext(), listModelsdata)
    }
}