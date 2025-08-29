package com.webmobrilweatherapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModelNews
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.HomeActivity
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.adapters.NewsAdapter
import com.webmobrilweatherapp.databinding.FragmentNewsBinding
import com.webmobrilweatherapp.model.NewsItem

class NewsFragment : Fragment() {

    lateinit var binding: FragmentNewsBinding
     lateinit var newsAdapter: NewsAdapter
    var accountViewModelNews: AccountViewModelNews?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(layoutInflater)
        (requireActivity() as HomeActivity).updateBottomBars(1)
        accountViewModelNews = ViewModelProvider(this).get(AccountViewModelNews::class.java)

        getnewuser()
        return binding.root
    }


    private fun getnewuser() {
         ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        Log.d("TAG", "getnewuserwewqewqeqwe ")
        accountViewModelNews?.getNewUser(
            "true",
            "8bf9c6ba60msha6bf0420ff39563p137db7jsn9bae606fee8a",
            "weather338.p.rapidapi.com")
            ?.observe(requireActivity()) {
                Log.d("TAG", "1342134${it} ")
                 ProgressD.hideProgressDialog()
                   if (it?.size!! >0) {
                        binding.txtnonews.visibility = View.GONE
                        newsAdapter =NewsAdapter(requireActivity(), it as List<NewsItem>)
                        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.recylerViewNews.layoutManager = layoutManager
                        binding.recylerViewNews.adapter = newsAdapter
                        // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }else{
                        binding.txtnonews.visibility=VISIBLE
                    }
                }

    }

}