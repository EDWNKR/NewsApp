package com.technoblizz.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.technoblizz.newsapp.adapters.AdapterNews
import com.technoblizz.newsapp.model.Article
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityPresenter.View {

    private var presenter: MainActivityPresenter? = null
    private var adapterNews: AdapterNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
        adapterNews = AdapterNews(null)

        initViews()

        presenter?.getEverything("technology")
    }

    override fun onSuccessGetNews(articleList: List<Article>) {
        adapterNews?.setArticleList(articleList)
        rv_news.setAdapter(adapterNews)
        adapterNews?.notifyDataSetChanged()

        rv_news.visibility = VISIBLE
        layout_error_search.visibility = GONE
    }

    override fun onErrorGetNews(throwable: Throwable) {
        layout_error_search.visibility = VISIBLE
        rv_news.visibility = GONE
        Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
    }

    override fun onEmptyGetNews() {
        layout_error_search.visibility = VISIBLE
        rv_news.visibility = GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = GONE
    }

    private fun initViews() {
        rv_news.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        rv_news.setLayoutManager(layoutManager)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.unsubscribe()
    }
}
