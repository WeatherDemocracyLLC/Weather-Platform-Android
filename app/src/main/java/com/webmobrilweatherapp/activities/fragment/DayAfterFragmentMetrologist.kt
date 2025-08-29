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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistDayAfterTomorrowAdapter
import com.webmobrilweatherapp.databinding.FragmentDatAfterMetrologistBinding
import com.webmobrilweatherapp.model.metrologist.viewuservote.DayAfterTomorrowItem

class DayAfterFragmentMetrologist : Fragment(),
    MetrologistDayAfterTomorrowAdapter.AdapterInterface {
    lateinit var binding: FragmentDatAfterMetrologistBinding
    var listSize = 0
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private lateinit var metrologistDayAfterTomorrowAdapter: MetrologistDayAfterTomorrowAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = FragmentDatAfterMetrologistBinding.inflate(layoutInflater)
        metrologistDayAfterTomorrow()
        return binding.root
    }

    private fun metrologistDayAfterTomorrow() {
        // ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getViewVote(
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                //ProgressD.hideProgressDialog()
                // Toast.makeText(context, it!!.data!!.dayAfterTomorrow!!.size.toString(), Toast.LENGTH_LONG).show()


                if (it!!.data!!.dayAfterTomorrow!!.size>0) {
                        binding.txtNovotes.visibility = GONE
                        binding.dayAfterfragmentmetrologist.visibility = VISIBLE
                        metrologistDayAfterTomorrowAdapter = MetrologistDayAfterTomorrowAdapter(
                            requireContext(),
                            it.data!!.dayAfterTomorrow!! as ArrayList<DayAfterTomorrowItem>,
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
                   // Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
            binding.txtNovotes.visibility = VISIBLE
            binding.dayAfterfragmentmetrologist.visibility = GONE
                }
            }

    }

    override fun onItemClick(id: String, name: String) {
        /*  intent.putExtra("id", id)
       intent.putExtra("airport", name)*/

    }
}