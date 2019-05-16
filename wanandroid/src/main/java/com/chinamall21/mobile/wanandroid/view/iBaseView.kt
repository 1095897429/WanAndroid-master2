package com.chinamall21.mobile.wanandroid.view


/**
 * desc：
 * author：Created by xusong on 2019/3/7 17:27.
 */

interface iBaseView {

    fun loading()

    fun loadComplete()

    fun loadError(msg: String)
}