package com.example.venkatnutalapati.nytimesnews.models

data class NewsResponse(
	var newsObj : ArrayList<NewsObj>,
	val meta : MetaData)
