package com.webmobrilweatherapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.WeatherVoteAdapter
import com.webmobrilweatherapp.databinding.ActivityWeatherVoteBinding
import com.webmobrilweatherapp.model.myweathervotepersentage.VotesItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.DecimalFormat

class WeatherVoteActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityWeatherVoteBinding
    var accountViewModel: AccountViewModel? = null
    lateinit var weatherVoteAdapter: WeatherVoteAdapter
    private lateinit var dialogs: BottomSheetDialog
    lateinit var tempId: String
    lateinit var textweekly: TextView
    lateinit var txtMonthly: TextView
    lateinit var textlastdays: TextView
    private var userid = 0
    private var filtertype:String ="3"
    private val progressStatus = 0
    private val handler: Handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = ActivityWeatherVoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        listener()
       // getUservotinglist()
        getUserfilter(filtertype)
    }
    private fun listener() {
        binding.imgvotemayour.setOnClickListener(this)
        binding.textWeekly.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.imgvotemayour -> {
                onBackPressed()
            }

            R.id.textWeekly -> {
                Dialogb()

            }
        }
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun getUserfilter(filtertype:String) {
        ProgressD.showLoading(this,getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        if (this != null) {
            accountViewModel?.getmyweathervotelist(
                filtertype,userid.toString(),"Bearer " + CommonMethod.getInstance(this).getPreference(
                    AppConstant.KEY_token))
                ?.observe(this) {
                    ProgressD.hideProgressDialog()
                    if (it != null && it.success == true) {
                       // getUservotinglist()
                         if (it.votes as List<VotesItem> != null && it.votes.size > 0) {
                            binding.recycleviewweathervote.visibility=VISIBLE
                            binding.nodataItem.visibility=GONE
                             var persntage=it.percentage
                             val form = DecimalFormat("0.00")
                             binding.txtpersentage.setText(form.format(persntage)+"%")
                             binding.ProgressBar.setProgress(form.format(persntage).toDouble().toInt())
                            // binding.txtpersentage.setText(""+persntage+"%")
                            weatherVoteAdapter = WeatherVoteAdapter(this, it.votes as ArrayList<VotesItem>)
                            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                            binding.recycleviewweathervote.layoutManager = layoutManager
                            binding.recycleviewweathervote.adapter = weatherVoteAdapter
                            Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                        }else{
                            binding.recycleviewweathervote.visibility=GONE
                            binding.nodataItem.visibility=VISIBLE
                        }
                    } else {
                       // Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    private fun Dialogb() {
        dialogs = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomfilter, null)
        textweekly = view.findViewById(R.id.textweekly)!!
        textlastdays = view.findViewById(R.id.textlastdays)!!
        txtMonthly = view.findViewById(R.id.txtMonthly)!!
        dialogs.setCancelable(true)
        dialogs.setContentView(view)
        dialogs.show()
        textweekly.setOnClickListener {
            filtertype= "1"
            binding.textWeekly.setText("Weekly")
            getUserfilter(filtertype.toString())
            dialogs.dismiss()
        }
        textlastdays.setOnClickListener {
            filtertype= "2"
            binding.textWeekly.setText("Last 15 Days")
            getUserfilter(filtertype.toString())
            dialogs.dismiss()
        }
        txtMonthly.setOnClickListener {
            filtertype= "3"
            binding.textWeekly.setText("Monthly")
            getUserfilter(filtertype.toString())
            dialogs.dismiss()
        }
    }
}