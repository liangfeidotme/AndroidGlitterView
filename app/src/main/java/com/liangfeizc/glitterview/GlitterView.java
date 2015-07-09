package com.liangfeizc.glitterview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Show bling bling stars when you touch or move on this view.
 *
 * Created by liangfeizc on 7/7/15.
 */
public class GlitterView extends View {
    public GlitterView(Context context) {
        this(context, null);
    }

    public GlitterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlitterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void onTouchMove(MotionEvent event) {

    }

    public void onTouchUp() {

    }

    public void onTouchCancel() {

    }


    /**
     * Star on the view.
     */
    protected class Star {
        int x, y;
        Paint paint;

        Star() {

        }

        void draw(Canvas canvas) {

        }
    }
}
