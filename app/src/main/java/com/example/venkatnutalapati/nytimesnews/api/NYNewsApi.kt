package com.example.venkatnutalapati.nytimesnews.api

import com.example.venkatnutalapati.nytimesnews.models.NYResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NYNewsApi {

	@GET("articlesearch.json")
	fun getNewsList(@Query("q") querry: String, @Query("page") page: Long?,
						 @Query("api-key") api: String): Single<NYResponse>

	companion object {

		const val API_KEY = "7f687e40cc194bcab540229e741a580f"

		val client = OkHttpClient().newBuilder()
			.addInterceptor(HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			})
			.build()

		fun getService(): NYNewsApi {
			val retrofit = Retrofit.Builder().client(client)
				.baseUrl("https://api.nytimes.com/svc/search/v2/")
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build()
			return retrofit.create(NYNewsApi::class.java)
		}

	}
}


