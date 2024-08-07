package com.example.beritakini.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beritakini.data.response.ArticlesItem
import com.example.beritakini.R

class NewsAdapter(private val listener: (ArticlesItem) -> Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var news = listOf<ArticlesItem>()

    fun setNews(news: List<ArticlesItem>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val ivNews: ImageView = itemView.findViewById(R.id.ivNews)

        fun bind(newsItem: ArticlesItem) {
            tvTitle.text = newsItem.title
            itemView.setOnClickListener { listener(newsItem) }

            val imageUrl = newsItem.urlToImage
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .apply(RequestOptions().dontTransform().placeholder(R.drawable.loading_image))
                    .into(ivNews)
            } else {
                ivNews.setImageResource(R.drawable.loading_image)
            }
        }
    }
}
