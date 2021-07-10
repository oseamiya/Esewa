# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.oseamiya.esewa.esewabysneha.EsewaBySneha {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/oseamiya/esewa/esewabysneha/repack'
-flattenpackagehierarchy
-dontpreverify
