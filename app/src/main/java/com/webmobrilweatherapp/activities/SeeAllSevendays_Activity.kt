package com.webmobrilweatherapp.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivitySeeAllSevendaysBinding


class SeeAllSevendays_Activity : AppCompatActivity() {
    lateinit var binding: ActivitySeeAllSevendaysBinding
    var accountViewModel: AccountViewModel? = null
    var lat: String =" 0.0"
    var long: String ="0.0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_sevendays)
        val list: ArrayList<String>? = intent.getSerializableExtra("dailyforcast") as ArrayList<String>?
        Toast.makeText(this,list.toString(), Toast.LENGTH_LONG).show()

        binding= DataBindingUtil.setContentView(this,R.layout.activity_see_all_sevendays)
        /*val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.seeAllRecyclerView.layoutManager = layoutManager
        binding.seeAllRecyclerView.adapter =
            Sevendays_Adapter(this, it.dailyForecasts as List<DailyForecastsItem>)
*/
    }

    private fun getTendaysWeather(strkey: String) {
        // Get user sign up response
        // key= CommonMethod.getInstance(requireContext()).getPreference(AppConstant.KEY)
        //Toast.makeText(this,strkey, Toast.LENGTH_LONG).show()

        if (this != null) {
            //ProgressD.showLoading(context,getResources().getString(R.string.logging_in))
            accountViewModel?.getTendays(strkey, "wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU")
                ?.observe(this) {
                    // ProgressD.hideProgressDialog()
                    Toast.makeText(this,"donedfw4c4evrf", Toast.LENGTH_LONG).show()
                    if (this != null) {
                        if (!this.isFinishing) {
                            if (this != null) {

                                Toast.makeText(this,"donedfw4c4evrf", Toast.LENGTH_LONG).show()

                            }
                        }
                    }
                }
        }
    }
}