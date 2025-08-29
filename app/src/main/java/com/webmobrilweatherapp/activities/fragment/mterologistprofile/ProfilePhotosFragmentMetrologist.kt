package com.webmobrilweatherapp.activities.fragment.mterologistprofile
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.metrologistadapter.ProfilefragmentphotosMetrologistAdapter
import com.webmobrilweatherapp.databinding.FragmentProfilePhotosMetrologistBinding

class ProfilePhotosFragmentMetrologist : Fragment() {
    lateinit var binding: FragmentProfilePhotosMetrologistBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
    private lateinit var profilefragmentphotosMetrologistAdapter: ProfilefragmentphotosMetrologistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = FragmentProfilePhotosMetrologistBinding.inflate(layoutInflater)
        getpostimageuser()
        return binding.root
    }

    private fun getpostimageuser() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        useridMetrologist = CommonMethod.getInstance(requireContext())
            .getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getpostimageMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                //  ProgressD.hideProgressDialog()

                if (it != null && it.success == true) {
                    if (it.post as List<String> != null && it.post.size > 0) {
                        binding.txtNoPhoto.visibility = GONE
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
                        binding.txtNoPhoto.visibility = VISIBLE
                    }

                } else {
                    binding.txtNoPhoto.visibility = VISIBLE

                }
            }
    }

}