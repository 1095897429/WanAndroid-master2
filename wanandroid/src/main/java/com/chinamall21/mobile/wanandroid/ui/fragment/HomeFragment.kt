package com.chinamall21.mobile.wanandroid.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.adapter.HomeAdapter
import com.chinamall21.mobile.wanandroid.bean.AccountBean
import com.chinamall21.mobile.wanandroid.bean.BannerBean
import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import com.chinamall21.mobile.wanandroid.presenter.HomePresenterImpl
import com.chinamall21.mobile.wanandroid.ui.activity.AccountActivity
import com.chinamall21.mobile.wanandroid.ui.activity.KnowledgeActivity
import com.chinamall21.mobile.wanandroid.ui.activity.WebContentActivity
import com.chinamall21.mobile.wanandroid.utils.Constant
import com.chinamall21.mobile.wanandroid.utils.Utils
import com.chinamall21.mobile.wanandroid.view.HomeFragmentView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * desc：
 * author：Created by xusong on 2019/3/7 14:45.
 * View(HomeFrgmentView一些界面的逻辑) 包含 P(HomePresenter 界面逻辑对应的具体操作)
 */
class HomeFragment : Fragment(), HomeFragmentView {

    private val AUTO_PLAY = 2
    private var currentPage = 0

    private val bannerHandler: Handler? = Handler {
        if (it.what == AUTO_PLAY) {
            headerViewPager.currentItem = headerViewPager.currentItem + 1
            it.target.sendEmptyMessageDelayed(AUTO_PLAY, 5000)
        }
        false
    }
    /**
     * presenter
     */
    private val homeFragmentPresenter: HomePresenterImpl by lazy {
        HomePresenterImpl(this)
    }
    /**
     * header viewpager
     */
    private lateinit var headerViewPager: ViewPager

    /**
     * banner Text
     */
    private lateinit var bannerText: TextView
    /**
     *  indictor container
     */
    private lateinit var indictorContainer: LinearLayout

    /**
     * adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, null)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        homeFragmentPresenter.getBanner()
        homeFragmentPresenter.getHomeList(currentPage)
        home_rv.layoutManager = LinearLayoutManager(activity)
        home_rv.adapter = homeAdapter
        //home_rv.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        home_refresh.setColorSchemeColors(getColor(activity, R.color.colorPrimary))
        var headerView = View.inflate(activity, R.layout.fragment_home_header, null)
        headerViewPager = headerView.findViewById(R.id.home_header)
        bannerText = headerView.findViewById(R.id.banner_text)
        indictorContainer = headerView.findViewById(R.id.banner_indictor)
        homeAdapter.addHeaderView(headerView)
        //开启自动轮播
        bannerHandler!!.sendEmptyMessageDelayed(AUTO_PLAY, 5000)

        //下拉刷新 run函数
        home_refresh.run {
            setOnRefreshListener {//接口名 -- 不显示onRefresh
                refresh()
            }
        }


        //上拉加载
        homeAdapter.run {
            setOnLoadMoreListener {
                currentPage++
                homeFragmentPresenter.getHomeList(currentPage)
            }
        }


        //条目点击
        homeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val datas = homeAdapter.data[position]
                var intent = Intent(context, WebContentActivity::class.java)
                intent.putExtra(Constant.data, datas.title)
                intent.putExtra(Constant.url, datas.link)
                intent.putExtra(Constant.name, datas.author)
                intent.putExtra(Constant.id, datas.id)
                context.startActivity(intent)
            }

            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.home_item_collect ->{
                        if(AccountBean.instance.isLogin){
                            val datas = homeAdapter.data[position]
                            if(datas.collect){
                                homeFragmentPresenter.cancelCollect(datas.id,position)
                            }else{
                                homeFragmentPresenter.collect(datas.id,position)
                            }
                        }else{
                            Utils.toast("请先登录")
                            startActivity(Intent(activity, AccountActivity::class.java))
                        }
                    }

                    R.id.home_item_type ->{
                        val datas = homeAdapter.data[position]
                        var intent = Intent(activity, KnowledgeActivity::class.java)
                        intent.putExtra(Constant.data,datas)
                        startActivity(intent)
                    }
                }

            }
        }
    }

    fun refresh() {
        currentPage = 0
        homeFragmentPresenter.getBanner()
        homeFragmentPresenter.getHomeList(currentPage)
    }

    override fun loadComplete() {
        home_refresh.isRefreshing = false
    }

    override fun loadError(msg: String) {
        Utils.toast(msg)
        home_refresh.isRefreshing = false
    }

    override fun loadBannerSuccess(result: BannerBean) {
        headerViewPager.adapter = BannerAdapter(activity, result.data)
        bannerText.run {
            text = result.data!![0].title
        }
        //添加指示器
        if (indictorContainer.childCount == 0) {
            for (i in 0 until result.data!!.size) {
                var point = ImageView(activity)
                point.setImageResource(R.drawable.banner_indictor)
                if (i == 0) {
                    point.isSelected = true
                }
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                params.leftMargin = 10
                point.layoutParams = params
                indictorContainer.addView(point)
            }
        }


//        result.data?.forEach {
//            var point = ImageView(activity)
//            point.setImageResource(R.drawable.banner_indictor)
//            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT)
//
//            params.leftMargin = 10
//            point.layoutParams = params
//            indictorContainer.addView(point)
//        }

        //viewpager 监听
        headerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                bannerText.text = result.data!![position % result.data!!.size].title
                for (i in 0 until result.data!!.size) {
                    indictorContainer.getChildAt(i).isSelected = false
                }
                indictorContainer.getChildAt(position % result.data!!.size).isSelected = true
            }
        })
    }

    override fun collectSuccess(result: HomeListBean,position: Int) {
        homeAdapter.data[position].collect =true
        homeAdapter.notifyDataSetChanged()
    }

    override fun cancelCollectSuccess(result: HomeListBean,position: Int) {
        homeAdapter.data[position].collect =false
        homeAdapter.notifyDataSetChanged()
    }

    override fun loadHomeListSuccess(result: HomeListBean) {
        if (currentPage == 0) {
            homeAdapter.setNewData(result.data.datas)
        } else {
            homeAdapter.addData(result.data.datas!!)
            if (result.data.over) {
                homeAdapter.loadMoreEnd()
            } else {
                homeAdapter.loadMoreComplete()
            }
        }
    }

    override fun loading() {
        home_refresh.isRefreshing = true
    }

    //viewpager adapter
    class BannerAdapter(var context: Context, var data: List<BannerBean.Data>?) : PagerAdapter() {
        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return Int.MAX_VALUE
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            var imageView = ImageView(context)
            Glide.with(context).load(data!![position % data!!.size].imagePath).centerCrop().into(imageView)
            container!!.addView(imageView)
            imageView.setOnClickListener {
                var intent = Intent(context, WebContentActivity::class.java)
                intent.putExtra(Constant.data, data!![position % data!!.size].title)
                intent.putExtra(Constant.url, data!![position % data!!.size].url)
                intent.putExtra(Constant.id, data!![position % data!!.size].id)
                context.startActivity(intent)
            }
            return imageView
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container!!.removeView(`object` as ImageView)
        }
    }

    fun removeMessage() {
        bannerHandler!!.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        removeMessage()
    }

    override fun onResume() {
        super.onResume()
        //开启自动轮播
        removeMessage()
        bannerHandler!!.sendEmptyMessageDelayed(AUTO_PLAY, 5000)
    }

}