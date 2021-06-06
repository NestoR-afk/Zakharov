package com.example.tinkofftest.network

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NetworkHelper() {
    companion object {
        fun checkConnection(context: Context): Boolean {

            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return ((cm.activeNetworkInfo != null) && (cm.activeNetworkInfo!!.isConnected()))
        }
    }
}