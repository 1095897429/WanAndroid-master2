package com.chinamall21.mobile.wanandroid.presenter

import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.net.ApiService
import com.chinamall21.mobile.wanandroid.net.RetrofitHelper
import com.chinamall21.mobile.wanandroid.view.Searchview
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * desc：
 * author：Created by xusong on 2019/3/12 12:42.
 */

class SearchPresenterImpl(view: Searchview) : SearchPresenter {

    private var mView: Searchview = view

    override fun getSearch(page: Int, content: String) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getSearchList(page, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?) {
                        mView.SearchResult(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    override fun collect(id: Int,position: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .addCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?) {
                        mView.collectSuccess(value!!,position)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    override fun cancelCollect(id: Int,position: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .removeCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?) {
                        mView.cancelCollectSuccess(value!!,position)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

}