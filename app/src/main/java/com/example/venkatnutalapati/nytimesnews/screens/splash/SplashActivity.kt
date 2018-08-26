package com.example.venkatnutalapati.nytimesnews.screens.splash

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.venkatnutalapati.nytimesnews.R

import android.util.Log
import com.example.venkatnutalapati.nytimesnews.screens.newsList.MainListActivity
import android.content.Intent
import android.os.Handler


class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
		setContentView(R.layout.splash_view)
		Log.d("venkat","in OnCreate calling load splash>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
		finishSplashLoading()
	}


	private fun finishSplashLoading() {
		Handler().postDelayed( {
			val mainIntent = Intent(this, MainListActivity::class.java)
			this.startActivity(mainIntent)
			this.finish()
		}, 15000)
	}
}

