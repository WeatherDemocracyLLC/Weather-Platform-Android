package com.webmobrilweatherapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.adapters.MakingMayorAdapter
import com.webmobrilweatherapp.databinding.FragmentMayorinstaBinding
import com.webmobrilweatherapp.model.mayorwether.DataItems

class MayorinstaFragment : Fragment() {
    lateinit var binding:FragmentMayorinstaBinding
    private var userid = 0
    private lateinit var makingMayorAdapter: MakingMayorAdapter
    var accountViewModel: AccountViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentMayorinstaBinding.inflate(layoutInflater)
        getmakingweathervote()
        return binding.root
    }

    private fun getmakingweathervote() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getmakingweathervote(binding.txtNOrecord,requireContext(),
            userid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token))
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItems> != null && it.data.size > 0) {
                        binding.txtNOrecord.visibility= View.GONE
                        makingMayorAdapter = MakingMayorAdapter(requireActivity(), it.data as List<DataItems>,it.data[0].mayor)
                        val layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        binding.recylerviewInstaMayor.layoutManager = layoutManager
                        binding.recylerviewInstaMayor.adapter = makingMayorAdapter
                    }else{
                        binding.txtNOrecord.visibility= View.VISIBLE
                    }
                } else {
                    //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}