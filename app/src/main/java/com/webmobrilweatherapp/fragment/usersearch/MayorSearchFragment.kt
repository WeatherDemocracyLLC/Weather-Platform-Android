package com.webmobrilweatherapp.fragment.usersearch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.adapters.UserSearchprofileAdapter
import com.webmobrilweatherapp.databinding.FragmentMayorSearchBinding
import com.webmobrilweatherapp.model.usersearching.Datum

class MayorSearchFragment : Fragment(),UserSearchprofileAdapter.AdapterInterface {
    lateinit var binding:FragmentMayorSearchBinding
    var listSize = 0
    var userType = 4
    private lateinit var userSearchprofileAdapter: UserSearchprofileAdapter
    var accountViewModel: AccountViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentMayorSearchBinding.inflate(layoutInflater)
        getsearchprofilesmetrologist()
        return binding.root
    }
   /* private fun getmayorlists() {
        // ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getmayorlists(
            userid.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token))
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    var city=it.data!!.mayor!!.name.toString()
                    var dates=it.data!!.mayor!!.createdAt.toString()
                    binding.datemayor.setText(city)
                    binding.txtCity.setText(dates)
                    Glide.with(this)
                        .load(ApiConstants.IMAGE_URL +it.data.mayor!!.profileImage)
                        .placeholder(R.drawable.edit_profileicon)
                        .into(binding.IMgMayors)
                } else {
                    //Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }*/

    private fun getsearchprofilesmetrologist() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        accountViewModel?.getsearchprofiles(
            userType.toString(), "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token
            )
        )
            ?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()
                if(binding.recyclmayor!=null) {
                    if (it != null && it.success == true) {
                        if (it.data as ArrayList<Datum> != null && it.data.size > 0) {
                            binding.txtNoUser.visibility = View.GONE
                            binding.constraintsearch.visibility = View.VISIBLE
                            userSearchprofileAdapter = UserSearchprofileAdapter(
                                requireContext(),
                                it.data as ArrayList<Datum>,
                                this,binding.txtNoUser
                            )
                            val layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL, false
                            )
                            binding.recyclmayor.layoutManager = layoutManager
                            binding.recyclmayor.adapter = userSearchprofileAdapter
                            // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        } else {

                            binding.txtNoUser.visibility = View.VISIBLE
                            binding.constraintsearch.visibility = View.GONE
                        }

                    } else {
                        Toast.makeText(context, it?.message.toString(), Toast.LENGTH_LONG).show()

                       /* if(it!!.message.toString()=="Unauthenticated.")
                        {

                            CommonMethod.getInstance(context)
                                .savePreference(AppConstant.KEY_loginStatus, false)
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            context?.startActivity(intent)
                            (activity as HomeActivity).finish()
                        }*/
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
                {

                    userSearchprofileAdapter.getFilter(binding.txtNoUser).filter(text)

                }
                else{

                }
            }
        })

    }

    override fun onItemClick(id: String, name: String) {
       // TODO("Not yet implemented")
    }

    override fun onResume() {
        getsearchprofilesmetrologist()
        super.onResume()
    }
}