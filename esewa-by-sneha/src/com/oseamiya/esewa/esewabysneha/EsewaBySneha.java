package com.oseamiya.esewa.esewabysneha;

import android.graphics.drawable.Drawable;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.YailList;

import android.app.Activity;
import android.content.Context;
import androidx.cardview.widget.CardView;

import com.oseamiya.esewa.esewabysneha.EsewaLib.ESewaConfiguration;

import java.io.IOException;

public class EsewaBySneha extends AndroidNonvisibleComponent {
  private String CONFIG_ENVIRONMENT;
  public  ESewaConfiguration eSewaConfiguration;
  private static ComponentContainer container;
  private Context context;
  private Activity activity;
  private Drawable esewaLogoSetDrawable;

  public EsewaBySneha(ComponentContainer container) {
    super(container.$form());
    this.context = container.$context();
    this.activity = (Activity) container.$context();
  }

  public static Drawable EsewaLogoSetDrawable(){
    try {
      return MediaUtil.getBitmapDrawable(container.$form() , "esewa_logo_set");
    } catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

}
