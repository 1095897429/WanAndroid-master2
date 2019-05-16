package com.chinamall21.mobile.wanandroid.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.chinamall21.mobile.wanandroid.R


/**
 * desc：
 * author：Created by xusong on 2019/3/11 15:03.
 */

class WebProgressView :View {

    private var mPaint: Paint
    private var mCurrentX: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0){
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = resources.getColor(R.color.progress_color)
        mPaint.strokeWidth = 30f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(0f, 0f, mCurrentX.toFloat(), 0f, mPaint)
    }

    fun setCurrentX(currentX: Int) {
        mCurrentX = currentX * (width / 100)
        invalidate()
    }
}