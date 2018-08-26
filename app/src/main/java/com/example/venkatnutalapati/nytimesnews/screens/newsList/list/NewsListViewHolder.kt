package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.venkatnutalapati.nytimesnews.R
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import io.reactivex.subjects.PublishSubject

class NewsListViewHolder(internal var view: View, clickSubject: PublishSubject<Int>) : RecyclerView.ViewHolder(view) {

	@BindView(R.id.news_item_image)
	lateinit var imageView: ImageView
	@BindView(R.id.news_title)
	lateinit var title: TextView
	@BindView(R.id.date)
	lateinit var date: TextView


	init {
		ButterKnife.bind(this, view)
		view.setOnClickListener { _ -> clickSubject.onNext(adapterPosition) }
	}

	internal fun bind(newsItem: NewsObj) {
		title.run {
			text = newsItem.snippet
		}
		date.run {
			text = newsItem.date
		}
	}

	companion object {
		fun create(parent: ViewGroup, clickSubject: PublishSubject<Int>): NewsListViewHolder {
			val layoutInflater = LayoutInflater.from(parent.context)
			val view = layoutInflater.inflate(R.layout.news_item_row_layout, parent, false)
			return NewsListViewHolder(view, clickSubject)
		}
	}
}
