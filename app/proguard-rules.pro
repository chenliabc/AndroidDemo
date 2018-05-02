# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\soft\Android\sdk2.3/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

#混淆时不适用大小写混合类名
-dontusemixedcaseclassnames
#指定不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
#不使用优化方案
-dontoptimize
#混淆时不做预校验
-dontpreverify
#保留注解属性
-keepattributes *Annotation*
#不混淆泛型
-keepattributes Signature
#保留行号
-keepattributes SourceFile,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keepclasseswithmembernames class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

-keep public class * extends android.view.View{
    public <init>(android.content.Context);
    public <init>(android.content.Context,android.util.AttributeSet);
    public <init>(android.content.Context,android.util.AttributeSet,int);
    public void set*(***);
    *** get*();
}

#保持native方法不做混淆
-keepclasseswithmembernames class *{
    native <methods>;
}

-keepclassmembers public class * extends android.view.View{
    void set*(***);
    *** get*();
}

-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

-keepclassmembers enum*{
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * implements android.os.Parcelable{
}

-keepclasseswithmembers class * implements java.io.Serializable{
}

-keepclassmembers class **.R$*{
    public static <fields>;
}

#library不显示警告信息
-dontwarn android.support.**