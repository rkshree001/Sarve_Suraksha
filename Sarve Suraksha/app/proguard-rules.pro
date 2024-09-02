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

# Keep BouncyCastle classes
-keep class org.bouncycastle.** { *; }
-dontwarn org.bouncycastle.**

# Keep Conscrypt classes
-keep class org.conscrypt.** { *; }
-dontwarn org.conscrypt.**

# Keep OpenJSSE classes
-keep class org.openjsse.** { *; }
-dontwarn org.openjsse.**

# Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-keep class okhttp3.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Keep safe on JSON serialization/de-serialization
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep serialized names for Gson
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Your model classes
-keep class com.bnet.sarvesuraksha.model_api.** { *; }
-keep class com.bnet.sarvesuraksha.model.** { *; }

# Retrofit interfaces
-keep interface com.bnet.sarvesuraksha.api.** { *; }

# To keep Retrofit annotations (like @GET, @POST, etc.)
-keepattributes RuntimeVisibleAnnotations

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
