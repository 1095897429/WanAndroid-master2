package com.chinamall21.mobile.wanandroid.view

import com.chinamall21.mobile.wanandroid.bean.HotBean

/**
 * desc：
 * author：Created by xusong on 2019/3/11 15:50.
 */

interface HotView : iBaseView {

    fun getHotKeySuc(result: HotBean)

    fun getCommonUse(result: HotBean)
}