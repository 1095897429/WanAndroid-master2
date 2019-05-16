package com.chinamall21.mobile.wanandroid.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.chinamall21.mobile.wanandroid.R
import com.chinamall21.mobile.wanandroid.bean.AccountBean
import com.chinamall21.mobile.wanandroid.bean.LoginResponse
import com.chinamall21.mobile.wanandroid.presenter.AccountPresenterImpl
import com.chinamall21.mobile.wanandroid.utils.Constant
import com.chinamall21.mobile.wanandroid.utils.Utils
import com.chinamall21.mobile.wanandroid.view.AccountView
import kotlinx.android.synthetic.main.activity_login.*


/**
 * desc：
 * author：Created by xusong on 2019/3/11 15:19.
 */

class AccountActivity : AppCompatActivity(), AccountView {

    /**
     * presenter
     */
    private val accountPresenter: AccountPresenterImpl by lazy {
        AccountPresenterImpl(this)
    }

    override fun LoginSuccess(result: LoginResponse) {
        if (result.errorCode == 0) {
            Utils.logE(result.data)
            Utils.toast("登录成功")
            Utils.logE(result.toString())
            initAccount(result)
            setResult(Constant.AccountCode)
            finish()
        } else {
            Utils.toast(result.errorMsg!!)
        }
    }

    private fun initAccount(result: LoginResponse) {
        AccountBean.instance.username = result.data.username
        AccountBean.instance.id = result.data.id
        AccountBean.instance.type = result.data.type
        AccountBean.instance.icon = result.data.icon!!
        AccountBean.instance.isLogin = true
        AccountBean.instance.collectIds = result.data.collectIds
        Utils.writeToCache(Constant.accountInfo, AccountBean.instance)
    }

    override fun registerSuccess(result: LoginResponse) {
        if (result.errorCode == 0) {
            Utils.logE(result.data)
            Utils.toast("注册成功")
            initAccount(result)
            setResult(Constant.AccountCode)
            finish()
        } else {
            Utils.toast(result.errorMsg!!)
        }
    }

    override fun loading() {}

    override fun loadComplete() {}

    override fun loadError(msg: String) {
        Utils.toast(msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(login_bar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        login_bar.setNavigationOnClickListener {
            finish()
        }

        bt_register.setOnClickListener {
            if (checkInput()) {
                accountPresenter.register(et_username.text.toString(),
                        et_pwd.text.toString(), et_pwd.text.toString())
            }
        }

        bt_login.setOnClickListener {
            if (checkInput()) {
                accountPresenter.login(et_username.text.toString(), et_pwd.text.toString())
            }
        }
    }

    fun checkInput(): Boolean {
        if (TextUtils.isEmpty(et_username.text.toString())) {
            Utils.toast("用户名不能为空")
            return false
        }

        if (TextUtils.isEmpty(et_pwd.text.toString())) {
            Utils.toast("密码不能为空")
            return false
        }
        return true
    }
}