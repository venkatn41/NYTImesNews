package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.util.Log
import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import io.reactivex.disposables.CompositeDisposable

class DataSourceFactory (private val newsApi : NYNewsApi,
                         private val disposable : CompositeDisposable,
                         private val query : String) : DataSource.Factory<Long, NewsObj>() {

	val newsDataSourceLiveData = MutableLiveData<NewsFeedDatasource>()

	override fun create(): DataSource<Long, NewsObj> {
		val newsFeedDatasource = NewsFeedDatasource(newsApi, disposable, query)
		newsDataSourceLiveData.postValue(newsFeedDatasource)
		return newsFeedDatasource
	}

}
