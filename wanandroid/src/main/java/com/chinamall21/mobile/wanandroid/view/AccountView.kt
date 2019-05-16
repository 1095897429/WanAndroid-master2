package com.chinamall21.mobile.wanandroid.view

import com.chinamall21.mobile.wanandroid.bean.LoginResponse


/**
 * desc：
 * author：Created by xusong on 2019/3/11 15:50.
 */

interface AccountView :iBaseView {

    fun LoginSuccess(result:LoginResponse)

    fun registerSuccess(result:LoginResponse)
}