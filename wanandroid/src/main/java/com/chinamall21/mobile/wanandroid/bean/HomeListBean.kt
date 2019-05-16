package com.chinamall21.mobile.wanandroid.bean

import java.io.Serializable


/**
 * desc：
 * author：Created by xusong on 2019/3/8 11:41.
 * 后台返回HomeListBean，Data包含集合
 */

class HomeListBean(var errorCode: Int,
                   var errorMsg: String?,
                   var data: Data //集合
) {

    data class Data(
            var offset: Int,
            var size: Int,
            var total: Int,
            var pageCount: Int,
            var curPage: Int,
            var over: Boolean,
            var datas: List<Datas>?

    ) {
        override fun toString(): String {
            return "Data(offset=$offset, size=$size, total=$total, pageCount=$pageCount, curPage=$curPage, over=$over, datas=$datas)"
        }
    }
    //每一项数据
    data class Datas( var id: Int,
                      var originId: Int,
                      var title: String,
                      var chapterId: Int,
                      var chapterName: String?,
                      var envelopePic: Any,
                      var link: String,
                      var author: String,
                      var origin: Any,
                      var publishTime: Long,
                      var zan: Any,
                      var desc: Any,
                      var visible: Int,
                      var niceDate: String,
                      var courseId: Int,
                      var collect: Boolean):Serializable

    override fun toString(): String {
        return "HomeListBean(errorCode=$errorCode, errorMsg=$errorMsg, data=$data)"
    }


}