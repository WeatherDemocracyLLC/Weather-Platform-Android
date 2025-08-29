package com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webmobrilweatherapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRepositoriesNews {
    private val TAG = AccountRepositoriesNews::class.java.simpleName

    fun getNewUser(sdk: String, rapidKey: String, newHost: String): LiveData<NewsResponse?> {
        val mutableLiveData = MutableLiveData<NewsResponse?>()
        val apiService = RetrofitConnectionNew.createService()
        val call = apiService.getNewUser(sdk, rapidKey, newHost)
        call.enqueue(object : Callback<NewsResponse?> {
            override fun onResponse(call: Call<NewsResponse?>, response: Response<NewsResponse?>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Response successful: ${response.body()}")
                    mutableLiveData.value = response.body()
                } else {
                    Log.e(TAG, "Response error: ${response.errorBody()?.string()}")
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<NewsResponse?>, t: Throwable) {
                Log.e(TAG, "API call failed: ${t.message}", t)
                mutableLiveData.value = null
            }
        })

        return mutableLiveData
    }
}
