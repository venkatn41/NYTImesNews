package com.example.venkatnutalapati.nytimesnews.screens.newsList.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.venkatnutalapati.nytimesnews.R
import com.example.venkatnutalapati.nytimesnews.models.NewsObj
import io.reactivex.Observable
import kotlinx.android.synthetic.main.news_item_row_layout.view.*
import io.reactivex.subjects.PublishSubject


class NewsListViewHolder(internal var view: View) : RecyclerView.ViewHolder(view) {

   internal fun bind(newsItem: NewsObj, subject: PublishSubject<NewsObj>) {

      itemView.news_title.text = newsItem.headline?.main

      Glide.with(itemView.context)
         .load(BASE_IMAGE_URL + newsItem?.multimedia?.get(0)?.url)
         .into(itemView.news_item_image)

      itemView.setOnClickListener {
         subject.onNext(newsItem)
      }
   }

   companion object {
      val BASE_IMAGE_URL: String = "https://www.nytimes.com/"
      fun create(parent: ViewGroup): NewsListViewHolder {
         val layoutInflater = LayoutInflater.from(parent.context)
         val view = layoutInflater.inflate(R.layout.news_item_row_layout, parent, false)
         return NewsListViewHolder(view)
      }
   }
}
