package com.example.tinkofftest

import android.app.Application
import com.example.tinkofftest.network.PlaceholderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp : Application() {

    lateinit var api: PlaceholderApi

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        val retrofit = Retrofit.Builder()
            .baseUrl(PlaceholderApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(PlaceholderApi::class.java)
    }

    companion object {
        lateinit var INSTANCE: MyApp
        private set
    }
}