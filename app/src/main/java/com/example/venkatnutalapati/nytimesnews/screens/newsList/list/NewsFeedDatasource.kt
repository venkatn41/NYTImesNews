package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi
import com.example.venkatnutalapati.nytimesnews.application.builder.NYNewsApiServiceModule
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers


class NewsFeedDatasource (val newsApi : NYNewsApi, val compositeDisposable : CompositeDisposable)
				: PageKeyedDataSource<Long, NewsObj>() {

	val networkState = MutableLiveData<NetworkState>()

	val initialLoad = MutableLiveData<NetworkState>()

	private var retryCompletable: Completable? = null

	override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, NewsObj>) {
		Log.d("venkat","Load initial")
		networkState.postValue(NetworkState.LOADING)
		initialLoad.postValue(NetworkState.LOADING)


		compositeDisposable.add(newsApi.getNewsList("trump", 1, NYNewsApi.API_KEY).subscribe({
			// clear retry since last request succeeded
			Log.d("venkat",">>>>requested for news +"+it.newsObj[1].snippet)
			setRetry(null)
			networkState.postValue(NetworkState.LOADED)
			initialLoad.postValue(NetworkState.LOADED)
			callback.onResult(it.newsObj, null, 2L)
		}, { throwable ->
			// keep a Completable for future retry
			setRetry(Action { loadInitial(params, callback) })
			val error = NetworkState.error(throwable.message)
			// publish the error
			networkState.postValue(error)
			initialLoad.postValue(error)
		}))
	}

	override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, NewsObj>) {
		// set network value to loading.
		networkState.postValue(NetworkState.LOADING)

		compositeDisposable.add(newsApi.getNewsList("trump", params.key, "API").subscribe({
			// clear retry since last request succeeded
			setRetry(null)
			networkState.postValue(NetworkState.LOADED)
			val nextKey = (params.key + 1).toLong()
			callback.onResult(it.newsObj, nextKey)
		}, { throwable ->
			// keep a Completable for future retry
			setRetry(Action { loadAfter(params, callback) })
			// publish the error
			networkState.postValue(NetworkState.error(throwable.message))
		}))

	}

	override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, NewsObj>) {

	}

	fun retry() {
		if (retryCompletable != null) {
			compositeDisposable.add(retryCompletable!!
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({ }, {  }))
		}
	}



	private fun setRetry(action: Action?) {
		if (action == null) {
			this.retryCompletable = null
		} else {
			this.retryCompletable = Completable.fromAction(action)
		}
	}

}
