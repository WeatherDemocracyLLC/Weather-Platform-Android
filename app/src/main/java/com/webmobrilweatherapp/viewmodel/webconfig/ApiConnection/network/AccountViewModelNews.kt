package com.webmobrilweatherapp.viewmodel.webconfig.ApiConnection.network

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.webmobrilweatherapp.model.NewsResponse

class AccountViewModelNews(application: Application) : AndroidViewModel(application) {

    private val accountRepositoriesNews: AccountRepositoriesNews = AccountRepositoriesNews()

    fun getNewUser(sdk: String, rapidKey: String, newHost: String): LiveData<NewsResponse?> {
        return accountRepositoriesNews.getNewUser(sdk, rapidKey, newHost)
    }
}
