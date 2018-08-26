package com.example.venkatnutalapati.nytimesnews.screens.newsList.di

import com.example.venkatnutalapati.nytimesnews.application.builder.AppComponent
import com.example.venkatnutalapati.nytimesnews.screens.newsList.MainListActivity

import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainModule::class))
internal interface MainComponenet {
	fun inject(activity: MainListActivity)
}
