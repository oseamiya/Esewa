package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

class LogInResponseDto implements Parcelable {
   private String merchantAuthenticationToken;
   private String identifier;
   private String environment;
   private String merchantName;
   private String userName;
   private String productName;
   private String message;
   private String totalAmount;
   private String balance;
   private String productUniqueId;
   private String callbackUrl;
   private String cookie;
   private int code;
   private boolean otpRequired;
   private String charge;
   private String cashBack;
   private long currentTimeOfTimer;
   public static final Creator<LogInResponseDto> CREATOR = new Creator<LogInResponseDto>() {
      public LogInResponseDto createFromParcel(Parcel parcel) {
         return new LogInResponseDto(parcel);
      }

      public LogInResponseDto[] newArray(int i) {
         return new LogInResponseDto[i];
      }
   };

   public long getCurrentTimeOfTimer() {
      return this.currentTimeOfTimer;
   }

   public void setCurrentTimeOfTimer(long currentTimeOfTimer) {
      this.currentTimeOfTimer = currentTimeOfTimer;
   }

   public LogInResponseDto() {
   }

   public LogInResponseDto(Parcel parcel) {
      this.merchantAuthenticationToken = parcel.readString();
      this.identifier = parcel.readString();
      this.environment = parcel.readString();
      this.merchantName = parcel.readString();
      this.userName = parcel.readString();
      this.productName = parcel.readString();
      this.message = parcel.readString();
      this.balance = parcel.readString();
      this.totalAmount = parcel.readString();
      this.productUniqueId = parcel.readString();
      this.callbackUrl = parcel.readString();
      this.cookie = parcel.readString();
      this.code = parcel.readInt();
      this.currentTimeOfTimer = parcel.readLong();
      this.otpRequired = parcel.readInt() == 1;
      this.charge = parcel.readString();
      this.cashBack = parcel.readString();
   }

   public String getMerchantAuthenticationToken() {
      return this.merchantAuthenticationToken;
   }

   public void setMerchantAuthenticationToken(String merchantAuthenticationToken) {
      this.merchantAuthenticationToken = merchantAuthenticationToken;
   }

   public String getIdentifier() {
      return this.identifier;
   }

   public void setIdentifier(String identifier) {
      this.identifier = identifier;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public void setEnvironment(String environment) {
      this.environment = environment;
   }

   public String getMerchantName() {
      return this.merchantName;
   }

   public void setMerchantName(String merchantName) {
      this.merchantName = merchantName;
   }

   public String getUserName() {
      return this.userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getBalance() {
      return this.balance;
   }

   public String getCallbackUrl() {
      return this.callbackUrl;
   }

   public void setCallbackUrl(String callbackUrl) {
      this.callbackUrl = callbackUrl;
   }

   public void setBalance(String balance) {
      this.balance = balance;
   }

   public String getProductName() {
      return this.productName;
   }

   public void setProductName(String productName) {
      this.productName = productName;
   }

   public String getTotalAmount() {
      return this.totalAmount;
   }

   public void setTotalAmount(String totalAmount) {
      this.totalAmount = totalAmount;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public int getCode() {
      return this.code;
   }

   public void setCode(int code) {
      this.code = code;
   }

   public String getProductUniqueId() {
      return this.productUniqueId;
   }

   public String getCookie() {
      return this.cookie;
   }

   public void setCookie(String cookie) {
      this.cookie = cookie;
   }

   public void setProductUniqueId(String productUniqueId) {
      this.productUniqueId = productUniqueId;
   }

   public boolean isOtpRequired() {
      return this.otpRequired;
   }

   public void setOtpRequired(boolean otpRequired) {
      this.otpRequired = otpRequired;
   }

   public String getCharge() {
      return this.charge;
   }

   public void setCharge(String charge) {
      this.charge = charge;
   }

   public String getCashBack() {
      return this.cashBack;
   }

   public void setCashBack(String cashBack) {
      this.cashBack = cashBack;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(this.merchantAuthenticationToken);
      parcel.writeString(this.identifier);
      parcel.writeString(this.environment);
      parcel.writeString(this.merchantName);
      parcel.writeString(this.userName);
      parcel.writeString(this.productName);
      parcel.writeString(this.message);
      parcel.writeString(this.balance);
      parcel.writeString(this.totalAmount);
      parcel.writeString(this.productUniqueId);
      parcel.writeString(this.callbackUrl);
      parcel.writeString(this.cookie);
      parcel.writeInt(this.code);
      parcel.writeLong(this.currentTimeOfTimer);
      parcel.writeInt(this.otpRequired ? 1 : 0);
      parcel.writeString(this.charge);
      parcel.writeString(this.cashBack);
   }
}