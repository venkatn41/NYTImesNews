package com.example.venkatnutalapati.nytimesnews.models

import com.google.gson.annotations.SerializedName

data class NewsObj(
	@SerializedName("web_url") val url: String? = null,
	@SerializedName("lead_paragraph") val authToken: String? = null,
	@SerializedName("pub_date") val date : String? = null,
	@SerializedName("_id") val id : String? = null,
	val multimedia: MutableList<Multimedia>? = null,
	val snippet: String? = null,
	val headline : Headline? = null)
