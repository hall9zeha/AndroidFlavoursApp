package com.barryzea.androidflavours.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.barryzea.androidflavours.R

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 06/02/2024.
 **/

class ImageViewCustom @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet?=null,
                                                defStyleAttr:Int=0,
): AppCompatImageView(context,attrs,defStyleAttr)
{
    private var ratio:Float=DEFAULT_RATIO

    init{
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
            with(a) {
                ratio = getFloat(R.styleable.AspectRatioImageView_ratio, DEFAULT_RATIO)
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = measuredWidth
        var height = measuredHeight

        if (width == 0 && height == 0) {
            return
        }

        if (width > 0) {
            height = (width * ratio).toInt()
        } else {
            width = (height / ratio).toInt()
        }

        setMeasuredDimension(width, height)
    }

    companion object {
        const val DEFAULT_RATIO = 1F
    }
}