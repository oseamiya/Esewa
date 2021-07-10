package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.google.appinventor.components.runtime.ComponentContainer;

public class ProgressDialogSdkLayout {
    private ComponentContainer container;
    private Context context;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;

    public void CreateLayout(){
         relativeLayout = new RelativeLayout(container.$context());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2 , -2);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.setBackgroundColor(Color.TRANSPARENT);


        //Now creating progress bar and adding the progress bar inside of Relativelayout

         progressBar = new ProgressBar(container.$context());
        View progressBarView = (View) progressBar;
        progressBarView.setLayoutParams(new ViewGroup.LayoutParams(dptopx(30) , dptopx(30)));

        relativeLayout.addView(progressBarView);

    }
    public int  dptopx(int dp){
        float value = dp * context.getResources().getDisplayMetrics().density;
        return Math.round(value);
    }
    public void setRelativeLayout(){
        //
    }
    public RelativeLayout getRelativeLayout(){
        return relativeLayout;
    }
    public void setProgressBar(){
        //
    }
    public ProgressBar getProgressBar(){
        return progressBar;
    }
}
