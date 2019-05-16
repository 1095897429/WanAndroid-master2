package com.chinamall21.mobile.wanandroid.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.bean.KnowledgeBean
import java.lang.StringBuilder

/**
 * desc：
 * author：Created by xusong on 2019/3/8 10:57.
 */

class KnowledgeAdapter(val context: Context, datas: List<KnowledgeBean.Data>?):
        BaseQuickAdapter<KnowledgeBean.Data, BaseViewHolder>(R.layout.item_knowledge) {
    override fun convert(helper: BaseViewHolder?, item: KnowledgeBean.Data?) {
        helper?.getView<TextView>(R.id.knowledge_item_title)?.text = item?.name
        var sb = StringBuilder()
        item?.children?.forEach {
            sb.append(it.name + "    ")
        }

        helper?.getView<TextView>(R.id.knowledge_item_content)?.text = sb.toString()
    }

}