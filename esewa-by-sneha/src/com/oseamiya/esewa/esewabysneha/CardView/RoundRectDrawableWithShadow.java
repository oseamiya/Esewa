package com.oseamiya.esewa.esewabysneha.CardView;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.Path.FillType;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
/*
import android.support.v7.cardview.R.color;
import android.support.v7.cardview.R.dimen;

 */

class RoundRectDrawableWithShadow extends Drawable {
    static final double COS_45 = Math.cos(Math.toRadians(45.0D));
    static final float SHADOW_MULTIPLIER = 1.5F;
    final int mInsetShadow;
    static RoundRectDrawableWithShadow.RoundRectHelper sRoundRectHelper;
    Paint mPaint;
    Paint mCornerShadowPaint;
    Paint mEdgeShadowPaint;
    final RectF mCardBounds;
    float mCornerRadius;
    Path mCornerShadowPath;
    float mMaxShadowSize;
    float mRawMaxShadowSize;
    float mShadowSize;
    float mRawShadowSize;
    private boolean mDirty = true;
    private final int mShadowStartColor;
    private final int mShadowEndColor;
    private boolean mAddPaddingForCorners = true;
    private boolean mPrintedShadowClipWarning = false;

    RoundRectDrawableWithShadow(Resources resources, int backgroundColor, float radius, float shadowSize, float maxShadowSize) {
        this.mShadowStartColor = resources.getColor(0x7f020003);
        this.mShadowEndColor = resources.getColor(0x7f020002);
        this.mInsetShadow = resources.getDimensionPixelSize(0x7f030000);
        this.mPaint = new Paint(5);
        this.mPaint.setColor(backgroundColor);
        this.mCornerShadowPaint = new Paint(5);
        this.mCornerShadowPaint.setStyle(Style.FILL);
        this.mCornerRadius = (float)((int)(radius + 0.5F));
        this.mCardBounds = new RectF();
        this.mEdgeShadowPaint = new Paint(this.mCornerShadowPaint);
        this.mEdgeShadowPaint.setAntiAlias(false);
        this.setShadowSize(shadowSize, maxShadowSize);
    }

    private int toEven(float value) {
        int i = (int)(value + 0.5F);
        return i % 2 == 1 ? i - 1 : i;
    }

    public void setAddPaddingForCorners(boolean addPaddingForCorners) {
        this.mAddPaddingForCorners = addPaddingForCorners;
        this.invalidateSelf();
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
        this.mCornerShadowPaint.setAlpha(alpha);
        this.mEdgeShadowPaint.setAlpha(alpha);
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mDirty = true;
    }

    void setShadowSize(float shadowSize, float maxShadowSize) {
        if (!(shadowSize < 0.0F) && !(maxShadowSize < 0.0F)) {
            shadowSize = (float)this.toEven(shadowSize);
            maxShadowSize = (float)this.toEven(maxShadowSize);
            if (shadowSize > maxShadowSize) {
                shadowSize = maxShadowSize;
                if (!this.mPrintedShadowClipWarning) {
                    this.mPrintedShadowClipWarning = true;
                }
            }

            if (this.mRawShadowSize != shadowSize || this.mRawMaxShadowSize != maxShadowSize) {
                this.mRawShadowSize = shadowSize;
                this.mRawMaxShadowSize = maxShadowSize;
                this.mShadowSize = (float)((int)(shadowSize * 1.5F + (float)this.mInsetShadow + 0.5F));
                this.mMaxShadowSize = maxShadowSize + (float)this.mInsetShadow;
                this.mDirty = true;
                this.invalidateSelf();
            }
        } else {
            throw new IllegalArgumentException("invalid shadow size");
        }
    }

