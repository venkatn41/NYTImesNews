package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi
import com.example.venkatnutalapati.nytimesnews.application.builder.NYNewsApiServiceModule
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import io.reactivex.disposables.CompositeDisposable

class NewsListViewModel : ViewModel() {

	lateinit var newsApi : NYNewsApi

	lateinit var newsList: LiveData<PagedList<NewsObj>>

	private val compositeDisposable = CompositeDisposable()

	private val pageSize = 15

	lateinit var sourceFactory: DataSourceFactory

	fun initDataSourceFactory() {
		Log.d("venkat","InitDataSourceFactory")
		sourceFactory = DataSourceFactory(newsApi, compositeDisposable)
		val config = PagedList.Config.Builder()
			.setPageSize(pageSize)
			.setInitialLoadSizeHint(pageSize * 2)
			.setEnablePlaceholders(false)
			.build()
		newsList = LivePagedListBuilder<Long, NewsObj>(sourceFactory, config).build()

	}

	fun setApi(newsApi : NYNewsApi) {
		this.newsApi = newsApi
	}

	fun retry() {
		sourceFactory.newsDataSourceLiveData.value!!.retry()
	}

	fun refresh() {
		sourceFactory.newsDataSourceLiveData.value!!.invalidate()
	}

	fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<NewsFeedDatasource, NetworkState>(
		sourceFactory.newsDataSourceLiveData, { it.networkState })

	fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<NewsFeedDatasource, NetworkState>(
		sourceFactory.newsDataSourceLiveData, { it.initialLoad })

	override fun onCleared() {
		super.onCleared()
		compositeDisposable.dispose()
	}
}
