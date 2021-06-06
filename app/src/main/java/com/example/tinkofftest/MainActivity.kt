package com.example.tinkofftest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.tinkofftest.fragments.ErrorFragment
import com.example.tinkofftest.fragments.ErrorFragmentListener
import com.example.tinkofftest.fragments.MainFragment
import com.example.tinkofftest.fragments.NetworkErrorListener
import com.example.tinkofftest.network.NetworkHelper

class MainActivity : AppCompatActivity(),
        ErrorFragmentListener,
        NetworkErrorListener {
    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        onNetworkError()


    }

    override fun onRepeatClicked() {
        if (NetworkHelper.checkConnection(this)) {
            val mainFragment = MainFragment()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.activity_main_frame, mainFragment)
                    .commit()
        }
    }

    override fun onNetworkError() {

        if (!NetworkHelper.checkConnection(this)) {
            val errorFragment = ErrorFragment()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.activity_main_frame, errorFragment)
                    .addToBackStack("error")
                    .commit()
        }
    }
}