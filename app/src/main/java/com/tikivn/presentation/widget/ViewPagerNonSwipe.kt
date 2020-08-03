package com.tikivn.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ViewPagerNonSwipe @JvmOverloads constructor(context: Context,attributes: AttributeSet?  = null): ViewPager(context,attributes) {
    private var enableSwipe: Boolean = false
    var smoothScroll: Boolean = true


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (!enableSwipe) return false
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (!enableSwipe) return false
        return super.onTouchEvent(ev)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, smoothScroll)
    }
}