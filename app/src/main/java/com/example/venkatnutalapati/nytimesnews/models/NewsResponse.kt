package com.example.venkatnutalapati.nytimesnews.models

data class NewsResponse(
	var docs : ArrayList<NewsObj>,
	val meta : MetaData
)
