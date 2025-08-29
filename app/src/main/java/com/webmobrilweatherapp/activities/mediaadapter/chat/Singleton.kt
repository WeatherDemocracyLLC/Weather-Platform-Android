package com.webmobrilweatherapp.activities.mediaadapter.chat

class Singleton {
    companion object {
        private val ourInstance = Singleton()
        fun getInstance(): Singleton {
            return ourInstance
        }
    }

    lateinit var currentUser: UserModel
}