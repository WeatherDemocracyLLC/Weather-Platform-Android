package com.webmobrilweatherapp.adapters.userchat

class SingletonUser {
    companion object {
        private val ourInstance = SingletonUser()
        fun getInstance(): SingletonUser {
            return ourInstance
        }
    }

    lateinit var currentUser: UserModel
}