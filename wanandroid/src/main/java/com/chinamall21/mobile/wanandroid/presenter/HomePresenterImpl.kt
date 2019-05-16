package com.chinamall21.mobile.wanandroid.presenter

import com.chinamall21.mobile.wanandroid.bean.BannerBean
import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.net.ApiService
import com.chinamall21.mobile.wanandroid.net.RetrofitHelper
import com.chinamall21.mobile.wanandroid.view.HomeFragmentView
import com.chinamall21.mobile.wanandroid.view.iBaseView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * desc：
 * author：Created by xusong on 2019/3/7 17:41.
 * 具体业务逻辑完成后，通过 具体界面的view接口来通知view做操作
 */

class HomePresenterImpl(view: iBaseView) : HomePresenter {

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

    // private var mView: HomeFragmentView? = null -- 可以的
    private var mView: HomeFragmentView = view as HomeFragmentView // as 用于类型转换

    override fun getBanner() {
        mView.loading()//具体的view操作

        RetrofitHelper.instance.create(ApiService::class.java)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BannerBean> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: BannerBean?) {
                        mView.loadBannerSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })

    }

    override fun getHomeList(curPage: Int) {
        mView.loading()
        RetrofitHelper.instance.create(ApiService::class.java)
                .getHomeList(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<HomeListBean> {//object:表示匿名类
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: HomeListBean?) {
                        mView.loadHomeListSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }

                    override fun onComplete() {
                        mView.loadComplete()
                    }
                })
    }

//    {
//        result ->
//
//        when (result.errorCode) {
//            "200" ->
//                Utils.logE("200")
//            else ->
//                Utils.logE("not 200")
//        }
//    },
//    {
//        error ->
//        Utils.logE("error")
//    }
}

