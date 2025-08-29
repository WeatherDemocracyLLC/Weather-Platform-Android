package com.webmobrilweatherapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.*
import com.webmobrilweatherapp.adapters.ViewvoterlistAdapter
import com.webmobrilweatherapp.databinding.FragmentViewVotesBinding
import com.webmobrilweatherapp.model.uservotinglist.DataItem
import com.webmobrilweatherapp.model.uservotinglist.User
import com.webmobrilweatherapp.viewmodel.ApiConstants

class ViewVotesFragment : Fragment() {
    lateinit var binding: FragmentViewVotesBinding
    var accountViewModel: AccountViewModel? = null
    private lateinit var viewvoterlistAdapter: ViewvoterlistAdapter
    private var userid = 0
    var userName: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentViewVotesBinding.inflate(layoutInflater)
        getUservotinglist()
        getusermayorhomepages()

        binding.textSeeall.setOnClickListener {
            val i = Intent(context, UserVotingListsActivity::class.java)
            startActivity(i)
        }
        binding.constraintvotes.setOnClickListener {
            val i = Intent(context, TopFiveMetrologistActivity::class.java)
            startActivity(i)
        }
        binding.layoutMayorodthecitry.setOnClickListener {
            val i = Intent(context, WeatherMayorActivity::class.java)
            startActivity(i)
        }
        return binding.root

    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun getUservotinglist() {
        userid = CommonMethod.getInstance(context).getPreference(AppConstant.KEY_User_id, 0)
        if (context != null) {
            //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
            accountViewModel?.getUservotinglist(
                userid.toString(),
                "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                    AppConstant.KEY_token
                )
            )
                ?.observe(requireActivity()) {
                    //ProgressD.hideProgressDialog()

                    if (isAdded){


                    if (it != null && it.success == true) {
                        if (it.data as List<DataItem> != null && it.data.size > 0) {
                            binding.txtNoVotingList.visibility = GONE
                            if (context != null) {
                                binding.textSeeall.visibility=View.VISIBLE
                                viewvoterlistAdapter = it.data.let { it1 ->ViewvoterlistAdapter(context!!,it1)
                                }!!
                                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                                binding.recycleviewvoteser.layoutManager = layoutManager
                                binding.recycleviewvoteser.adapter = viewvoterlistAdapter
                            } else {
                            }
                        } else {
                            binding.textSeeall.visibility=View.GONE

                            binding.txtNoVotingList.visibility = VISIBLE
                        }
                        // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), it?.message, Toast.LENGTH_LONG).show()
                    }
                    }
                }
        }
    }
    private fun getusermayorhomepages() {
        //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
         accountViewModel?.getusermayorhomepages("Bearer " + CommonMethod.getInstance(context).getPreference(
            AppConstant.KEY_token)
        )
            ?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()
                if (isAdded) {
                    if (it != null && it.success == true) {
                        if (it.mayor?.name != null) {
                            binding.txtcomingsoon.visibility = GONE
                            binding.imagehomegrupuser.setText(it.mayor!!.name + " \nis the mayor of the\n" + it.mayor.city + " city")
                            Glide.with(this)
                                .load(ApiConstants.IMAGE_URL + it.mayor.profileImage)
                                .placeholder(R.drawable.edit_profileicon)
                                .into(binding.imagehomelogouser)

                        } else {
                            binding.txtcomingsoon.visibility = VISIBLE
                        }


                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}