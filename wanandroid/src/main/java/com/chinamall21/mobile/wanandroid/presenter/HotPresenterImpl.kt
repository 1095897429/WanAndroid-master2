package com.chinamall21.mobile.wanandroid.presenter

import com.chinamall21.mobile.wanandroid.bean.HotBean
import com.chinamall21.mobile.wanandroid.net.ApiService
import com.chinamall21.mobile.wanandroid.net.RetrofitHelper
import com.chinamall21.mobile.wanandroid.view.HotView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * desc：
 * author：Created by xusong on 2019/3/12 12:42.
 */

class HotPresenterImpl(view: HotView): HotPresenter {

    private var mView: HotView = view

    override fun getHotKey() {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getHotKeyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HotBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HotBean?) {
                        mView.getHotKeySuc(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

    override fun getCommonUse() {
        RetrofitHelper.instance.create(ApiService::class.java)
                .getFriendList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HotBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HotBean?) {
                        mView.getCommonUse(value!!)
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