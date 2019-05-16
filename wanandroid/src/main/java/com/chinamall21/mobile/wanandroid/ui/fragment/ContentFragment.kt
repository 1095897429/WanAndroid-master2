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
import com.chinamall21.mobile.wanandroid.adapter.HomeAdapter
import com.chinamall21.mobile.wanandroid.bean.AccountBean
import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.bean.KnowledgeBean
import com.chinamall21.mobile.wanandroid.presenter.KnowledgePresenterImpl
import com.chinamall21.mobile.wanandroid.ui.activity.AccountActivity
import com.chinamall21.mobile.wanandroid.ui.activity.WebContentActivity
import com.chinamall21.mobile.wanandroid.utils.Constant
import com.chinamall21.mobile.wanandroid.utils.Utils
import com.chinamall21.mobile.wanandroid.view.KnowledgeView
import kotlinx.android.synthetic.main.fragment_content.*

/**
 * desc：
 * author：Created by xusong on 2019/3/7 14:45.
 */

class ContentFragment : Fragment(), KnowledgeView {

    private var mRootView: View? = null
    private var isViewCreated: Boolean = false
    private var isVisibletoUser: Boolean = false
    private var cid = 0
    private var currentPage = 0

    private val presenter: KnowledgePresenterImpl by lazy {
        KnowledgePresenterImpl(this)
    }
    /**
     * adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, null)
    }

    companion object {
        fun newInstance(cid: Int): ContentFragment {
            var bundle = Bundle()
            bundle.putInt(Constant.id, cid)
            val fragmnet = ContentFragment()
            fragmnet.arguments = bundle
            return fragmnet
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            cid = it.getInt(Constant.id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == mRootView) {
            mRootView = inflater!!.inflate(R.layout.fragment_content, container, false)
        }

        return mRootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        isViewCreated = true
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisibletoUser = isVisibleToUser
        lazyLoad()
    }

    private fun lazyLoad() {
        if (isVisibletoUser && isViewCreated) {
            presenter.getKnowledgeContent(currentPage, cid)
            isViewCreated = false
            isVisibletoUser = false

            content_rv.layoutManager = LinearLayoutManager(activity)
            content_rv.adapter = homeAdapter
            content_refresh.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary))

            //下拉刷新
            content_refresh.run {
                setOnRefreshListener {
                    currentPage = 0
                    presenter.getKnowledgeContent(currentPage, cid)
                }
            }
            //上拉加载
            homeAdapter.run {
                setOnLoadMoreListener {
                    currentPage++
                    presenter.getKnowledgeContent(currentPage, cid)
                }
            }

            homeAdapter.setOnItemClickListener { adapter, view, position ->
                val datas = homeAdapter.data[position]
                var intent = Intent(context, WebContentActivity::class.java)
                intent.putExtra(Constant.data, datas.title)
                intent.putExtra(Constant.url, datas.link)
                intent.putExtra(Constant.name, datas.author)
                intent.putExtra(Constant.id, datas.id)
                context.startActivity(intent)
            }
            homeAdapter.setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.home_item_collect ->{
                        if(AccountBean.instance.isLogin){
                            val datas = homeAdapter.data[position]
                            if(datas.collect){
                                presenter.cancelCollect(datas.id,position)
                            }else{
                                presenter.collect(datas.id,position)
                            }
                        }else{
                            Utils.toast("请先登录")
                            startActivity(Intent(activity, AccountActivity::class.java))
                        }
                    }

                }
            }
        }

    }

    override fun collectSuccess(result: HomeListBean,position: Int) {
        homeAdapter.data[position].collect =true
        homeAdapter.notifyDataSetChanged()
    }

    override fun cancelCollectSuccess(result: HomeListBean,position: Int) {
        homeAdapter.data[position].collect =false
        homeAdapter.notifyDataSetChanged()
    }

    override fun loading() {
        if (currentPage == 0)
            content_refresh.isRefreshing = true
    }

    override fun loadComplete() {
        content_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        content_refresh.isRefreshing = false
    }

    override fun getKnowledgeSuc(result: KnowledgeBean) {}

    override fun getKnowLedgeContent(result: HomeListBean) {
        if (currentPage == 0) {
            homeAdapter.setNewData(result.data.datas)
            if (result.data.over) {
                homeAdapter.setEnableLoadMore(false)
            }
        } else {
            homeAdapter.addData(result.data.datas!!)
            if (result.data.over) {
                homeAdapter.loadMoreEnd()
            } else {
                homeAdapter.loadMoreComplete()
            }
        }
    }

}