package com.chinamall21.mobile.wanandroid

import android.app.Application
import android.content.Context
import com.chinamall21.mobile.wanandroid.utils.Preference


/**
 * desc：
 * author：Created by xusong on 2019/3/11 18:56.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        // 初始化 SharedPreference
        Preference.setContext(applicationContext)
    }

    companion object {
        var context:Context? = null

        fun getAppContext(): Context? {
            return context
        }
    }
}

