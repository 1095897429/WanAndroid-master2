package com.chinamall21.mobile.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.chinamall21.mobile.wanandroid.App.Companion.context
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.adapter.HomeAdapter
import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.presenter.CollectPresenterImpl
import com.chinamall21.mobile.wanandroid.utils.Constant
import com.chinamall21.mobile.wanandroid.utils.Utils
import com.chinamall21.mobile.wanandroid.view.CollectView
import kotlinx.android.synthetic.main.activity_collect.*

/**
 * desc：
 * author：Created by xusong on 2019/3/19 15:52.
 */

 class CollectActivity : AppCompatActivity(), CollectView {
    override fun collectOutArticle(result: HomeListBean) {

    }

    private var currentPage = 0

    private val collectPresenter: CollectPresenterImpl by lazy {
        CollectPresenterImpl(this)
    }

    private val collectAdapter: HomeAdapter by lazy {
        HomeAdapter(this, null,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)
        setSupportActionBar(collect_bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collect_bar.setNavigationOnClickListener {
            finish()
        }
        collect_rv.layoutManager = LinearLayoutManager(this)
        collect_rv.adapter = collectAdapter

        collectPresenter.getCollectList(currentPage)
        //上拉加载
        collectAdapter.run {
            setOnLoadMoreListener {
                currentPage++
                collectPresenter.getCollectList(currentPage)
            }
        }

        //下拉刷新
        collect_refresh.run {
            setOnRefreshListener {
                currentPage = 0
                collectPresenter.getCollectList(currentPage)
            }
        }

        //条目点击
        collectAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val datas: HomeListBean.Datas = adapter.data[position] as HomeListBean.Datas
                var intent = Intent(context, WebContentActivity::class.java)

                intent.putExtra(Constant.data, datas.title)
                intent.putExtra(Constant.name, datas.author)
                intent.putExtra(Constant.url, datas.link)
                intent.putExtra(Constant.id, datas.id)
                startActivity(intent)
            }

            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.home_item_collect -> {
                        val datas = collectAdapter.data[position]
                        collectPresenter.cancelCollect(datas.originId, position)
                        Utils.logE("collect")
                    }

                    R.id.home_item_type -> {
                        val datas = collectAdapter.data[position]
                        var intent = Intent(this@CollectActivity, KnowledgeActivity::class.java)
                        intent.putExtra(Constant.data, datas)
                        startActivity(intent)
                    }
                }

            }
        }
    }

    override fun getCollectSuc(result: HomeListBean) {
        if (currentPage == 0) {
            collectAdapter.setNewData(result.data.datas)
            if (result.data.over) {
                collectAdapter.setEnableLoadMore(false)
            }
            collect_rv.scrollToPosition(0)
        } else {
            collectAdapter.addData(result.data.datas!!)
            if (result.data.over) {
                collectAdapter.loadMoreEnd()
            } else {
                collectAdapter.loadMoreComplete()
            }
        }
    }

    override fun collectSuccess(result: HomeListBean, position: Int) {}

    override fun cancelCollectSuccess(result: HomeListBean, position: Int) {
        Utils.logE("cancelCollectSuccess")
        collectAdapter.data.removeAt(position)
        collectAdapter.notifyDataSetChanged()
    }

    override fun loading() {
        collect_refresh.isRefreshing = true
    }

    override fun loadComplete() {
        collect_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        collect_refresh.isRefreshing = false
    }

}