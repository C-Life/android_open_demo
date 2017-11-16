# 开放平台Android APP消息推送集成指南

## 概述  

开放平台APP消息推送集成了极光推送和百度推送功能，封装了推送接口。 替换申请的相关KEY值和相关配置就可以直接使用，简单快捷。节省开发者集成过程的繁琐细节。 本文档基于开放平台APP消息推送集成流程，详细介绍集成百度推送和极光推送流程和注意事项。

## 快速集成极光推送

### 1.极光推送集成准备

#### 1.1.申请极光开发者账号，创建应用。
![](https://i.imgur.com/M0yig29.png)

#### 1.1.2.设置Android推送
![](https://i.imgur.com/Ud78sdp.png)

#### 1.1.3.查看对应的AppKey和Master Secret
![](https://i.imgur.com/XeHtiyk.png)

### 2.APP集成极光推送
目前极光推送SDK只支持Android 2.3或以上版本的手机系统。富媒体信息流功能则需Android3.0或以上版本的系统。

#### 2.1.在 module 的 gradle 中添加依赖

	android {
	    ......
	    defaultConfig {
	        applicationId "com.het.sdk.demo" //JPush上注册的包名和工程包名必须要一致
	        ......
	
	        ndk {
	            //选择要添加的对应cpu类型的.so库。
	            abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a'
	            // 还可以添加 'x86', 'x86_64', 'mips', 'mips64'
	        }
	
	        manifestPlaceholders = [
	            JPUSH_PKGNAME : "com.het.sdk.demo", //填包名
	            JPUSH_APPKEY : "你的appkey", //JPush上注册的包名对应的appkey.
	            JPUSH_CHANNEL : "developer-default", //暂时填写默认值即可.
	        ]
	        ......
	    }
	    ......
	}

	dependencies {
	    ......
	
	    compile 'cn.jiguang.sdk:jpush:3.0.5'  // 此处以JPush 3.0.5 版本为例。
	    compile 'cn.jiguang.sdk:jcore:1.1.2'  // 此处以JCore 1.1.2 版本为例。
	    ......
	}

注 : 如果在添加以上 abiFilter 配置之后android Studio出现以下提示：  

    NDK integration is deprecated in the current plugin. Consider trying the new experimental plugin
则在 Project 根目录的gradle.properties文件中添加：  

    android.useDeprecatedNdk=true

#### 2.2.AndroidManifest的替换变量
	<?xml version="1.0" encoding="utf-8"?>
	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
	    package="您应用的包名">

	    <!-- Required -->
	    <permission
	        android:name="您应用的包名.permission.JPUSH_MESSAGE"
	        android:protectionLevel="signature" />
	
	    <uses-permission android:name="android.permission.RECEIVE_USER_PRESENT" />
	    <uses-permission android:name="android.permission.INTERNET" />
	    <uses-permission android:name="android.permission.WAKE_LOCK" />
	    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
	    <uses-permission android:name="android.permission.VIBRATE" />
	    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
	    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	    <uses-permission android:name="android.permission.WRITE_SETTINGS" />
	    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	
	    <!-- Optional. Required for location feature -->
	    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" /> <!-- 用于开启 debug 版本的应用在6.0 系统上 层叠窗口权限 -->
	    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
	    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
	    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
	    <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS" />
	    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
	    <uses-permission android:name="android.permission.GET_TASKS" />
	
	    <application
	        android:icon="@drawable/ic_launcher"
	        android:label="@string/app_name"
	        android:name="Your Application Name">
	
	        <!-- =====================极光推送开始=========================== -->
        <!-- Required SDK 核心功能 -->
        <!-- option since 2.0.5 可配置PushService，DaemonService,PushReceiver,AlarmReceiver的android:process参数 将JPush相关组件设置为一个独立进程 -->
        <!-- 如：android:process=":remote" -->
        <service
            android:name="cn.jpush.android.service.PushService"
            android:enabled="true"
            android:exported="false">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.REGISTER" />
                <action android:name="cn.jpush.android.intent.REPORT" />
                <action android:name="cn.jpush.android.intent.PushService" />
                <action android:name="cn.jpush.android.intent.PUSH_TIME" />
            </intent-filter>
        </service>

        <!-- since 1.8.0 option 可选项。用于同一设备中不同应用的JPush服务相互拉起的功能。 -->
        <!-- 若不启用该功能可删除该组件，将不拉起其他应用也不能被其他应用拉起 -->
        <!-- <service -->
        <!-- android:name="cn.jpush.android.service.DaemonService" -->
        <!-- android:enabled="true" -->
        <!-- android:exported="true" -->
        <!-- tools:ignore="ExportedService"> -->
        <!-- <intent-filter> -->
        <!-- <action android:name="cn.jpush.android.intent.DaemonService" /> -->


        <!-- <category android:name="您应用的包名" /> -->
        <!-- </intent-filter> -->
        <!-- </service> -->


        <!-- Required -->
        <receiver
            android:name="cn.jpush.android.service.PushReceiver"
            android:enabled="true"
            tools:ignore="ExportedReceiver">
            <intent-filter android:priority="1000">
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED_PROXY" />

                <category android:name="您应用的包名" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.USER_PRESENT" />
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
            <!-- Optional -->
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED" />
                <action android:name="android.intent.action.PACKAGE_REMOVED" />

                <data android:scheme="package" />
            </intent-filter>
        </receiver>
        <!-- Required SDK核心功能 -->
        <activity
            android:name="cn.jpush.android.ui.PushActivity"
            android:configChanges="orientation|keyboardHidden"
            android:exported="false">
            <intent-filter>
                <action android:name="cn.jpush.android.ui.PushActivity" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="您应用的包名" />
            </intent-filter>
        </activity>
        <!-- Required SDK核心功能 -->
        <service
            android:name="cn.jpush.android.service.DownloadService"
            android:enabled="true"
            android:exported="false" />
        <!-- Required SDK核心功能 -->
        <receiver android:name="cn.jpush.android.service.AlarmReceiver" />

        <!-- User defined. 用户自定义的广播接收器 -->
        <receiver
            android:name="自定义 Receiver"
            android:enabled="true"
            tools:ignore="ExportedReceiver">
            <intent-filter>

                <!-- Required 用户注册SDK的intent -->
                <action android:name="cn.jpush.android.intent.REGISTRATION" />
                <!-- Required 用户接收SDK消息的intent -->
                <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED" />
                <!-- Required 用户接收SDK通知栏信息的intent -->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED" />
                <!-- Required 用户打开自定义通知栏的intent -->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED" />
                <!-- Optional 用户接受Rich Push Javascript 回调函数的intent -->
                <action android:name="cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK" />
                <!-- 接收网络变化 连接/断开 since 1.6.3 -->
                <action android:name="cn.jpush.android.intent.CONNECTION" />

                <category android:name="您应用的包名" />
            </intent-filter>
        </receiver>

        <!-- Required. For publish channel feature -->
        <!-- JPUSH_CHANNEL 是为了方便开发者统计APK分发渠道。 -->
        <!-- 例如: -->
        <!-- 发到 Google Play 的APK可以设置为 google-play; -->
        <!-- 发到其他市场的 APK 可以设置为 xxx-market。 -->
        <!-- 目前这个渠道统计功能的报表还未开放。 -->
        <meta-data
            android:name="JPUSH_CHANNEL"
            android:value="developer-default" />
        <!-- Required. AppKey copied from Portal -->
        <meta-data
            android:name="JPUSH_APPKEY"
            android:value="您应用的Appkey" />

        <!-- =====================极光推送结束=========================== -->

	    </application>
	</manifest>

#### 2.3.自定义广播接收器
通过广播可以获取到RegistrationId，表示集成成功了。  

	public class JPushReceiver extends BroadcastReceiver {

	    private static final String TAG = "JPush";
	
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        Bundle bundle = intent.getExtras();
	        Logc.e(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
	
	        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
	            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
	            ACache.get(context).put(JPushInterface.EXTRA_REGISTRATION_ID, regId);
	            Logc.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
	            //send the Registration Id to your server...
	
	        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
	            processCustomMessage(context, bundle);
	        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
	            Logc.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
	            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
	            Logc.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
	        } else {
				...............
	        }
	    }

		..............................
	}

#### 2.4.启动极光推送服务
	//初始化极光推送服务   百度推送不能在application里面初始化 极光推送可以
	HetPushManager.getInstance().init(new JpushService(this));

#### 2.5.停止极光推送服务
	
	HetPushManager.getInstance().stopPush();

## 快速集成百度推送

### 1.百度推送集成准备

#### 1.1.申请百度开发者账号，创建应用
![](https://i.imgur.com/ghbtOqc.png)

#### 1.2.Android 推送配置，获取API KEY和SECRET KEY
![](https://i.imgur.com/2eTzgeA.png)

### 2.APP集成百度推送

#### 2.1.下载最新的Android SDK压缩包并解压，在新建工程或已有工程中增加百度云推送功能。 注意：如果您的Android工程使用的是Android API level 21及以上的版本，您的通知图标背景必须是透明的，否则在Android5.0及以上的机器上通知图标可能会变成白色的方块。

#### 2.2.导入jar包和so库文件（Android Studio 开发环境）
在工程中“src/main”目录中新建名为jniLibs的目录。将解压后的libs文件夹中所有文件拷贝到您的工程的jniLibs文件夹中如果您的工程中没有其他的.so文件，建议只拷贝armeabi文件夹。 如果您的工程中还使用了其他的.so文件，只需要拷贝对应目录下的.so文件即可。

![](https://i.imgur.com/IUZi7wv.png)

#### 2.3.配置AndroidManifest文件
在当前工程的AndroidManifest.xml文件中，添加权限和声明信息：

	<!-- Push service 运行需要的权限 -->
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.READ_PHONE_STATE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
	<uses-permission android:name="android.permission.WRITE_SETTINGS" />
	<uses-permission android:name="android.permission.VIBRATE" />
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	<uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<!-- 富媒体需要声明的权限 -->
	<uses-permission android:name="android.permission.ACCESS_DOWNLOAD_MANAGER"/>
	<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
	<uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
	
	<!-- 适配Android N系统必需的ContentProvider写权限声明，写权限包含应用包名-->
	<uses-permission android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName" />
	<permission
	        android:name="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName"
	        android:protectionLevel="signature">
	</permission>

    <!-- push应用定义消息receiver声明 -->
    <receiver android:name="YourPackageName.BaiduPushReceiver">
         <intent-filter>

            <!-- 接收push消息 -->
            <action android:name="com.baidu.android.pushservice.action.MESSAGE" />
            <!-- 接收bind,unbind,fetch,delete等反馈消息 -->
            <action android:name="com.baidu.android.pushservice.action.RECEIVE" />
            <action android:name="com.baidu.android.pushservice.action.notification.CLICK" />
         </intent-filter>
     </receiver>
	
	<!-- push service start -->
	<!-- 用于接收系统消息以保证PushService正常运行 -->
	<receiver android:name="com.baidu.android.pushservice.PushServiceReceiver"
	    android:process=":bdservice_v1" >
	    <intent-filter>
	        <action android:name="android.intent.action.BOOT_COMPLETED" />
	        <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
	        <action android:name="com.baidu.android.pushservice.action.notification.SHOW" />
	        <action android:name="com.baidu.android.pushservice.action.media.CLICK" />
	        <!-- 以下四项为可选的action声明，可大大提高service存活率和消息到达速度 -->
	        <action android:name="android.intent.action.MEDIA_MOUNTED" />
	        <action android:name="android.intent.action.USER_PRESENT" />
	        <action android:name="android.intent.action.ACTION_POWER_CONNECTED" />
	        <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED" />
	    </intent-filter>
	</receiver>
	<!-- Push服务接收客户端发送的各种请求-->
	<receiver android:name="com.baidu.android.pushservice.RegistrationReceiver"
	    android:process=":bdservice_v1" >
	    <intent-filter>
	        <action android:name="com.baidu.android.pushservice.action.METHOD" />
	        <action android:name="com.baidu.android.pushservice.action.BIND_SYNC" />
	    </intent-filter>
	    <intent-filter>
	        <action android:name="android.intent.action.PACKAGE_REMOVED" />
	        <data android:scheme="package" />
	    </intent-filter>
	</receiver>
	<service android:name="com.baidu.android.pushservice.PushService" android:exported="true"
	    android:process=":bdservice_v1" >
	    <intent-filter >
	            <action android:name="com.baidu.android.pushservice.action.PUSH_SERVICE" />
	    </intent-filter>
	</service>
	
	<!-- 4.4版本新增的CommandService声明，提升小米和魅族手机上的实际推送到达率 -->
	<service android:name="com.baidu.android.pushservice.CommandService"
	    android:exported="true" />
	
	<!-- 适配Android N系统必需的ContentProvider声明，写权限包含应用包名-->
	<provider
	    android:name="com.baidu.android.pushservice.PushInfoProvider"
	    android:authorities="YourPackageName.bdpush"
	    android:writePermission="baidu.push.permission.WRITE_PUSHINFOPROVIDER.YourPackageName"
	    android:protectionLevel = "signature"
	    android:exported="true" />
	
	<!-- push结束 -->

#### 2.4.创建百度推送广播接收器

	public class BaiduPushReceiver extends PushMessageReceiver {
	
	    public static final String TAG = BaiduPushReceiver.class
	            .getSimpleName();
	    public static String Baidu_ChannelId;
	
	
	    @Override
	    public void onBind(Context context, int errorCode, String appid,
	                       String userId, String channelId, String requestId) {
	        String responseString = "onBind errorCode=" + errorCode + " appid="
	                + appid + " userId=" + userId + " channelId=" + channelId
	                + " requestId=" + requestId;
	        Logc.d(TAG, responseString);
	
	        if (errorCode == 0) {
	            // 绑定成功
	            Logc.d(TAG, "绑定成功");
	            Baidu_ChannelId = channelId;
	        }
	    }
		................................
	}

#### 2.5.启动百度推送服务
	//初始化百度推送服务  不能在application 初始化
	//在主 Activiy 的 OnCreate 方法中，调用接口 startWork， 其中 loginValue 是 apiKey。（注意：不
	//要在 Application 的 onCreate 里去做 startWork 的操作，否则可能造成应用循环重启的问题，
	//将严重影响应用的功能和性能。）
	HetPushManager.getInstance().init(new BdPushService(this));


#### 2.6.停止百度推送服务
	HetPushManager.getInstance().stop();

#### 2.7.错误码说明


|error_code | 描述 |
|--------|------------------------------------|
| 0 | 绑定成功 |
| 10001 | 当前网络不可用，请检查网络 |
| 10002 | 服务不可用，连接server失败 |
| 10003 | 服务不可用，503错误 |
| 10101 | 应用集成方式错误，请检查各项声明和权限 |
| 20001 | 未知错误 |
| 30600 | 服务内部错误 |
| 30601 | 非法函数请求，请检查您的请求内容 |
| 30602 | 请求参数错误，请检查您的参数 |
| 30603 | 非法构造请求，服务端验证失败 |
| 30605 | 请求的数据在服务端不存在 |
| 30608 | 绑定关系不存在或未找到 |
| 30609 | 一个百度账户绑定设备超出个数限制(多台设备登录同一个百度账户) |
| 30612 | 百度账户绑定应用时被禁止，需要白名单授权 |