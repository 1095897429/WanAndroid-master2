package com.chinamall21.mobile.wanandroid.bean


/**
 * desc：
 * author：Created by xusong on 2019/3/7 16:53.
 * 后台返回一个banner对象，包含一个Data集合
 */

data class BannerBean(var errorCode:String,var errorMsg:String, var data: List<Data>?){

    data class Data(
            var id: Int,
            var url: String,
            var imagePath: String,
            var title: String,
            var desc: String,
            var isVisible: Int,
            var order: Int,
            var type : Int
    ) {
        override fun toString(): String {
            return "Data(id=$id, url='$url', imagePath='$imagePath', title='$title', desc='$desc', isVisible=$isVisible, order=$order, type=$type)"
        }
    }

    override fun toString(): String {
        return "BannerBean(errorCode='$errorCode', errorMsg='$errorMsg', data=$data)"
    }


}