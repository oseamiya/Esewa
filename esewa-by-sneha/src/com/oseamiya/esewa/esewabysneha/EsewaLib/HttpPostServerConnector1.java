package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.content.SharedPreferences;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpPostServerConnector1 {
   private static final long CONN_MGR_TIMEOUT = 10000L;
   private static final int CONN_TIMEOUT = 50000;
   private static final int SO_TIMEOUT = 50000;
   private final HttpServerConnectorDto serverConnectorDto;
   private String serverResponse;
   private SharedPreferences sharedPreferences;

   public HttpPostServerConnector1(HttpServerConnectorDto serverConnectorDto) {
      this.serverConnectorDto = serverConnectorDto;
   }

   public String communicateWithServer(String environment) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, KeyStoreException, UnrecoverableKeyException {
      AppLog.showLog("environment:::: " + environment);
      if (environment.equalsIgnoreCase("test")) {
         TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
               return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
         }};
         SSLContext sc = SSLContext.getInstance("SSL");
         sc.init((KeyManager[])null, trustAllCerts, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
         HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
               return true;
            }
         };
         HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
      }

      URL url = new URL(this.serverConnectorDto.getUrlToConnect());
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("content-type", "application/json");
      if (this.serverConnectorDto.getCookie() != null) {
         conn.setRequestProperty("Cookie", this.serverConnectorDto.getCookie());
      }

      conn.setRequestProperty("platform", "Android, " + AppUtil.getDeviceName());
      conn.setConnectTimeout(50000);
      conn.setReadTimeout(50000);
      if (this.serverConnectorDto.getClientIdHeader() != null) {
         conn.setRequestProperty("merchantId", this.serverConnectorDto.getClientIdHeader());
      }

      if (this.serverConnectorDto.getEnvironment() != null) {
         conn.setRequestProperty("environment", this.serverConnectorDto.getEnvironment());
      }

      if (this.serverConnectorDto.getIdentifierHeader() != null) {
         conn.setRequestProperty("identifier", this.serverConnectorDto.getIdentifierHeader());
      }

      if (this.serverConnectorDto.getAccessTokenHeader() != null) {
         conn.setRequestProperty("merchantAuthToken", this.serverConnectorDto.getAccessTokenHeader());
      }

      if (this.serverConnectorDto.getSecretKeyHeader() != null) {
         conn.setRequestProperty("merchantSecret", this.serverConnectorDto.getSecretKeyHeader());
      }

      if (this.serverConnectorDto.getAuthorizationHeader() != null) {
         conn.setRequestProperty("Authorization", this.serverConnectorDto.getAuthorizationHeader());
      }

      if (this.serverConnectorDto.getMerchantAuthenticationCodeHeader() != null) {
         conn.setRequestProperty("merchantAuthenticationCode", this.serverConnectorDto.getMerchantAuthenticationCodeHeader());
      }

      if (this.serverConnectorDto.getOtp() != null) {
         conn.setRequestProperty("otp", this.serverConnectorDto.getOtp());
      }

      AppLog.showLog("Login API:::: " + this.serverConnectorDto.getUrlToConnect());
      AppLog.showLog("Headers:::: " + conn.getRequestProperties().toString());
      if (this.serverConnectorDto.getJsonObject() != null) {
         try {
            AppLog.showLog("Request Body:::: " + this.serverConnectorDto.getJsonObject().toString());
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(this.serverConnectorDto.getJsonObject().toString());
            wr.flush();
         } catch (UnsupportedEncodingException var14) {
            var14.printStackTrace();
         }
      }

      StringBuilder sb = new StringBuilder();
      int HttpResult = conn.getResponseCode();
      if (HttpResult != 200) {
         if (HttpResult == 401) {
            return "Invalid username or password";
         } else if (HttpResult != 404 && HttpResult != 400) {
            if (HttpResult == 522) {
               return "time out";
            } else {
               AppLog.showLog("Error response::: " + conn.getResponseMessage());
               return conn.getResponseMessage();
            }
         } else {
            return "eSewa Server Error";
         }
      } else {
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
         String line = null;

         while((line = br.readLine()) != null) {
            sb.append(line).append("\n");
         }

         br.close();
         JSONObject jsonObject = null;
         Map<String, List<String>> map = conn.getHeaderFields();
         Iterator var10 = map.entrySet().iterator();

         while(var10.hasNext()) {
            Entry<String, List<String>> entry = (Entry)var10.next();
            AppLog.showLog("Key : " + (String)entry.getKey() + " ,Value : " + entry.getValue());
            if (entry.getKey() != null && ((String)entry.getKey()).equalsIgnoreCase("Set-Cookie")) {
               try {
                  jsonObject = (new JSONObject(sb.toString())).put("Cookie", ((List)entry.getValue()).get(0));
               } catch (JSONException var13) {
                  var13.printStackTrace();
               }
            }
         }

         if (jsonObject != null) {
            AppLog.showLog("Success Response::: " + jsonObject.toString());
            return jsonObject.toString();
         } else {
            AppLog.showLog("Success Response::: " + sb.toString());
            return sb.toString();
         }
      }
   }
}