    public boolean getPadding(Rect padding) {
        int vOffset = (int)Math.ceil((double)calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        int hOffset = (int)Math.ceil((double)calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        padding.set(hOffset, vOffset, hOffset, vOffset);
        return true;
    }

    static float calculateVerticalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners) {
        return addPaddingForCorners ? (float)((double)(maxShadowSize * 1.5F) + (1.0D - COS_45) * (double)cornerRadius) : maxShadowSize * 1.5F;
    }

    static float calculateHorizontalPadding(float maxShadowSize, float cornerRadius, boolean addPaddingForCorners) {
        return addPaddingForCorners ? (float)((double)maxShadowSize + (1.0D - COS_45) * (double)cornerRadius) : maxShadowSize;
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
        this.mCornerShadowPaint.setColorFilter(cf);
        this.mEdgeShadowPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }

    void setCornerRadius(float radius) {
        radius = (float)((int)(radius + 0.5F));
        if (this.mCornerRadius != radius) {
            this.mCornerRadius = radius;
            this.mDirty = true;
            this.invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        if (this.mDirty) {
            this.buildComponents(this.getBounds());
            this.mDirty = false;
        }

        canvas.translate(0.0F, this.mRawShadowSize / 2.0F);
        this.drawShadow(canvas);
        canvas.translate(0.0F, -this.mRawShadowSize / 2.0F);
        sRoundRectHelper.drawRoundRect(canvas, this.mCardBounds, this.mCornerRadius, this.mPaint);
    }

    private void drawShadow(Canvas canvas) {
        float edgeShadowTop = -this.mCornerRadius - this.mShadowSize;
        float inset = this.mCornerRadius + (float)this.mInsetShadow + this.mRawShadowSize / 2.0F;
        boolean drawHorizontalEdges = this.mCardBounds.width() - 2.0F * inset > 0.0F;
        boolean drawVerticalEdges = this.mCardBounds.height() - 2.0F * inset > 0.0F;
        int saved = canvas.save();
        canvas.translate(this.mCardBounds.left + inset, this.mCardBounds.top + inset);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0.0F, edgeShadowTop, this.mCardBounds.width() - 2.0F * inset, -this.mCornerRadius, this.mEdgeShadowPaint);
        }

        canvas.restoreToCount(saved);
        saved = canvas.save();
        canvas.translate(this.mCardBounds.right - inset, this.mCardBounds.bottom - inset);
        canvas.rotate(180.0F);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0.0F, edgeShadowTop, this.mCardBounds.width() - 2.0F * inset, -this.mCornerRadius + this.mShadowSize, this.mEdgeShadowPaint);
        }

        canvas.restoreToCount(saved);
        saved = canvas.save();
        canvas.translate(this.mCardBounds.left + inset, this.mCardBounds.bottom - inset);
        canvas.rotate(270.0F);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0.0F, edgeShadowTop, this.mCardBounds.height() - 2.0F * inset, -this.mCornerRadius, this.mEdgeShadowPaint);
        }

        canvas.restoreToCount(saved);
        saved = canvas.save();
        canvas.translate(this.mCardBounds.right - inset, this.mCardBounds.top + inset);
        canvas.rotate(90.0F);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0.0F, edgeShadowTop, this.mCardBounds.height() - 2.0F * inset, -this.mCornerRadius, this.mEdgeShadowPaint);
        }

        canvas.restoreToCount(saved);
    }

    private void buildShadowCorners() {
        RectF innerBounds = new RectF(-this.mCornerRadius, -this.mCornerRadius, this.mCornerRadius, this.mCornerRadius);
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-this.mShadowSize, -this.mShadowSize);
        if (this.mCornerShadowPath == null) {
            this.mCornerShadowPath = new Path();
        } else {
            this.mCornerShadowPath.reset();
        }

        this.mCornerShadowPath.setFillType(FillType.EVEN_ODD);
        this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0F);
        this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0F);
        this.mCornerShadowPath.arcTo(outerBounds, 180.0F, 90.0F, false);
        this.mCornerShadowPath.arcTo(innerBounds, 270.0F, -90.0F, false);
        this.mCornerShadowPath.close();
        float startRatio = this.mCornerRadius / (this.mCornerRadius + this.mShadowSize);
        this.mCornerShadowPaint.setShader(new RadialGradient(0.0F, 0.0F, this.mCornerRadius + this.mShadowSize, new int[]{this.mShadowStartColor, this.mShadowStartColor, this.mShadowEndColor}, new float[]{0.0F, startRatio, 1.0F}, TileMode.CLAMP));
        this.mEdgeShadowPaint.setShader(new LinearGradient(0.0F, -this.mCornerRadius + this.mShadowSize, 0.0F, -this.mCornerRadius - this.mShadowSize, new int[]{this.mShadowStartColor, this.mShadowStartColor, this.mShadowEndColor}, new float[]{0.0F, 0.5F, 1.0F}, TileMode.CLAMP));
        this.mEdgeShadowPaint.setAntiAlias(false);
    }

    private void buildComponents(Rect bounds) {
        float verticalOffset = this.mRawMaxShadowSize * 1.5F;
        this.mCardBounds.set((float)bounds.left + this.mRawMaxShadowSize, (float)bounds.top + verticalOffset, (float)bounds.right - this.mRawMaxShadowSize, (float)bounds.bottom - verticalOffset);
        this.buildShadowCorners();
    }

    float getCornerRadius() {
        return this.mCornerRadius;
    }

    void getMaxShadowAndCornerPadding(Rect into) {
        this.getPadding(into);
    }

    void setShadowSize(float size) {
        this.setShadowSize(size, this.mRawMaxShadowSize);
    }

    void setMaxShadowSize(float size) {
        this.setShadowSize(this.mRawShadowSize, size);
    }

    float getShadowSize() {
        return this.mRawShadowSize;
    }

    float getMaxShadowSize() {
        return this.mRawMaxShadowSize;
    }

    float getMinWidth() {
        float content = 2.0F * Math.max(this.mRawMaxShadowSize, this.mCornerRadius + (float)this.mInsetShadow + this.mRawMaxShadowSize / 2.0F);
        return content + (this.mRawMaxShadowSize + (float)this.mInsetShadow) * 2.0F;
    }

    float getMinHeight() {
        float content = 2.0F * Math.max(this.mRawMaxShadowSize, this.mCornerRadius + (float)this.mInsetShadow + this.mRawMaxShadowSize * 1.5F / 2.0F);
        return content + (this.mRawMaxShadowSize * 1.5F + (float)this.mInsetShadow) * 2.0F;
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
        this.invalidateSelf();
    }

    interface RoundRectHelper {
        void drawRoundRect(Canvas var1, RectF var2, float var3, Paint var4);
    }
}

