package com.oseamiya.esewa.esewabysneha.CardView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build.VERSION;
/*
import android.support.v7.cardview.R.style;
import android.support.v7.cardview.R.styleable;

 */
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;

public class CardView extends FrameLayout implements CardViewDelegate {
    private static final CardViewImpl IMPL;
    private boolean mCompatPadding;
    private boolean mPreventCornerOverlap;
    private final Rect mContentPadding = new Rect();
    private final Rect mShadowBounds = new Rect();

    public CardView(Context context) {
        super(context);
        this.initialize(context, (AttributeSet)null, 0);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize(context, attrs, defStyleAttr);
    }

    public void setPadding(int left, int top, int right, int bottom) {
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
    }

    public boolean getUseCompatPadding() {
        return this.mCompatPadding;
    }

    public void setUseCompatPadding(boolean useCompatPadding) {
        if (this.mCompatPadding != useCompatPadding) {
            this.mCompatPadding = useCompatPadding;
            IMPL.onCompatPaddingChanged(this);
        }
    }

    public void setContentPadding(int left, int top, int right, int bottom) {
        this.mContentPadding.set(left, top, right, bottom);
        IMPL.updatePadding(this);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!(IMPL instanceof CardViewApi21)) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode;
            switch(widthMode) {
                case Integer.MIN_VALUE:
                case 1073741824:
                    heightMode = (int)Math.ceil((double)IMPL.getMinWidth(this));
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(heightMode, MeasureSpec.getSize(widthMeasureSpec)), widthMode);
                default:
                    heightMode = MeasureSpec.getMode(heightMeasureSpec);
                    switch(heightMode) {
                        case Integer.MIN_VALUE:
                        case 1073741824:
                            int minHeight = (int)Math.ceil((double)IMPL.getMinHeight(this));
                            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(minHeight, MeasureSpec.getSize(heightMeasureSpec)), heightMode);
                        default:
                            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    }
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        int[] styleableCardView = { 0x7f010000, 0x7f010001, 0x7f010002, 0x7f010003, 0x7f010004, 0x7f010005, 0x7f010006, 0x7f010007, 0x7f010008, 0x7f010009, 0x7f01000a };
        TypedArray a = context.obtainStyledAttributes(attrs, styleableCardView, defStyleAttr, 0x7f040002);
        int backgroundColor = a.getColor(0, 0);
        float radius = a.getDimension(1, 0.0F);
        float elevation = a.getDimension(2, 0.0F);
        float maxElevation = a.getDimension(3, 0.0F);
        this.mCompatPadding = a.getBoolean(4, false);
        this.mPreventCornerOverlap = a.getBoolean(5, true);
        int defaultPadding = a.getDimensionPixelSize(6, 0);
        this.mContentPadding.left = a.getDimensionPixelSize(7, defaultPadding);
        this.mContentPadding.top = a.getDimensionPixelSize(9, defaultPadding);
        this.mContentPadding.right = a.getDimensionPixelSize(8, defaultPadding);
        this.mContentPadding.bottom = a.getDimensionPixelSize(10, defaultPadding);
        if (elevation > maxElevation) {
            maxElevation = elevation;
        }

        a.recycle();
        IMPL.initialize(this, context, backgroundColor, radius, elevation, maxElevation);
    }

    public void setCardBackgroundColor(int color) {
        IMPL.setBackgroundColor(this, color);
    }

    public int getContentPaddingLeft() {
        return this.mContentPadding.left;
    }

    public int getContentPaddingRight() {
        return this.mContentPadding.right;
    }

    public int getContentPaddingTop() {
        return this.mContentPadding.top;
    }

    public int getContentPaddingBottom() {
        return this.mContentPadding.bottom;
    }

    public void setRadius(float radius) {
        IMPL.setRadius(this, radius);
    }

    public float getRadius() {
        return IMPL.getRadius(this);
    }

    public void setShadowPadding(int left, int top, int right, int bottom) {
        this.mShadowBounds.set(left, top, right, bottom);
        super.setPadding(left + this.mContentPadding.left, top + this.mContentPadding.top, right + this.mContentPadding.right, bottom + this.mContentPadding.bottom);
    }

    public void setCardElevation(float radius) {
        IMPL.setElevation(this, radius);
    }

    public float getCardElevation() {
        return IMPL.getElevation(this);
    }

    public void setMaxCardElevation(float radius) {
        IMPL.setMaxElevation(this, radius);
    }

    public float getMaxCardElevation() {
        return IMPL.getMaxElevation(this);
    }

    public boolean getPreventCornerOverlap() {
        return this.mPreventCornerOverlap;
    }

    public void setPreventCornerOverlap(boolean preventCornerOverlap) {
        if (preventCornerOverlap != this.mPreventCornerOverlap) {
            this.mPreventCornerOverlap = preventCornerOverlap;
            IMPL.onPreventCornerOverlapChanged(this);
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new CardViewApi21();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new CardViewJellybeanMr1();
        } else {
            IMPL = new CardViewEclairMr1();
        }

        IMPL.initStatic();
    }
}
