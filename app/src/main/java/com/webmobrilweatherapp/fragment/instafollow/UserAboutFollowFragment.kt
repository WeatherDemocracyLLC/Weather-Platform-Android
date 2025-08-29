package com.webmobrilweatherapp.fragment.instafollow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.databinding.FragmentUserAboutFollowBinding

class UserAboutFollowFragment : Fragment() {
    lateinit var binding:FragmentUserAboutFollowBinding
    private var userInstaid = ""
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
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentUserAboutFollowBinding.inflate(layoutInflater)
        getuserprofile()
        return binding.root
    }

    private fun getuserprofile() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
      //  userInstaid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserId)
        accountViewModel?.getuserprofile(
            userInstaid.toString(), "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
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