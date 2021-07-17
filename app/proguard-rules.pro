# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#---------------------------------------------------------
# 壓縮等級
#-optimizationpasses 5
# 不混合大小寫
#-dontusemixedcaseclassnames
# 不去忽略非公用的類別
#-dontskipnonpubliclibraryclasses
#-dontpreverify
# 混淆時是否記錄日誌
#-verbose

#google
-keep class com.google.** { *; }
-dontwarn com.google.**

#android x
-keep class androidx.** {*;}
-dontwarn androidx.**

#e2e SDK
-keep class com.cg.jpki.** {*;}
-dontwarn com.cg.jpki.**
-keep interface com.cg.jpki.**
-keep enum com.cg.jpki.**

#apache
-keep class org.apache.** { *; }
-dontwarn org.apache.**
-keepattributes Signature


#解決 Gson 被 Obfuscated 後會出現 ClassCastException
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
##---------------End: proguard configuration for Gson  ----------

# Keep names - Native method names. Keep all native class/method names.
# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}
# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,includedescriptorclasses,allowshrinking class * {
    native <methods>;
}

-keepattributes Exceptions,InnerClasses
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature