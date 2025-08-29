package com.webmobrilweatherapp.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.adapters.UsertvotinglistAdapter
import com.webmobrilweatherapp.databinding.ActivityVotingListBinding
import com.webmobrilweatherapp.function.MySingleton
import com.webmobrilweatherapp.model.uservotinglist.DataItem

class VotingListActivity : AppCompatActivity(), View.OnClickListener,
    UsertvotinglistAdapter.AdapterInterface {

    lateinit var binding: ActivityVotingListBinding
    var accountViewModel: AccountViewModel? = null
    private lateinit var usertvotinglistAdapter: UsertvotinglistAdapter
    private var usertype = "0"
    private var userid = 0
    var listSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voting_list)

        usertype = intent.getStringExtra(AppConstant.USER_TYPE).toString()
        MySingleton.handleTheme(this, usertype)
        getUservotinglist()

        listener()
    }

    private fun listener() {
        binding.imgarrowbackvoting.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.imgarrowbackvoting -> {
                onBackPressed()
            }
        }
    }

    private fun getUservotinglist() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        userid = CommonMethod.getInstance(this).getPreference(AppConstant.KEY_User_id, 0)
        accountViewModel?.getUservotinglist(
            userid.toString(), "Bearer " + CommonMethod.getInstance(this).getPreference(
                AppConstant.KEY_token)
        )?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null && it.success == true) {
                    if (it.data as List<DataItem> != null && it.data.size > 0) {
                        binding.cardView2.visibility=VISIBLE
                        binding.Txtnovoting.visibility = GONE
                        usertvotinglistAdapter = UsertvotinglistAdapter(this, it.data as ArrayList<DataItem>,it.data[0].user, this)
                        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        binding.votingRecyclerview.layoutManager = layoutManager
                        binding.votingRecyclerview.adapter = usertvotinglistAdapter
                    } else {
                        binding.Txtnovoting.visibility = VISIBLE
                        binding.cardView2.visibility = GONE
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
                usertvotinglistAdapter.filter.filter(editable)
                if (listSize > 0)
                    usertvotinglistAdapter.filter.filter(editable)
            }
        })
    }

    override fun onItemClick(id: String, name: String) {
        intent.putExtra("id", id)
        intent.putExtra("airport", name)

    }

}