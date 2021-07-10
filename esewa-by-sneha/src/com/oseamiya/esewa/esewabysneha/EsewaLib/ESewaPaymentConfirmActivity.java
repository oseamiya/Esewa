package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.widget.AppCompatImageView;;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.oseamiya.esewa.esewabysneha.CardView.CardView;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

//import androidx.appcompat.widget.AppCompatImageView;
//import androidx.widget.CardView;
/*
import com.android.esewa.esewasdk.R.id;
import com.android.esewa.esewasdk.R.layout;

 */


public class ESewaPaymentConfirmActivity extends Activity implements OnClickListener, AsyncResponseReturn<String> {
   private Button buttonPay;
   private Button buttonCancel;
   private TextView textViewUserName;
   private TextView textViewBalance;
   private TextView textViewMerchantName;
   private TextView textViewProductName;
   private TextView textViewTotalAmount;
   private TextView textViewCashBack;
   private TextView textViewCharge;
   private TextView commissionViewTotalAmt;
   private EditText editTextOtp;
   private CardView commissionView;
   private LinearLayout cashBackLL;
   private LinearLayout chargeLL;
   private LinearLayout otpLL;
   private LogInResponseDto logInResponseDto;
   private CountDownTimer countDownTimer;
   private long timeRemainForSleep;
   private ProgressDialog progressDialog;
   private AppCompatImageView backButton;
   Double totalAmount;

   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      //This class is modified by @oseamiya so that esewa sdk will work on MIT APP INVENTOR
      LayoutPaymentConfirmation layoutPaymentConfirmation = new LayoutPaymentConfirmation();
      this.setContentView(layoutPaymentConfirmation.getLinearLayout());
      this.initViews();
      this.logInResponseDto = (LogInResponseDto)this.getIntent().getParcelableExtra("LOGIN_RESPONSE");
      if (this.logInResponseDto.isOtpRequired()) {
         this.otpLL.setVisibility(0);
      }

      AppLog.showLog("Current time in timer: " + this.logInResponseDto.getCurrentTimeOfTimer());
      this.timeRemainForSleep = this.logInResponseDto.getCurrentTimeOfTimer();
      if (this.logInResponseDto != null) {
         this.textViewUserName.setText(this.logInResponseDto.getUserName());
         this.textViewMerchantName.setText(this.logInResponseDto.getMerchantName());
         this.textViewBalance.setText(this.logInResponseDto.getBalance());
         this.textViewProductName.setText(this.logInResponseDto.getProductName());
         this.textViewTotalAmount.setText(this.logInResponseDto.getTotalAmount());
         this.totalAmount = Double.valueOf(this.logInResponseDto.getTotalAmount());
         NumberFormat formatter = new DecimalFormat("#0.00");
         if (Double.valueOf(this.logInResponseDto.getCashBack()) > 0.0D) {
            this.commissionView.setVisibility(0);
            this.cashBackLL.setVisibility(0);
            this.textViewCashBack.setText(this.logInResponseDto.getCashBack());
            this.totalAmount = this.totalAmount - Double.valueOf(this.logInResponseDto.getCashBack());
            this.commissionViewTotalAmt.setText(formatter.format(this.totalAmount));
         }

         if (Double.valueOf(this.logInResponseDto.getCharge()) > 0.0D) {
            this.commissionView.setVisibility(0);
            this.chargeLL.setVisibility(0);
            this.textViewCharge.setText(this.logInResponseDto.getCharge());
            this.totalAmount = this.totalAmount + Double.valueOf(this.logInResponseDto.getCharge());
            this.commissionViewTotalAmt.setText(formatter.format(this.totalAmount));
         }
      }

