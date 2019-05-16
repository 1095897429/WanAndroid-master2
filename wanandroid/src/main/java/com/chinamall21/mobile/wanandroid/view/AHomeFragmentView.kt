package com.chinamall21.mobile.wanandroid.view

import com.chinamall21.mobile.wanandroid.bean.ABanner
import com.chinamall21.mobile.wanandroid.bean.HomeListBean

interface AHomeFragmentView : AiBaseView{

    fun loadBannerSuccess(result : ABanner)

    fun loadHomeListSuccuss(result: HomeListBean)

    fun collectSuccess(result: HomeListBean,position:Int)

    fun cancelCollectSuccess(result: HomeListBean,position: Int)
}