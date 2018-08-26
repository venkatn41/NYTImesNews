package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.venkatnutalapati.nytimesnews.R
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import io.reactivex.subjects.PublishSubject
import java.util.ArrayList


class NewsListAdapter(private val retryCallback: () -> Unit)
	: PagedListAdapter<NewsObj, RecyclerView.ViewHolder>(UserDiffCallback) {

	private var networkState: NetworkState? = null
	private val itemClicks = PublishSubject.create<Int>()
	internal var newsList: ArrayList<NewsObj> = ArrayList()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (viewType) {
			R.layout.news_item_row_layout -> NewsListViewHolder.create(parent, itemClicks)
			R.layout.item_network_state -> NetworkStateViewHolder.create(parent, retryCallback)
			else -> throw IllegalArgumentException("unknown view type")
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (getItemViewType(position)) {
			R.layout.news_item_row_layout -> (holder as NewsListViewHolder).bind(newsList[position])
			R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
		}
	}

	private fun hasExtraRow(): Boolean {
		return networkState != null && networkState != NetworkState.LOADED
	}

	override fun getItemViewType(position: Int): Int {
		return if (hasExtraRow() && position == itemCount - 1) {
			R.layout.item_network_state
		} else {
			R.layout.news_item_row_layout
		}
	}

	fun swapAdapter(newList: PagedList<NewsObj>?) {
		newsList.clear()
		newsList.addAll(newList!!)
		notifyDataSetChanged()
	}

	override fun getItemCount(): Int {
		return super.getItemCount() + if (hasExtraRow()) 1 else 0
	}

	/**
	 * Set the current network state to the adapter
	 * but this work only after the initial load
	 * and the adapter already have list to add new loading raw to it
	 * so the initial loading state the activity responsible for handle it
	 *
	 * @param newNetworkState the new network state
	 */
	fun setNetworkState(newNetworkState: NetworkState?) {
		if (currentList != null) {
			if (currentList!!.size != 0) {
				val previousState = this.networkState
				val hadExtraRow = hasExtraRow()
				this.networkState = newNetworkState
				val hasExtraRow = hasExtraRow()
				if (hadExtraRow != hasExtraRow) {
					if (hadExtraRow) {
						notifyItemRemoved(super.getItemCount())
					} else {
						notifyItemInserted(super.getItemCount())
					}
				} else if (hasExtraRow && previousState !== newNetworkState) {
					notifyItemChanged(itemCount - 1)
				}
			}
		}
	}

	companion object {
		val UserDiffCallback = object : DiffUtil.ItemCallback<NewsObj>() {
			override fun areItemsTheSame(oldItem: NewsObj, newItem: NewsObj): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(oldItem: NewsObj, newItem: NewsObj): Boolean {
				return oldItem == newItem
			}
		}
	}

}
