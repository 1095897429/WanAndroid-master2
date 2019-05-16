package com.chinamall21.mobile.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.adapter.HomeAdapter
import com.chinamall21.mobile.wanandroid.bean.AccountBean
import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.presenter.SearchPresenterImpl
import com.chinamall21.mobile.wanandroid.utils.Constant
import com.chinamall21.mobile.wanandroid.utils.Utils
import com.chinamall21.mobile.wanandroid.view.Searchview
import kotlinx.android.synthetic.main.activity_search.*


/**
 * desc：
 * author：Created by xusong on 2019/3/19 15:52.
 */

class SearchActivity : AppCompatActivity(), Searchview {

    private var currentPage = 0
    private lateinit var mQuery:String

    private var searchView: SearchView? = null

    private val searchPresenter: SearchPresenterImpl by lazy {
        SearchPresenterImpl(this)
    }

    private val searchAdapter: HomeAdapter by lazy {
        HomeAdapter(this,null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(stoolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        stoolbar.setNavigationOnClickListener {
            finish()
        }
        search_rv.layoutManager = LinearLayoutManager(this)
        search_rv.adapter = searchAdapter
        //上拉加载
        searchAdapter.run {
            setOnLoadMoreListener {
                currentPage++
                searchPresenter.getSearch(currentPage,mQuery)
            }
        }

        //下拉刷新
        search_refresh.run {
            setOnRefreshListener {
                currentPage = 0
                searchPresenter.getSearch(currentPage,mQuery)
            }
        }

        //条目点击
        searchAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val datas:HomeListBean.Datas = adapter.data[position] as HomeListBean.Datas
                var intent = Intent(this@SearchActivity, WebContentActivity::class.java)
                intent.putExtra(Constant.data, datas.title)
                intent.putExtra(Constant.url, datas.link)
                intent.putExtra(Constant.id, datas.id)
                intent.putExtra(Constant.name, datas.author)
                startActivity(intent)
            }

            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.home_item_collect ->{
                        if(AccountBean.instance.isLogin){
                            val datas = searchAdapter.data[position]
                            if(datas.collect){
                                searchPresenter.cancelCollect(datas.id,position)
                            }else{
                                searchPresenter.collect(datas.id,position)
                            }
                        }else{
                            Utils.toast("请先登录")
                            startActivity(Intent(this@SearchActivity, AccountActivity::class.java))
                        }
                    }

                    R.id.home_item_type -> {
                        val datas = searchAdapter.data[position]
                        var intent = Intent(this@SearchActivity, KnowledgeActivity::class.java)
                        intent.putExtra(Constant.data, datas)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        searchView = MenuItemCompat.getActionView(menu?.findItem(R.id.search)) as SearchView
        searchView!!.setIconifiedByDefault(false)
        searchView!!.queryHint ="搜你想搜的.."
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchPresenter.getSearch(0,query)
                mQuery = query
                searchView?.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun SearchResult(result: HomeListBean) {
        if (currentPage == 0) {
            searchAdapter.setNewData(result.data.datas)
            if (result.data.over) {
                searchAdapter.setEnableLoadMore(false)
            }
            search_rv.scrollToPosition(0)
        } else {
            searchAdapter.addData(result.data.datas!!)
            if (result.data.over) {
                searchAdapter.loadMoreEnd()
            } else {
                searchAdapter.loadMoreComplete()
            }
        }
    }

    override fun collectSuccess(result: HomeListBean, position: Int) {
        searchAdapter.data[position].collect =true
        searchAdapter.notifyDataSetChanged()
    }

    override fun cancelCollectSuccess(result: HomeListBean, position: Int) {
        searchAdapter.data[position].collect =false
        searchAdapter.notifyDataSetChanged()
    }

    override fun loading() {
        search_refresh.isRefreshing = true
    }

    override fun loadComplete() {
        search_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        search_refresh.isRefreshing = false
    }



}