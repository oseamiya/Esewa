package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.content.Context;
import android.graphics.Typeface;
// import android.support.v7.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

class CustomFontTextView extends AppCompatAutoCompleteTextView {
   public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
      super(context, attrs, defStyle);
      if (!this.isInEditMode()) {
         this.init();
      }

   }

   public CustomFontTextView(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
   }

   public CustomFontTextView(Context context) {
      this(context, (AttributeSet)null);
   }

   private void init() {
      Typeface tf = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/Tahoma.ttf");
      this.setTypeface(tf);
   }
}