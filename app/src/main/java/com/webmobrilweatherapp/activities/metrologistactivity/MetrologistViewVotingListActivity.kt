package com.webmobrilweatherapp.activities.metrologistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.viewmodel.webconfig.ApiConnection.network.AccountViewModelMetrologist
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.activities.AppConstant
import com.webmobrilweatherapp.activities.CommonMethod
import com.webmobrilweatherapp.activities.ProgressD
import com.webmobrilweatherapp.activities.metrologistadapter.MetrologistUsertvotinglistAdapter
 import com.webmobrilweatherapp.databinding.ActivityMetrologistViewVotingListBinding
import com.webmobrilweatherapp.model.uservotinglist.DataItem

class MetrologistViewVotingListActivity : AppCompatActivity(),MetrologistUsertvotinglistAdapter.AdapterInterface{
    lateinit var binding:ActivityMetrologistViewVotingListBinding
    var accountViewModelMetrologist: AccountViewModelMetrologist? = null
    private var useridMetrologist = "0"
    var listSize = 0
    private lateinit var metrologistUsertvotinglistAdapter: MetrologistUsertvotinglistAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModelMetrologist = ViewModelProvider(this).get(AccountViewModelMetrologist::class.java)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_metrologist_view_voting_list)
        val window: Window = this.getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.red))
        getUservotinglist()
        binding.imgarrowbackvoting.setOnClickListener {
            onBackPressed()
        }

     }

    private fun getUservotinglist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        useridMetrologist = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_idMetrologist)
        accountViewModelMetrologist?.getUservotinglistMetrologist(
            useridMetrologist, "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token_Metrologist
            )
        )
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItem> != null && it.data.size > 0) {
                        binding.cardView2.visibility= View.VISIBLE
                        binding.Txtnovoting.visibility = View.GONE
                        metrologistUsertvotinglistAdapter = MetrologistUsertvotinglistAdapter(this, it.data as ArrayList<DataItem>, it.data[0].user, this)
                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.votingRecyclerview.layoutManager = layoutManager
                        binding.votingRecyclerview.adapter = metrologistUsertvotinglistAdapter
                    } else {
                        binding.Txtnovoting.visibility = View.VISIBLE
                        binding.cardView2.visibility = View.GONE
                    }

                    // Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
                }
            }
        binding.searcBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                metrologistUsertvotinglistAdapter.filter.filter(editable)
                if (listSize > 0)
                    metrologistUsertvotinglistAdapter.filter.filter(editable)
            }
        })
    }

    override fun onItemClick(id: String, name: String) {
        intent.putExtra("id", id)
        intent.putExtra("airport", name)
    }
}