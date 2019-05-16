package com.chinamall21.mobile.wanandroid.view

import com.chinamall21.mobile.wanandroid.bean.HomeListBean

/**
 * desc：
 * author：Created by xusong on 2019/3/11 15:50.
 */

interface CollectView : iBaseView {

    fun getCollectSuc(result: HomeListBean)

    fun collectSuccess(result:HomeListBean,position:Int)

    fun collectOutArticle(result:HomeListBean)

    fun cancelCollectSuccess(result:HomeListBean,position:Int)

}