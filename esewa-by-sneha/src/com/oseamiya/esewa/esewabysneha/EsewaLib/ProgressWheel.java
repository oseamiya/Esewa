package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Parcelable.Creator;
//import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
//import android.annotation.NonNull;
//import com.android.esewa.esewasdk.R.styleable;

class ProgressWheel extends View {
   private int circleRadius = 28;
   private int barWidth = 4;
   private int rimWidth = 4;
   private final int barLength = 16;
   private boolean fillRadius = false;
   private double timeStartGrowing = 0.0D;
   private double barSpinCycleTime = 460.0D;
   private float barExtraLength = 0.0F;
   private boolean barGrowingFromFront = true;
   private long pausedTimeWithoutGrowing = 0L;
   private int barColor = -1442840576;
   private int rimColor = 16777215;
   private Paint barPaint = new Paint();
   private Paint rimPaint = new Paint();
   private RectF circleBounds = new RectF();
   private float spinSpeed = 230.0F;
   private long lastTimeAnimated = 0L;
   private boolean linearProgress;
   private float mProgress = 0.0F;
   private float mTargetProgress = 0.0F;
   private boolean isSpinning = false;

   public ProgressWheel(Context context, AttributeSet attrs) {
      super(context, attrs);
      int[] arrayAttributes = new int[] {

      };
     // this.parseAttributes(context.obtainStyledAttributes(attrs, ""));
   }

   public ProgressWheel(Context context) {
      super(context);
   }

   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      int viewWidth = this.circleRadius + this.getPaddingLeft() + this.getPaddingRight();
      int viewHeight = this.circleRadius + this.getPaddingTop() + this.getPaddingBottom();
      int widthMode = MeasureSpec.getMode(widthMeasureSpec);
      int widthSize = MeasureSpec.getSize(widthMeasureSpec);
      int heightMode = MeasureSpec.getMode(heightMeasureSpec);
      int heightSize = MeasureSpec.getSize(heightMeasureSpec);
      int width;
      if (widthMode == 1073741824) {
         width = widthSize;
      } else if (widthMode == Integer.MIN_VALUE) {
         width = Math.min(viewWidth, widthSize);
      } else {
         width = viewWidth;
      }

      int height;
      if (heightMode != 1073741824 && widthMode != 1073741824) {
         if (heightMode == Integer.MIN_VALUE) {
            height = Math.min(viewHeight, heightSize);
         } else {
            height = viewHeight;
         }
      } else {
         height = heightSize;
      }

