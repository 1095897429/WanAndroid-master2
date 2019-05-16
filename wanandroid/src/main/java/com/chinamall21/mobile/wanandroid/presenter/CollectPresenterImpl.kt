package com.chinamall21.mobile.wanandroid.presenter

import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.net.ApiService
import com.chinamall21.mobile.wanandroid.net.RetrofitHelper
import com.chinamall21.mobile.wanandroid.view.CollectView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * desc：
 * author：Created by xusong on 2019/3/21 18:56.
 */

class CollectPresenterImpl(view:CollectView) :CollectPresenter {

    override fun collectOutArticle(title: String, author: String, link: String) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .addCollectOutsideArticle(title,author,link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?){
                        mView.collectOutArticle(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    private var mView = view

    override fun getCollectList(curPage: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getCollectArticle(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?){
                        mView.getCollectSuc(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    override fun collect(id: Int, position: Int) {

    }

    override fun cancelCollect(id: Int, position: Int) {
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



