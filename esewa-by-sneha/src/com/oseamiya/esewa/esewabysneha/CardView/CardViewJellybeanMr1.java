package com.oseamiya.esewa.esewabysneha.CardView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class CardViewJellybeanMr1 extends CardViewEclairMr1 {
    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper() {
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
                canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
            }
        };
    }
}

