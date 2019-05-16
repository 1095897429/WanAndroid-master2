package com.chinamall21.mobile.wanandroid.presenter

interface AHomePresenter {
    fun getBanner()

    fun getHomeList(curPage:Int)

    fun collect(id:Int,position:Int)

    fun cancelCollect(id:Int,position:Int)
}