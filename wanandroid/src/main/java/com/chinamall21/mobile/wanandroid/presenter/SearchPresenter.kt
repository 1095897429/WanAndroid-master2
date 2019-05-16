package com.chinamall21.mobile.wanandroid.presenter

/**
 * desc：
 * author：Created by xusong on 2019/3/7 17:27.
 */

interface SearchPresenter {

    fun getSearch(page:Int,content:String)
    fun collect(id:Int,position:Int)

    fun cancelCollect(id:Int,position:Int)
}