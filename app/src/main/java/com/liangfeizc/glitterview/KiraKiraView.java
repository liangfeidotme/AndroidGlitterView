package com.liangfeizc.glitterview;

/**
 * Created by rufi on 7/7/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * This view has an effect on the point
 * where a user is touching;
 */
public class KiraKiraView extends View {
    private final static String TAG = "KiraKraView";

    private List<Star> mStarList = new ArrayList<>();
    private PostDraw postDraw;

    public KiraKiraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setWillNotDraw(false);
        postDraw = new PostDraw();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                return false;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onTouchUp();
                return false;
        }
        return super.onTouchEvent(event);
    }

    public void onTouchMove(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        makeStar(x, y);

        for (int i = 0, n = event.getHistorySize(); i < n; i++) {
            x = event.getHistoricalX(i);
            y = event.getHistoricalY(i);
            makeStar(x, y);
        }

        invalidate();
    }

    public void onTouchUp() {
        postDraw.doInBackground(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.YELLOW);
        p.setStrokeWidth(1.5f);
        drawStars(canvas, p);
        animStars();
    }

    protected void drawStars(Canvas canvas, Paint p) {
        for (Star t : mStarList) {
            t.draw(canvas, p);
        }
    }

    protected void makeStar(float x, float y) {
        int f = (int) (Math.random() * 2) * 0;
        if (f == 0 && mStarList.size() < 200) {
            mStarList.add(new Star(x, y));
        }
    }

    protected boolean animStars() {
        boolean cont = true;
        if (mStarList.size() < 1) {
            // キラキラ終了
            cont = false;
        }

        for (int i = 0; i < mStarList.size(); i++) {
            Star t = mStarList.get(i);
            t.calc();
            if (t.isFinished()) {
                mStarList.remove(i);
                i--;
            }
        }
        return cont;
    }

    // キラキラエフェクト用
    protected class Star {
        float posX, posY;
        float vecX, vecY;
        float sz;
        int alpha;

        Star(float x, float y) {
            posX = x + (float) (Math.random() * 100) - 50;
            posY = y + (float) (Math.random() * 100) - 50;
            posY -= 75.f;

            vecX = (float) (Math.random() * 1) - 0.5f;
            vecY = (float) (Math.random() * 1) - 0.5f;
            sz = (float) (Math.random() * 35) + 10;

            alpha = 255;
        }

        public void draw(Canvas canvas, Paint p) {
            float sz1, sz2;
            p.setStrokeWidth(3.0f);
            p.setARGB(255, 244, 240, 0);
            sz1 = sz * alpha / 240;
            sz2 = sz1 * 2 / 3;
            canvas.drawLine(posX - sz2 / 2, posY, posX + sz2 / 2, posY, p);
            canvas.drawLine(posX, posY - sz1 / 2, posX, posY + sz1 / 2, p);
        }

        public void calc() {
            posX += vecX;
            posY += vecY;
            alpha -= 10;
        }

        public boolean isFinished() {
            return (alpha <= 0);
        }
    }

    class PostDraw extends AsyncTask<KiraKiraView, Void, Void> {

        @Override
        protected Void doInBackground(KiraKiraView... kiraKiraViews) {
            if (kiraKiraViews[0] == null) return null;

            KiraKiraView kiraKiraView = kiraKiraViews[0];
            while (kiraKiraView.animStars()) {
                try {
                    sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                kiraKiraView.invalidate();
            }
            ;
            return null;
        }
    }

}

