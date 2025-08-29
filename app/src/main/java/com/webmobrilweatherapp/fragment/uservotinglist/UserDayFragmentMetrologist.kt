package com.webmobrilweatherapp.fragment.uservotinglist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistDayAdapter
import com.webmobrilweatherapp.databinding.FragmentUserDayMetrologistBinding
import com.webmobrilweatherapp.model.metrologist.viewuservote.TodayItem

class UserDayFragmentMetrologist : Fragment(), MetrologistDayAdapter.AdapterInterface {
    lateinit var binding:FragmentUserDayMetrologistBinding
    var listSize = 0
    var accountViewModel: AccountViewModel? = null
    private lateinit var metrologistDayAdapter: MetrologistDayAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentUserDayMetrologistBinding.inflate(layoutInflater)
        getViewVoteUser()
        return binding.root
    }

    private fun getViewVoteUser() {
        ProgressD.showLoading(requireContext(), getResources().getString(R.string.logging_in))
        accountViewModel?.getViewVoteUser(
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token)
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    if (it.data!!.today!! as List<TodayItem> != null && it.data.today?.size!! > 0) {
                        binding.dayfragmentmetrologist.visibility= View.VISIBLE
                        binding.txtNovotes.visibility = View.GONE
                        metrologistDayAdapter = MetrologistDayAdapter(
                            requireContext(),
                            it.data.today!! as ArrayList<TodayItem>,
                            this,binding.txtNovotes
                        )
                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.recylerviewDay.layoutManager = layoutManager
                        binding.recylerviewDay.adapter = metrologistDayAdapter
                        binding.searcbar.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                            override fun afterTextChanged(editable: Editable) {
                                val text: String =
                                    binding.searcbar.text.toString().toLowerCase()
                                    metrologistDayAdapter.getFilter(binding.txtNovotes).filter(text)
                                    if (listSize > 0)
                                        metrologistDayAdapter.getFilter(binding.txtNovotes).filter(text)

                            }
                        })
                        // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        binding.txtNovotes.visibility = View.VISIBLE
                        binding.dayfragmentmetrologist.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun onItemClick(id: String, name: String) {
    }
}