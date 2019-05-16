package com.chinamall21.mobile.wanandroid.view

import com.chinamall21.mobile.wanandroid.bean.BannerBean
import com.chinamall21.mobile.wanandroid.bean.HomeListBean

/**
 * desc：
 * author：Created by xusong on 2019/3/7 15:20.
 * 具体的业务内容
 */

interface HomeFragmentView :iBaseView {

    fun loadBannerSuccess(result:BannerBean)

    fun loadHomeListSuccess(result:HomeListBean)

    fun collectSuccess(result:HomeListBean,position:Int)

    fun cancelCollectSuccess(result:HomeListBean,position:Int)
}