package com.example.beritakini.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beritakini.R
import com.example.beritakini.data.response.ArticlesItem

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS = "extraNews"
    }

    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivNews: ImageView
    private lateinit var btnGoToNews: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvTitle = findViewById(R.id.tvTitle)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvDescription = findViewById(R.id.tvDescription)
        ivNews = findViewById(R.id.ivNews)
        btnGoToNews = findViewById(R.id.btnGoToNews)

        val news = intent.getParcelableExtra<ArticlesItem>(EXTRA_NEWS)

        if (news != null) {
            tvTitle.text = news.title
            tvAuthor.text = news.author
            tvDescription.text = news.description

            Glide.with(this@DetailActivity)
                .load(news.urlToImage)
                .apply(RequestOptions().dontTransform().placeholder(R.drawable.loading_image))
                .into(ivNews)

            btnGoToNews.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
                startActivity(browserIntent)
            }
        } else {
            Log.e("DetailActivity", "News data is null")
        }
    }
}
