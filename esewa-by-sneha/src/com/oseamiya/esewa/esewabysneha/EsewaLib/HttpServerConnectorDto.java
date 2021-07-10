package com.oseamiya.esewa.esewabysneha.EsewaLib;

import org.json.JSONObject;

class HttpServerConnectorDto {
   private String merchantAuthenticationCodeHeader;
   private String accessTokenHeader;
   private String authorizationHeader;
   private String identifierHeader;
   private String urlToConnect;
   private String cookie;
   private boolean applyTimeOut;
   private String clientIdHeader;
   private String secretKeyHeader;
   private JSONObject jsonObject;
   private String environment;
   private String otp;

   public String getMerchantAuthenticationCodeHeader() {
      return this.merchantAuthenticationCodeHeader;
   }

   public String getAuthorizationHeader() {
      return this.authorizationHeader;
   }

   public void setAuthorizationHeader(String authorizationHeader) {
      this.authorizationHeader = authorizationHeader;
   }

   public String getIdentifierHeader() {
      return this.identifierHeader;
   }

   public void setIdentifierHeader(String identifierHeader) {
      this.identifierHeader = identifierHeader;
   }

   public String getAccessTokenHeader() {
      return this.accessTokenHeader;
   }

   public void setAccessTokenHeader(String accessTokenHeader) {
      this.accessTokenHeader = accessTokenHeader;
   }

   public String getEnvironment() {
      return this.environment;
   }

   public JSONObject getJsonObject() {
      return this.jsonObject;
   }

   public void setJsonObject(JSONObject jsonObject) {
      this.jsonObject = jsonObject;
   }

   public String getClientIdHeader() {
      return this.clientIdHeader;
   }

   public void setClientIdHeader(String clientIdHeader) {
      this.clientIdHeader = clientIdHeader;
   }

   public String getSecretKeyHeader() {
      return this.secretKeyHeader;
   }

   public void setSecretKeyHeader(String secretKeyHeader) {
      this.secretKeyHeader = secretKeyHeader;
   }

   public boolean isApplyTimeOut() {
      return this.applyTimeOut;
   }

   public void setApplyTimeOut(boolean applyTimeOut) {
      this.applyTimeOut = applyTimeOut;
   }

   public String getUrlToConnect() {
      return this.urlToConnect;
   }

   public void setUrlToConnect(String urlToConnect) {
      this.urlToConnect = urlToConnect;
   }

   public String getCookie() {
      return this.cookie;
   }

   public void setCookie(String cookie) {
      this.cookie = cookie;
   }

   public String getOtp() {
      return this.otp;
   }

   public void setOtp(String otp) {
      this.otp = otp;
   }
}