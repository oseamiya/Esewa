package com.oseamiya.esewa.esewabysneha.EsewaLib;

class Encryptor {
   private static final String key = "esewaskey";

   public static String encryptString(String str) {
      StringBuilder sb = new StringBuilder(str);
      int lenStr = str.length();
      int lenKey = "esewaskey".length();
      int i = 0;

      for(int j = 0; i < lenStr; ++j) {
         if (j >= lenKey) {
            j = 0;
         }

         sb.setCharAt(i, (char)(str.charAt(i) ^ "esewaskey".charAt(j)));
         ++i;
      }

      String bb = sb.toString();
      return Base64.encode(bb);
   }
}