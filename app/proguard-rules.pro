# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\AsTools\Android\Sdk/tools/proguard/proguard-android.txt
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

-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-ignorewarnings       #抑制警告

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keep public class * extends android.app.Application # 保持哪些类不被混淆继承 application的类

-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context,android.util.AttributeSet);
 }
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep public class * extends android.view.View{
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

###################common##############
 -keep class com.hxwl.wulinfeng.bean.**{*;}##bean不混淆
 -keep class com.hxwl.wulinfeng.db.**{*;}##db不混淆
 -keep class com.hxwl.wulinfeng.view.**{*;}##自定义控件
 ################gson##################
 -keep class com.google.gson.** {*;}
 #-keep class com.google.**{*;}
 -keep class sun.misc.Unsafe { *; }
 -keep class com.google.gson.stream.** { *; }
 -keep class com.google.gson.examples.android.model.** { *; }
 -keep class com.google.** {
     <fields>;
     <methods>;
 }
 -keepattributes *Annotation*

 -keep class sun.misc.Unsafe { *; }

-keepattributes Signature
 -keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
 }
 -dontwarn com.google.gson.**

 ################不混淆R类##################
 -keep public class com.hxwl.wulinfeng.R$*{
     public static final int *;
 }
 ####################support.v4#####################
-dontwarn android.support.**
 ####################三方jar包忽略 规则：-keep class com.baidu.* { ; }**#####################
-keep class com.nineoldandroids.** { *; }
-keep class com.youth.banner.** { *; }
-keep class me.iwf.photopicker.** { *; }
 ### greenDAO 3
 -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
 public static java.lang.String TABLENAME;
 }
 -keep class **$Properties

 # If you do not use SQLCipher:
 -dontwarn org.greenrobot.greendao.database.**
 # If you do not use RxJava:
 -dontwarn rx.**
 ##okhttp
 -dontwarn com.squareup.okhttp.**
 -keep class com.squareup.okhttp3.** { *; }
 -keep class com.squareup.okio.** { *; }
 -dontwarn okio.**
 ##注解jar
 -keep class com.jakewharton.** { *; }
 -dontwarn butterknife.internal.**
 #--------------下面为乐视SDk混淆选项-------------------------#
-dontoptimize
-dontpreverify
-dontwarn com.letv.**
-keep class com.letv.** { *; }
#--------------极光推送-------------------------#
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#--------------百度统计-------------------------#
-keep class com.baidu.bottom.** { *; }
-keep class com.baidu.kirin.** { *; }
-keep class com.baidu.mobstat.** { *; }
#--------------Talking Data 统计-------------------------#
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}
-keep class dice.** {*; }
-dontwarn dice.**

#阿里混淆
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**









