package com.chinamall21.mobile.wanandroid.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.ui.fragment.AHomeFragment
import com.chinamall21.mobile.wanandroid.ui.fragment.HomeFragment

/**
 * test Activity
 */
class ATestActivity : AppCompatActivity() {

    private var mineFragment : AHomeFragment = AHomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}