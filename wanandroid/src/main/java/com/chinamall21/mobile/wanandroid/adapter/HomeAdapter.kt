package com.chinamall21.mobile.wanandroid.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.bean.HomeListBean
import java.text.SimpleDateFormat


/**
 * desc：
 * author：Created by xusong on 2019/3/8 10:57.
 */

class HomeAdapter : BaseQuickAdapter<HomeListBean.Datas, BaseViewHolder> {

    private  var collect: Boolean = false
    constructor(context: Context, datas: List<HomeListBean.Data>?) : this(context, datas, false)
    constructor(context: Context, datas: List<HomeListBean.Data>?, isCollect: Boolean) : super(R.layout.item_rv_home) {
        collect = isCollect
    }


    override fun convert(helper: BaseViewHolder?, item: HomeListBean.Datas?) {
        val format = SimpleDateFormat("yyyy-MM-dd")
        if(collect){
            item!!.collect = collect
        }
        helper!!.setText(R.id.home_item_author, item!!.author)
                .setText(R.id.home_item_time, format.format(item.publishTime))
                .setText(R.id.home_item_title, item.title)
                .setText(R.id.home_item_type, item.chapterName)
                .setImageResource(
                        R.id.home_item_collect,
                        if (item.collect) R.drawable.ic_action_like else R.drawable.ic_action_no_like)
                .addOnClickListener(R.id.home_item_collect)
                .addOnClickListener(R.id.home_item_type)

    }

}