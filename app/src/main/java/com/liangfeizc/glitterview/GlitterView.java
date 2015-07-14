package com.liangfeizc.glitterview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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
    private static final int STARS_DISAPPEAR_TIME = 500;
    private static final float STAR_PAINT_STROKE_WITH = 1.5f;

    private int mStarColor;
    private int mMaxStarCount;
    private Paint mPaint;
    private List<Star> mStars;
    private ValueAnimator mStarsDisappearAnim;

    public GlitterView(Context context) {
        this(context, null);
    }

    public GlitterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlitterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        // init() must be called after initAttrs for we have to get the max star count which is
        // used as the capacity of the star list.
        init();
    }

    private void initAttrs(final Context context, final AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GlitterView);
        try {
            mStarColor = a.getColor(R.styleable.GlitterView_starColor, Color.RED);
            mMaxStarCount = a.getInteger(R.styleable.GlitterView_maxStarCount, MAX_STAR_COUNT);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mStarColor);
        mPaint.setStrokeWidth(STAR_PAINT_STROKE_WITH);

        mStars = new ArrayList<>(mMaxStarCount);

        // When users stop touching the screen, all the left stars drawn on the screen
        // must disappear in a smooth way, so I use a ValueAnimator to achieve this effect.
        mStarsDisappearAnim = ValueAnimator.ofFloat(.0f, 1.0f).setDuration(STARS_DISAPPEAR_TIME);
        mStarsDisappearAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        mStarsDisappearAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStars.clear();
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStars(canvas);
    }

    /**
     * Called when {@link android.view.MotionEvent} is ACTION_MOVE.
     * @param event motion event which carries touch coordinates.
     */
    public void onTouchMove(MotionEvent event) {
        makeStar(event.getX(), event.getY());

        // draw stars according to the history points.
        for (int i = 0, n = event.getHistorySize(); i < n; i++) {
            makeStar(event.getHistoricalX(i), event.getHistoricalY(i));
        }

        invalidate();
    }

    /**
     * Called when {@link android.view.MotionEvent} is ACTION_UP or ACTION_CANCEL.
     * @param event motion event
     */
    public void onTouchOver(MotionEvent event) {
        mStarsDisappearAnim.start();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                onTouchOver(event);
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
     * Star drawn on the view.
     */
    protected class Star {
        float x, y;
        float delta;
        int alpha;

        public Star(float x, float y) {
            this.x = x + (float) (Math.random() * 100) - 50;
            this.y = y + (float) (Math.random() * 100) - 50;
            alpha = (int) (Math.random() * 100) + 155;
            delta = (int) (Math.random() * 10) - 5;
        }

        public void draw(Canvas canvas, Paint paint) {
            paint.setAlpha(alpha);
            delta = delta * alpha / 200;
            canvas.drawLine(x - delta, y, x + delta, y, paint);
            canvas.drawLine(x, y - delta, x, y + delta, paint);
        }

        public boolean dim() {
            alpha -= 5;
            return alpha <= 0;
        }
    }
}
