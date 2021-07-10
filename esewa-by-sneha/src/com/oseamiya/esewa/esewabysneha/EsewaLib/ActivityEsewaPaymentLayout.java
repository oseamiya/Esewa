package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.MediaUtil;

import java.io.IOException;

public class ActivityEsewaPaymentLayout {
    private ComponentContainer container;
    private Context context;
    private RelativeLayout relativeLayout;

    public void CreateLayout() throws IOException {
        relativeLayout = new RelativeLayout(container.$context());
        relativeLayout.setBackgroundColor(Color.parseColor("#61bc47"));


        //Create AppCompatImageView
        AppCompatImageView appCompatImageView = new AppCompatImageView(container.$context());
        appCompatImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        View appCompatImageViewinView = (View) appCompatImageView;
        RelativeLayout.LayoutParams layoutparamsforappCompatImageView = new RelativeLayout.LayoutParams(-1, dpttopx(80));
        layoutparamsforappCompatImageView.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        appCompatImageView.setLayoutParams(layoutparamsforappCompatImageView);
        appCompatImageView.setBackgroundDrawable(MediaUtil.getBitmapDrawable(container.$form() , "img_background_splash_footer"));

        relativeLayout.addView(appCompatImageViewinView);

        //Again creating  the app compat image view so , this is image icon
        AppCompatImageView appCompatImageView1 = new AppCompatImageView(container.$context());
        View appCompatImageView1InView =  (View) appCompatImageView1;
        //appCompatImageView1InView.setLayoutParams(new ViewGroup.LayoutParams(dpttopx(170) , dpttopx(128)));
        RelativeLayout.LayoutParams layoutParamsForAppCompatimageView1 = new RelativeLayout.LayoutParams(dpttopx(170), dpttopx(128));
        layoutParamsForAppCompatimageView1.addRule(RelativeLayout.ALIGN_LEFT);
        appCompatImageView1InView.setLayoutParams(layoutParamsForAppCompatimageView1);

        relativeLayout.addView(appCompatImageView1InView);

        //Now for appcompattextview  , this show the verison of the app , can be  negleted also
        AppCompatTextView appCompatTextView = new AppCompatTextView(container.$context());
        appCompatTextView.setGravity(Gravity.CENTER);
        appCompatTextView.setText(0x7f140023);
        relativeLayout.addView(appCompatTextView);

        //Again creating the progress bar
        ProgressBar progressBar = new ProgressBar(container.$context());
        View progressBarInview = (View) progressBar;
        progressBarInview.setLayoutParams(new ViewGroup.LayoutParams(dpttopx(20) , dpttopx(20)));

        relativeLayout.addView(progressBarInview);



    }
    public int dpttopx(int dp){
        float value = context.getResources().getDisplayMetrics().density * dp;
        return Math.round(value);
    }
    public void setRelativeLayout(){
        //
    }
    public RelativeLayout getRelativeLayout(){
        return relativeLayout;
    }
}
