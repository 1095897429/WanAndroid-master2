package com.chinamall21.mobile.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.adapter.KnowledgeAdapter
import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.bean.KnowledgeBean
import com.chinamall21.mobile.wanandroid.presenter.KnowledgePresenterImpl
import com.chinamall21.mobile.wanandroid.ui.activity.KnowledgeActivity
import com.chinamall21.mobile.wanandroid.utils.Constant
import com.chinamall21.mobile.wanandroid.utils.Utils
import com.chinamall21.mobile.wanandroid.view.KnowledgeView
import kotlinx.android.synthetic.main.fragment_knowledge.*

/**
 * desc：
 * author：Created by xusong on 2019/3/7 14:45.
 */

class KnowledgeFragment : Fragment(),KnowledgeView {
    override fun collectSuccess(result: HomeListBean, position: Int) {

    }

    override fun cancelCollectSuccess(result: HomeListBean, position: Int) {

    }

    override fun getKnowLedgeContent(result: HomeListBean) {

    }

    private val presenter:KnowledgePresenterImpl by lazy {
       KnowledgePresenterImpl(this)
    }
    /**
     * adapter
     */
    private val knowledgeAdapter: KnowledgeAdapter by lazy {
        KnowledgeAdapter(activity, null)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_knowledge, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.getKnowledge()
        knowledge_rv.layoutManager = LinearLayoutManager(activity)
        knowledge_rv.adapter = knowledgeAdapter
        //home_rv.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        knowledge_refresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary))

        //下拉刷新
        knowledge_refresh.run {
            setOnRefreshListener {
                presenter.getKnowledge()
            }
        }
        //点击事件
        knowledgeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                var intent = Intent(activity,KnowledgeActivity::class.java)
                intent.putExtra(Constant.data,knowledgeAdapter.data[position])
                startActivity(intent)
            }
        }
    }

    override fun loading() {}

    override fun loadComplete() {
        knowledge_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        Utils.toast(msg)
        knowledge_refresh.isRefreshing = false
    }

    override fun getKnowledgeSuc(result: KnowledgeBean) {
        knowledgeAdapter.setNewData(result.data)
    }
}