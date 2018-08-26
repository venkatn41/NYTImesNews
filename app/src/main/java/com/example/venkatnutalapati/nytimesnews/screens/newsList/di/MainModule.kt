package com.example.venkatnutalapati.nytimesnews.screens.newsList.di

import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi
import com.example.venkatnutalapati.nytimesnews.screens.newsList.list.MainModel
import dagger.Module
import dagger.Provides

@Module
class MainModule {

	@Provides
	internal fun provideModel(api: NYNewsApi): MainModel {
		return MainModel(api)
	}
}
