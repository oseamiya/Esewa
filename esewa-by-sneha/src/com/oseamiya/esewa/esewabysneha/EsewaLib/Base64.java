package com.oseamiya.esewa.esewabysneha.EsewaLib;

class Base64 {
   static byte[] encodeData = new byte[64];
   static String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

   private Base64() {
   }

   static String encode(String s) {
      return encode(s.getBytes());
   }

   private static String encode(byte[] src) {
      return encode(src, 0, src.length);
   }

   private static String encode(byte[] src, int start, int length) {
      byte[] dst = new byte[(length + 2) / 3 * 4 + length / 160];
      int dstIndex = 0;
      int state = 0;
      int old = 0;
      int len = 0;
      int max = length + start;

      for(int srcIndex = start; srcIndex < max; ++srcIndex) {
         int x = src[srcIndex];
         ++state;
         switch(state) {
         case 1:
            dst[dstIndex++] = encodeData[x >> 2 & 63];
            break;
         case 2:
            dst[dstIndex++] = encodeData[old << 4 & 48 | x >> 4 & 15];
            break;
         case 3:
            dst[dstIndex++] = encodeData[old << 2 & 60 | x >> 6 & 3];
            dst[dstIndex++] = encodeData[x & 63];
            state = 0;
         }

         old = x;
         ++len;
         if (len >= 160) {
            dst[dstIndex++] = 10;
            len = 0;
         }
      }

      switch(state) {
      case 1:
         dst[dstIndex++] = encodeData[old << 4 & 48];
         dst[dstIndex++] = 61;
         dst[dstIndex++] = 61;
         break;
      case 2:
         dst[dstIndex++] = encodeData[old << 2 & 60];
         dst[dstIndex++] = 61;
      }

      return new String(dst);
   }

   static byte[] decode(String s) {
      int end = 0;
      if (s.endsWith("=")) {
         ++end;
      }

      if (s.endsWith("==")) {
         ++end;
      }

      int len = (s.length() + 3) / 4 * 3 - end;
      byte[] result = new byte[len];
      int dst = 0;

      try {
         for(int src = 0; src < s.length(); ++src) {
            int code = charSet.indexOf(s.charAt(src));
            if (code == -1) {
               break;
            }

            int var10001;
            switch(src % 4) {
            case 0:
               result[dst] = (byte)(code << 2);
               break;
            case 1:
               var10001 = dst++;
               result[var10001] |= (byte)(code >> 4 & 3);
               result[dst] = (byte)(code << 4);
               break;
            case 2:
               var10001 = dst++;
               result[var10001] |= (byte)(code >> 2 & 15);
               result[dst] = (byte)(code << 6);
               break;
            case 3:
               var10001 = dst++;
               result[var10001] |= (byte)(code & 63);
            }
         }
      } catch (ArrayIndexOutOfBoundsException var7) {
      }

      return result;
   }

   static {
      for(int i = 0; i < 64; ++i) {
         byte c = (byte)charSet.charAt(i);
         encodeData[i] = c;
      }

   }
}