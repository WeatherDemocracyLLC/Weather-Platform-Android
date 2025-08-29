package com.webmobrilweatherapp.activities.fragment.instafollowmetrologist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.metrologistadapter.ProfilefragmentphotosMetrologistAdapter
import com.webmobrilweatherapp.databinding.FragmentMetrologistPhotoFollowBinding

class MetrologistPhotoFollowFragment : Fragment() {
    lateinit var binding: FragmentMetrologistPhotoFollowBinding
    private var userInstaid = ""
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private lateinit var profilefragmentphotosMetrologistAdapter: ProfilefragmentphotosMetrologistAdapter


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
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=FragmentMetrologistPhotoFollowBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        getpostimageuser()
        return binding.root
    }
    private fun getpostimageuser() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
     //   userInstaid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_IntsUserIdMetrologist)
        accountViewModelMetrologist?.getpostimageMetrologist(
            userInstaid, "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.post as List<String> != null && it.post.size > 0) {
                        binding.txtNoPhoto.visibility = View.GONE
                        profilefragmentphotosMetrologistAdapter =
                            ProfilefragmentphotosMetrologistAdapter(
                                requireActivity(),
                                it.post as List<String>
                            )
                        val layoutManager = GridLayoutManager(requireContext(), 3)
                        binding.RecylerViewPhotosMetrologist.layoutManager = layoutManager
                        binding.RecylerViewPhotosMetrologist.adapter =
                            profilefragmentphotosMetrologistAdapter
                        // Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                    } else {
                        binding.txtNoPhoto.visibility = View.VISIBLE
                    }

                } else {
                    //  Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}