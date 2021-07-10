package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.WindowManager.BadTokenException;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
/*
import com.android.esewa.esewasdk.R.drawable;
import com.android.esewa.esewasdk.R.layout;
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.widget.RelativeLayout;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.appinventor.components.runtime.util.MediaUtil;

class AppUtil {
   public static String ERROR_MESSAGE;
   public static String TECHNICAL_ERROR_MESSAGE;

   public static String convertStreamToString(InputStream is) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();

      try {
         String line;
         try {
            while((line = reader.readLine()) != null) {
               sb.append(line).append("\n");
            }
         } catch (IOException var13) {
            var13.printStackTrace();
         }
      } finally {
         try {
            is.close();
         } catch (IOException var12) {
            var12.printStackTrace();
         }

      }

      return sb.toString();
   }

   public static boolean isInternetConnectionAvailable(Context context, int internetType) {
      boolean isAvailable = false;
      ConnectivityManager cm = (ConnectivityManager)context.getSystemService("connectivity");
      NetworkInfo networkInfo = cm.getActiveNetworkInfo();
      if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
         switch(internetType) {
         case 0:
            if (networkInfo.getType() == 1 || networkInfo.getType() == 0) {
               isAvailable = true;
            }
            break;
         case 1:
            if (networkInfo.getType() == 1) {
               isAvailable = true;
            }
         case 2:
            if (networkInfo.getType() == 0) {
               isAvailable = true;
            }
         }
      }

      return isAvailable;
   }

   public static void showAlertAndDismiss(String noticeText, Context context) {
      Builder noticeAlertBuilder = new Builder(context);
      noticeAlertBuilder.setMessage(noticeText);
      noticeAlertBuilder.setCancelable(false);
      noticeAlertBuilder.setNegativeButton("OK", new OnClickListener() {
         public void onClick(DialogInterface dialog, int arg1) {
            dialog.dismiss();
         }
      });
      AlertDialog dialog = noticeAlertBuilder.create();
      dialog.show();
      setDialogSelector(dialog);
   }

   public static void showTimeOutAlertAndDismiss(final Context context) {
      Builder noticeAlertBuilder = new Builder(context);
      noticeAlertBuilder.setMessage("The session is expired. Please try again later.");
      noticeAlertBuilder.setCancelable(false);
      noticeAlertBuilder.setNegativeButton("OK", new OnClickListener() {
         public void onClick(DialogInterface dialog, int arg1) {
            dialog.dismiss();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("com.esewa.android.sdk.paymentConfirmation", "Session Time Out");
            ((Activity)context).setResult(2, returnIntent);
            ((Activity)context).finish();
         }
      });
      AlertDialog dialog = noticeAlertBuilder.create();
      dialog.show();
      ((Activity)context).setRequestedOrientation(5);
      setDialogSelector(dialog);
   }

   public static void showNoInternetDialogAndFinish(final Context context) {
      Builder noticeAlertBuilder = new Builder(context);
      noticeAlertBuilder.setMessage("Internet not available");
      noticeAlertBuilder.setCancelable(false);
      noticeAlertBuilder.setNegativeButton("OK", new OnClickListener() {
         public void onClick(DialogInterface dialog, int arg1) {
            dialog.dismiss();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("com.esewa.android.sdk.paymentConfirmation", "Internet not available");
            ((Activity)context).setResult(2, returnIntent);
            ((Activity)context).finish();
         }
      });
      AlertDialog dialog = noticeAlertBuilder.create();
      dialog.show();
      setDialogSelector(dialog);
   }

   public static void showNoInternetDialogAndHideDialog(Context context) {
      Builder noticeAlertBuilder = new Builder(context);
      noticeAlertBuilder.setMessage("Internet not available");
      noticeAlertBuilder.setCancelable(false);
      noticeAlertBuilder.setNegativeButton("OK", new OnClickListener() {
         public void onClick(DialogInterface dialog, int arg1) {
            dialog.dismiss();
         }
      });
      AlertDialog dialog = noticeAlertBuilder.create();
      dialog.show();
      setDialogSelector(dialog);
   }

   public static void showNoResponseMessageAndFinish(final Context context, final String s) {
      Builder noticeAlertBuilder = new Builder(context);
      noticeAlertBuilder.setMessage("Sorry, your request cannot be proceed at this time try again later");
      noticeAlertBuilder.setCancelable(false);
      noticeAlertBuilder.setNegativeButton("OK", new OnClickListener() {
         public void onClick(DialogInterface dialog, int arg1) {
            dialog.dismiss();
            Intent returnIntent = new Intent();
            ((Activity)context).setResult(2, returnIntent);
            returnIntent.putExtra("com.esewa.android.sdk.paymentConfirmation", s);
            ((Activity)context).finish();
         }
      });
      AlertDialog dialog = noticeAlertBuilder.create();
      dialog.show();
      setDialogSelector(dialog);
   }

   public static void showErrorMessageAndFinish(final Context context, JSONObject jsonObject, final Boolean isGoBack) {
      try {
         JSONObject jsonObjectMessage = jsonObject.getJSONObject("message");
         ERROR_MESSAGE = jsonObjectMessage.getString("errorMessage");
         TECHNICAL_ERROR_MESSAGE = jsonObjectMessage.getString("technicalErrorMessage");
         AppLog.showLog(TECHNICAL_ERROR_MESSAGE);
      } catch (JSONException var6) {
         var6.printStackTrace();
      }

      Builder noticeAlertBuilder = new Builder(context);
      noticeAlertBuilder.setMessage(ERROR_MESSAGE);
      noticeAlertBuilder.setCancelable(false);
      noticeAlertBuilder.setNegativeButton("OK", new OnClickListener() {
         public void onClick(DialogInterface dialog, int arg1) {
            dialog.dismiss();
            if (isGoBack) {
               Intent returnIntent = new Intent();
               ((Activity)context).setResult(2, returnIntent);
               returnIntent.putExtra("com.esewa.android.sdk.paymentConfirmation", AppUtil.TECHNICAL_ERROR_MESSAGE);
               ((Activity)context).finish();
            }

         }
      });
      AlertDialog dialog = noticeAlertBuilder.create();
      dialog.show();
      setDialogSelector(dialog);
   }

   public static void setDialogSelector(AlertDialog dialog) {
       // GradientDrawable green_selector_gray = new GradientDrawable();
      Button negButton = dialog.getButton(-2);
      if (negButton != null) {
         //added

         negButton.setBackgroundColor(Color.GREEN);
      }

      Button posButton = dialog.getButton(-1);
      if (posButton != null) {
         posButton.setBackgroundColor(Color.GREEN);
      }

   }

   public static String getDeviceName() {
      String manufacturer = Build.MANUFACTURER;
      String model = Build.MODEL;
      return model.startsWith(manufacturer) ? capitalize(model) : capitalize(manufacturer) + " " + model;
   }

   public static ProgressDialog createProgressDialog(Context mContext, String message) {
      ProgressDialog dialog = new ProgressDialog(mContext);

      try {
         dialog.show();
      } catch (BadTokenException var4) {
      }

      dialog.setCancelable(false);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
      //Modified by @oseamiya to create esewa payment in AppInventor
      ProgressDialogSdkLayout progressDialogSdkLayout = new ProgressDialogSdkLayout();
      RelativeLayout relativeLayout = progressDialogSdkLayout.getRelativeLayout();
      dialog.setContentView(relativeLayout);
      dialog.setMessage(message);
      return dialog;
   }

   public static String capitalize(String s) {
      if (s != null && s.length() != 0) {
         char first = s.charAt(0);
         return Character.isUpperCase(first) ? s : Character.toUpperCase(first) + s.substring(1);
      } else {
         return "";
      }
   }

   public static void hideKeyboard(Activity activity) {
      if (activity.getCurrentFocus() != null) {
         InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService("input_method");
         inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
      }

   }
}