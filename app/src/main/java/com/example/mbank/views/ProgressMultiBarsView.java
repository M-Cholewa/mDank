package com.example.mbank.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.example.mbank.R;
import com.example.mbank.utils.UIUtils;


public class ProgressMultiBarsView extends View
        implements ValueAnimator.AnimatorUpdateListener {

    //consts
    private static final float SWEEP_ANGLE = 140;
    private static final int ARCS_COUNT = 6;

    //variables
    private ValueAnimator animator;
    private int[] colors;
    private int strokeWidth;
    long progress = 180; //max value is ARCS_COUNT * 360, when all arcs meet in starting point

    //draw objects
    private Paint arcPaint;

    public ProgressMultiBarsView(Context context) {
        super(context);
        init();
    }

    public ProgressMultiBarsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressMultiBarsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode())
            setWillNotDraw(true);
        arcPaint = new Paint();
        strokeWidth = UIUtils.dpToPx(1, getContext());

        arcPaint.setStrokeWidth(strokeWidth);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setAntiAlias(true);

        colors = new int[]{
                getColor(R.color.mBankGreen),
                getColor(R.color.mBankBlue),
                getColor(R.color.mBankRed),
                getColor(R.color.mBankYellow),
                getColor(R.color.mBankBlack),
                getColor(R.color.mBankDarkRed),
        };

        setupAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArcs(canvas, UIUtils.dpToPx(2, getContext()), 8);
        super.onDraw(canvas);
    }

    private void drawArcs(Canvas canvas, int space, int sweepLengthDiff) {
        for (int i = 0; i < ARCS_COUNT; i++) {
            arcPaint.setColor(colors[i]);
            float left = i * space + i * strokeWidth + strokeWidth / 2f;
            float top = left;
            float right = getWidth() - (i * space + i * strokeWidth) - strokeWidth / 2f;
            float bottom = getHeight() - (i * space + i * strokeWidth) - strokeWidth / 2f;
            float sweep = SWEEP_ANGLE - i * sweepLengthDiff;

            float arcSpace = ((float)i/(float)ARCS_COUNT)*progress;
            float startAngle = 180 + progress - arcSpace;

            canvas.drawArc(left, top, right, bottom, startAngle, sweep, false, arcPaint);
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        progress = (int)animation.getAnimatedValue();
        invalidate();
    }

    private void setupAnimator(){
        animator = ValueAnimator.ofInt(0, ARCS_COUNT*360);
        animator.setDuration(4500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(this);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        setWillNotDraw(false);
    }

    //getters
    private int getColor(int colorRes) {
        return getResources().getColor(colorRes, null);
    }
}
