package com.example.beritakini.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beritakini.LoginActivity
import com.example.beritakini.R
import com.example.beritakini.data.remote.APIClient
import com.example.beritakini.data.response.ArticlesItem
import com.example.beritakini.data.response.NewsResponse
import com.example.beritakini.ui.detail.DetailActivity
import com.example.beritakini.ui.detail.DetailActivity.Companion.EXTRA_NEWS
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NewsAdapter
    private lateinit var rvNews: RecyclerView
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
    private lateinit var btnLogout: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNews = findViewById(R.id.rvNews)
        shimmerViewContainer = findViewById(R.id.shimmer_view_container)
        btnLogout = findViewById(R.id.btnLogout)
        auth = FirebaseAuth.getInstance()

        getNews()
        initRecylerView()

        btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun initRecylerView() {
        adapter = NewsAdapter { news ->
            moveActivity(news)
        }
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = adapter
    }

    private fun moveActivity(news: ArticlesItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_NEWS, news)
        startActivity(intent)
    }

    private fun getNews() {
        shimmerViewContainer.startShimmer()
        APIClient.create().getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles: List<ArticlesItem> =
                        response.body()?.articles as List<ArticlesItem>
                    adapter.setNews(articles)
                    adapter.notifyDataSetChanged()
                    shimmerViewContainer.stopShimmer()
                    shimmerViewContainer.visibility = View.GONE
                    rvNews.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                shimmerViewContainer.stopShimmer()
                shimmerViewContainer.visibility = View.GONE
                rvNews.visibility = View.VISIBLE
            }
        })
    }

    private fun logout() {
        auth.signOut()
        Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}
