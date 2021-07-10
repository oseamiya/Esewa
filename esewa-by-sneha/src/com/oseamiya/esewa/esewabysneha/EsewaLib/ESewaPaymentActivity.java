package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
// import com.android.esewa.esewasdk.R.layout;
import org.json.JSONException;
import org.json.JSONObject;

public class ESewaPaymentActivity extends Activity implements AsyncResponseReturn<String> {
   private ESewaPayment eSewaPayment;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // This class is modified by @oseamiya to support  MIT AppInventor Support
      ActivityEsewaPaymentLayout activityEsewaPaymentLayout = new ActivityEsewaPaymentLayout();
      this.setContentView(activityEsewaPaymentLayout.getRelativeLayout());
      ESewaConfiguration eSewaConfiguration = (ESewaConfiguration)this.getIntent().getParcelableExtra("com.esewa.android.sdk.config");
      this.eSewaPayment = (ESewaPayment)this.getIntent().getParcelableExtra("com.esewa.android.sdk.payment");
      this.eSewaPayment.setEnvironment(eSewaConfiguration.getEnvironment());
      String clientId = eSewaConfiguration.getClientId();
      String secretKey = eSewaConfiguration.getSecretKey();
      if (AppUtil.isInternetConnectionAvailable(this, 0)) {
         HttpServerConnectorDto httpServerConnectorDto = new HttpServerConnectorDto();
         if (eSewaConfiguration.getEnvironment().equalsIgnoreCase("test")) {
            httpServerConnectorDto.setUrlToConnect("https://uat.esewa.com.np/mobile/merchant");
         } else if (eSewaConfiguration.getEnvironment().equals("local")) {
            httpServerConnectorDto.setUrlToConnect("http://10.13.208.83:8100/mobile/merchant");
         } else if (eSewaConfiguration.getEnvironment().equalsIgnoreCase("live")) {
            httpServerConnectorDto.setUrlToConnect("https://esewa.com.np/mobile/merchant");
         } else {
            httpServerConnectorDto.setUrlToConnect("http://10.13.208.83:8100/mobile/merchant");
         }

         httpServerConnectorDto.setApplyTimeOut(true);
         httpServerConnectorDto.setClientIdHeader(Encryptor.encryptString(clientId));
         httpServerConnectorDto.setSecretKeyHeader(Encryptor.encryptString(secretKey));
         JSONObject jsonObjectForEnvironment = new JSONObject();

         try {
            jsonObjectForEnvironment.put("environment", Encryptor.encryptString(eSewaConfiguration.getEnvironment()));
         } catch (JSONException var8) {
            var8.printStackTrace();
         }

         httpServerConnectorDto.setJsonObject(jsonObjectForEnvironment);
         ClientAuthenticationController clientAuthenticationController = new ClientAuthenticationController(this, httpServerConnectorDto, this.eSewaPayment);
         if (httpServerConnectorDto.getUrlToConnect().equals("ABCD")) {
            AppUtil.showNoResponseMessageAndFinish(this, "Invalid Test Environment");
         } else {
            clientAuthenticationController.execute(new String[0]);
         }
      } else {
         AppUtil.showNoInternetDialogAndFinish(this);
      }

   }

   public void onTaskStarted() {
   }

   public void onTaskFinished(String s) {
      if (s.equals("Server error") | s.equals("eSewa Server Error") | s.equals("Server error") | s.equals("eSewa Server Error") | s.equals("time out")) {
         AppLog.showLog("verification response::::::::::" + s);
         AppUtil.showNoResponseMessageAndFinish(this, s);
      } else {
         try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("environment")) {
               AppLog.showLog("Environment:::::" + jsonObject.getString("environment"));
               this.eSewaPayment.setEnvironment(HashEncryption.decryptString(jsonObject.getString("environment")));
            }

            if (!jsonObject.getString("merchantAuthToken").equals("N/A")) {
               String merchantAuthToken = HashEncryption.decryptString(jsonObject.getString("merchantAuthToken"));
               AppLog.showLog("merchantAuthToken from Server:::::" + merchantAuthToken);
               Intent loginIntent = new Intent(this, ESewaLoginActivity.class);
               loginIntent.setFlags(33554432);
               loginIntent.putExtra("com.esewa.android.sdk.payment", this.eSewaPayment);
               loginIntent.putExtra("merchantAuthToken", jsonObject.getString("merchantAuthToken"));
               this.startActivity(loginIntent);
               this.finish();
            } else {
               AppUtil.showErrorMessageAndFinish(this, jsonObject, true);
            }
         } catch (JSONException var5) {
            var5.printStackTrace();
         }
      }

   }
}