package com.chinamall21.mobile.wanandroid.presenter

/**
 * desc：
 * author：Created by xusong on 2019/3/7 17:27.
 */

interface AccountPresenter {

    fun login(username: String, password: String)

    fun register(username: String, password: String, repassword: String)


}