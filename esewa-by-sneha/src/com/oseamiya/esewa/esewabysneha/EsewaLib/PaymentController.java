package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

class PaymentController extends AsyncTask<String, Void, String> {
   String environment;
   private AsyncResponseReturn<String> asyncResponseReturn;
   private HttpServerConnectorDto httpServerConnectorDto;

   PaymentController(AsyncResponseReturn<String> asyncResponseReturn, HttpServerConnectorDto httpServerConnectorDto, String environment) {
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
      } catch (NoSuchAlgorithmException | URISyntaxException | KeyManagementException | UnrecoverableKeyException | KeyStoreException | IOException var4) {
         var4.printStackTrace();
      }

      return response;
   }

   protected void onPostExecute(String s) {
      super.onPostExecute(s);
      this.asyncResponseReturn.onTaskFinished(s);
   }
}