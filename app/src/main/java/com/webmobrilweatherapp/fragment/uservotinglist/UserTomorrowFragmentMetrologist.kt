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
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistTomorrowAdapter
import com.webmobrilweatherapp.databinding.FragmentUserTomorrowMetrologistBinding
import com.webmobrilweatherapp.model.metrologist.viewuservote.TomorrowItem

class UserTomorrowFragmentMetrologist : Fragment() , MetrologistTomorrowAdapter.AdapterInterface {
    lateinit var binding:FragmentUserTomorrowMetrologistBinding
    var listSize = 0
    var accountViewModel: AccountViewModel? = null

    private lateinit var metrologistTomorrowAdapter: MetrologistTomorrowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding=FragmentUserTomorrowMetrologistBinding.inflate(layoutInflater)
        metrologistTomorrow()
        return binding.root
    }

    private fun metrologistTomorrow() {
        //ProgressD.showLoading(requireContext(),getResources().getString(R.string.logging_in))
        accountViewModel?.getViewVoteUser(
            "Bearer " + CommonMethod.getInstance(context).getPreference(
                AppConstant.KEY_token)
        )
            ?.observe(requireActivity()) {
                // ProgressD.hideProgressDialog()
                if (it != null && it.code == 200) {
                    if (it.data!!.tomorrow!! as List<TomorrowItem> != null && it.data.tomorrow!!.size > 0) {
                        binding.txtNovotes.visibility = View.GONE
                        binding.Tommrowfragmentmetrologist.visibility = View.VISIBLE
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
                        binding.Tommrowfragmentmetrologist.visibility = View.GONE
                    }
                } else {
                    binding.txtNovotes.visibility = VISIBLE
                    Toast.makeText(context, it?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onItemClick(id: String, name: String) {

    }
}