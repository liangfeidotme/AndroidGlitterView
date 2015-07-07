package com.liangfeizc.glitterview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by rufi on 7/7/15.
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
}
