package com.liangfeizc.glitterview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Show bling bling stars when you touch or move on this view.
 *
 * Created by liangfeizc on 7/7/15.
 */
public class GlitterView extends View {
    private static final String TAG = "GlitterView";

    private static final int MAX_STAR_COUNT = 100;

    private List<Star> mStars;
    private Paint mPaint;

    public GlitterView(Context context) {
        this(context, null);
    }

    public GlitterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlitterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(final AttributeSet attrs) {

    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(1.5f);
        mStars = new ArrayList<>(MAX_STAR_COUNT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStars(canvas);
    }


    public void onTouchMove(MotionEvent event) {
        makeStar(event.getRawX(), event.getRawY());

        for (int i = 0, n = event.getHistorySize(); i < n; i++) {
            makeStar(event.getHistoricalX(i), event.getHistoricalY(i));
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    protected void makeStar(float x, float y) {
        if (mStars.size() < MAX_STAR_COUNT) {
            mStars.add(new Star(x, y));
        }
    }

    protected void drawStars(Canvas canvas) {
        for (int i = 0; i < mStars.size(); i++) {
            Star star = mStars.get(i);
            star.draw(canvas, mPaint);
            if (star.dim()) {
                mStars.remove(i--);
            }
        }
    }

    /**
     * Star on the view.
     */
    protected class Star {
        float x, y;
        int alpha = 255;

        public Star(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Canvas canvas, Paint paint) {
            paint.setAlpha(alpha);
            canvas.drawLine(x - 5, y, x + 5, y, paint);
            canvas.drawLine(x, y - 5, x, y + 5, paint);
        }

        public boolean dim() {
            alpha -= 10;
            return alpha <= 0;
        }
    }
}
