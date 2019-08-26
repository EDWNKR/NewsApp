package com.technoblizz.newsapp

import com.technoblizz.newsapp.api.DataSource
import com.technoblizz.newsapp.model.Article
import com.technoblizz.newsapp.model.ModelNews

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by edwin on 26/08/19.
 */
class MainActivityPresenter(view: MainActivity) {

    private val composite = CompositeDisposable()

    private val view: View

    interface View {

        fun onSuccessGetNews(articleList: List<Article>)

        fun onErrorGetNews(throwable: Throwable)

        fun onEmptyGetNews()

        fun showProgressBar()

        fun hideProgressBar()

    }

    init { this.view = view }

    fun getEverything(keyword: String) {
        DataSource.getDataSource()!!.getEverything(keyword, "b5705aa5ea6f4062980f81d68940071b")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ModelNews> {
                override fun onSubscribe(d: Disposable) {

                    composite.add(d)
                    view.showProgressBar()
                }

                override fun onNext(modelNews: ModelNews) {

                    if (modelNews.totalResults == 0) {
                        view.onEmptyGetNews()
                    } else {
                        view.onSuccessGetNews(modelNews.articles!!)
                    }
                }

                override fun onError(e: Throwable) {
                    view.onErrorGetNews(e)
                    view.hideProgressBar()
                }

                override fun onComplete() {
                    view.hideProgressBar()
                }
            })
    }

    fun unsubscribe() {
        composite.dispose()
    }
}
