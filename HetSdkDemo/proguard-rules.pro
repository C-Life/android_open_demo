#**************************基本不用动区域******************************
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-ignorewarnings                # 抑制警告 忽略警告
-dontshrink # 不压缩输入的类文件
-dontoptimize #优化  不优化输入的类文件
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-dontusemixedcaseclassnames                    # 是否使用大小写混合
-dontskipnonpubliclibraryclasses               # 是否混淆第三方jar
-dontpreverify                                 # 混淆时是否做预校验
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*       # 混淆时所采用的算法
-verbose #混淆时是否记录日志

#######腾讯Bugly混淆################
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}


#####################记录生成的日志数据,gradle build时在本项目根目录输出################

#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt
###################################################################################

-dontwarn okio.**
-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.android.volley.toolbox.**
-dontwarn android.support.**
-dontwarn com.google.android.gms.**
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
#继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep class javax.xml**  { *; }
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep class **.R$* {
 *;
}

-keepclassmembers class * {
    void *(*Event);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#// natvie 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
#RenderScript
-keep class android.support.v8.renderscript.** { *; }

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
#---------------------------------------------------------------------------------------------------

#**************************DEMO混淆区域*******************************
#---------------------------------实体类-------------------------------------
-keep public class com.het.sdk.demo.model.** {*;}
-keep public class com.het.sdk.demo.widget.** {*;}

#===================butterknife======================
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#===================butterknife======================

#****************************第三方公共包***************************************************
#---------------------------------保护第三方包-----------------------------
-keep class org.apache.http.**
-keep interface org.apache.http.**
-dontwarn org.apache.**
#===========sharesdk===========
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}

-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

#xstream
-dontwarn com.thoughtworks.xstream.**
-keep class com.thoughtworks.xstream.** {*;}

#===========nineoldandroids-2.4.0.jar===========
-keep public class com.nineoldandroids.** {*;}

#===========zxing===========
-keep class com.google.zxing.** {*;}
-dontwarn com.google.zxing.**
#===========百度定位===========
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

#==============极光推送开始==============#
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
#==============极光推送结束==============#

#==============百度推送开始==============#
#-libraryjars libs/pushservice-5.9.0.59.jar
-dontwarn com.baidu.**
-keep class com.baidu.**{*; }
#==============百度推送结束==============#

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
###rxandroid-1.2.1
-keepclassmembers class rx.android.**{*;}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

#===========recyclerview-animators===========
-keep class jp.wasabeef.** {*;}
-dontwarn jp.wasabeef.*

#===========ormlite===========
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
#===========umeng（友盟 ）===========
-keep class com.umeng.analytics.** {*;}
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**

#==========facebook==========
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

#================Fresco混淆===START==============#
#==native方法
-keepclassmembers class * {
    native <methods>;
}

# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {@com.facebook.common.internal.DoNotStrip *;}
# can not display gif image.
-keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
    public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory, com.facebook.imagepipeline.core.ExecutorSupplier);
}
-keep class com.facebook.animated.gif.** {*;}
-dontwarn javax.annotation.**
# facebook fresco end -------------------------------------------------
#================Fresco混淆===END==============#

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**

-keep class com.facebook.**
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}

-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#==========友盟自动更新==========
-keep public class com.umeng.fb.ui.ThreadView {
}
-keep public class * extends com.umeng.**
# 以下包不进行过滤
-keep class com.umeng.** { *; }


#==========AndFix==========
-keep class * extends java.lang.annotation.Annotation
-keepclasseswithmembernames class * {
    native <methods>;
}

#==========eventbus 3.0==========
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#==========EventBus==========
-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepclassmembers class ** {
public void xxxxxx(**);
}

#==========gson==========
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ==========support-v4==========
-dontwarn android.support.v4.**
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.app.Fragment

# ==========support-v7==========
-dontwarn android.support.v7.**
-keep class android.support.v7.internal.** { *; }
-keep interface android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }

# ==========support design==========
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
#-------------------------------------------------------------------------

#==========activeandroid==========
-keep class com.activeandroid.** { *; }
-dontwarn com.ikoding.app.biz.dataobject.**
-keep public class com.ikoding.app.biz.dataobject.** { *;}
-keepattributes *Annotation*

-keepattributes Exceptions,InnerClasses

-keep class io.rong.** {*;}

-keep class * implements io.rong.imlib.model.MessageContent{*;}

-keepattributes Signature

-keep class sun.misc.Unsafe { *; }

-keep class com.google.gson.examples.android.model.** { *; }

-keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
 public static java.lang.String TABLENAME;
}
-keep class **$Properties
-dontwarn org.eclipse.jdt.annotation.**

-keep class com.ultrapower.** {*;}

