package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.util.Log
import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi
import com.example.venkatnutalapati.nytimesnews.models.NewsResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.FutureTask

class MainModel(internal var api: NYNewsApi) {


	//Observable for providing the city name based on coordinates provided.
	internal fun provideNews(query: String, page : Long, apikey : String): Observable<NewsResponse> {
		Log.d("venkat","Provide new api :"+(api.getNewsList(query,page, apikey).subscribe()))



				return api.getNewsList(query,page, apikey)
	}



}
