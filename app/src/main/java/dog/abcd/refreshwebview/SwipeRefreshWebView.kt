package dog.abcd.refreshwebview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

class SwipeRefreshWebView : WebView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var refreshStateListener: ((Boolean) -> Unit)? = null
    private var preEnable = true

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        preEnable = preEnable && (scrollY == 0)
        refreshStateListener?.invoke(clampedY && preEnable)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            preEnable = true
            refreshStateListener?.invoke(false)
        }
        return super.onTouchEvent(event)
    }
}