package com.example.venkatnutalapati.nytimesnews.models

import com.google.gson.annotations.SerializedName

data class Headline(
	val main : String? = null,
	@SerializedName("print_Headline")val headline : String? = null)

