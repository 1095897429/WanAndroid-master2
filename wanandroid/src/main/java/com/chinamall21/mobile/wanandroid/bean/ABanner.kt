package com.chinamall21.mobile.wanandroid.bean

data class ABanner(var errorCode:String,var errorMsg:String,var data :List<Data>?)  {

    data class Data(
            var id: Int,
            var url: String,
            var imagePath: String,
            var title: String,
            var desc: String,
            var isVisible: Int,
            var order: Int,
            var type : Int
    ){
        override fun toString(): String {
            return "Data(id = $id , url = $url)"
        }
    }

    override fun toString(): String {
        return super.toString()
    }

}