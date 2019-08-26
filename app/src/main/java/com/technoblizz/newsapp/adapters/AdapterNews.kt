package com.technoblizz.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.technoblizz.newsapp.R
import com.technoblizz.newsapp.model.Article

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

class AdapterNews internal constructor(articleList: List<Article>?) : RecyclerView.Adapter<AdapterNews.NewsViewHolder>() {


    private var articleList: List<Article>? = null

    init {
        setArticleList(articleList)
    }

    internal fun setArticleList(articleList: List<Article>?) {
        if (articleList == null) {
            this.articleList = ArrayList()
        } else {
            this.articleList = articleList
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.adapter_news,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int = articleList?.size!!

    override fun onBindViewHolder(newsViewHolder: NewsViewHolder, i: Int) {
        newsViewHolder.bind(articleList!![i])
    }

    inner class NewsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView
        var tvTitle: TextView
        var tvDescription: TextView
        var tvDate: TextView

        init {
            imageView = itemView.findViewById(R.id.iv)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvDate = itemView.findViewById(R.id.tvDate)
        }

        fun bind(article: Article) {
            Glide.with(itemView).load(article.urlToImage).into(imageView)
            tvTitle.text = article.title
            tvDescription.text = article.description

            var newdate: String? = null
            val strDate = article.publishedAt
            val localeID = Locale("in", "ID")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

            val convertedDate: Date?
            try {
                convertedDate = dateFormat.parse(strDate!!)
                val sdfmonth = SimpleDateFormat("dd MMMM yyyy", localeID)
                newdate = sdfmonth.format(convertedDate!!)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            tvDate.text = newdate

        }
    }
}
