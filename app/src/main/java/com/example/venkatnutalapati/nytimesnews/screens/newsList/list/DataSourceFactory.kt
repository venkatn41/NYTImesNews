package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.util.Log
import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import io.reactivex.disposables.CompositeDisposable

class DataSourceFactory (private val newsApi : NYNewsApi, private val disposable : CompositeDisposable)
										: DataSource.Factory<Long, NewsObj>() {

	val newsDataSourceLiveData = MutableLiveData<NewsFeedDatasource>()

	override fun create(): DataSource<Long, NewsObj> {
		Log.d("venkat","Creating data source")
		val newsFeedDatasource = NewsFeedDatasource(newsApi, disposable)
		newsDataSourceLiveData.postValue(newsFeedDatasource)
		return newsFeedDatasource
	}

}
