package com.webmobrilweatherapp.activities

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network.AccountViewModel
import com.webmobrilweatherapp.R
import com.webmobrilweatherapp.databinding.ActivityTermsPrivacyBinding
import com.webmobrilweatherapp.network.ApiStatusConstant

class TermsPrivacyActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityTermsPrivacyBinding
    var accountViewModel: AccountViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsPrivacyBinding.inflate(layoutInflater)
        accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        val view = binding.root
        setContentView(view)

        accountViewModel!!.getTermPrivacy("2")
        initialization()
        listener()
         getTermPolicy()

    }

    private fun getTermPolicy() {
        ProgressD.showLoading(this, getResources().getString(R.string.logging_in))
        accountViewModel?.getTermPrivacy("2")
            ?.observe(this) {
                ProgressD.hideProgressDialog()
                if (it != null) {
                    val respo = it
                    val statusCode = respo.code
                    if (statusCode == ApiStatusConstant.API_200) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.textloremprivacy.text = Html.fromHtml(
                                respo.result.termsAndConditions,
                                Html.FROM_HTML_MODE_COMPACT
                            )
                            binding.PrivacyPolicy.text = Html.fromHtml(
                                respo.result.privacyPolicy,
                                Html.FROM_HTML_MODE_COMPACT
                            )
                        } else {
                            binding.textloremprivacy.text =
                                Html.fromHtml(respo.result.termsAndConditions)
                            binding.PrivacyPolicy.text = Html.fromHtml(
                                respo.result.privacyPolicy
                            )
                        }

                    }
                }
            }


    }

    private fun initialization() {
    }

    private fun listener() {
        binding.cross.setOnClickListener(this)


    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.cross -> {

                onBackPressed()
            }
        }

    }
}