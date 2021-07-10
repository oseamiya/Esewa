package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ESewaConfiguration implements Parcelable {
   public static final String ESEWA_CONFIGURATION = "com.esewa.android.sdk.config";
   public static final String ENVIRONMENT_PRODUCTION = "live";
   public static final String ENVIRONMENT_TEST = "test";
   public static final String ENVIRONMENT_LOCAL = "local";
   public static String clientId;
   public static String secretKey;
   public static String environment;
   public static final Creator<ESewaConfiguration> CREATOR = new Creator<ESewaConfiguration>() {
      public ESewaConfiguration createFromParcel(Parcel parcel) {
         return new ESewaConfiguration(parcel);
      }

      public ESewaConfiguration[] newArray(int size) {
         return new ESewaConfiguration[size];
      }
   };

   public ESewaConfiguration() {
   }

   public ESewaConfiguration clientId(String clientId) {
      ESewaConfiguration.clientId = clientId;
      return this;
   }

   public ESewaConfiguration secretKey(String secretKey) {
      ESewaConfiguration.secretKey = secretKey;
      return this;
   }

   public ESewaConfiguration environment(String environment) {
      ESewaConfiguration.environment = environment;
      return this;
   }

   String getClientId() {
      return clientId;
   }

   String getSecretKey() {
      return secretKey;
   }

   public String getEnvironment() {
      return environment;
   }

   public int describeContents() {
      return 0;
   }

   private ESewaConfiguration(Parcel parcel) {
      clientId = parcel.readString();
      secretKey = parcel.readString();
      environment = parcel.readString();
   }

   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(clientId);
      parcel.writeString(secretKey);
      parcel.writeString(environment);
   }

   // $FF: synthetic method
   ESewaConfiguration(Parcel x0, Object x1) {
      this(x0);
   }
}
    