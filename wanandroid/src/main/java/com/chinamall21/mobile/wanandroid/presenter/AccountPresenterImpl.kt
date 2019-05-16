package com.chinamall21.mobile.wanandroid.presenter

import com.chinamall21.mobile.wanandroid.bean.LoginResponse
import com.chinamall21.mobile.wanandroid.net.ApiService
import com.chinamall21.mobile.wanandroid.net.RetrofitHelper
import com.chinamall21.mobile.wanandroid.view.AccountView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * desc：
 * author：Created by xusong on 2019/3/11 15:54.
 */

class AccountPresenterImpl(view:AccountView):AccountPresenter {

    private var mView: AccountView = view

    override fun login(username: String, password: String) {
        RetrofitHelper.instance.create(ApiService::class.java)
                .loginWanAndroid(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LoginResponse> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: LoginResponse?){
                        mView.LoginSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {}
                })
    }

    override fun register(username: String, password: String, repassword: String) {
        RetrofitHelper.instance.create(ApiService::class.java)
                .registerWanAndroid(username,password,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LoginResponse> {
                    override fun onSubscribe(d: Disposable?) {}

                    override fun onNext(value: LoginResponse?){
                        mView.registerSuccess(value!!)
                    }

                    override fun onError(e: Throwable?) {
                        mView.loadError(e.toString())
                    }
                    override fun onComplete() {

                    }
                })
    }
}