package com.chinamall21.mobile.wanandroid.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup


/**
 * desc：
 * author：Created by xusong on 2019/3/13 11:58.
 */

class FlowLayout : ViewGroup {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        //定义控件宽度
        var width = 0
        //定义控件高度
        var height = 0
        //定义每行最大宽度
        var lineWidth = 0
        //定义每行最大高度
        var lineHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            if (lineWidth + childWidth > widthSize) {//大于控件宽度换行
                width = Math.max(lineWidth, width)
                height += lineHeight
                lineWidth = childWidth
                lineHeight = childHeight

            } else {
                lineWidth += childWidth
                lineHeight = Math.max(childHeight, lineHeight)
            }
            if (i == childCount - 1) {//如果是最后一个子控件要计算他的宽高再算进去
                width = Math.max(lineWidth, width)
                height += lineHeight
            }

        }

        setMeasuredDimension(if (widthMode == View.MeasureSpec.EXACTLY) widthSize else width,
                if (heightMode == View.MeasureSpec.EXACTLY) heightSize else height)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //定义每行最大宽度
        var lineWidth = 0
        //定义每行最大高度
        var lineHeight = 0
        var left = 0
        var top = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            val lp = child.layoutParams as ViewGroup.MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            if (lineWidth + childWidth > measuredWidth) {//大于控件宽度换行
                top += lineHeight
                //换行把left值为0
                left = 0
                lineWidth = childWidth
                lineHeight = childHeight

            } else {
                lineWidth += childWidth
                lineHeight = Math.max(childHeight, lineHeight)
            }
            val lc = left + lp.leftMargin
            val tc = top + lp.topMargin
            val rc = lc + child.measuredWidth
            val bc = tc + child.measuredHeight
            child.layout(lc, tc, rc, bc)
            left += childWidth
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }
}