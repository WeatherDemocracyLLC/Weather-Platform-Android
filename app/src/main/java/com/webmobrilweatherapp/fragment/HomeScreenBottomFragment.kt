package com.webmobrilweatherapp.fragment

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.Bottomdialogadapter


class HomeScreenBottomFragment : BottomSheetDialogFragment() {
    var accountViewModel: AccountViewModel? = null
    lateinit var binding: HomeScreenBottomFragment


    private lateinit var bottomdialogadapter: Bottomdialogadapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        return inflater.inflate(R.layout.homescreenbottom, container, false)
        //getprepration()
    }
    /*private fun getprepration() {
        // Get user sign up response
        // var strToken= CommonMethod.getInstance(requireContext()).getPreference<>(AppConstant.KEY)
        accountViewModel?.getprepration("Bearer "+CommonMethod.getInstance(context).getPreference(
            AppConstant.KEY_token))
            ?.observe(requireActivity(), {
                // ProgressBarUtils.hideProgressDialog()
                if (it != null&& it.code==200) {
                    bottomdialogadapter = Bottomdialogadapter(context, it.code as List<DataItem>)


                    binding.recyclerView.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
                    binding.recylerviewbottom.adapter = bottomdialogadapter
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it?.message,Toast.LENGTH_LONG).show()
                }
            })
    }*/
}