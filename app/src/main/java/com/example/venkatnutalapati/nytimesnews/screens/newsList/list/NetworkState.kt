package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

enum class Status {
	RUNNING,
	SUCCESS,
	FAILED
}

data class NetworkState private constructor(
	val status: Status,
	val message: String? = null) {
	companion object {
		val LOADED = NetworkState(Status.SUCCESS)
		val LOADING = NetworkState(Status.RUNNING)
		fun error(msg: String?) = NetworkState(Status.FAILED, msg)
	}
}