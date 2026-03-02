# ProGuard rules for Pocket Dev

# Keep Retrofit models
-keep class com.pocketdev.data.remote.** { *; }
-keep class com.pocketdev.model.** { *; }

# Keep Room entities
-keep class com.pocketdev.data.local.entity.** { *; }

# Rhino JavaScript
-keep class org.mozilla.javascript.** { *; }
-dontwarn org.mozilla.javascript.**

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**

# Chaquopy
-keep class com.chaquo.python.** { *; }

# Sora Editor
-keep class io.github.rosemoe.sora.** { *; }
-dontwarn io.github.rosemoe.sora.**
