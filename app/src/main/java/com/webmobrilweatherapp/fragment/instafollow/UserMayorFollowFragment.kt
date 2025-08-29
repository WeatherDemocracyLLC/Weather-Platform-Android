package com.webmobrilweatherapp.fragment.instafollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.adapters.MakingMayorAdapter
import com.webmobrilweatherapp.databinding.FragmentUserMayorFollowBinding
import com.webmobrilweatherapp.model.mayorwether.DataItems

class UserMayorFollowFragment : Fragment() {
    lateinit var binding:FragmentUserMayorFollowBinding
    private var userInstaid = ""
    private lateinit var makingMayorAdapter: MakingMayorAdapter
    var accountViewModel: AccountViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userInstaid = it.getString("uID").toString() // Retrieve the passed ID
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentUserMayorFollowBinding.inflate(layoutInflater)
        getmakingweathervote()
        return binding.root
    }
    private fun getmakingweathervote() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
     //   userInstaid =CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserId)
        accountViewModel?.getmakingweathervote(binding.txtNOrecord,requireContext(),
           userInstaid, "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token))
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItems> != null && it.data.size > 0) {
                        binding.txtNOrecord.visibility=GONE
                        makingMayorAdapter = MakingMayorAdapter(requireActivity(), it.data as List<DataItems>,it.data[0].mayor)
                        val layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        binding.recylerviewInstaMayor.layoutManager = layoutManager
                        binding.recylerviewInstaMayor.adapter = makingMayorAdapter
                    }else{
                        binding.txtNOrecord.visibility= VISIBLE
                    }
                } else {
                    //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}