      this.setMeasuredDimension(width, height);
   }

   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
      super.onSizeChanged(w, h, oldw, oldh);
      this.setupBounds(w, h);
      this.setupPaints();
      this.invalidate();
   }

   private void setupPaints() {
      this.barPaint.setColor(this.barColor);
      this.barPaint.setAntiAlias(true);
      this.barPaint.setStyle(Style.STROKE);
      this.barPaint.setStrokeWidth((float)this.barWidth);
      this.rimPaint.setColor(this.rimColor);
      this.rimPaint.setAntiAlias(true);
      this.rimPaint.setStyle(Style.STROKE);
      this.rimPaint.setStrokeWidth((float)this.rimWidth);
   }

   private void setupBounds(int layout_width, int layout_height) {
      int paddingTop = this.getPaddingTop();
      int paddingBottom = this.getPaddingBottom();
      int paddingLeft = this.getPaddingLeft();
      int paddingRight = this.getPaddingRight();
      if (!this.fillRadius) {
         int minValue = Math.min(layout_width - paddingLeft - paddingRight, layout_height - paddingBottom - paddingTop);
         int circleDiameter = Math.min(minValue, this.circleRadius * 2 - this.barWidth * 2);
         int xOffset = (layout_width - paddingLeft - paddingRight - circleDiameter) / 2 + paddingLeft;
         int yOffset = (layout_height - paddingTop - paddingBottom - circleDiameter) / 2 + paddingTop;
         this.circleBounds = new RectF((float)(xOffset + this.barWidth), (float)(yOffset + this.barWidth), (float)(xOffset + circleDiameter - this.barWidth), (float)(yOffset + circleDiameter - this.barWidth));
      } else {
         this.circleBounds = new RectF((float)(paddingLeft + this.barWidth), (float)(paddingTop + this.barWidth), (float)(layout_width - paddingRight - this.barWidth), (float)(layout_height - paddingBottom - this.barWidth));
      }

   }
 /*
   private void parseAttributes(TypedArray a) {
      DisplayMetrics metrics = this.getContext().getResources().getDisplayMetrics();
      this.barWidth = (int)TypedValue.applyDimension(1, (float)this.barWidth, metrics);
      this.rimWidth = (int)TypedValue.applyDimension(1, (float)this.rimWidth, metrics);
      this.circleRadius = (int)TypedValue.applyDimension(1, (float)this.circleRadius, metrics);
      this.circleRadius = (int)a.getDimension(styleable.ProgressWheel_circleRadius, (float)this.circleRadius);
      this.fillRadius = a.getBoolean(styleable.ProgressWheel_fillRadius, false);
      this.barWidth = (int)a.getDimension(styleable.ProgressWheel_barWidth, (float)this.barWidth);
      this.rimWidth = (int)a.getDimension(styleable.ProgressWheel_rimWidth, (float)this.rimWidth);
      float baseSpinSpeed = a.getFloat(styleable.ProgressWheel_spinSpeed, this.spinSpeed / 360.0F);
      this.spinSpeed = baseSpinSpeed * 360.0F;
      this.barSpinCycleTime = (double)a.getInt(styleable.ProgressWheel_barSpinCycleTime, (int)this.barSpinCycleTime);
      this.barColor = a.getColor(styleable.ProgressWheel_barColor, this.barColor);
      this.rimColor = a.getColor(styleable.ProgressWheel_rimColor, this.rimColor);
      this.linearProgress = a.getBoolean(styleable.ProgressWheel_linearProgress, false);
      if (a.getBoolean(styleable.ProgressWheel_progressIndeterminate, false)) {
         this.spin();
      }



      a.recycle();
   }

  */

   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      canvas.drawArc(this.circleBounds, 360.0F, 360.0F, false, this.rimPaint);
      boolean mustInvalidate = false;
      float factor;
      if (this.isSpinning) {
         mustInvalidate = true;
         long deltaTime = SystemClock.uptimeMillis() - this.lastTimeAnimated;
         factor = (float)deltaTime * this.spinSpeed / 1000.0F;
         this.updateBarLength(deltaTime);
         this.mProgress += factor;
         if (this.mProgress > 360.0F) {
            this.mProgress -= 360.0F;
         }

         this.lastTimeAnimated = SystemClock.uptimeMillis();
         float from = this.mProgress - 90.0F;
         float length = 16.0F + this.barExtraLength;
         canvas.drawArc(this.circleBounds, from, length, false, this.barPaint);
      } else {
         float progress;
         float offset;
         if (this.mProgress != this.mTargetProgress) {
            mustInvalidate = true;
            offset = (float)(SystemClock.uptimeMillis() - this.lastTimeAnimated) / 1000.0F;
            progress = offset * this.spinSpeed;
            this.mProgress = Math.min(this.mProgress + progress, this.mTargetProgress);
            this.lastTimeAnimated = SystemClock.uptimeMillis();
         }

         offset = 0.0F;
         progress = this.mProgress;
         if (!this.linearProgress) {
            factor = 2.0F;
            offset = (float)(1.0D - Math.pow((double)(1.0F - this.mProgress / 360.0F), (double)(2.0F * factor))) * 360.0F;
            progress = (float)(1.0D - Math.pow((double)(1.0F - this.mProgress / 360.0F), (double)factor)) * 360.0F;
         }

         canvas.drawArc(this.circleBounds, offset - 90.0F, progress, false, this.barPaint);
      }

      if (mustInvalidate) {
         this.invalidate();
      }

   }

   private void updateBarLength(long deltaTimeInMilliSeconds) {
      long pauseGrowingTime = 200L;
      if (this.pausedTimeWithoutGrowing >= pauseGrowingTime) {
         this.timeStartGrowing += (double)deltaTimeInMilliSeconds;
         if (this.timeStartGrowing > this.barSpinCycleTime) {
            this.timeStartGrowing -= this.barSpinCycleTime;
            this.pausedTimeWithoutGrowing = 0L;
            this.barGrowingFromFront = !this.barGrowingFromFront;
         }

         float distance = (float)Math.cos((this.timeStartGrowing / this.barSpinCycleTime + 1.0D) * 3.141592653589793D) / 2.0F + 0.5F;
         int barMaxLength = 270;
         float destLength = (float)(barMaxLength - 16);
         if (this.barGrowingFromFront) {
            this.barExtraLength = distance * destLength;
         } else {
            float newLength = destLength * (1.0F - distance);
            this.mProgress += this.barExtraLength - newLength;
            this.barExtraLength = newLength;
         }
      } else {
         this.pausedTimeWithoutGrowing += deltaTimeInMilliSeconds;
      }

   }

   public void spin() {
      this.lastTimeAnimated = SystemClock.uptimeMillis();
      this.isSpinning = true;
      this.invalidate();
   }

   public void setProgress(float progress) {
      if (this.isSpinning) {
         this.mProgress = 0.0F;
         this.isSpinning = false;
      }

      if (progress > 1.0F) {
         --progress;
      } else if (progress < 0.0F) {
         progress = 0.0F;
      }

      if (progress != this.mTargetProgress) {
         if (this.mProgress == this.mTargetProgress) {
            this.lastTimeAnimated = SystemClock.uptimeMillis();
         }

         this.mTargetProgress = Math.min(progress * 360.0F, 360.0F);
         this.invalidate();
      }
   }

   public void setInstantProgress(float progress) {
      if (this.isSpinning) {
         this.mProgress = 0.0F;
         this.isSpinning = false;
      }

      if (progress > 1.0F) {
         --progress;
      } else if (progress < 0.0F) {
         progress = 0.0F;
      }

      if (progress != this.mTargetProgress) {
         this.mTargetProgress = Math.min(progress * 360.0F, 360.0F);
         this.mProgress = this.mTargetProgress;
         this.lastTimeAnimated = SystemClock.uptimeMillis();
         this.invalidate();
      }
   }

   public Parcelable onSaveInstanceState() {
      Parcelable superState = super.onSaveInstanceState();
      ProgressWheel.WheelSavedState ss = new ProgressWheel.WheelSavedState(superState);
      ss.mProgress = this.mProgress;
      ss.mTargetProgress = this.mTargetProgress;
      ss.isSpinning = this.isSpinning;
      ss.spinSpeed = this.spinSpeed;
      ss.barWidth = this.barWidth;
      ss.barColor = this.barColor;
      ss.rimWidth = this.rimWidth;
      ss.rimColor = this.rimColor;
      ss.circleRadius = this.circleRadius;
      ss.linearProgress = this.linearProgress;
      ss.fillRadius = this.fillRadius;
      return ss;
   }

   public void onRestoreInstanceState(Parcelable state) {
      if (!(state instanceof ProgressWheel.WheelSavedState)) {
         super.onRestoreInstanceState(state);
      } else {
         ProgressWheel.WheelSavedState ss = (ProgressWheel.WheelSavedState)state;
         super.onRestoreInstanceState(ss.getSuperState());
         this.mProgress = ss.mProgress;
         this.mTargetProgress = ss.mTargetProgress;
         this.isSpinning = ss.isSpinning;
         this.spinSpeed = ss.spinSpeed;
         this.barWidth = ss.barWidth;
         this.barColor = ss.barColor;
         this.rimWidth = ss.rimWidth;
         this.rimColor = ss.rimColor;
         this.circleRadius = ss.circleRadius;
         this.linearProgress = ss.linearProgress;
         this.fillRadius = ss.fillRadius;
      }
   }

   public float getProgress() {
      return this.isSpinning ? -1.0F : this.mProgress / 360.0F;
   }

   public void setLinearProgress(boolean isLinear) {
      this.linearProgress = isLinear;
      if (!this.isSpinning) {
         this.invalidate();
      }

   }

   public int getCircleRadius() {
      return this.circleRadius;
   }

   public void setCircleRadius(int circleRadius) {
      this.circleRadius = circleRadius;
      if (!this.isSpinning) {
         this.invalidate();
      }

   }

   public int getBarWidth() {
      return this.barWidth;
   }

   public void setBarWidth(int barWidth) {
      this.barWidth = barWidth;
      if (!this.isSpinning) {
         this.invalidate();
      }

   }

   public int getBarColor() {
      return this.barColor;
   }

   public void setBarColor(int barColor) {
      this.barColor = barColor;
      this.setupPaints();
      if (!this.isSpinning) {
         this.invalidate();
      }

   }

   public int getRimColor() {
      return this.rimColor;
   }

   public void setRimColor(int rimColor) {
      this.rimColor = rimColor;
      this.setupPaints();
      if (!this.isSpinning) {
         this.invalidate();
      }

   }

   public float getSpinSpeed() {
      return this.spinSpeed / 360.0F;
   }

   public void setSpinSpeed(float spinSpeed) {
      this.spinSpeed = spinSpeed * 360.0F;
   }

   public int getRimWidth() {
      return this.rimWidth;
   }

   public void setRimWidth(int rimWidth) {
      this.rimWidth = rimWidth;
      if (!this.isSpinning) {
         this.invalidate();
      }

   }

   static class WheelSavedState extends BaseSavedState {
      float mProgress;
      float mTargetProgress;
      boolean isSpinning;
      float spinSpeed;
      int barWidth;
      int barColor;
      int rimWidth;
      int rimColor;
      int circleRadius;
      boolean linearProgress;
      boolean fillRadius;
      public static final Creator<ProgressWheel.WheelSavedState> CREATOR = new Creator<ProgressWheel.WheelSavedState>() {
         public ProgressWheel.WheelSavedState createFromParcel(Parcel in) {
            return new ProgressWheel.WheelSavedState(in);
         }

         public ProgressWheel.WheelSavedState[] newArray(int size) {
            return new ProgressWheel.WheelSavedState[size];
         }
      };

      WheelSavedState(Parcelable superState) {
         super(superState);
      }

      private WheelSavedState(Parcel in) {
         super(in);
         this.mProgress = in.readFloat();
         this.mTargetProgress = in.readFloat();
         this.isSpinning = in.readByte() != 0;
         this.spinSpeed = in.readFloat();
         this.barWidth = in.readInt();
         this.barColor = in.readInt();
         this.rimWidth = in.readInt();
         this.rimColor = in.readInt();
         this.circleRadius = in.readInt();
         this.linearProgress = in.readByte() != 0;
         this.fillRadius = in.readByte() != 0;
      }

      public void writeToParcel(/*NonNull*/ Parcel out, int flags) {
         super.writeToParcel(out, flags);
         out.writeFloat(this.mProgress);
         out.writeFloat(this.mTargetProgress);
         out.writeByte((byte)(this.isSpinning ? 1 : 0));
         out.writeFloat(this.spinSpeed);
         out.writeInt(this.barWidth);
         out.writeInt(this.barColor);
         out.writeInt(this.rimWidth);
         out.writeInt(this.rimColor);
         out.writeInt(this.circleRadius);
         out.writeByte((byte)(this.linearProgress ? 1 : 0));
         out.writeByte((byte)(this.fillRadius ? 1 : 0));
      }

      // $FF: synthetic method
      WheelSavedState(Parcel x0, Object x1) {
         this(x0);
      }
   }
}