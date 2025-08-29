package com.webmobrilweatherapp.fragment.profile

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
 import com.webmobrilweatherapp.databinding.FragmentProfileMayorBinding
 import com.webmobrilweatherapp.model.mayorwether.DataItems


class profileMayorFragment : Fragment() {
    lateinit var binding: FragmentProfileMayorBinding
    private var userid = 0
    private lateinit var makingMayorAdapter: MakingMayorAdapter
    var accountViewModel: AccountViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentProfileMayorBinding.inflate(layoutInflater)
        getmakingweathervote()
        return binding.root
    }

    private fun getmakingweathervote() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid =
            CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getmakingweathervote(binding.txtNoMayor, requireContext(),
            userid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token))
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (binding.recylerviewMayor != null) {

                    if (it != null && it.success == true) {
                        if (it.data as List<DataItems> != null && it.data.size > 0) {
                            binding.txtNoMayor.visibility = GONE
                            makingMayorAdapter = MakingMayorAdapter(requireActivity(),
                                it.data as ArrayList<DataItems>,
                                it.data[0].mayor)
                            val layoutManager = LinearLayoutManager(requireContext(),
                                LinearLayoutManager.VERTICAL, false)
                            binding.recylerviewMayor.layoutManager = layoutManager
                            binding.recylerviewMayor.adapter = makingMayorAdapter
                        } else {
                            binding.txtNoMayor.visibility = VISIBLE
                        }
                    } else {
                                                }
                }

            }
    }
}