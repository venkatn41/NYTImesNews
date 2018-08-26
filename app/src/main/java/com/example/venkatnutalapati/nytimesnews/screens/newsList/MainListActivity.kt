package com.example.venkatnutalapati.nytimesnews.screens.newsList

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import butterknife.ButterKnife

import kotlinx.android.synthetic.main.activity_main.*
import com.example.venkatnutalapati.nytimesnews.R
import com.example.venkatnutalapati.nytimesnews.api.NYNewsApi
import com.example.venkatnutalapati.nytimesnews.application.AppController
import com.example.venkatnutalapati.nytimesnews.application.builder.NYNewsApiServiceModule
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import com.example.venkatnutalapati.nytimesnews.screens.newsList.di.DaggerMainComponenet
import com.example.venkatnutalapati.nytimesnews.screens.newsList.list.MainModel
import com.example.venkatnutalapati.nytimesnews.screens.newsList.list.NetworkState
import com.example.venkatnutalapati.nytimesnews.screens.newsList.list.NewsListAdapter
import com.example.venkatnutalapati.nytimesnews.screens.newsList.list.NewsListViewModel
import com.example.venkatnutalapati.nytimesnews.screens.newsList.list.Status

import kotlinx.android.synthetic.main.item_network_state.*
import javax.inject.Inject


class MainListActivity : AppCompatActivity() {

	lateinit var newsViewModel: NewsListViewModel

	private lateinit var newsAdapter: NewsListAdapter

	@Inject
	lateinit var newsApi : NYNewsApi

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)
		ButterKnife.bind(this)
		DaggerMainComponenet.builder().appComponent(AppController.getNetComponent()).build().inject(this)
		newsViewModel = ViewModelProviders.of(this).get(NewsListViewModel::class.java)
		newsViewModel.setApi(newsApi)
		Log.d("venkat","Calling init data source factory :"+newsApi)
		newsViewModel.initDataSourceFactory()
		initAdapter()
		initSwipeToRefresh()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.menu_main, menu)

		val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
		(menu.findItem(R.id.search_box)?.actionView as SearchView).apply {
			setSearchableInfo(searchManager.getSearchableInfo(componentName))
		}

		(menu.findItem(R.id.search_box)?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				Log.d("venkat","Press querysubmit :"+query)
				return false
			}
			override fun onQueryTextChange(newText: String): Boolean {
				Log.d("venkat","Press querytextchange")
				return true
			}
		})
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return when (item.itemId) {
			R.id.search_box -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	private fun initAdapter() {
		val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		newsAdapter = NewsListAdapter {
			newsViewModel.retry()
		}
		newsRecyclerView.layoutManager = linearLayoutManager
		newsRecyclerView.adapter = newsAdapter
		newsViewModel.newsList.observe(this, Observer<PagedList<NewsObj>> { newsAdapter.swapAdapter(it) })
		newsViewModel.getNetworkState().observe(this, Observer<NetworkState> { newsAdapter.setNetworkState(it) })
	}

	private fun initSwipeToRefresh() {
		newsViewModel.getRefreshState().observe(this, Observer { networkState ->
			if (newsAdapter.currentList != null) {
				if (newsAdapter.currentList!!.size > 0) {
					usersSwipeRefreshLayout.isRefreshing = networkState?.status == NetworkState.LOADING.status
				} else {
					setInitialLoadingState(networkState)
				}
			} else {
				setInitialLoadingState(networkState)
			}
		})
		usersSwipeRefreshLayout.setOnRefreshListener({ newsViewModel.refresh() })
	}

	private fun setInitialLoadingState(networkState: NetworkState?) {
		//error message
		errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
		if (networkState?.message != null) {
			errorMessageTextView.text = networkState.message
		}

		//loading and retry
		retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
		loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

		usersSwipeRefreshLayout.isEnabled = networkState?.status == Status.SUCCESS
		retryLoadingButton.setOnClickListener { newsViewModel.retry() }
	}
}
