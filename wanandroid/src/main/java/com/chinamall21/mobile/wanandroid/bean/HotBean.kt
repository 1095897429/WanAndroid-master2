package com.chinamall21.mobile.wanandroid.bean


/**
 * desc：
 * author：Created by xusong on 2019/3/12 18:45.
 */

class HotBean(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>?

) {
    data class Data(
            var id: Int,
            var name: String,
            var link: Any,
            var visible: Int,
            var order: Int,
            var icon: Any

    ) {
        override fun toString(): String {
            return "Data(id=$id, name='$name', link=$link, visible=$visible, order=$order, icon=$icon)"
        }
    }

    override fun toString(): String {
        return "HotBean(errorCode=$errorCode, errorMsg=$errorMsg, data=$data)"
    }

}