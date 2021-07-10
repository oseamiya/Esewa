package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ESewaPayment implements Parcelable {
   public static final String EXTRA_RESULT_MESSAGE = "com.esewa.android.sdk.paymentConfirmation";
   public static final int RESULT_EXTRAS_INVALID = 2;
   public static final String ESEWA_PAYMENT = "com.esewa.android.sdk.payment";
   protected static final String PRODUCT_NAME = "productName";
   protected static final String PRODUCT_AMOUNT = "totalAmount";
   protected static final String ENVIRONMENT = "environment";
   protected static final String CALLBACK_URL = "callbackUrl";
   private String amount;
   private String productName;
   private String environment;
   private String productUniqueId;
   private String callbackUrl;
   private String merchantAuthToken;
   public static final Creator<ESewaPayment> CREATOR = new Creator<ESewaPayment>() {
      public ESewaPayment createFromParcel(Parcel parcel) {
         return new ESewaPayment(parcel);
      }

      public ESewaPayment[] newArray(int size) {
         return new ESewaPayment[size];
      }
   };

   public ESewaPayment(String amount, String productName, String productUniqueId, String callbackUrl) {
      this.amount = amount;
      this.productName = productName;
      this.productUniqueId = productUniqueId;
      this.callbackUrl = callbackUrl;
   }

   public String getAmount() {
      return this.amount;
   }

   public String getCallbackUrl() {
      return this.callbackUrl;
   }

   public String getProductName() {
      return this.productName;
   }

   public void setEnvironment(String environment) {
      this.environment = environment;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public String getMerchantAuthToken() {
      return this.merchantAuthToken;
   }

   public void setMerchantAuthToken(String merchantAuthToken) {
      this.merchantAuthToken = merchantAuthToken;
   }

   public String getProductUniqueId() {
      return this.productUniqueId;
   }

   public ESewaPayment(Parcel parcel) {
      this.amount = parcel.readString();
      this.productName = parcel.readString();
      this.environment = parcel.readString();
      this.productUniqueId = parcel.readString();
      this.callbackUrl = parcel.readString();
      this.merchantAuthToken = parcel.readString();
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(this.amount);
      parcel.writeString(this.productName);
      parcel.writeString(this.environment);
      parcel.writeString(this.productUniqueId);
      parcel.writeString(this.callbackUrl);
      parcel.writeString(this.merchantAuthToken);
   }
}