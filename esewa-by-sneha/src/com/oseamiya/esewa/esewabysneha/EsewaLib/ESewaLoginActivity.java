package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
//import android.support.v7.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatImageView;

//import android.support.v7.widget.SwitchCompat;
import androidx.appcompat.widget.SwitchCompat;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/*
import com.android.esewa.esewasdk.R.id;
import com.android.esewa.esewasdk.R.layout;
import com.android.esewa.esewasdk.R.string;
*/
import java.nio.charset.Charset;

import com.oseamiya.esewa.esewabysneha.LoginActivityLayout;
import org.json.JSONException;
import org.json.JSONObject;

public class ESewaLoginActivity extends Activity implements AsyncResponseReturn<String> {
   private EditText editTextPassword;
   private EditText editTextUsername;
   private SwitchCompat rememberSwitch;
   private Button buttonSignIn;
   private Button buttonRegister;
   private AppCompatImageView buttonCancel;
   private JSONObject jsonObjectForProduct;
   private ESewaPayment eSewaPayment;
   private String merchantAuthToken = null;
   private long currentTimeOfTimer;
   private ProgressDialog progressDialog;
   private CountDownTimer countDownTimer;

   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //@oseamiya has fixed it to write esewa programatically
      this.setContentView(new LoginActivityLayout().getScrollView());
      this.initViews();
      if (savedInstanceState != null) {
         this.editTextUsername.setText(savedInstanceState.getString("userName"));
         this.editTextPassword.setText(savedInstanceState.getString("password"));
      }

      this.eSewaPayment = (ESewaPayment)this.getIntent().getParcelableExtra("com.esewa.android.sdk.payment");
      this.merchantAuthToken = this.getIntent().getStringExtra("merchantAuthToken");
      this.eSewaPayment.setMerchantAuthToken(this.merchantAuthToken);
      this.jsonObjectForProduct = new JSONObject();

      try {
         this.jsonObjectForProduct.put("environment", Encryptor.encryptString(this.eSewaPayment.getEnvironment()));
         this.jsonObjectForProduct.put("productName", Encryptor.encryptString(this.eSewaPayment.getProductName()));
         this.jsonObjectForProduct.put("totalAmount", Encryptor.encryptString(this.eSewaPayment.getAmount()));
      } catch (JSONException var3) {
         var3.printStackTrace();
      }

