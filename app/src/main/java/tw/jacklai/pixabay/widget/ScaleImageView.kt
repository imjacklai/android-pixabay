package tw.jacklai.pixabay.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * Created by jacklai on 28/03/2018.
 */

class ScaleImageView : ImageView {
    private var initWidth = 0
    private var initHeight = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (initWidth > 0 && initHeight > 0) {
            val width = View.MeasureSpec.getSize(widthMeasureSpec)
            var height = View.MeasureSpec.getSize(heightMeasureSpec)
            val scale = initHeight.toFloat() / initWidth.toFloat()
            if (width > 0) {
                height = (width.toFloat() * scale).toInt()
            }
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    fun setInitSize(initWidth: Int, initHeight: Int) {
        this.initWidth = initWidth
        this.initHeight = initHeight
    }
}