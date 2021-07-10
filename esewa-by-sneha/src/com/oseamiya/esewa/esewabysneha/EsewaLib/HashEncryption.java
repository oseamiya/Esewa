package com.oseamiya.esewa.esewabysneha.EsewaLib;

class HashEncryption {
   private static final String key = "esewaskey";

   public static String decryptString(String encryptedText) {
      try {
         byte[] byteEncryptedText = Base64.decode(encryptedText);
         String encryptedPlainText = new String(byteEncryptedText);
         StringBuilder sb = new StringBuilder(encryptedPlainText);
         int lenStr = encryptedPlainText.length();
         int lenKey = "esewaskey".length();
         int i = 0;

         for(int j = 0; i < lenStr; ++j) {
            if (j >= lenKey) {
               j = 0;
            }

            sb.setCharAt(i, (char)(encryptedPlainText.charAt(i) ^ "esewaskey".charAt(j)));
            ++i;
         }

         return sb.toString();
      } catch (Exception var8) {
         return null;
      }
   }
}