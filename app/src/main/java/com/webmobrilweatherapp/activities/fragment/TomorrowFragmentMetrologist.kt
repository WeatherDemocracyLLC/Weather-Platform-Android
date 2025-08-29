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
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistTomorrowAdapter
import com.webmobrilweatherapp.databinding.FragmentTomorrowMetrologistBinding
import com.webmobrilweatherapp.model.metrologist.viewuservote.TomorrowItem

class TomorrowFragmentMetrologist : Fragment(), MetrologistTomorrowAdapter.AdapterInterface {
    lateinit var binding: FragmentTomorrowMetrologistBinding
    var listSize = 0
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private lateinit var metrologistTomorrowAdapter: MetrologistTomorrowAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = FragmentTomorrowMetrologistBinding.inflate(layoutInflater)
        metrologistTomorrow()
        return binding.root
    }

    private fun metrologistTomorrow() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getViewVote(
            "Bearer " + CommonMethod.getInstance(requireContext()).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    if (it.data!!.tomorrow!! as List<TomorrowItem> != null && it.data.tomorrow!!.size > 0) {
                        binding.txtNovotes.visibility = GONE
                        binding.Tommrowfragmentmetrologist.visibility = VISIBLE
                        metrologistTomorrowAdapter = MetrologistTomorrowAdapter(
                            requireContext(),
                            it.data.tomorrow!! as ArrayList<TomorrowItem>,
                            this,binding.txtNovotes
                        )
                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.recylerviewTomarrow.layoutManager = layoutManager
                        binding.recylerviewTomarrow.adapter = metrologistTomorrowAdapter
                        binding.searcbar.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                            override fun afterTextChanged(editable: Editable) {
                                val text: String =
                                    binding.searcbar.text.toString().toLowerCase()
                                    metrologistTomorrowAdapter.getFilter(binding.txtNovotes).filter(text)
                                    if (listSize > 0)
                                        metrologistTomorrowAdapter.getFilter(binding.txtNovotes).filter(text)

                            }
                        })
                        //Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    } else {
                        binding.txtNovotes.visibility = VISIBLE
                        binding.Tommrowfragmentmetrologist.visibility = GONE
                    }
                } else {
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }


    }

    override fun onItemClick(id: String, name: String) {
        /*  intent.putExtra("id", id)
       intent.putExtra("airport", name)*/

    }
}