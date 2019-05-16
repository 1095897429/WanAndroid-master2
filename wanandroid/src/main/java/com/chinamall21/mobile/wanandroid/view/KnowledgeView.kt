package com.chinamall21.mobile.wanandroid.view

import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.bean.KnowledgeBean

/**
 * desc：
 * author：Created by xusong on 2019/3/11 15:50.
 */

interface KnowledgeView : iBaseView {

    fun getKnowledgeSuc(result: KnowledgeBean)

    fun getKnowLedgeContent(result:HomeListBean)

    fun collectSuccess(result:HomeListBean,position:Int)

    fun cancelCollectSuccess(result:HomeListBean,position:Int)
}