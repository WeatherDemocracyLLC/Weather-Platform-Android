package com.webmobrilweatherapp.activities.metrologistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.adapters.WeatherMayormetrologistAdapter
import com.webmobrilweatherapp.databinding.ActivityMetrologistWeatherMayorBinding
import com.webmobrilweatherapp.model.weathermayorlist.DataItem

class MetrologistWeatherMayorActivity : AppCompatActivity(),WeatherMayormetrologistAdapter.AdapterInterface {
    lateinit var binding: ActivityMetrologistWeatherMayorBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    var listSize = 0
    private lateinit var  weatherMayormetrologistAdapter:WeatherMayormetrologistAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist =
            ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_metrologist_weather_mayor)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        binding.WeatherMayor.ImgBack.setOnClickListener {
            onBackPressed()
        }
        binding.WeatherMayor.txtLocation.setText("Weather Mayor")
        getweathermayorlistsmetrologist()
    }
    private fun getweathermayorlistsmetrologist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModelMetrologist?.getweathermayorlistsmetrologist(
            "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItem>!=null&& it.data.size>0) {
                        binding.txtNodataItem.visibility=GONE
                        binding.searchBar.visibility=VISIBLE
                        weatherMayormetrologistAdapter = WeatherMayormetrologistAdapter(this, it.data as ArrayList<DataItem>,it.data.get(0).mayor,this)
                        val layoutManager =LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.weatherrecyclerviewmetrologist.layoutManager = layoutManager
                        binding.weatherrecyclerviewmetrologist.adapter = weatherMayormetrologistAdapter
                    } else {
                        binding.txtNodataItem.visibility=VISIBLE
                        binding.searchBar.visibility=GONE
                    }

                    // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    binding.txtNodataItem.visibility=VISIBLE
                    //Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                    weatherMayormetrologistAdapter.filter.filter(editable)
                    if (listSize > 0)
                        weatherMayormetrologistAdapter.filter.filter(editable)

            }
        })
    }

    override fun onItemClick(id: String, name: String) {

    }


}