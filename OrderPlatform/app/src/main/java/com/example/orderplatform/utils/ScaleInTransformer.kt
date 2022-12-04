package com.example.orderplatform.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


// 自定义跳转动画
class ScaleInTransformer : ViewPager2.PageTransformer {

    private val minScale = 0.85f
    private val centerF = 0.5f

    override fun transformPage(page: View, position: Float) {
        page.elevation = -abs(position)
        val pageW = page.width
        val pageH = page.height
        page.pivotY = pageH / 2f
        page.pivotX = pageW / 2f

        if (position < -1) {
            page.scaleX = minScale
            page.scaleY = minScale
            page.pivotX = pageW.toFloat()
        } else if (position <= 1) {
            if (position < 0) {
                val scaleFactor = (1 + position) * (1 - minScale) + minScale
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.pivotX = pageW * (centerF + centerF * -position)
            } else {
                val scaleFactor = (1 - position) * (1 - minScale) + minScale
                page.scaleX = scaleFactor
                page.scaleY = scaleFactor
                page.pivotX = pageW * ((1 - position) * centerF)
            }
        } else {
            page.scaleX = minScale
            page.scaleY = minScale
            page.pivotX = 0f
        }
    }
}