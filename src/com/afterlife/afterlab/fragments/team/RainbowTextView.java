package com.afterlife.afterlab.fragments.team;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.DisplayUtils;
import com.hanks.htextview.base.HTextView;
import com.android.settings.R;
import android.graphics.Color;

/**
 * RainbowTextView
 * Created by hanks on 2017/3/14.
 */

public class RainbowTextView extends HTextView {

    private Matrix mMatrix;
    private float mTranslate;
    private float colorSpeed;
    private float colorSpace;
    private int[] colors = {getResources().getColor(R.color.text_white), getResources().getColor(R.color.text_black)};
    //private int[] colors = {R.color.text, R.color.text2, R.color.text3, R.color.text4, R.color.text5, R.color.text6};
    private LinearGradient mLinearGradient;

    public RainbowTextView(Context context) {
        this(context, null);
    }

    public RainbowTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainbowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @Override
    public void setAnimationListener(AnimationListener listener) {
        throw new UnsupportedOperationException("Invalid operation for rainbow");
    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RainbowTextView);
        colorSpace = typedArray.getDimension(R.styleable.RainbowTextView_colorSpace, DisplayUtils.dp2px(150));
        colorSpeed = typedArray.getDimension(R.styleable.RainbowTextView_colorSpeed, DisplayUtils.dp2px(5));
        typedArray.recycle();

        mMatrix = new Matrix();
        initPaint();
    }

    public float getColorSpace() {
        return colorSpace;
    }

    public void setColorSpace(float colorSpace) {
        this.colorSpace = colorSpace;
    }

    public float getColorSpeed() {
        return colorSpeed;
    }

    public void setColorSpeed(float colorSpeed) {
        this.colorSpeed = colorSpeed;
    }

    public void setColors(int... colors) {
        this.colors = colors;
        initPaint();
    }

    private void initPaint() {
        mLinearGradient = new LinearGradient(0, 0, colorSpace, 0, colors, null, Shader.TileMode.MIRROR);
        getPaint().setShader(mLinearGradient);
    }

    @Override
    public void setProgress(float progress) {
    }

    @Override
    public void animateText(CharSequence text) {
        setText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMatrix == null) {
            mMatrix = new Matrix();
        }
        mTranslate += colorSpeed;
        mMatrix.setTranslate(mTranslate, 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        super.onDraw(canvas);
        postInvalidateDelayed(30);
    }
}
