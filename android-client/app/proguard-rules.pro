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

-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

# kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Change here com.yourcompany.yourpackage
-keep,includedescriptorclasses class com.sergey_y.simpletweets.data.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.sergey_y.simpletweets.data.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.sergey_y.simpletweets.data.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile