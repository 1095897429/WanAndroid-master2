package com.chinamall21.mobile.wanandroid.presenter

import com.chinamall21.mobile.wanandroid.view.AiBaseView
import com.chinamall21.mobile.wanandroid.view.HomeFragmentView

class AHomePresenterImpl(view :AiBaseView) : AHomePresenter {

    private var mView:HomeFragmentView = view as HomeFragmentView

    override fun getBanner() {
        mView.loading()
    }

    override fun getHomeList(curPage: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun collect(id: Int, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelCollect(id: Int, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}