package com.chinamall21.mobile.wanandroid.presenter

import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.bean.KnowledgeBean
import com.chinamall21.mobile.wanandroid.net.ApiService
import com.chinamall21.mobile.wanandroid.net.RetrofitHelper
import com.chinamall21.mobile.wanandroid.view.KnowledgeView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * desc：
 * author：Created by xusong on 2019/3/12 12:42.
 */

class KnowledgePresenterImpl(view:KnowledgeView):KnowledgePresenter {

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

    private var mView:KnowledgeView = view

    override fun getKnowledge() {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getTypeTreeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<KnowledgeBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: KnowledgeBean?) {
                        mView.getKnowledgeSuc(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    override fun getKnowledgeContent(page:Int,id:Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getArticleList(page,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?) {
                        mView.getKnowLedgeContent(value!!)
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