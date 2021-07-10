package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

class LoginController extends AsyncTask<String, Void, String> {
   private HttpServerConnectorDto httpServerConnectorDto;
   private AsyncResponseReturn<String> asyncResponseReturn;
   String environment;

   public LoginController(AsyncResponseReturn<String> asyncResponseReturn, HttpServerConnectorDto httpServerConnectorDto, String environment) {
      this.asyncResponseReturn = asyncResponseReturn;
      this.httpServerConnectorDto = httpServerConnectorDto;
      this.environment = environment;
   }

   protected void onPreExecute() {
      super.onPreExecute();
      this.asyncResponseReturn.onTaskStarted();
   }

   protected String doInBackground(String... strings) {
      AppLog.showLog(" URL TO CONNECT FOR CLIENT AUTHENTICATION" + this.httpServerConnectorDto.getUrlToConnect());
      String response = "";

      try {
         response = (new HttpPostServerConnector1(this.httpServerConnectorDto)).communicateWithServer(this.environment);
      } catch (IOException var4) {
         var4.printStackTrace();
      } catch (NoSuchAlgorithmException var5) {
         var5.printStackTrace();
      } catch (KeyManagementException var6) {
         var6.printStackTrace();
      } catch (URISyntaxException var7) {
         var7.printStackTrace();
      } catch (KeyStoreException var8) {
         var8.printStackTrace();
      } catch (UnrecoverableKeyException var9) {
         var9.printStackTrace();
      }

      return response;
   }

   protected void onPostExecute(String s) {
      super.onPostExecute(s);
      this.asyncResponseReturn.onTaskFinished(s);
   }
}