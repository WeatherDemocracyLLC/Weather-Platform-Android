package com.webmobrilweatherapp.activities.fragment.metrologistsearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistSearchprofileAdapter
import com.webmobrilweatherapp.model.usersearching.Datum
import com.webmobrilweatherapp.databinding.FragmentSearchMayorMetrologistBinding

class SearchMayorFragmentMetrologist : Fragment(),
    MetrologistSearchprofileAdapter.AdapterInterface {
    lateinit var binding: FragmentSearchMayorMetrologistBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    var userType = 4
    var listSize = 0
    private lateinit var metrologistSearchprofileAdapter: MetrologistSearchprofileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchMayorMetrologistBinding.inflate(layoutInflater)
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        getmayorlistsMetrologist()
        return binding.root
    }
    private fun getmayorlistsMetrologist() {
        //  ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getsearchprofilesmetrologist(
            userType.toString(),
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (!requireActivity().isFinishing) {

                    if (binding.recylerviewSearchMayor != null) {
                        if (it != null && it.success == true) {
                            if (it.data as ArrayList<Datum> != null && it.data.size > 0) {
                                binding.searchVoterMterologist.visibility = View.VISIBLE
                                metrologistSearchprofileAdapter = MetrologistSearchprofileAdapter(
                                    requireContext(), it.data as ArrayList<Datum>, this,binding.txtNoUser)
                                val layoutManager = LinearLayoutManager(requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false)
                                binding.recylerviewSearchMayor.layoutManager = layoutManager
                                binding.recylerviewSearchMayor.adapter =
                                    metrologistSearchprofileAdapter
                                //   Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            } else {
                                binding.searchVoterMterologist.visibility = View.GONE
                            }

                        } else {
                            Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        binding.searcbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val text: String = binding.searcbar.text.toString().toLowerCase()
                metrologistSearchprofileAdapter.getFilter(binding.txtNoUser).filter(text)
                if (listSize > 0)
                    metrologistSearchprofileAdapter.getFilter(binding.txtNoUser).filter(text)
            }
        })
    }
    override fun onItemClick(id: String, name: String) {
        /*  intent.putExtra("id", id)
       intent.putExtra("airport", name)*/
    }
    override fun onResume() {
        getmayorlistsMetrologist()
        super.onResume()
    }
}