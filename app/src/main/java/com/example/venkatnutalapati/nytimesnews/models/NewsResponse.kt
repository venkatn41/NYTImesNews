package com.example.venkatnutalapati.nytimesnews.models

data class NewsResponse(
	val docs : ArrayList<NewsObj>,
	val meta : MetaData
)