#*****************************公共模块**************************************
-keep class rx.**{*;}
-keep class rx.internal.util.** {*;}
-keep class com.het.basic.data.api.token.model.AuthModel{*;}
-keep class com.het.basic.**{*;}
-keep class com.het.basic.data.http.okhttp.OkHttpManager{*;}
-keep class com.het.basic.AppDelegate{*;}
-keep public class com.het.basic.constact.** { *; }
-keep public class com.het.basic.model.** { *; }
-keep public class com.het.basic.base.helper.RxSchedulers { *; }
-keep public class com.het.basic.data.api.token.HetParamsMerge { *; }
-keep public class com.het.basic.data.api.token.TokenManager { *; }
-keep public class com.het.basic.data.api.login.** { *; }
-keep public class com.het.basic.utils.** { *; }
-keep public class com.het.basic.data.api.utils.** { *; }
-keep public class com.het.basic.data.http.retrofit2.RetrofitManager { *; }
-keep public class com.het.basic.data.http.okhttp.listener.DownloadProgressListener { *; }

#-keep class com.third.factory.Const  { *; }
-keep class com.hiflying.smartlink.SmartLinkedModule  { *; }
-keep class com.handmark.pulltorefresh.library.extras.**  { *; }
-keep class com.het.librebind.constant.**  { *; }
-keep class com.het.librebind.model.**  { *; }
-keep class com.het.librebind.utils.** { *; }
-keep class com.xlwtech.**  { *; }
#System.loadLibrary
-keep class com.mediatek.elian.ElianNative{ *; }
-keep class com.mediatek.elian.ElianNative$LoadLib{
    public <fields>;
    public <methods>;
}
-keepnames class com.mediatek.elian.ElianNative$* {
    public <fields>;
    public <methods>;
}
-keepnames class com.mediatek.elian.ElianNative$* {*;}
-keepnames class com.realtek.simpleconfiglib.Crypt {*;}
-keepnames class com.sctech.cfe.Xactivity {*;}
#====zbar====
-keep class net.sourceforge.zbar.** { *; }
-keep class com.broadcom.cooee.** { *; }
-keep class com.het.zbar.** { *; }
#----------------------------------------------------------------------------------

#---------------------------------BlueToothSupport-------------------------------
#====BlueToothSupport相关实体类====
-keep class com.het.ble.**  { *; }
-keep class com.het.bluetoothbase.**  { *; }
-keep class com.het.bluetoothoperate.**  { *; }
-keep class org.dom4j.**  { *; }
-keep class javax.xml.**  { *; }
-keep class com.het.device.ui.**  { *; }
-keep class com.google.gson.**  { *; }
#----------------------------------------------------------------------------------

#---------------------------------udpcore-------------------------------
#====udpcore相关实体类====
-keep class com.het.wifi.common.model.**  { *; }
-keep class com.het.wifi.common.db.**  { *; }
-keep class com.het.wifi.common.protocol.**  { *; }
-keep class com.het.UdpCore.smartlink.**  { *; }
-keep class com.het.UdpCore.smartlink.callback.**  { *; }
-keep class com.het.UdpCore.smartlink.ti.callback.**  { *; }
-keep class com.het.UdpCore.Utils.Logc  { *; }
-keep class com.het.wifi.common.protocol.coder.bean.** { *; }
#----------------------------------------------------------------------------------

#---------------------------------BluetoothBase-------------------------------
-keep class android.bluetooth.** { *; }
-keep class com.het.bluetoothbase.scan.**  { *; }
-keep class com.het.bluetoothbase.conn.**  { *; }
#----------------------------------------------------------------------------------

#---------------------------------BluetoothOperate-------------------------------
-keep class com.het.bluetoothoperate.model.**  { *; }
-keep class com.het.bluetoothoperate.listener.**  { *; }
#----------------------------------------------------------------------------------

#**************************het的h5库*******************************
-keep class com.het.h5.sdk.manager.** { *; }
-keep class com.het.h5.sdk.callback.** { *; }
-keep class com.het.h5.sdk.bean.** { *; }
-keep class com.het.h5.sdk.event.** { *; }
# --------------------------------------------------------------------------

##===================腾讯X5内核=====STAT=============
-keep class com.tencent.smtt.export.external.**{*;}
-keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {*;}
-keep class com.tencent.smtt.sdk.CacheManager {public *;}
-keep class com.tencent.smtt.sdk.CookieManager {public *;}
-keep class com.tencent.smtt.sdk.WebHistoryItem {public *;}
-keep class com.tencent.smtt.sdk.WebViewDatabase {public *;}
-keep class com.tencent.smtt.sdk.WebBackForwardList {public *;}
-keep public class com.tencent.smtt.sdk.WebView {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
	public static final <fields>;
	public java.lang.String getExtra();
	public int getType();
}
-keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {public <methods>;}
-keep public class com.tencent.smtt.sdk.WebView$PictureListener {
	public <fields>;
	public <methods>;
}
-keep public enum com.tencent.smtt.sdk.WebSettings$** {*;}
-keep public enum com.tencent.smtt.sdk.QbSdk$** {*;}
-keep public class com.tencent.smtt.sdk.WebSettings {public *;}
-keep public class com.tencent.smtt.sdk.ValueCallback {
	public <fields>;
	public <methods>;
}

#===========okhttp===========
-dontwarn com.squareup.okhttp3.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp3.** { *;}
-keep class okio.**{*;}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**
-dontwarn java.nio.file.*
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
