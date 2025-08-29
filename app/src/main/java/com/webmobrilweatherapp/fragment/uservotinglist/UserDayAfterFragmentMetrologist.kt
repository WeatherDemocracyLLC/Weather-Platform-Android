package com.webmobrilweatherapp.fragment.uservotinglist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.util.Log
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistDayAfterTomorrowAdapter
import com.webmobrilweatherapp.databinding.FragmentUserDayAfterMetrologistBinding
import com.webmobrilweatherapp.model.metrologist.viewuservote.DayAfterTomorrowItem

class UserDayAfterFragmentMetrologist : Fragment() ,MetrologistDayAfterTomorrowAdapter.AdapterInterface{
    lateinit var binding:FragmentUserDayAfterMetrologistBinding
    private lateinit var metrologistDayAfterTomorrowAdapter: MetrologistDayAfterTomorrowAdapter
    var listSize = 0
    var accountViewModel: AccountViewModel? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentUserDayAfterMetrologistBinding.inflate(layoutInflater)
        metrologistDayAfterTomorrow()
        return binding.root
    }
    private fun metrologistDayAfterTomorrow() {
        // ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        accountViewModel?.getViewVoteUser(
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token)
        )
            ?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    Log.e("i am here","top");
                    if (it.data!!.dayAfterTomorrow!! as List<DayAfterTomorrowItem> != null && it.data.dayAfterTomorrow?.size!! > 0) {
                        Log.e("i am here","second");
                        if(it.data.dayAfterTomorrow.isNotEmpty()){
                            Log.e("i am here","whivsecond");
                            binding.txtNovotes.visibility = View.GONE
                        }else{
                            Log.e("i am here","thissecond");
                            binding.txtNovotes.visibility = VISIBLE
                        }

                        binding.dayAfterfragmentmetrologist.visibility = View.VISIBLE
                        metrologistDayAfterTomorrowAdapter = MetrologistDayAfterTomorrowAdapter(
                            requireContext(),
                            it.data.dayAfterTomorrow!! as ArrayList<DayAfterTomorrowItem>,
                            this,binding.txtNovotes
                        )
                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.recylerviewDayAfter.layoutManager = layoutManager
                        binding.recylerviewDayAfter.adapter = metrologistDayAfterTomorrowAdapter
                        binding.searcbar.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                            override fun afterTextChanged(editable: Editable) {
                                val text: String =
                                    binding.searcbar.text.toString().toLowerCase()
                                    metrologistDayAfterTomorrowAdapter.getFilter(binding.txtNovotes).filter(text)
                                    if (listSize > 0)
                                        metrologistDayAfterTomorrowAdapter.getFilter(binding.txtNovotes).filter(text)
                            }
                        })
                        // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        Log.e("i am here","firt");
                        binding.txtNovotes.visibility = VISIBLE
                        binding.dayAfterfragmentmetrologist.visibility = View.GONE
                    }
                } else {
                    Log.e("i am here","=final");
                    binding.txtNovotes.visibility = VISIBLE
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun onItemClick(id: String, name: String) {

    }
}