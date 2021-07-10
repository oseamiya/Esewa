package com.oseamiya.esewa.esewabysneha.EsewaLib;

interface AsyncResponseReturn<T> {
   void onTaskStarted();

   void onTaskFinished(T var1);
}