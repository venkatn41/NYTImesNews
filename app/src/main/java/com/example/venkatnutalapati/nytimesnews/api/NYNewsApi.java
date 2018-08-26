package com.example.venkatnutalapati.nytimesnews.api;

import com.example.venkatnutalapati.nytimesnews.models.NewsResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYNewsApi {

	public String API_KEY = "SFSDF24242353434";

	@GET("//articlesearch.json/")
	Observable<NewsResponse> getNewsList(@Query("q") String querry, @Query("page") Long page, @Query("api-key") String api);
}