      this.finishIfSleep();
      this.buttonPay.setOnClickListener(this);
      this.buttonCancel.setOnClickListener(this);
      this.backButton.setOnClickListener(this);
   }

   private void finishIfSleep() {
      this.countDownTimer = (new CountDownTimer(this.timeRemainForSleep, 1000L) {
         public void onTick(long l) {
         }

         public void onFinish() {
            AppUtil.showTimeOutAlertAndDismiss(ESewaPaymentConfirmActivity.this);
         }
      }).start();
   }

   public void initViews() {
      LayoutPaymentConfirmation layoutPaymentConfirmation = new LayoutPaymentConfirmation();

      this.buttonCancel = (Button) layoutPaymentConfirmation.getButtonCancel();
      this.buttonPay = (Button) layoutPaymentConfirmation.getButtonPay();
      this.textViewUserName = (TextView) layoutPaymentConfirmation.getNameOfPerson();
      this.textViewBalance = (TextView) layoutPaymentConfirmation.getTextViewBalance();
      this.textViewMerchantName = (TextView) layoutPaymentConfirmation.getTextViewMerchantName();
      this.textViewProductName = (TextView) layoutPaymentConfirmation.getTextViewProductName();
      this.backButton = (AppCompatImageView) layoutPaymentConfirmation.getBackButton();
      this.textViewTotalAmount = (TextView) layoutPaymentConfirmation.getTotalAmount();
      this.editTextOtp = (EditText)  layoutPaymentConfirmation.getEditTextOtp();
      this.otpLL = (LinearLayout) layoutPaymentConfirmation.getOtpPLL();
      this.commissionView = (CardView) layoutPaymentConfirmation.getComissionView();
      this.cashBackLL = (LinearLayout) layoutPaymentConfirmation.getCashBackLL();
      this.chargeLL = (LinearLayout) layoutPaymentConfirmation.getChargeLL();
      this.textViewCashBack = (TextView) layoutPaymentConfirmation.getTextViewCashBack();
      this.textViewCharge = (TextView) layoutPaymentConfirmation.getTextViewCharge();
      this.commissionViewTotalAmt = (TextView) layoutPaymentConfirmation.getComissionViewtotalPaymentAmount();
   }

   public void onClick(View view) {
      if (view == (View) this.buttonPay) {
         this.countDownTimer.cancel();
         if (AppUtil.isInternetConnectionAvailable(this, 0)) {
            this.setButtonClickable(false);
         }

         if (Double.parseDouble(this.logInResponseDto.getBalance()) < Double.parseDouble(this.logInResponseDto.getTotalAmount())) {
            this.setButtonClickable(true);
         } else if (AppUtil.isInternetConnectionAvailable(this, 0)) {
            if (this.otpLL.getVisibility() == 0 && this.editTextOtp.getText().toString().isEmpty()) {
               this.editTextOtp.setError("Required");
               this.setButtonClickable(true);
            } else {
               this.makePayment();
            }

            AppUtil.hideKeyboard(this);
         } else {
            AppUtil.showNoInternetDialogAndHideDialog(this);
         }
         //Modified view.getId() by oseamiya
      } else if (view.getId() == 0x7f0b0073 || view.getId() == 0x7f0b001b) {
         this.countDownTimer.cancel();
         this.setResult(0);
         this.finish();
      }

   }

   private void setButtonClickable(boolean buttonClickable) {
      this.buttonPay.setClickable(buttonClickable);
      this.buttonCancel.setClickable(buttonClickable);
      this.backButton.setClickable(buttonClickable);
   }

   private void makePayment() {
      HttpServerConnectorDto httpServerConnectorDto = new HttpServerConnectorDto();
      String var2 = this.logInResponseDto.getEnvironment();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 3322092:
         if (var2.equals("live")) {
            var3 = 0;
         }
         break;
      case 3556498:
         if (var2.equals("test")) {
            var3 = 1;
         }
         break;
      case 103145323:
         if (var2.equals("local")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
         httpServerConnectorDto.setUrlToConnect("https://esewa.com.np/mobile/payment");
         break;
      case 1:
         httpServerConnectorDto.setUrlToConnect("https://uat.esewa.com.np/mobile/payment");
         break;
      case 2:
         httpServerConnectorDto.setUrlToConnect("http://10.13.208.83:8100/mobile/payment");
      }

      httpServerConnectorDto.setIdentifierHeader(this.logInResponseDto.getIdentifier());
      httpServerConnectorDto.setAccessTokenHeader(this.logInResponseDto.getMerchantAuthenticationToken());
      httpServerConnectorDto.setCookie(this.logInResponseDto.getCookie());
      if (this.logInResponseDto.isOtpRequired()) {
         httpServerConnectorDto.setOtp(this.editTextOtp.getText().toString().trim());
      }

      JSONObject jsonObject = new JSONObject();

      try {
         jsonObject.put("totalAmount", Encryptor.encryptString(this.logInResponseDto.getTotalAmount()));
         jsonObject.put("productId", Encryptor.encryptString(this.logInResponseDto.getProductUniqueId()));
         jsonObject.put("productName", Encryptor.encryptString(this.logInResponseDto.getProductName()));
         jsonObject.put("callbackUrl", Encryptor.encryptString(this.logInResponseDto.getCallbackUrl()));
         jsonObject.put("environment", Encryptor.encryptString(this.logInResponseDto.getEnvironment()));
      } catch (JSONException var4) {
         var4.printStackTrace();
      }

      httpServerConnectorDto.setJsonObject(jsonObject);
      (new PaymentController(this, httpServerConnectorDto, this.logInResponseDto.getEnvironment())).execute(new String[0]);
   }

   public void onBackPressed() {
      this.countDownTimer.cancel();
   }

   public void onTaskStarted() {
      this.progressDialog = AppUtil.createProgressDialog(this, "Confirming Payment ...");
      this.progressDialog.show();
   }

   public void onTaskFinished(String s) {
      if (this.progressDialog != null) {
         this.progressDialog.dismiss();
      }

      if (s.equals("Server error") | s.equals("eSewa Server Error") | s.equals("Invalid username or password")) {
         AppUtil.showNoResponseMessageAndFinish(this, s);
      } else {
         try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("message")) {
               JSONObject jsonObjectMessage = jsonObject.getJSONObject("message");
               if (jsonObjectMessage.has("successMessage")) {
                  String productId = HashEncryption.decryptString(jsonObject.getString("productId"));
                  String productName = HashEncryption.decryptString(jsonObject.getString("productName"));
                  String totalAmount = HashEncryption.decryptString(jsonObject.getString("totalAmount"));
                  String environment = HashEncryption.decryptString(jsonObject.getString("environment"));
                  String code = HashEncryption.decryptString(jsonObject.getString("code"));
                  String merchant = HashEncryption.decryptString(jsonObject.getString("merchantName"));
                  JSONObject jsonObjectTransactionDetail = jsonObject.getJSONObject("transactionDetails");
                  String status = HashEncryption.decryptString(jsonObjectTransactionDetail.getString("status"));
                  String referenceId = HashEncryption.decryptString(jsonObjectTransactionDetail.getString("referenceId"));
                  String date = HashEncryption.decryptString(jsonObjectTransactionDetail.getString("date"));
                  JSONObject finalJsonObject = new JSONObject();
                  finalJsonObject.put("productId", productId);
                  finalJsonObject.put("productName", productName);
                  finalJsonObject.put("totalAmount", totalAmount);
                  finalJsonObject.put("environment", environment);
                  finalJsonObject.put("code", code);
                  finalJsonObject.put("merchantName", merchant);
                  finalJsonObject.put("message", jsonObjectMessage);
                  JSONObject finalJsonObjectTransactionDetail = new JSONObject();
                  finalJsonObjectTransactionDetail.put("status", status);
                  finalJsonObjectTransactionDetail.put("referenceId", referenceId);
                  finalJsonObjectTransactionDetail.put("date", date);
                  finalJsonObject.put("transactionDetails", finalJsonObjectTransactionDetail);
                  String finalResponseString = finalJsonObject.toString();
                  if (this.countDownTimer != null) {
                     this.countDownTimer.cancel();
                  }

                  Intent returnIntent = new Intent();
                  this.setResult(-1, returnIntent);
                  returnIntent.putExtra("com.esewa.android.sdk.paymentConfirmation", finalResponseString);
                  this.finish();
               } else if (jsonObjectMessage.has("errorMessage")) {
                  if ("Invalid Token.".equalsIgnoreCase(jsonObjectMessage.getString("errorMessage"))) {
                     AppUtil.showAlertAndDismiss("Invalid verification code", this);
                     this.setButtonClickable(true);
                  } else {
                     AppUtil.showErrorMessageAndFinish(this, jsonObject, true);
                  }
               }
            } else {
               this.setResult(0);
               this.finish();
            }
         } catch (JSONException var18) {
            var18.printStackTrace();
         }
      }

   }
}