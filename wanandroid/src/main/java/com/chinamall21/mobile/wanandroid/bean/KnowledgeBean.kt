package com.chinamall21.mobile.wanandroid.bean

import java.io.Serializable


/**
 * desc：
 * author：Created by xusong on 2019/3/12 12:21.
 */

class KnowledgeBean (var errorCode: Int,
                       var errorMsg: String?,
                       var data: List<Data>) {

    data class Data(
            var id: Int,
            var name: String,
            var courseId: Int,
            var parentChapterId: Int,
            var order: Int,
            var visible: Int,
            var children: List<Children>?

    ):Serializable {
        data class Children(
                var id: Int,
                var name: String,
                var courseId: Int,
                var parentChapterId: Int,
                var order: Int,
                var visible: Int

        ) : Serializable {
            override fun toString(): String {
                return "Children(id=$id, name='$name', courseId=$courseId, parentChapterId=$parentChapterId, order=$order, visible=$visible"
            }
        }

        override fun toString(): String {
            return "Data(id=$id, name='$name', courseId=$courseId, parentChapterId=$parentChapterId, order=$order, visible=$visible, children=$children)"
        }
    }

    override fun toString(): String {
        return "KnowledgeBean(errorCode=$errorCode, errorMsg=$errorMsg, data=$data)"
    }

}