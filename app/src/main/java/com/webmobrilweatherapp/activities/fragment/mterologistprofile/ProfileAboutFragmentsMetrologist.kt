package com.webmobrilweatherapp.activities.fragment.mterologistprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.databinding.FragmentProfileAboutFragmentsMetrologistBinding


class ProfileAboutFragmentsMetrologist : Fragment() {
    lateinit var binding: FragmentProfileAboutFragmentsMetrologistBinding
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
        binding = FragmentProfileAboutFragmentsMetrologistBinding.inflate(layoutInflater)
        getuserprofileMetrologist()
        return binding.root
    }


    private fun getuserprofileMetrologist() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        useridMetrologist = CommonMethod.getInstance(requireContext())
            .getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.textdavidshaww.setText(it.data?.name)
                    binding.textdavidshawemail.setText(it.data?.email.toString())
                    binding.textdavidshawMadison.setText(it.data?.city)
                    binding.textlastactive.setText(it.data?.lastActive)
                } else {
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }
}