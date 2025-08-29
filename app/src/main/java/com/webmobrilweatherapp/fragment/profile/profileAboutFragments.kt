package com.webmobrilweatherapp.fragment.profile

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
import com.webmobrilweatherapp.databinding.FragmentProfileAboutFragmentsBinding

class ProfileAboutFragments : Fragment() {
    lateinit var binding: FragmentProfileAboutFragmentsBinding
    var accountViewModel: AccountViewModel? = null
    private var userid = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentProfileAboutFragmentsBinding.inflate(layoutInflater)
        getuserprofile()
        return binding.root
    }


    private fun getuserprofile() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        userid =
            CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getuserprofile(
            userid.toString(), "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    binding.textdavidshaww.setText(it.data?.name)
                    binding.textdavidshawemail.setText(it.data?.email.toString())
                    binding.textdavidshawMadison.setText(it.data?.city)
                   // binding.textlastactive.setText(it.data?.lastActive)
                } else {
                    Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                   /* if(it!!.message.toString()=="Unauthenticated.")
                    {

                        CommonMethod.getInstance(context)
                            .savePreference(AppConstant.KEY_loginStatus, false)
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        this?.startActivity(intent)
                        (this as HomeActivity).finish()
                    }                */      }
            }
    }
}