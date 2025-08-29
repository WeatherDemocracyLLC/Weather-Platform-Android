package com.webmobrilweatherapp.activities.fragment.instafollowmetrologist

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
import com.webmobrilweatherapp.databinding.FragmentMetrologistAboutFollowBinding

class MetrologistAboutFollowFragment : Fragment() {
    lateinit var binding: FragmentMetrologistAboutFollowBinding
    private var userInstaid = ""
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null


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
        binding=FragmentMetrologistAboutFollowBinding.inflate(layoutInflater)
        getuserprofileMetrologist()
        return binding.root
    }
    private fun getuserprofileMetrologist() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
     //   userInstaid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserIdMetrologist)
        accountViewModelMetrologist?.getuserprofileMetrologist(
            userInstaid, "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
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
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}