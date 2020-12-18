package dog.abcd.refreshwebview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

public class SwipeRefreshWebView extends WebView {

    public interface RefreshStateListener {
        public void refreshState(boolean canRefresh);
    }

    private RefreshStateListener refreshStateListener;

    private boolean preEnable = true;

    public void setRefreshStateListener(RefreshStateListener refreshStateListener) {
        this.refreshStateListener = refreshStateListener;
    }

    public SwipeRefreshWebView(Context context) {
        super(context);
    }

    public SwipeRefreshWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRefreshWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SwipeRefreshWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        preEnable = preEnable && scrollY == 0;
        if (refreshStateListener != null) {
            refreshStateListener.refreshState(clampedY && preEnable);
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (refreshStateListener != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            preEnable = true;
            refreshStateListener.refreshState(false);
        }
        return super.onTouchEvent(event);
    }

}
