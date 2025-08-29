package com.webmobrilweatherapp.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.WeathermayorAdapter
import com.webmobrilweatherapp.databinding.ActivityWeatherMayorBinding
import com.webmobrilweatherapp.function.MySingleton
import com.webmobrilweatherapp.model.weathermayorlist.DataItem

class WeatherMayorActivity : AppCompatActivity(), View.OnClickListener,WeathermayorAdapter.AdapterInterface{

    lateinit var binding: ActivityWeatherMayorBinding
    private var usertype = "0"
     var listSize = 0
    private lateinit var weathermayorAdapter: WeathermayorAdapter
    var accountViewModel: AccountViewModel? = null
    var imageLists: ArrayList<DataItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        binding = ActivityWeatherMayorBinding.inflate(layoutInflater)
        getUservotinglist()
        val view = binding.root
        setContentView(view)
        listener()

    }

    private fun listener() {
        binding.imgarrowbackmayour.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.imgarrowbackmayour -> {
                onBackPressed()
            }
        }
    }


    private fun getUservotinglist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getweathermayorlists(
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token)
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItem>!=null&& it.data.size>0) {
                        binding.searchBar.visibility=VISIBLE
                        binding.txtNoweather.visibility=GONE
                        for (i in 0 until it.data.size) {
                            Log.d("TAG", "getUservotinglist"+i)
                            imageLists.clear()
                            imageLists.addAll(it.data)
                            weathermayorAdapter = WeathermayorAdapter(this, imageLists,imageLists.get(i).mayor,this)
                            val layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                            binding.weatherRecyclerview.layoutManager = layoutManager
                            binding.weatherRecyclerview.adapter = weathermayorAdapter

                            binding.searchBar.addTextChangedListener(object : TextWatcher {
                                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                                override fun afterTextChanged(editable: Editable) {
                                    weathermayorAdapter.filter.filter(editable)
                                    if (listSize > 0)
                                        weathermayorAdapter.filter.filter(editable)
                                }
                            })
                        }
                    } else {
                        binding.searchBar.visibility=GONE
                        binding.txtNoweather.visibility= VISIBLE
                    }

                    // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun onItemClick(id: String, name: String) {

    }

}