      this.buttonSignIn.setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
            AppUtil.hideKeyboard(ESewaLoginActivity.this);
            if (AppUtil.isInternetConnectionAvailable(ESewaLoginActivity.this, 0)) {
               if (!ESewaLoginActivity.this.merchantAuthToken.isEmpty()) {
                  if (ESewaLoginActivity.this.validateEditTexts()) {
                     HttpServerConnectorDto httpServerConnectorDtoForLogin = new HttpServerConnectorDto();
                     String var3 = ESewaLoginActivity.this.eSewaPayment.getEnvironment();
                     byte var4 = -1;
                     switch(var3.hashCode()) {
                     case 3322092:
                        if (var3.equals("live")) {
                           var4 = 0;
                        }
                        break;
                     case 3556498:
                        if (var3.equals("test")) {
                           var4 = 1;
                        }
                        break;
                     case 103145323:
                        if (var3.equals("local")) {
                           var4 = 2;
                        }
                     }

                     switch(var4) {
                     case 0:
                        httpServerConnectorDtoForLogin.setUrlToConnect("https://esewa.com.np/mobile/login/v1");
                        break;
                     case 1:
                        httpServerConnectorDtoForLogin.setUrlToConnect("https://uat.esewa.com.np/mobile/login/v1");
                        break;
                     case 2:
                        httpServerConnectorDtoForLogin.setUrlToConnect("http://10.13.208.83:8100/mobile/login/v1");
                     }

                     httpServerConnectorDtoForLogin.setAccessTokenHeader(ESewaLoginActivity.this.merchantAuthToken);
                     httpServerConnectorDtoForLogin.setAuthorizationHeader(ESewaLoginActivity.this.getB64Auth(ESewaLoginActivity.this.editTextUsername.getText().toString(), ESewaLoginActivity.this.editTextPassword.getText().toString()));
                     httpServerConnectorDtoForLogin.setJsonObject(ESewaLoginActivity.this.jsonObjectForProduct);
                     (new LoginController(ESewaLoginActivity.this, httpServerConnectorDtoForLogin, ESewaLoginActivity.this.eSewaPayment.getEnvironment())).execute(new String[0]);
                     ESewaLoginActivity.this.rememberEsewaID();
                  }
               } else {
                  ESewaLoginActivity.this.finish();
               }
            } else {
               AppUtil.showNoInternetDialogAndHideDialog(ESewaLoginActivity.this);
            }

         }
      });
      this.buttonCancel.setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
            ESewaLoginActivity.this.countDownTimer.cancel();
            ESewaLoginActivity.this.setResult(0);
            ESewaLoginActivity.this.finish();
         }
      });
      this.buttonRegister.setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
            ESewaLoginActivity.this.openAppOrPlaystore();
         }
      });
      this.finishIfSleep();
   }

   private void rememberEsewaID() {
      if (this.rememberSwitch.isChecked()) {
         SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         Editor editor = sharedPreferences.edit();
         editor.putString("eSewaId", this.editTextUsername.getText().toString());
         editor.apply();
      }

   }

   private void checkSavedEsewaId() {
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      if (sharedPreferences.contains("eSewaId") && sharedPreferences.getString("eSewaId", (String)null) != null) {
         this.editTextUsername.setText(sharedPreferences.getString("eSewaId", ""));
         this.editTextPassword.requestFocus();
      }

   }

   public void onBackPressed() {
      this.countDownTimer.cancel();
      this.setResult(0);
      this.finish();
   }

   private boolean validateEditTexts() {
      boolean isUserNameValid;
      if (!this.editTextUsername.getText().toString().isEmpty()) {
         if (!this.editTextUsername.getText().toString().startsWith("98") && !this.editTextUsername.getText().toString().startsWith("97")) {
            if (this.editTextUsername.getText().toString().matches(this.getResources().getString(0x7f140024))) {
               isUserNameValid = true;
            } else {
               isUserNameValid = false;
               this.editTextUsername.setError("eSewa Id must be a valid mobile number or email");
            }
         } else if (this.editTextUsername.getText().toString().matches("9[87]{1}[0-9]{8}")) {
            isUserNameValid = true;
         } else {
            isUserNameValid = false;
            this.editTextUsername.setError("eSewa Id must be a valid mobile number or email");
         }
      } else {
         isUserNameValid = false;
         this.editTextUsername.setError("Required");
      }

      boolean isPasswordValid;
      if (!this.editTextPassword.getText().toString().isEmpty()) {
         isPasswordValid = true;
      } else {
         isPasswordValid = false;
         this.editTextPassword.setError("Required");
      }

      return isUserNameValid && isPasswordValid;
   }

   protected void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      String username = this.editTextUsername.getText().toString();
      String password = this.editTextPassword.getText().toString();
      outState.putString("userName", username);
      outState.putString("password", password);
   }

   private void initViews() {
      // Added while modification of esewa classes by @oseamiya
      LoginActivityLayout loginActivityLayout = new LoginActivityLayout();

      this.editTextUsername = (EditText) loginActivityLayout.getAppCompatEditText();
      this.editTextPassword = (EditText) loginActivityLayout.getAppCompatEditText1();
      this.buttonSignIn = (Button) loginActivityLayout.getSignInButton();
      this.buttonCancel = (AppCompatImageView) loginActivityLayout.getAppCompatImageView1();
      this.buttonRegister = (Button) loginActivityLayout.getRegisterButton();
      this.rememberSwitch = (SwitchCompat) loginActivityLayout.getSwitchCompat();
      this.checkSavedEsewaId();
   }

   private void openAppOrPlaystore() {
      try {
         if (this.getPackageManager().getApplicationInfo("com.f1soft.esewa", 0).enabled) {
            Intent intent = this.getPackageManager().getLaunchIntentForPackage("com.f1soft.esewa");
            intent.putExtra("Start register through home page login", true);
            this.startActivity(intent);
         }
      } catch (NameNotFoundException var2) {
         this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.f1soft.esewa")));
      }

   }

   private String getB64Auth(String userName, String password) {
      String auth = userName + ":" + password;
      byte[] encodedAuth = org.apache.commons.codec.binary.Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
      return "Basic " + new String(encodedAuth);
   }

   public void onTaskStarted() {
      this.progressDialog = AppUtil.createProgressDialog(this, "Signing In ...");
      this.progressDialog.show();
   }

   private void finishIfSleep() {
      this.countDownTimer = (new CountDownTimer(300000L, 1000L) {
         public void onTick(long l) {
            ESewaLoginActivity.this.currentTimeOfTimer = l;
         }

         public void onFinish() {
            AppUtil.showTimeOutAlertAndDismiss(ESewaLoginActivity.this);
         }
      }).start();
   }

   public void onTaskFinished(String s) {
      if (this.progressDialog != null) {
         this.progressDialog.dismiss();
      }

      LogInResponseDto logInResponseDto = new LogInResponseDto();
      JSONObject jsonObject = null;
      if (s.equals("Invalid username or password")) {
         this.editTextPassword.setText("");
      } else {
         try {
            jsonObject = new JSONObject(s);
            if (jsonObject.has("identifier")) {
               logInResponseDto.setIdentifier(jsonObject.getString("identifier"));
            }

            if (jsonObject.has("environment")) {
               logInResponseDto.setEnvironment(HashEncryption.decryptString(jsonObject.getString("environment")));
            }

            if (jsonObject.has("merchantName")) {
               logInResponseDto.setMerchantName(HashEncryption.decryptString(jsonObject.getString("merchantName")));
            }

            if (jsonObject.has("userName")) {
               logInResponseDto.setUserName(HashEncryption.decryptString(jsonObject.getString("userName")));
            }

            if (jsonObject.has("balance")) {
               logInResponseDto.setBalance(HashEncryption.decryptString(jsonObject.getString("balance")));
            }

            if (jsonObject.has("productName")) {
               logInResponseDto.setProductName(HashEncryption.decryptString(jsonObject.getString("productName")));
            }

            if (jsonObject.has("totalAmount")) {
               logInResponseDto.setTotalAmount(HashEncryption.decryptString(jsonObject.getString("totalAmount")));
            }

            if (jsonObject.has("Cookie")) {
               logInResponseDto.setCookie(jsonObject.getString("Cookie"));
            }

            if (jsonObject.has("charge")) {
               logInResponseDto.setCharge(HashEncryption.decryptString(jsonObject.getString("charge")));
            }

            if (jsonObject.has("cashBack")) {
               logInResponseDto.setCashBack(HashEncryption.decryptString(jsonObject.getString("cashBack")));
            }

            if (this.editTextPassword.getText().toString().trim().matches(this.getResources().getString(0x7f140027))) {
               logInResponseDto.setOtpRequired(true);
            }

            logInResponseDto.setProductUniqueId(this.eSewaPayment.getProductUniqueId());
            logInResponseDto.setMerchantAuthenticationToken(this.eSewaPayment.getMerchantAuthToken());
            logInResponseDto.setCallbackUrl(this.eSewaPayment.getCallbackUrl());
            logInResponseDto.setCurrentTimeOfTimer(this.currentTimeOfTimer);
         } catch (JSONException var7) {
            var7.printStackTrace();
         }

         assert jsonObject != null;

         if (jsonObject.has("message")) {
            try {
               JSONObject jsonObjectMessage = jsonObject.getJSONObject("message");
               if (jsonObjectMessage.has("successMessage")) {
                  this.countDownTimer.cancel();
                  Intent intent = new Intent(this, ESewaPaymentConfirmActivity.class);
                  intent.putExtra("LOGIN_RESPONSE", logInResponseDto);
                  intent.setFlags(33554432);
                  this.startActivity(intent);
                  this.finish();
               } else if (jsonObjectMessage.has("errorMessage")) {
                  this.countDownTimer.cancel();
                  AppUtil.showErrorMessageAndFinish(this, jsonObject, false);
               } else {
                  this.countDownTimer.cancel();
                  this.setResult(0);
                  this.finish();
               }
            } catch (JSONException var6) {
               var6.printStackTrace();
            }
         }
      }

   }
}