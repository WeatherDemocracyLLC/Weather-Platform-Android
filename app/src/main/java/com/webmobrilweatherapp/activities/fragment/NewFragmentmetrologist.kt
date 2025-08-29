package com.webmobrilweatherapp.activities.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModelNews
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistactivity.MetrilogistHomeActivity
import com.webmobrilweatherapp.adapters.NewsAdapter
import com.webmobrilweatherapp.databinding.FragmentNewFragmentmetrologistBinding
import com.webmobrilweatherapp.model.NewsItem

class NewFragmentmetrologist : Fragment() {
    lateinit var binding: FragmentNewFragmentmetrologistBinding
    lateinit var newsAdapter: NewsAdapter
    var accountViewModelNews: AccountViewModelNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModelNews = ViewModelProvider(this).get(AccountViewModelNews::class.java)
        binding = FragmentNewFragmentmetrologistBinding.inflate(layoutInflater)
        (requireActivity() as MetrilogistHomeActivity).updateBottomBar(1)
          getnewuser()
        return binding.root
    }

    private fun getnewuser() {
        ProgressD.showLoading(context, getResources().getString(R.string.logging_in))
        accountViewModelNews?.getNewUser(
            "true",
            "8bf9c6ba60msha6bf0420ff39563p137db7jsn9bae606fee8a",
            "weather338.p.rapidapi.com"
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (it != null) {
                    binding.txtnonews.visibility = View.GONE
                    newsAdapter = NewsAdapter(requireActivity(), it as List<NewsItem>)
                    val layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.recylerViewNews.layoutManager = layoutManager
                    binding.recylerViewNews.adapter = newsAdapter
                } else {
                    binding.txtnonews.visibility = View.VISIBLE
                }

              //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()

            }

}
}
