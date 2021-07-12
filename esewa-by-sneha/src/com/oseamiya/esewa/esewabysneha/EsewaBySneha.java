package com.oseamiya.esewa.esewabysneha;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.YailList;

import android.app.Activity;
import android.content.Context;


import com.oseamiya.esewa.esewabysneha.EsewaLib.ESewaConfiguration;
import com.oseamiya.esewa.esewabysneha.EsewaLib.ESewaPayment;
import com.oseamiya.esewa.esewabysneha.EsewaLib.ESewaPaymentActivity;

import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EsewaBySneha extends AndroidNonvisibleComponent {
  public  ESewaConfiguration eSewaConfiguration;
  private static ComponentContainer container;
  private Context context;
  private Activity activity;
  private String CONFIG_ENVIRONMENT ;

  public EsewaBySneha(ComponentContainer container) {
    super(container.$form());
    this.context = container.$context();
    this.activity = (Activity) container.$context();
    this.CONFIG_ENVIRONMENT =  ESewaConfiguration.ENVIRONMENT_TEST;
  }
  @SimpleProperty
  public void isTest(boolean value){
    if(value){
      this.CONFIG_ENVIRONMENT = ESewaConfiguration.ENVIRONMENT_TEST;
    } else{
      this.CONFIG_ENVIRONMENT = ESewaConfiguration.ENVIRONMENT_PRODUCTION;
    }
  }
  @SimpleFunction
  public void CreatePayment(String merchantId , String merchantSecretKey){
    eSewaConfiguration = new ESewaConfiguration()
            .clientId(merchantId)
            .secretKey(merchantSecretKey)
            .environment(this.CONFIG_ENVIRONMENT);
  }
  @SimpleFunction
  public void MakePayment(String amount , String productName , String uniqueId , String callBackUrl){
    ESewaPayment eSewaPayment = new ESewaPayment(amount , productName , uniqueId + System.nanoTime() , callBackUrl);
    Intent intent = new Intent(this.activity , ESewaPaymentActivity.class);
    intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION , eSewaConfiguration);
    intent.putExtra(ESewaPayment.ESEWA_PAYMENT , eSewaPayment);
    (this.activity).startActivityForResult(intent , 1);

  }
  @SimpleEvent
  public void OnPaymentSucess(String proof){
    EventDispatcher.dispatchEvent(this , "OnPaymentSucess" , new Object[]{proof});
  }
  @SimpleEvent
  public void OnPaymentCancelled(){
    EventDispatcher.dispatchEvent(this  , "OnPaymentCancelled" );
  }

  public void onActivityResult(int requestCode , int resultCode , Intent data){
    if(requestCode == 1){
      if(resultCode == RESULT_OK){
        String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
        OnPaymentSucess(s);
      } else if(resultCode == RESULT_CANCELED){
        OnPaymentCancelled();
      } else if(resultCode == ESewaPayment.RESULT_EXTRAS_INVALID){
        OnPaymentSucess(data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE));
      }
    }
  }
}
