package com.webmobrilweatherapp.activities.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistDayAdapter
import com.webmobrilweatherapp.databinding.FragmentDayMetrologistBinding
import com.webmobrilweatherapp.model.metrologist.viewuservote.TodayItem

class DayFragmentMetrologist : Fragment(), MetrologistDayAdapter.AdapterInterface {
    lateinit var binding: FragmentDayMetrologistBinding
    var listSize = 0
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private lateinit var metrologistDayAdapter: MetrologistDayAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = FragmentDayMetrologistBinding.inflate(layoutInflater)
        metrologistDay()
        return binding.root
    }

    private fun metrologistDay() {
        ProgressD.showLoading(requireContext(), getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getViewVote(
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                ProgressD.hideProgressDialog()

                if (!requireActivity().isFinishing) {

                    if (binding.recylerviewDay != null) {
                        if (it != null && it.code == 200) {
                            if (it.data!!.today!! as List<TodayItem> != null && it.data.today?.size!! > 0) {
                                binding.dayfragmentmetrologist.visibility = VISIBLE
                                binding.txtNovotes.visibility = GONE
                                metrologistDayAdapter = MetrologistDayAdapter(
                                    requireContext(),
                                    it.data.today!! as ArrayList<TodayItem>,
                                    this, binding.txtNovotes
                                )
                                val layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                binding.recylerviewDay.layoutManager = layoutManager
                                binding.recylerviewDay.adapter = metrologistDayAdapter
                                binding.searcbar.addTextChangedListener(object : TextWatcher {
                                    override fun beforeTextChanged(
                                        charSequence: CharSequence,
                                        i: Int,
                                        i1: Int,
                                        i2: Int
                                    ) {
                                    }

                                    override fun onTextChanged(
                                        charSequence: CharSequence,
                                        i: Int,
                                        i1: Int,
                                        i2: Int
                                    ) {
                                    }

                                    override fun afterTextChanged(editable: Editable) {
                                        val text: String =
                                            binding.searcbar.text.toString().toLowerCase()

                                        metrologistDayAdapter.getFilter(binding.txtNovotes)
                                            .filter(text)
                                        if (listSize > 0)
                                            metrologistDayAdapter.getFilter(binding.txtNovotes)
                                                .filter(text)

                                    }
                                })
                                // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            } else {
                                binding.txtNovotes.visibility = VISIBLE
                                binding.dayfragmentmetrologist.visibility = GONE
                            }
                        } else {
                            Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
    }

    override fun onItemClick(id: String, name: String) {
        /*  intent.putExtra("id", id)
       intent.putExtra("airport", name)*/
    }
}