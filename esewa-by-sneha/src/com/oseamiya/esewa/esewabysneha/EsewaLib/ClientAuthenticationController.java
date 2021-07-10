package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import com.oseamiya.esewa.esewabysneha.EsewaLib.ESewaPayment;

class ClientAuthenticationController extends AsyncTask<String, Void, String> {
   private AsyncResponseReturn<String> asyncResponseReturn;
   private HttpServerConnectorDto httpServerConnectorDto;
   private ESewaPayment eSewaPayment;

   public ClientAuthenticationController(AsyncResponseReturn<String> asyncResponseReturn, HttpServerConnectorDto httpServerConnectorDto, ESewaPayment eSewaPayment) {
      this.asyncResponseReturn = asyncResponseReturn;
      this.httpServerConnectorDto = httpServerConnectorDto;
      this.eSewaPayment = eSewaPayment;
   }

   protected String doInBackground(String... strings) {
      AppLog.showLog(" URL TO CONNECT FOR CLIENT AUTHENTICATION" + this.httpServerConnectorDto.getUrlToConnect());
      AppLog.showLog(" URL TO CONNECT FOR CLIENT AUTHENTICATION" + this.eSewaPayment.getEnvironment());
      String response = "";

      try {
         response = (new HttpPostServerConnector1(this.httpServerConnectorDto)).communicateWithServer(this.eSewaPayment.getEnvironment());
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
    