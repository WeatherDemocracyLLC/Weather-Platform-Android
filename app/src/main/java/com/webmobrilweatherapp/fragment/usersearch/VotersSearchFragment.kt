package com.webmobrilweatherapp.fragment.usersearch

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.HomeActivity
import com.webmobrilweatherapp.activities.LoginActivity
import com.webmobrilweatherapp.adapters.UserSearchprofileAdapter
import com.webmobrilweatherapp.databinding.FragmentVotersSearchBinding
import com.webmobrilweatherapp.model.usersearching.Datum


class VotersSearchFragment : Fragment(), UserSearchprofileAdapter.AdapterInterface {
    lateinit var binding: FragmentVotersSearchBinding
    var accountViewModel: AccountViewModel? = null
    private lateinit var userSearchprofileAdapter: UserSearchprofileAdapter
    var listSize = 0
    var userType = 2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = FragmentVotersSearchBinding.inflate(layoutInflater)
        getsearchprofilesmetrologist()
        return binding.root
    }

    private fun getsearchprofilesmetrologist() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        accountViewModel?.getsearchprofiles(
            userType.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()

                if(binding.recyclevoters!=null) {
                    if (it != null && it.success == true) {
                        if (it.data as ArrayList<Datum> != null && it.data.size > 0) {
                            binding.txtNoUser.visibility = GONE
                            binding.constraintsearch.visibility = VISIBLE
                            userSearchprofileAdapter = UserSearchprofileAdapter(
                                requireContext(),
                                it.data as ArrayList<Datum>,
                                this,binding.txtNoUser
                            )
                            val layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL, false
                            )
                            binding.recyclevoters.layoutManager = layoutManager
                            binding.recyclevoters.adapter = userSearchprofileAdapter
                            // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        } else {
                            binding.txtNoUser.visibility = VISIBLE
                            binding.constraintsearch.visibility = GONE
                        }

                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()
                        if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            context?.startActivity(intent)
                            (activity as HomeActivity).finish()
                        }
                    }
                }
            }
        binding.searcbar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val text: String = binding.searcbar.text.toString().toLowerCase()

                userSearchprofileAdapter.getFilter(binding.txtNoUser).filter(text)
                if (listSize > 0)
                    userSearchprofileAdapter.getFilter(binding.txtNoUser).filter(text)
            }

        })

    }

    override fun onItemClick(id: String, name: String) {
        /* intent.putExtra("id", id)
         intent.putExtra("airport", name)*/
    }

    override fun onResume() {
        getsearchprofilesmetrologist()
        super.onResume()
    }
}