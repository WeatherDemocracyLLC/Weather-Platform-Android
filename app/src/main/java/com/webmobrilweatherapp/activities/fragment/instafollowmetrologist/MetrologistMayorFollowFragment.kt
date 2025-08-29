package com.webmobrilweatherapp.activities.fragment.instafollowmetrologist

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
import com.webmobrilweatherapp.databinding.FragmentMetrologistMayorFollowBinding

class MetrologistMayorFollowFragment : Fragment() {
    lateinit var binding: FragmentMetrologistMayorFollowBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var userInstaid = ""
    private lateinit var makingMayorAdapter: MakingMayorAdapter


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
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=FragmentMetrologistMayorFollowBinding.inflate(layoutInflater)
        getmakingweathervote()
        return binding.root
    }

    private fun getmakingweathervote() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
       // userInstaid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserIdMetrologist)
        accountViewModelMetrologist?.getmakingweathervotemetrologist(
            userInstaid, "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            ))
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItems> != null && it.data.size > 0) {
                        binding.txtNOrecord.visibility= View.GONE
                        makingMayorAdapter = MakingMayorAdapter(requireActivity(), it.data as List<DataItems>,it.data[0].mayor)
                        val layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        binding.recylerviewmetrologistmayor.layoutManager = layoutManager
                        binding.recylerviewmetrologistmayor.adapter = makingMayorAdapter
                    }else{
                        binding.txtNOrecord.visibility= View.VISIBLE
                    }
                } else {
                    //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}