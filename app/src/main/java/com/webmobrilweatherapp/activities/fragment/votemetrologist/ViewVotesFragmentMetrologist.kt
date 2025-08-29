package com.webmobrilweatherapp.activities.fragment.votemetrologist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
 import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.metrologistactivity.SendAlertActivity
import com.webmobrilweatherapp.activities.metrologistactivity.ViewVotingListActivity
import com.webmobrilweatherapp.activities.metrologistadapter.ViewvoterlistMetrologistAdapter
import com.webmobrilweatherapp.databinding.FragmentViewVotesMetrologistBinding
import com.webmobrilweatherapp.model.uservotinglist.DataItem

class ViewVotesFragmentMetrologist : Fragment() {
    lateinit var binding:FragmentViewVotesMetrologistBinding
    lateinit var viewvoterlistMetrologistAdapter: ViewvoterlistMetrologistAdapter
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=FragmentViewVotesMetrologistBinding.inflate(layoutInflater)
        binding.ImgViewUser.setOnClickListener {
            val i = Intent(activity, ViewVotingListActivity::class.java)
            startActivity(i)
        }
        binding.textSeeall.setOnClickListener {
            val i = Intent(activity, ViewVotingListActivity::class.java)
            startActivity(i)
        }
        binding.layoutSendWeathrAlart.setOnClickListener {
            var intent = Intent(context, SendAlertActivity::class.java)
            startActivity(intent)
        }
        getUservotinglistmetrologist()
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun getUservotinglistmetrologist() {
        useridMetrologist = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_idMetrologist)
        if (context != null) {
            //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
            accountViewModelMetrologist?.getUservotinglistmetrologist(
                useridMetrologist,
                "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                    AppConstant.KEY_token_Metrologist))
                ?.observe(requireActivity()) {
                    //ProgressD.hideProgressDialog()
                    if (isAdded) {


                        if (it != null && it.success == true) {
                            if (it.data as List<DataItem> != null && it.data.size > 0) {

                                binding.textSeeall.visibility = View.VISIBLE

                                binding.txtNoVotingList.visibility = View.GONE
                                if (context != null) {
                                    viewvoterlistMetrologistAdapter = it.data.let { it1 ->
                                        ViewvoterlistMetrologistAdapter(context!!, it1)
                                    }!!
                                    val layoutManager = LinearLayoutManager(context,
                                        LinearLayoutManager.VERTICAL,
                                        false)
                                    binding.recycleviewvoteser.layoutManager = layoutManager
                                    binding.recycleviewvoteser.adapter =
                                        viewvoterlistMetrologistAdapter
                                } else {

                                }
                            } else {
                                binding.textSeeall.visibility = View.GONE
                                binding.txtNoVotingList.visibility = View.VISIBLE
                            }
                            // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(requireContext(), it?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
}