package com.chinamall21.mobile.wanandroid.presenter


/**
 * desc：
 * author：Created by xusong on 2019/3/7 17:27.
 * 界面操作 对应具体的逻辑，一般为网络操作
 */

interface HomePresenter {
    fun getBanner()

    fun getHomeList(curPage:Int)

    fun collect(id:Int,position:Int)

    fun cancelCollect(id:Int,position:Int)
}