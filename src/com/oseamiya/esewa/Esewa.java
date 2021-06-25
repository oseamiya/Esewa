package com.oseamiya.esewa;

import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.util.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.annotations.androidmanifest.*;
import com.google.appinventor.components.annotations.UsesActivities;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;

import com.esewa.android.sdk.payment.ESewaConfiguration;
import com.esewa.android.sdk.payment.ESewaPayment;
import com.esewa.android.sdk.payment.ESewaPaymentActivity;


@DesignerComponent(
    iconName = "",
    description = "",
    version = 1,
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    helpUrl = "https://github.com/oseamiya/"
)
@UsesLibraries(libraries = "esewaSdk.jar")
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.ACCESS_NETWORK_STATE")
@UsesActivities(activities = {
    @ActivityElement(name = "com.esewa.android.sdk.payment.ESewaPaymentConfirmActivity"),
    @ActivityElement(name = "com.esewa.android.sdk.payment.ESewaLoginActivity"),
    @ActivityElement(name = "com.esewa.android.sdk.payment.ESewaPaymentActivity")
    }
)
@SimpleObject(external = true)

public class Esewa extends AndroidNonvisibleComponent{
    private ComponentContainer container;
    private Context context;
    private Activity activity;
    private String CONFIG_ENVIRONMENT;
    private ESewaConfiguration esewaConfiguration;
    public Esewa(ComponentContainer container){
        super(container.$form());
        this.context = container.$context();
        this.activity = (Activity) container.$context();
        this.CONFIG_ENVIRONMENT = "live";
    }
    @SimpleProperty(description = "To set configuration environment to test")
    public void IsTest(boolean value){
        if(value){
            this.CONFIG_ENVIRONMENT = "test";
        }else{
            this.CONFIG_ENVIRONMENT = "live";
        }
    }

    @SimpleFunction(description = "Intialize the esewa pay with client id and product name")
    public void Init(String clientId , String clientSecret , String productName){
        if(this.CONFIG_ENVIRONMENT == "test"){
             esewaConfiguration = new ESewaConfiguration()
                               .clientId(clientId)
                               .secretKey(clientSecret)
                               .environment(ESewaConfiguration.ENVIRONMENT_TEST);
        }
    }
    @SimpleEvent
    public void OnProductCancelled(){
        EventDispatcher.dispatchEvent(this , "OnProductCancelled");
    }
    @SimpleEvent
    public void OnProductSuccessfull(String proof){
        EventDispatcher.dispatchEvent(this , "OnProductSuccessfull" , new Object[]{proof});
    }
    @SimpleEvent
    public void OnDataInvalid(String message){
        EventDispatcher.dispatchEvent(this , "OnDataInvalid" , new Object[]{message});
    }

    @SimpleFunction
    public void Create(String productPrice ,String productName ,String productId , String callBackUrl){
        ESewaPayment esewaPayment = new ESewaPayment(productPrice  , productName , productId , callBackUrl);
        Intent intent = new Intent(this.activity, ESewaPaymentActivity.class);
        intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION , esewaConfiguration);
        intent.putExtra(ESewaPayment.ESEWA_PAYMENT , esewaPayment);
        (this.activity).startActivityForResult(intent , 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == -1){ //the value of RESULT_OKAY is -1
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                OnProductSuccessfull(s);
               
            }else if(resultCode == 0){  //the value of RESULT_CANCELLED is 0
                OnProductCancelled();
            }else if(resultCode == ESewaPayment.RESULT_EXTRAS_INVALID){
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                OnDataInvalid(s);
            }
        }
    }
    



   
}
