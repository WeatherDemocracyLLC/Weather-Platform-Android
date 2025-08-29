package com.webmobrilweatherapp.activities.fragment.mterologistprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.adapters.MakingMayorAdapter
import com.webmobrilweatherapp.model.mayorwether.DataItems
import com.webmobrilweatherapp.databinding.FragmentProfileMayorBinding

class profileMayorFragmentMetrologist : Fragment() {
    lateinit var binding: FragmentProfileMayorBinding
    private var useridMetrologist = "0"
    private lateinit var makingMayorAdapter: MakingMayorAdapter
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=FragmentProfileMayorBinding.inflate(layoutInflater)
       getmakingweathervote()
        return binding.root
    }

    private fun getmakingweathervote() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        useridMetrologist = CommonMethod.getInstance(requireContext())
            .getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getmakingweathervotemetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token))
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItems> != null && it.data.size > 0) {
                        binding.txtNoMayor.visibility= View.GONE
                        makingMayorAdapter = MakingMayorAdapter(requireActivity(), it.data as ArrayList<DataItems>,it.data[0]. mayor)
                        val layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        binding.recylerviewMayor.layoutManager = layoutManager
                        binding.recylerviewMayor.adapter = makingMayorAdapter
                    }else{
                        binding.txtNoMayor.visibility= View.VISIBLE
                    }
                } else {
                    //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}