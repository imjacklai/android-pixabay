package tw.jacklai.pixabay

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet

/**
 * Created by jacklai on 28/03/2018.
 */

class SquareCardView : CardView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}