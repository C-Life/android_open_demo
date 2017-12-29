
# 开放平台 Android SDK 集成

开放平台SDK是在android Studio（以下称AS）环境上使用的。封装了设备接入所需的接口，集成简单，操作方便。

## 1.SDK集成准备

### 1.1.创建应用
  AS新建Android项目，然后通过https://open.clife.cn/#/home注册一个开发者账号。登录到开放平台创建应用完善详细资料。此部分请参考《clife开发平台使用手册》。  创建产品之后创建APP获取到后台分配的appId和appSecret。

### 1.2.配置项目根目录build.gradle

	allprojects {
	    repositories {
	        jcenter()
	         maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
	    }
	}

### 1.3.引用SDK到工程

集成了第三方登录的gradle依赖 

	//引用库形式 集成了第三方登录的引用
	compile 'com.github.szhittech:HetCLifeOpenSdk:1.1.2-SNAPSHOT'

基础SDK的gradle依赖

	//引用库形式
	compile 'com.github.szhittech:HetCLifeOpenSdkBase:1.0.2-SNAPSHOT'

模组注册

	    //乐鑫信息科技(esptouchmodule) 模组ID：7
	    compile 'com.github.szhittech:esptouchmodule:1.0.1-SNAPSHOT'
	    //clifeAP绑定(hetapmodule) 模组ID：28
	    compile 'com.github.szhittech:hetapmodule:1.0.1-SNAPSHOT'
	    //clifesmartlink绑定(在庆科基础上修改 hetsmartlink) 模组ID：10
	    compile 'com.github.szhittech:hetsmartlink:1.0.1-SNAPSHOT'
	    //科中龙(realtekmodule) 模组ID：4
	    compile 'com.github.szhittech:realtekmodule:1.0.1-SNAPSHOT'
	    //新力维_NL6621底层库(xlwmodule) 模组ID：6
	    compile 'com.github.szhittech:xlwmodule:1.0.1-SNAPSHOT'
	    //双驰达(sctechmodule) 模组ID：15
	    compile 'com.github.szhittech:sctechmodule:1.0.1-SNAPSHOT'
	    //信驰达_MTK7681底层库(elianmodule) 模组ID：5
	    compile 'com.github.szhittech:elianmodule:1.0.1-SNAPSHOT'
	    //Marvell(marvellmodule) 模组ID：v1=8，v2=27
	    compile 'com.github.szhittech:marvellmodule:1.0.1-SNAPSHOT'
	    //博通(cooeemodule) 模组ID：20
	    compile 'com.github.szhittech:cooeemodule:1.0.1-SNAPSHOT'


查看开放平台产品模组类型，选择模组的依赖包。

![](https://i.imgur.com/98xFDg4.png)

### 1.4.配置AndroidManifest.xml 添加权限

请将下面权限配置代码复制到 AndroidManifest.xml 文件中：

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
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />

权限说明：

|权限 | 用途 |
|--------|------------------------------------|
| INTERNET | 允许应用可以访问网络 |
| READ_PHONE_STATE | 允许应用访问手机状态 |
| ACCESS_NETWORK_STATE | 允许应用获取网络信息状态，如当前的网络连接是否有效 |
| RECEIVE_BOOT_COMPLETED | 系统广播权限 |
| WRITE_SETTINGS | 允许应用读写系统设置项 |
| VIBRATE | 允许应用震动 |
| WRITE_EXTERNAL_STORAGE | 	允许应用写入外部存储 |
| DISABLE_KEYGUARD | 	允许程序禁用键盘锁  |
| ACCESS_COARSE_LOCATION | 	允许程序访问位置信息  |
| ACCESS_WIFI_STATE | 	允许程序访问Wi-Fi网络状态信息  |
| CAMERA | 	允许程序打开相机  |
| WAKE_LOCK | 	允许应用在手机屏幕关闭后后台进程仍然运行  |

#### 1.5.Android6.0系统文件读写权限设置
Android 6.0+新增了运行时权限动态检测，敏感权限必须要动态申请。开发者可以使用SDK提供的RxPermissions来动态申请权限。

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions.getInstance(AppDelegate.getAppContext())
                    .request(Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .subscribe(grant -> {
                        if (!grant) {//申请权限成功与否
                            finish();
                        }
                    });
        }



## 2.SDK配置

### 2.1.初始化SDK

在application初始化SDK，设置日志，开发环境和sdk配置等。

 	/**
     * 配置SKD
     *
     * @param appId
     * @param appSecret
     */
    private void configApplication(String appId, String appSecret) {
        ConfigModel configModel = new ConfigModel();
        configModel.setLog(true); //是否开启log信息
        configModel.setHost(HetCodeConstants.TYPE_PRODUCE_HOST); //环境设置
        configModel.setH5UIconfig(UIJsonConfig.getInstance(this).getJsonString(UIJsonConfig.fileName, this));
        //配置开放平台第三方登录  不需要使用开放平台第三方登录的不需要
        mLoginDelegate = new HetSdkThirdDelegate.Builder(this)
                .registerQQ(UIJsonConfig.getTencentAppID())
                .registerWeixin(UIJsonConfig.getWechatAppID(),UIJsonConfig.getWechatAppSecret())
                .registerSinaWeibo(UIJsonConfig.getSinaAppID(), UIJsonConfig.getSinaAppSecret(), this.mSinaRedirectURL)
               .create();
        HetSdk.getInstance().init(this, appId, appSecret, configModel);
    }

1、appId、appSecret可以在开放平台创建的应用的应用详情里查看。
2、HetSdkThirdDelegate 配置第三方社交平台（微信、QQ、新浪微博登录和分享），需要的开发者自行配置，不需要的可以不要。关于第三方登录的集成请参考   **（SDK第三方登录的集成）**。
3、configModel.setH5UIconfig 配置授权登录页面主题样式; 通过参数定义的JSON字符串来进行配置，例如demoAPP是通过assets/h5UIConfig.json这个文件来组装JSON字符串的。

**接口调用请求说明**
SDK初始化接口 HetSdk.getInstance().init（）

**参数说明**

| 参数名称 | 是否必须 | 字段类型 | 参数说明 |
|---------|---------|---------|---------|
| appId | 是 | string | 应用标识 |
| context | 是 | Context | 上下文常量 |
| secret | 是 | string | 用户密匙 |
| configModel | 是 | ConfigModel | 初始配置 |

**configModel说明**

| 字段名称 | 字段类型 | 字段说明 |
|---------|---------|---------|
| isLog | boolean | 是否开启调试信息 |
| host | int | 网络环境设置: 0x01：正式 0x02：预发布 0x03:内网 0x04：测试环境 |
| H5UIconfig | String | APP初始配置 |

**H5UIconfig配置说明**
SDK的授权登录页面样式可以通过JSON参数来配置，包括是否需要第三方登录，登录页面的样式等。可以参考SDk的DEMO工程通过assets/h5UIConfig.json的配置，配置详情：

	 {
	  "app_id": "your_app_id",
	  "app_secret": "your_app_app_secret",
	  "navBackgroundColor": "FF3285FF",
	  "navTitleColor": "FFFFFFFF",
	  "logoshow": true,
	  "loginBtnBackgroundColor": "FFFFFFFF",
	  "loginBtnBorderColor": "FF5BA532",
	  "loginBtnFontColor": "FF000000",
	  "weiboLogin": true,
	  "weixinLogin": true,
	  "qqLogin": true,
	  "copyRight": "",
	  "privacyPolicy": "",
	  "SMSTemplet": "",
	  "tencent_app_id": "your_tencent_app_id",
	  "wechat_app_id": "your_wechat_app_id",
	  "wechat_app_secret": "your_wechat_app_secret",
	  "sina_app_id": "your_sina_app_id",
	  "sina_app_secret": "your_sina_app_secret"
	}

字段说明：

| 字段名称 | 字段类型 | 字段说明 |
|---------|---------|---------|
| app_id | String | APP标识(开放平台创建应用申请获得) |
| app_secret | String | APP密钥(开放平台创建应用申请获得) |
| navBackgroundColor | String | 授权登录标题栏的背景色（例如：FF3285FF ）|
| navTitleColor | String | 授权登录标题栏的文字颜色（例如：FFFFFFFF）|
| logoshow | boolean | 授权登录页面的安全登录的图标是否显示（false，true）|
| loginBtnBackgroundColor | String | 授权登录页面登录按钮背景颜色（例如：FFFFFFFF）|
| loginBtnBorderColor | String | 授权登录页面登录按钮背景边框颜色（例如：FF5BA532）|
| loginBtnFontColor | String | 授权登录页面登录按钮文字颜色（例如：FF000000）|
| weiboLogin | boolean | 授权登录页面，是否显示微博登录（false，true）|
| weixinLogin | boolean | 授权登录页面，是否显示微信登录（false，true）|
| qqLogin | boolean | 授权登录页面，是否显示QQ登录（false，true）|
| tencent_app_id | String | QQ登录的应用标识（腾讯开放平台创建应用申请获得）|
| wechat_app_id | String | 微信登录的应用标识（微信开放平台创建应用申请获得）|
| wechat_app_secret | String | 微信登录的应用密钥（微信开放平台创建应用申请获得）|
| sina_app_id | String | 新浪微博登录的应用标识（新浪开放平台创建应用申请获得）|
| sina_app_secret | String | 新浪微博的应用密钥（新浪开放平台创建应用申请获得）|
| copyRight | String | 保留字段|
| privacyPolicy | String | 保留字段|
| SMSTemplet | String | 保留字段|

**备注:   颜色值都使用argb的格式，前2位表示透明度，后6位是rgb颜色值。**

### 2.2.注册模组

 	/**
     * 模组注册
     */
	private void registerModule() {
        try {
            ModuleManager.getInstance().registerModule(HeTApModuleImpl.class, getApplicationContext());//clifeAP绑定
            ModuleManager.getInstance().registerModule(HeTSmartlinkImpl.class, getApplicationContext());//clifesmartlink绑定
            ModuleManager.getInstance().registerModule(RealtekModuleImpl.class, getApplicationContext());//科中龙(realtekmodule)
            ModuleManager.getInstance().registerModule(XlwModuleImpl.class, getApplicationContext());//新力维_NL6621底层库
            ModuleManager.getInstance().registerModule(SctechModuleImpl.class, getApplicationContext());//双驰达(sctechmodule)
            ModuleManager.getInstance().registerModule(ElianModuleImpl.class, getApplicationContext());//信驰达_MTK7681底层库
            ModuleManager.getInstance().registerModule(MarvellV1WiFiImpl.class, getApplicationContext());//Marvell(marvellmodule)
            ModuleManager.getInstance().registerModule(MarvellV2WiFiImpl.class, getApplicationContext());//Marvell(marvellmodule)
            ModuleManager.getInstance().registerModule(EsptouchModuleImpl.class, getApplicationContext());//乐鑫信息科技(esptouchmodule)
            ModuleManager.getInstance().registerModule(CooeeModuleImpl.class, getApplicationContext());//博通(cooeemodule)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

在application注册模组，模组注册是按需注册，根据开放平台产品模组类型来选择。

## 3.用户模块

### 3.1.非私有服务器授权登录
HetNewAuthApi.getInstance().authorize() 跳转到授权登录页面。

	 /**
	  * 授权登录
	  *
	  * @param authCallback 回调
	  * @param specifyTitle 标题栏文字
	  * @param navigationBarTextColor 标题栏文字颜色
	  * @param navBackground 标题栏背景颜色
	*/
	public void authorize(Context mContext, AuthCallback authCallback,String specifyTitle,int navigationBarTextColor,int navBackground) {
	    ...
	}
	protected void auth() {

	       HetNewAuthApi.getInstance().authorize(activity, new AuthCallback() {
	          @Override
	          public void onSuccess(int code, String msg) {
	                       //登录成功 do something
	          	}
	          @Override
	          public void onFailed(int code, String msg) {
	                       //登录失败 do something
	          	}
	          },"授权登录",Color.parseColor("#ff3285ff")，Color.parseColor("#FFFFFFFF"));

	    }

登录成功之后，SDK还会抛出HetCodeConstants.Login.LOGIN_SUCCESS事件。 开发者也可以订阅这个事件来监听登录状态。

	RxManage.getInstance().register(HetCodeConstants.Login.LOGIN_SUCCESS, o -> {
	            //登录成功  刷新界面
	});

授权登录页面：

<img src="https://i.imgur.com/0gc7Gqa.png" width = "360" height = "620" alt="图片名称" align=center />

### 3.2.云云对接用户授权登录
为了适应不同的业务需求，同时也考虑平台的安全问题SDK也提供了云云对接用户授权验证接口，该流程请参考文档[C-Life开放平台验证码三方授权流程](%E9%AA%8C%E8%AF%81%E7%A0%81%E4%B8%89%E6%96%B9%E6%8E%88%E6%9D%83%E6%B5%81%E7%A8%8B)。


### 3.3.退出登录

退出登录SDK接口

	HetSdk.getInstance().logout();

例如：

	 public void logout() {
	        new AlertDialog.Builder(activity)
	                .setMessage(activity.getString(R.string.confirm_logout))
	                .setPositiveButton(activity.getString(R.string.logout_sure), (dialog, which) -> {
	                    dialog.dismiss();
	                        HetSdk.getInstance().logout();
	                })
	                .setNegativeButton(activity.getString(R.string.logout_cancel), null)
	                .show();
	    }


退出登录成功，SDK会抛出HetCodeConstants.Login.LOGINOUT_SUCCESS事件。开发者可以订阅这个事件来监听退出登录。
如：接收退出登录的事件，刷新页面

	RxManage.getInstance().register(HetCodeConstants.Login.LOGINOUT_SUCCESS, o -> {
	            //退出登录刷新页面
	});

### 3.4.判断登录状态

判断登录状态的接口

	HetSdk.getInstance().isAuthLogin();

### 3.5.异地登录

开放平台的账号只能在一台设备上登录。当同一个账号同时在2台设备上登录时，服务器会把前面登录成功的设备踢下线。 被踢下线设备的SDK会退出登录，并且抛出HetCodeConstants.Login.EC_LOGINOUT的RxBus事件，通知账号在其他设备登录。
开发者可以订阅这个事件，处理异地登录。 例：

	RxManage.getInstance().register(HetCodeConstants.Login.EC_LOGINOUTT, s -> {
	          //账号在其他设备登录，此时HetSdk.getInstance().isAuthLogin() 为false，跳转页面刷新到未登录状态。
	          .............
	});


### 3.6.获取用户信息

HetUserApi.getInstance().getUserMess()获取用户信息

    /**
     * 获取用户信息
     * @param iCallback 回调
     */
    public void getUserMess(final IHetCallback iCallback) {
       .......


调用实例：

 	public void getUserInfo() {
        //账号登录之后才可以获取到用户信息，否则获取不到
        if (!HetSdk.getInstance().isAuthLogin()) return;
        HetUserApi.getInstance().getUserMess(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                //获取用户信息成功
                Type type = new TypeToken<HetUserInfoBean>() {
                }.getType();
                //users 包含账号的所有用户信息
                HetUserInfoBean users = GsonUtil.getGsonInstance().fromJson(msg, type);
            }
            @Override
            public void onFailed(int code, String msg) {
                //获取用户信息失败
            }
        });
    }


接口返回UserInfoBean的用户详细参数说明：

| 参数名称 | 字段类型 | 参数说明 |
|---------|---------|---------|
| userId | String | 用户ID |
| userName | String | 用户名称 |
| email | String | 用户邮箱 |
| sex | number | 性别（1-男，2-女）|
| birthday | String | 生日（yyyy-MM-dd）|
| weight | number | 体重（克）|
| height | number | 身高（厘米）|
| avatar | String | 头像URL |
| city | String | 城市名 |
| account | String | 登录账号 |

### 3.7.修改密码

调用 HetNewAuthApi.getInstance().alterPassword() 跳转到修改密码的页面。

	/**
	     * 修改密码
	     * @param mContext 上下文
	     * @param authCallback  回调
	     * @param phone 手机号
	     * @param specifyTitle 标题栏文字
	     * @param navigationBarTextColor 标题栏文字颜色
	     * @param navBackground 标题栏背景颜色
	     * @throws Exception
	     */
	public void alterPassword(Context mContext, AuthCallback authCallback, String phone,String specifyTitle,int navigationBarTextColor,int navBackground) throws Exception{
	        ............
	}

通过用户的登录账号来修改密码，调用实例：

	public void editPwd(String account) {
	        if (!HetSdk.getInstance().isAuthLogin()) return;
	        HetNewAuthApi.getInstance().alterPassword(activity, new AuthCallback() {
	           @Override
	           public void onSuccess(int code, String msg) {
	                    //修改密码成功
	           }
	           @Override
	           public void onFailed(int code, String msg) {
	                   //修改密码失败
	           }
	        }, account, "修改密码",Color.parseColor("#ff3285ff")，Color.parseColor("#FFFFFFFF"));
	}

修改密码页面：  

<img src="https://i.imgur.com/jJyysqX.png" width = "360" height = "620" alt="图片名称" align=center />

## 4.设备绑定

开放平台封装了wifi设备和蓝牙设备绑定接口，包括手机与服务器、手机与智能设备的通讯接口。开发者只要获取到设备的产品ID就可以调用SDK的绑定接口进行设备绑定，不需要关注复杂的绑定流程。  

### 4.1.获取产品ID 
开放平台SDK提供了三种方式来获取产品ID。  

第一种：获取设备大类和设备小类（设备小类信息包含产品ID），具体分成2个步骤。  
1.获取设备大类型  
HetDeviceListApi.getInstance().getTypeList() 获取APP支持绑定的设备大类型。  
调用实例：

	HetDeviceListApi.getInstance().getTypeList(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    Type type = new TypeToken<List<DeviceTypeModel>>() {
                    }.getType();
                    //获取设备大类成功  刷新UI
                    List<DeviceTypeModel> models = GsonUtil.getGsonInstance().fromJson(list, type);
                    ............
                }
            }
            @Override
            public void onFailed(int code, String msg) {
                //获取设备大类列表失败
                   .............
            }
     });

服务器返回的结果示例：  

	{
	    "data": [
	    {
	
	        "deviceTypeId": 1,
	        "deviceTypeName": "冰箱",
	        "deviceTypeIcon": null
	    }],
	    "code": 0
	}

DeviceTypeModel参数说明：

| 字段名称 | 字段类型 | 参数说明 |
|---------|---------|---------|
| deviceTypeId | number | 设备大分类标识 |
| deviceTypeName | String | 设备大分类名称 |
| deviceTypeIcon | String | 设备图标 |

2.获取设备小类型  
HetDeviceListApi.getInstance().getSubTypeListProduct() 获取APP支持绑定的设备小类型。  
通过选择的设备大类，查询大类下的小类列表。  

	HetDeviceListApi.getInstance().getSubTypeListProduct(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                if (code == 0) {
                    Type type = new TypeToken<List<DeviceSubModel>>() {
                    }.getType();
                    List<DeviceSubModel> models = GsonUtil.getGsonInstance().fromJson(list, type);
                }
            }
            @Override
            public void onFailed(int code, String msg) {
				//获取设备小类列表失败
                   .............
            }
        }, deviceType);//deviceType 设备大分类标识

服务器返回结果示例：  

	{
	    "data": [
	    {
	        "deviceTypeId": 1,
	        "deviceSubtypeId": 1,
	        "deviceSubtypeName": "冰箱",
	        "productId": 1,
	        "productName": "惠而浦-KST",
	        "productCode": "kst-001",
	        "productIcon": "http://200.200.200.50/v1/device/icon/1.png",
	        "moduleId": 3,
	        "moduleType": 1,
	        "moduleName": "汉枫"，
	        "remark": "备注",
	        "radiocastName":null,
	        "ssid":null,
	        "ssidPassword":null,
	        "deviceCode": "0000C3AA00010105",
	        "guideUrl"："http://200.200.200.50/XXX
	    }],
	    "code": 0
	}

返回字段说明：  

| 字段名称 | 字段类型 | 参数说明 |
|---------|---------|---------|
| deviceTypeId	 | number | 设备大分类标识 |
| deviceSubtypeId | number | 设备子分类标识 |
| deviceSubtypeName	 | string | 设备子分类名称 |
| productId | number | 设备型号标识 |
| productName | string | 设备型号名称 |
| productCode | string | 设备型号编码 |
| productIcon | string | 设备型号图标 |
| moduleId | number | 模块类型（1-WiFi，2-蓝牙，3-音频，4-GSM，5-红外，6-直连，8-zigbee，9-ap模式） |
| moduleType | number | 设备型号图标 |
| moduleName | string | 模块名称 |
| remark | string | 备注 |
| radiocastName | string | 设备广播名 |
| ssid | string | WIFI ssid |
| ssidPassword | string | ssid密码 |
| deviceCode | string | 设备编码 |
| guideUrl | string | 引导页URL |

获取到设备的产品ID（productId 字段），就可以开始绑定了。

第二种：扫描开放平台创建的产品二维码来获取。  
![](https://i.imgur.com/Tge2Ypk.png)

扫描结果示例：  

	http://open.clife.net/v1/web/open/product?param={"a":3531}

3531 即是产品ID。

解析扫描结果，调用 HetQrCodeApi.getInstance().dealQrCode()方法获取产品信息

	//二维码规则
    private void parseQrCodeVersion(String url) {
        String param = Uri.parse(url).getQueryParameter("param");
        if (TextUtils.isEmpty(param)) {
            tips(getResources().getString(R.string.qr_code_error));
        } else {
            HetQrCodeApi.getInstance().dealQrCode(new IHetCallback() {
                @Override
                public void onSuccess(int code, String msg) {
                    DeviceProductBean deviceProductBean = new Gson().fromJson(msg, DeviceProductBean.class);
                    //获取到产品信息之后，按照第三步和第四步的流程来绑定
                }
                @Override
                public void onFailed(int code, String msg) {
                    //获取到产品信息失败
                    finish();
                }
            }, url);
        }
    }


第三种：在开放平台后台直接直接查看产品ID，详情请查《clife开发平台使用手册》。  
![](https://i.imgur.com/TDwtXPH.png)


根据项目需求选择合适的方式来获取产品ID。然后根据设备类型选择SDK的绑定接口。 具体分为WIFI绑定和蓝牙绑定2种。通过设备小类(moduleType字段)判断是WIFI设备还是蓝牙设备，进入相应的绑定设备绑定流程。 如：

	int type = deviceSubModel.getModuleType(); // type == 1标识WIFI  type ==2标识蓝牙  type ==9标识AP模式

| moduleType | 绑定类型 |
|---------|---------|
| 1	 | wifi设备 SmartLink绑定 |
| 2 |  蓝牙设备 |
| 9	 | wifi设备 AP绑定 |


### 4.2.根据产品ID获取产品信息
	/**
     * 根据产品id获取信息
     * @param productId 产品ID
     * @param callback
     */
	public static void getProductByProductId(String productId, IHetCallback callback) {
       ....
    }
调用示例：  

    HetQrCodeApi.getProductByProductId(productId, new IHetCallback() {
            @Override
            public void onSuccess(int i, String s) {
                //获取产品信息成功
                ............
            }

            @Override
            public void onFailed(int i, String s) {
               //获取产品信息失败
			   ............
            }
    });

正确的Json返回结果：

	{
	    "code": 0,
	    "data": {
	            "productId": 6,
	            "deviceSubtypeId": 3,
	            "deviceSubtypeName": "多门冰箱",
	            "productIcon": "http://200.200.200.50/v1/device/icon",
	            "productName": "通用冰箱",
	            "productCode": "CC-1003",
	            "bindType": 1,
	            "moduleId": 2,
	            "moduleName": "TI CC3200R2",
	            "moduleType": 1,
	            "deviceTypeId": 1,
	            "radiocastName": "HET_",
	            "deviceCode": "00000199000B0301",
	            "guideUrl"："http://200.200.200.50/XXX
	    }
	}
返回字段说明：  

| 字段名称 | 字段类型 | 字段说明 |
|---------|---------|---------|
| productId | int | 产品ID |
| deviceSubtypeId | int | 设备子分类ID |
| deviceSubTypeName | string | 设备子分类名称 |
| productIcon | string | 产品LOGO |
| productName | string | 设备接入秘钥 |
| productCode | 	string | 	产品型号 |
| bindType | int | 绑定类型 |
| moduleId | int | 芯片模块ID |
| moduleName | string | 芯片模块名称 |
| moduleType | int | 模块类型（1-WiFi，2-蓝牙，9-ap模式） |
| deviceTypeId | int | 设备分类ID |
| radiocastName | string | 设备广播名 |
| deviceTypeName | string | 设备分类名称 |
| deviceCode | string | 设备编码 |
| guideUrl | string | 引导页URL |

### 4.3.设备绑定

#### 4.3.1.WIFI设备绑定
WIFI设备绑定分AP模式绑定和smartLink模式绑定。

##### 4.3.1.1.绑定模式简介

WIFI设备AP绑定流程图如下：

![](https://i.imgur.com/AXPq6FR.png)

APP启动绑定之前，将设备设置成配置模式，设备将会成为一个Wifi热点。 手机连接设备热点，将发送要配置的路由器ssid和密码给设备，然后APP将配置信息给设备，之后设备自行于服务器绑定，APP从服务器查询绑定状态。  
使用开放平台提供的模组固件，设备产生的Wifi热点以“HET-xxx”开头，没有密码。其他厂商提供的模组，SoftAP热点名称由各自厂商指定。  
AP绑定的交互流程：  
1.获取路由器ssid和密码  
2.手机连接路由器热点  
3.手机切换设备热点  
4.传入参数 产品ID productId、设备大类ID、设备小类ID、路由器ssid 和 密码，启动绑定  

WIFI设备SmartLink绑定流程图如下：

 ![](https://i.imgur.com/J5AWpvN.png)

APP启动绑定之前，将设备设置成配置模式。 APP发送要配置的路由器ssid和密码，开启扫描设备服务将扫描到的设备进行绑定，获取绑定结果。  
1.获取路由器ssid和密码  
2.传入参数产品ID productId，路由器ssid 和 密码，启动绑定  

##### 4.3.1.2.启动绑定  

HetWifiBindApi.getInstance().startBind() 启动绑定。

	/**
	     * 开始绑定设备
	     *
	     * @param ssid wifi名称
	     * @param wifiPassword wifi密码
	     * @param productId    产品ID
	     * @param iWifiBind    绑定接口回调
	     */
	public void startBind(Activity activity,String ssid,String wifiPassword, String productId, IWifiBind iWifiBind) {
	           .............
	    }
	使用实例：
	HetWifiBindApi.getInstance().startBind(this, sSidInfoBean.getSsid(), sSidInfoBean.getPass(), "" + currentDevice.getProductId(), new IWifiBind() {
	            @Override
	            public void onStatus(HetWifiBindState hetWifiBindState, String msg) {
	            }
	            @Override
	            public void onFailed(int errId, String msg) {
	                //绑定失败  显示失败界面
	                showBindFail();
	            }
	            @Override
	            public void onSuccess(DeviceModel deviceModel) {
	                //绑定成功  显示成功界面
	                showBindSuccess();
	            }
	            @Override
	            public void onProgress(int type, int value) {
	                //扫描绑定的进度
	                if(HetDeviceConstans.SCAN_PROGESS == type) {//扫描的进度
	                    setTextProgress(value);
	                }else if(HetDeviceConstans.BIND_PROGESS == type){//绑定的进度
	                    showBindDialog();
	                }
	            }
	        });



#### 4.3.2.BLE蓝牙设备绑定
HetCommonBleBindApi.getInstance().startBind() 启动蓝牙设备扫描绑定。整个过程有2个步骤：  
第一步：扫描搜索周围设备；  
第二步：选择扫描到的某个设备绑定到服务器；  

蓝牙网关设备绑定方式跟前面的方式有些小区别，两种方式的蓝牙设备绑定流程图如下：

 ![](https://i.imgur.com/b4qGgZi.png)

具体的接口调用说明：

	HetCommonBleBindApi.getInstance().startBind(this, "" + deviceProductBean1.getProductId(), new ICommonBleBind() {
	            @Override
	            public void onScanDevices(int code, String devices) {
	                if (code == 0) {
	                    Type type = new TypeToken<List<DeviceProductBean>>() {
	                    }.getType();
	                    List<DeviceProductBean> scanDevices = GsonUtil.getGsonInstance().fromJson(devices, type);
	                    //获取到扫描到的蓝牙设备,显示扫描的设备列表。选择某一个设备之后调用 bindToServer绑定到服务器
	                    showScanSucess(scanDevices);
	                }
	            }
	            @Override
	            public void onFailed(int errId, String msg) {
	                  //绑定失败 刷新界面
	                hideDialog();
	                showBindFail();
	            }
	            @Override
	            public void onSuccess(DeviceModel deviceModel) {
	                 //绑定成功 刷新界面
	                showBindSuccess();
	            }
	            @Override
	            public void onProgress(int type, int value) {
	                if (HetDeviceConstans.SCAN_PROGESS == type) { //扫描进度
	                    setTextProgress(value);
	                } else if (HetDeviceConstans.BIND_PROGESS == type) {//绑定进度
	                    showBindDialog();
	                }
	            }
	        });
	
	//扫描到设备列表，选择其中一个设备，绑定到服务器
	HetCommonBleBindApi.getInstance().bindToServer(deviceProductBean);


**绑定无法绑定？这里给出设备无法绑定的几种检查方法：**  

设备是否置为绑定模式，是否在绑定的有效时间内  
是否正确输入wifi密码,请确认手机是否能正常连接网络  
是扫描不到设备还是绑定不了设备,扫描失败会有对应提示是扫描不到设备，还是绑定不了设备  
设备是否已在CLife开放平台注册，并按照要求将大小类信息写入设备中  
APP端服务是否开启（udpservice）


## 5.设备管理

### 5.1.设备model说明

SDK所有的设备devicemodel，参数说明  

| 字段名称 | 字段类型 | 字段说明 |
|---------|---------|---------|
| deviceId | String | 设备标识 |
| macAddress | String | MAC地址 |
| deviceBrandId | number | 设备品牌标识 |
| deviceBrandName | String | 设备品牌名称 |
| deviceTypeId | number | 设备大分类标识 |
| deviceTypeName | String | 设备大分类名称 |
| deviceSubtypeId | number | 设备子分标识 |
| deviceSubtypeName | String | 设备子分类名称 |
| deviceName | String | 设备名称 |
| roomId | String | 房间标识 |
| roomName | String | 房间名称 |
| authUserId | String | 授权设备用户标识 |
| bindTime | String | 绑定时间 |
| onlineStatus | number | 在线状态（1-正常，2-异常） |
| share | number | 设备分享（1-是，2-否，3-扫描分享） |
| controlType | number | 控制类型（1-原生，2-插件，3-H5插件） |
| userKey | String | MAC与设备ID生成的KEY |
| productId | number | 设备型号标识 |
| productName | String | 设备型号名称 |
| productCode | String | 设备型号编码 |
| productIcon | String | 设备型号图标 |
| moduleId | number | 模块标识 |
| moduleType | number | 模块类型（1-WiFi，2-蓝牙，9-AP模式） |
| moduleName | String | 模块名称 |
| radiocastName | String | 设备广播名 |
| deviceCode | String | 设备编码 |

### 5.2.获取设备列表

HetDeviceListApi.getInstance().getBindList()获取设备列表，设备按照归属来划分有2种：  
第一种是绑定设备。 用户绑定的设备，这类设备用户拥有这台设备的所有权限。  
第二种是分享设备。 别人分享的设备，这类设备用户拥有控制权限，但是不可以再分享给其他人。   
可以根据设备deviceModel的share字段来判断是否是分享的设备。

	HetDeviceListApi.getInstance().getBindList(new IHetCallback() {
	        @Override
	        public void onSuccess ( int code, String s){
	            if (code == 0) {
	                if (!TextUtils.isEmpty(s)) {
	                    Type type = new TypeToken<List<DeviceModel>>() {
	                    }.getType();
	                    List<DeviceModel> models = GsonUtil.getGsonInstance().fromJson(list, type);
	                    //获取设备列表成功列表
	                }
	            }
	        }
	        @Override
	        public void onFailed ( int code, String msg){
	            //获取列表失败
	        }
	}


### 5.3.删除设备

SDK删除设备列表中的设备有2中情况：  
第一种：绑定设备。 调用HetDeviceManagerApi.getInstance().unBind()解除绑定。 实例：
 
	HetDeviceManagerApi.getInstance().unBind(deviceModel, new IHetCallback() {
	   @Override
	   public void onSuccess(int code, String msg) {
	   //解绑成功
	   }
	   @Override
	   public void onFailed(int code, String msg) {
	   //解绑失败
	   }
	});

第二种：分享设备。 调用HetDeviceShareApi.getInstance().deviceDel()解绑分享关系。  实例：  

	HetDeviceShareApi.getInstance().deviceDel(new IHetCallback() {
		 @Override
		 public void onSuccess(int code, String msg) {
		   iHetCallback.onSuccess(code, msg);
		 }
		@Override
		public void onFailed(int code, String msg) {
		  iHetCallback.onFailed(code, msg);
		}
	}, deviceModel.getDeviceId(), null);

传参是设备ID和用户UserId。 UserId传null表示被分享者解除分享关系，传userId表示设备拥有者回收这个用户的设备控制授权。

### 5.4.设备分享

#### 5.4.1.获取设备授权的用户列表    

调用HetDeviceShareApi.getInstance().getDeviceAuthUser()获取设备授权的用户列表，调用实例：

	HetDeviceShareApi.getInstance().getDeviceAuthUser(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    Type type = new TypeToken<List<DeviceAuthUserModel>>() {
                    }.getType();
                    List<DeviceAuthUserModel> deviceAuthUsers= GsonUtil.getGsonInstance().fromJson(s, type);
                    //获取到设备授权的用户列表
                        .................
                }
            }
            @Override
            public void onFailed(int code, String msg) {
                  //获取设备授权的用户列表失败
            }
        }, deviceId);

DeviceAuthUserModel 的字段说明：

| 参数名称 | 字段类型 | 参数说明 |
|---------|---------|---------|
| userId | String | 用户ID |
| userName | String | 用户名称 |
| avatar | String | 用户名称 |
| authTime | String | 授权时间 |

#### 5.4.2.用户设备授权删除  

调用HetDeviceShareApi.getInstance().deviceDel()解除分享关系。

	HetDeviceShareApi.getInstance().deviceDel(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                   //删除成功,刷新获取设备授权用户列表
                    ...................
                }
            }
            @Override
            public void onFailed(int code, String msg) {
               //删除失败
            }
        }, deviceId, userId);


#### 5.4.3.分享设备 

设备分享有面对面二维码分享和第三方平台（微信 、QQ等）分享两种方式。 绑定设备才能分享，分享设备不能再分享给其他用户。  

第一种：面对面分享，通过deviceId（要分享的设备的标识）获取分享码，分享的流程图如下：

 ![](https://i.imgur.com/2k0B7F0.png)

具体的接口调用说明：

	 /**
	 * @param shareType   5是面对面分享  6第三方分享
	 */
	HetDeviceShareApi.getInstance().getShareCode(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    Type treeType = new TypeToken<ShareCodeModel>() {
                    }.getType();
                    ShareCodeModel codeModel = GsonUtil.getGsonInstance().fromJson(s, treeType);
                    //分享邀请码获取成功
                }
            }
            @Override
            public void onFailed(int code, String msg) {
                //分享邀请码获取失败
            }
        }, deviceId, shareType);

ShareCodeModel字段说明:  

| 参数名称 | 字段类型 | 参数说明 |
|---------|---------|---------|
| shareCode | String | 设备分享码 （面对面分享） |
| h5Url | String | H5 页面地址（第三方分享） |

将分享码用二维码的形式展示。被分享的用户，通过二维码扫描，调用 HetDeviceShareApi.getInstance().authShareDevice()完成设备分享。

    HetDeviceShareApi.getInstance().authShareDevice(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                ToastUtil.showToast(mContext, "设备分享成功");
                RxManage.getInstance().post(HetShareEvent.HET_EVENT_MAIN_SHARE_SUCCEE,null);
            }
            @Override
            public void onFailed(int code, String msg) {
                ToastUtil.showToast(mContext, "设备分享失败");
            }
        }, code, "5");//5是面对面分享   6是远程分享


设备分享成功，抛出RxBus事件，监听事件刷新设备列表。  

第二种：第三方分享（通过QQ、微信分享设备）  
利用第三方社交平台，可以快速的实现设备分享。有利于实现产品的快速推广。 远程的第三方分享一定要集成第三方分享服务。详细集成实例请参考下面 **第三方平台服务的集成（登录和分享）**的集成。 分享的流程图如下：

 ![](https://i.imgur.com/KkINUuf.png)

具体的流程有3个步骤：  
第一步：获取分享URL地址  

HetDeviceShareApi.getInstance().getShareCode(IHetCallback callback,String deviceId,String shareType)
参数 shareType(5是面对面分享  6远程分享) 标识分享类型，返回远程分享设备的分享地址。

	HetDeviceShareApi.getInstance().getShareCode(new IHetCallback() {
            @Override
            public void onSuccess(int code, String s) {
                if (code == 0) {
                    Type treeType = new TypeToken<ShareCodeModel>() {
                    }.getType();
                    ShareCodeModel codeModel = GsonUtil.getGsonInstance().fromJson(s, treeType);
                    //分享邀请码获取成功
                     String shareUrl = codeModel.getH5Url();
                     //调用第三分享接口把这个网页地址分享到第三方平台
                      .............
                }
            }
            @Override
            public void onFailed(int code, String msg) {
                //分享邀请码获取失败
            }
        }, deviceId, "6");


第二步:分享者分享URL到第三方平台

SDK提供了第三方分享的接口(暂时只支持微信，QQ，新浪微博)。分享者把URL分享地址通过第三方平台发送给分享者，邀请被分享者控制设备。

1.集成第三方服务  
详细的集成流程请查看 [第三方平台服务的集成（登录和分享）](#jump)

2.初始化SKD第三方分享接口

    HetSdkThirdDelegate mShareManager = HetSdkThirdDelegate.getInstance();
        CommonShareProxy mShareProxy = new CommonShareProxy(this);
        mShareManager.setShareOperate(new CommonShareOperate(mContext));
        mICommonShareListener = new ICommonShareListener() {
            @Override
            public void onStartShare(CommonSharePlatform sharePlatform) {
                CommonShareWebpage webpage = new CommonShareWebpage(sharePlatform);
                webpage.setUiListener(this);
                webpage.setTitle("设备分享");
                webpage.setDescription("设备分享，极速体验");
                webpage.setAppName(getString(R.string.app_name));
                webpage.setTargetUrl(shareUrl);
                webpage.setWebpageUrl(shareUrl);
                webpage.setBm(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_share));
                webpage.setSharePlatform(sharePlatform);
                mShareManager.shareWebpage(webpage);
            }
            @Override
            public void onShareSuccess(CommonSharePlatform sharePlatform, String msg) {
                UserMessShareActivity.this.runOnUiThread(() -> {
                    ToastUtil.showToast(mContext, "分享成功");
                });
            }
            @Override
            public void onShareFialure(CommonSharePlatform sharePlatform, String msg) {
                UserMessShareActivity.this.runOnUiThread(() -> {
                    ToastUtil.showToast(mContext, "分享失败");
                });
            }
            @Override
            public void onShareCancel(CommonSharePlatform sharePlatform, String msg) {
                UserMessShareActivity.this.runOnUiThread(() -> {
                    ToastUtil.showToast(mContext, "分享取消");
                });
            }
        };

3.添加回调
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mShareManager != null && mShareProxy != null) {
            mShareProxy.onActivityResult(requestCode, resultCode, data);
        }
    }
4.开始分享

	mICommonShareListener.onStartShare(CommonSharePlatform.WeixinFriend);//微信分享
	mICommonShareListener.onStartShare(CommonSharePlatform.QQ_Friend);//QQ分享
	分享类型有5种
	public enum CommonSharePlatform {
	    WeixinFriend,//微信好友
	    WeixinFriendCircle,//微信朋友圈
	    QQ_Friend,//QQ好友
	    QQ_Zone,//QQ空间
	    SinaWeibo;//新浪微博
	.....
	}

5.退出释放资源

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mShareManager != null) {
            mShareManager.releaseResource();
            mShareManager = null;
        }
    }

第三步:被分享者接收设备控制邀请，完成设备的分享。

被分享者通过第三方平台（微信或QQ）收到URL，在浏览器打开URL。浏览器请求打开用户APP（通过scheme协议），用户APP获取到shareCode调用HetDeviceShareApi.getInstance().authShareDevice() 接收设备控制邀请。  

    HetDeviceShareApi.getInstance().authShareDevice(new IHetCallback() {
        @Override
        public void onSuccess(int code, String msg) {
            RxManage.getInstance().post(HetShareEvent.HET_EVENT_MAIN_SHARE_SUCCEE, null);
        }

        @Override
        public void onFailed(int code, String msg) {
        }
    }, shareCode, "6");

浏览器打开用户APP的scheme协议，需要在 AndroidManifest.xml 中加上一下代码

        <activity
            android:name="your_open_activity"
            android:screenOrientation="portrait">

            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data android:scheme="hetopenplatform" />
            </intent-filter>

        </activity>

注意：该Activity是浏览器请求打开app第一个请求打开的界面。


## 6.设备控制

SDK封装了WIFI设备控制接口和BLE蓝牙设备控制接口，需要根据具体的设备类型来调用。

| 字段名称 | 字段类型 | 参数说明 |
|---------|---------|---------|
| moduleType | number | 模块类型（1-WiFi，2-蓝牙，9-WIFI-AP模式） |

### 6.1.WIFI设备控制 

wifi设备控制流程图示如下：

 ![](https://i.imgur.com/6Q1Dk6V.png)

SDK提供统一的数据流接口。接收设备数据和监听设备状态都是通过IWifiDeviceData这个接口来完成，发送数据调用HetDeviceWifiControlApi.getInstance().setDataToDevice()。  

IWifiDeviceData 的接口说明：

	public interface IWifiDeviceData {
	    /**
	     * 获取控制数据
	     * @param jsonData
	     */
	    void onGetConfigData(String jsonData);
	
	    /**
	     * 获取运行数据
	     * @param jsonData
	     */
	    void onGetRunData(String jsonData);
	
	    /**
	     * 获取异常数据
	     * @param jsonData
	     */
	    void onGetErrorData(String jsonData);
	
	    /**
	     * 设备状态
	     * @param onlineStatus 在线状态（1-正常，2-异常不在线）
	     */
	    void onDeviceStatues(int onlineStatus);
	
	    /**
	     * 收取数据异常
	     * @param code 错误码
	     * @param msg  错误信息
	     *  1000 无法连接服务器
	     *  1001 设备不在线
	     */
	    void onDataError(int code, String msg);
	}

WIFI设备控制具体可以分成3个步骤：  

第一步：设置接收设备数据的监听 

	HetWifiDeviceControApi.getInstance().start(deviceModel.getDeviceId(), deviceModel.getMacAddress());
	private IWifiDeviceData iWifiDeviceData = new IWifiDeviceData() {
	        @Override
	        public void onGetConfigData(String jsonData) {
	            //获取到设备上报控制数据   根据开放平台配置的协议解析成相应的数据
	            System.out.println("onGetConfigData: " + jsonData);
	            LedConfigModel configModel = GsonUtil.getInstance().toObject(jsonData, LedConfigModel.class);
	            if (ledConfigModel != null) {
	                ledConfigModel = configModel;
	            }
	        }
	        @Override
	        public void onGetRunData(String jsonData) {
	             //获取到设备上报运行数据   根据开放平台配置的协议解析成相应的数据
	            System.out.println("onGetRunData: " + jsonData);
	            LedRunDataModel runDataModel = GsonUtil.getInstance().toObject(jsonData,    LedRunDataModel.class);
	            if (runDataModel != null) {
	                ledRunDataModel = runDataModel;
	            }
	        }
	        @Override
	        public void onGetErrorData(String jsonData) {
	           //获取到设备上报故障数据   根据开放平台配置的协议解析成相应的数据
	            System.out.println("onGetErrorData: " + jsonData);
	        }
	        @Override
	        public void onDeviceStatues(int onlineStatus) {
	        }
	        @Override
	        public void onDataError(int code, String msg) {
	            System.out.println("onDataError: " + msg + " code " + code);
	        }
	    };

可以根据IWifiDeviceData 的回调接口接收设备数据渲染UI。  

第二步：控制设备  

调用HetDeviceWifiControlApi.getInstance().setDataToDevice()发送控制数据到服务器。调用的时候把需要发送的控制数据组装好。

	HetDeviceWifiControlApi.getInstance().setDataToDevice(new IHetCallback() {
	            @Override
	            public void onSuccess(int code, String msg) {
	            }
	            @Override
	            public void onFailed(int code, String msg) {
	            }
	       }, deviceModel.getDeviceId(), GsonUtil.getInstance().toJson(ledConfigModel));

第三步：释放资源  
停止使用设备控制功能的时候释放资源，例如退出控制界面时，实例：

	 @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        HetWifiDeviceControApi.getInstance().stop();
	    }

注意: 设备控制的时候会有一个updateFlag字段。  
这个修改标记位是为了做统计和配置下发的时候设备执行相应的功能。下发数据必须传递updateflag标志

例如，空气净化器（广磊K180）配置信息协议：
紫外线(1)、负离子(2)、臭氧(3)、儿童锁(4)、开关(5)、WiFi(6)、过滤网(7)、模式(8)、定时(9)、风量(10) 上面一共上10个功能，那么updateFlag就2个字节，没超过8个功能为1个字节，超过8个为2个字节，超过16个为3个字节，以此类推。

打开负离子，2个字节，每一个bit的值为下：
0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0

### 6.2.蓝牙设备控制 
  
蓝牙设备控制主要是通过手机和蓝牙设备先建立连接，然后根据定义的协议进行数据交互，具体的交互流程图如下：

 ![](https://i.imgur.com/mFZmrXE.png)

具体的蓝牙控制分成5个步骤：  

第一步：初始化HetCommonBleControlApi

	HetCommonBleControlApi.getInstance().init(this);

第二步：手机建立蓝牙连接,设置数据的收发回调

	private void connect() {
	    HetCommonBleControlApi.getInstance().connect(macAddress,connectCallback);
	}
	private final IConnectCallback connectCallback = new IConnectCallback() {
	    @Override
	    public void onConnectSuccess(BluetoothGatt gatt, int status) {
	            conDevice.setText("连接成功");
	            HookManager.getInstance().enableHook(true, "enable@hook");
	            HookManager.getInstance().addHook(hookCallBack);
	    }
	    @Override
	    public void onConnectFailure(final BleException exception) {
	            conDevice.setText("连接失败");
	    }
	    @Override
	    public void onDisconnect(String mac) {
	            conDevice.setText("连接断开");
		}
	}


IHookCallBack 监听发送数据和接收数据的回调。开发者可以在此监听app发送的数据和收到的设备数据。
	
	 private IHookCallBack hookCallBack = new IHookCallBack() {
	        @Override
	        public void onWrite(byte[] bytes) {
	            showInfo("Send:" + HexUtil.encodeHexStr(bytes));
	        }
	        @Override
	        public void onRead(byte[] bytes) {
	            showInfo("Read:" + HexUtil.encodeHexStr(bytes));
	        }
	        @Override
	        public void onReceived(byte[] bytes) {
	            showInfo("Rec:" + HexUtil.encodeHexStr(bytes));
	        }
	    };

备注:connect方法是允许重复调用的。对于已经连接的成功的连接，在此调用connect方法，SDK会直接返回连接成功不会再次去连接设备。  

第三步：写数据  
SDK对开放平台蓝牙设备提供写数据的标准接口 HetCommonBleControlApi.getInstance().write()，调用实例：

    HetCommonBleControlApi.getInstance().write(macAddress,CmdIndexConstant.HET_COMMAND_BIND_APP,writeCallback);

    private IBleCallback<HetOpenPlatformCmdInfo> writeCallback = new IBleCallback<HetOpenPlatformCmdInfo>() {
        @Override
        public void onSuccess(HetOpenPlatformCmdInfo cmdInfo, int type) {
			//byte[] bytes = (byte[]) cmdInfo.getReceivePacket();
			//showInfo(HexUtil.encodeHexStr(bytes));
        }

        @Override
        public void onFailure(BleException exception) {
            showInfo(exception.getDescription());
        }
    };

第一个参数是设备mac。  
第二个参数是开放平台蓝牙协议标准指令类型。可以参照一下：    

| 指令类型 | 指令说明 |
|---------|---------|
| CmdIndexConstant.HET_COMMAND_BIND_APP | 绑定APP |
| CmdIndexConstant.HET_COMMAND_GET_TIME_APP | 获取设备时间 |
| CmdIndexConstant.HET_COMMAND_SET_TIME_APP | 设置设备时间 |
| CmdIndexConstant.HET_COMMAND_GET_HISTORY_DATA_APP | 获取设备历史数据 |
| CmdIndexConstant.HET_COMMAND_CLEAR_HISTORY_DATA_APP | 清楚设备历史数据 |
| CmdIndexConstant.HET_COMMAND_GET_REAL_TIME_DATA_APP | 获取真实的设备数据 |
| CmdIndexConstant.HET_COMMAND_CONFIG_DATA_APP | 下发控制协议 |
 
第三个参数是写数据成功与否的监听回调。这里开发者可以自行做重发处理。  

第四步：读数据  
SDK对开放平台蓝牙设备提供写数据的标准接口 HetCommonBleControlApi.getInstance().read()。SDK中提供了标准的获取设备信息的接口，调用实例:

    HetCommonBleControlApi.getInstance().read(macAddress,CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SYSTEM_ID,readCallback);

    private IBleCallback<HetOpenPlatformCmdInfo> readCallback = new IBleCallback<HetOpenPlatformCmdInfo>() {
        @Override
        public void onSuccess(HetOpenPlatformCmdInfo cmdInfo, int type) {
            byte[] bytes = (byte[]) cmdInfo.getReceivePacket();
            String msg = "Read:" + ConvertUtil.convertHexToString(HexUtil.encodeHexStr(bytes));
			//成功读取到设备数据
			.............

        }

        @Override
        public void onFailure(BleException exception) {
            //读取设备数据失败
			.......
        }
    };

第一个参数是设备mac。  
第二个参数是开放平台蓝牙协议标准指令类型。可以参照一下：  

| 指令类型 | 指令说明 |
|---------|---------|
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SYSTEM_ID | System Id  |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_FIRMWARE_REVISION | Firmware Revision   |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_HARDWARE_REVISION | Hardware Revision  |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SOFTWARE_REVISION | Software Revision  |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_SERIAL_NUMBER | Serial Number  |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MANUFACTURE_NAME | Manufacture Name  |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_MODEL_NUMBER | Model Number  |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_PNP_ID | PnP ID  |
| CmdIndexConstant.DeviceInfoConstant.HET_COMMAND_BATTERY | Battery Level  |

第三个参数是读数据成功与否的监听回调。  

第五步：释放资源(断开连接)

	HetCommonBleControlApi.getInstance().disConnect(macAddress);



## 7.其他接口

### 7.1.意见反馈

HetFeedbackApi.getInstance().addFeedback() 提交意见反馈

    /**
     * 意见反馈
     *
     * @param iCallback the callback
     * @param contact   联系方式  可以传null匿名提交
     * @param content   反馈内容
     */
    public void addFeedback(final IHetCallback iCallback, String contact, String content) {
        FeedbackDeal.addFeedback(iCallback, contact, content);
    }


### 7.2.消息模块

消息模块接口详情：  


    /**
     * 刷新列表
     *
     * @param callback    回调
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页数据大小
     */
    public void refreshList(IHetCallback callback, String messageId, String messageType, String pageRows) {
        ....
    }
    /**
     * 加载更多
     *
     * @param callback    回调
     * @param messageId   消息ID
     * @param messageType 消息类型
     * @param pageRows    每页数据大小
     */
    public void loadList(IHetCallback callback, String messageId, String messageType, String pageRows) {
        ....
    }
    /**
     * 删除消息
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public void deleteMessage(IHetCallback callback, String messageId) {
        ....
    }
    /**
     * 消息标记为已读
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public void readMessage(IHetCallback callback, String messageId) {
        ....
    }
    /**
     * 消息标记为已读
     *
     * @param callback  回调
     * @param messageId 消息ID
     */
    public void updateMsg(IHetCallback callback, String messageId) {
        ....
    }

根据项目的需求来调用相关接口。

调用HetMessageApi.getInstance().refreshList()获取消息列表，调用传参说明：

| 参数名称 | 是否必须 | 字段类型 | 参数说明 |
|---------|---------|---------|---------|
| appId | 是 | string | 应用标识 |
| accessToken | 是 | string | 访问凭证 |
| timestamp | 是 | number | 时间戳 |
| messageId | 是 | number | 消息标识，只有上拉时传值，下拉时不能传值 |
| messageType | 否 | number | 0-系统消息；1-添加好友；2-邀请控制设备；3-查看帖子评论；5-运营互动 |
| selType | 否 | number | 查询类型。<font color="red">按照人查询消息时不传值，按照app查询时，必传1</font> |
| pageRows | 否 | number | 每页数据大小 |
| pageIndex | 否 | number | 加载第几页 |

返回json结果：

	{
	    "data":
	        {
	            "pager":{"totalRows":0,
	                "pageRows":1,
	                "pageIndex":1,
	                "paged":false,
	                "defaultPageRows":20,
	                "currPageRows":0,
	                "pageStartRow":0,
	                "hasPrevPage":false,
	                "hasNextPage":false,
	                "totalPages":0,
	                "pageEndRow":0},
	            "list": [{
	                    "messageId":1,
	                    "title":"特特特特",
	                    "description":"大声答答",
	                    "businessParam":"11111",
	                    "sender":1,
	                    "icon":"http://www.test.com",
	                    "createTime":1434014367000,
	                    "messageType":1,
	                    "status":1，
	                    "level2":3,
	                    "content":"http://200.200.200.50/clife_app/page/topic-view.html?type=2&id=927",
	                    "readed":0,                   
	                    "readonly":0,
	                    "summary":null,
	                    "pictureUrl":null
	                   }]
	          },
	    "code": 0
	} 
返回的结果字段说明：  

| 字段名称 | 字段类型 | 字段说明 |
|---------|---------|---------|
| messageId | number | 消息标识 |
| title | string | 标题 |
| description | string | 描述 |
| businessParam | string | 业务参数的值(系统推送消息对应消息详情URL(businessParam为空时不要跳转)；添加好友消息对应用户Id，控制设备消息对应设备ID，查看帖子评论对应帖子详情URL。）|
| sender | number | 发送者ID |
| icon | string | 图标URL |
| messageType | number | 消息类型：0-系统消息；1-添加好友；2-邀请控制设备；3-查看帖子评论；5-运营互动；其他后续补充 |
| createTime | number | 时间戳 |
| status | number | (系统消息的时候如果操作类标识)系统消息下的二级分类：1-无正文；2-文本H5；3-外链；4-设备 |
| content | String | 表示设备信息时候建议接口调用时传json格式值)系统消息内容 |
| readed | number | 消息是否已读（0-已读 1-未读）|
| readonly | number | 消息是否只读（0-只读类 1-操作类） |
| summary | String | 简要描述 |
| pictureUrl | String | 简图路径 |


# <span id="jump">第三方社交平台服务集成</span>

第三方登录和分享的集成，SDK目前只支持三种方式，也是目前比较主流的第三方平台。包括微信、QQ、和新浪微博。  
具体过程分5个步骤：  
第一步：gradle 引用

	//引用库形式 集成了第三方登录和分享的引用
	compile 'com.github.szhittech:HetCLifeOpenSdk:1.1.1-SNAPSHOT'

第二步：在集成之前需要在微信开放平台、腾讯开放平台、新浪开放平台创建应用，获取到相应的appID和appSecret。  
第三步：在Application里面配置第三方登录SDK。  

    //配置第三方登录
    mLoginDelegate = new HetSdkThirdDelegate.Builder(this)
                .registerQQ("your_qq_app_id")
                .registerWeixin("your_weixin_app_id", "your_weixin_app_secret")
                .registerSinaWeibo("your_sina_app_id", "your_sina_app_secret", "your_sina_redirect_url"L)
                .create();

注意：your_sina_redirect_url是新浪微博用于OAuth authorize页面回调的url。

第四步：配置清单文件AndroidManifest.xml

    <!-- ====================第三方登录分享开始 ========================== -->
  		<activity
            android:name="com.het.open.lib.wb.WBEntryActivity"
            android:configChanges="keyboardHidden|orientation"
            android:launchMode="singleTask"
            android:screenOrientation="portrait"
            android:windowSoftInputMode="stateAlwaysHidden">
            <intent-filter>
                <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY" />

                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
        <activity
            android:name=".wxapi.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation"
            android:exported="true"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <!-- 这里要把1106346235替换成自己在腾讯开放平台注册的appId -->
                <data android:scheme="tencent1106346235" />
            </intent-filter>
        </activity>
        <!-- sinaweibo -->
        <activity
            android:name="com.sina.weibo.sdk.component.WeiboSdkBrowser"
            android:configChanges="keyboardHidden|orientation"
            android:exported="false"
            android:screenOrientation="portrait"
            android:windowSoftInputMode="adjustResize" />
        <service
            android:name="com.sina.weibo.sdk.net.DownloadService"
            android:exported="false" />
        <!-- =====================第三方登录分享结束=========================== -->

并添加相应的权限

	<uses-permission android:name="android.permission.INTERNET"></uses-permission>
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
	<uses-permission android:name="android.permission.WRITE_APN_SETTINGS"></uses-permission>
	<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>

第五步：设置微信登录的回调页面。  
在项目包名目录下添加一个wxapi目录在wxapi里面新建一个WXEntryActivity页面，如：

	public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	    private IWXAPI api;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Logc.e("Weixin", "WXEntryActivity....onCreate", false);
	        api = WXAPIFactory.createWXAPI(this, UIJsonConfig.getWechatAppID(), true);
	        api.registerApp(UIJsonConfig.getWechatAppID());
	        api.handleIntent(getIntent(), this);
	    }
	    @Override
	    public void onReq(BaseReq arg0) {
	        Logc.e("WXEntryActivity....onReq", false);
	    }
	    @Override
	    public void onResp(BaseResp resp) {
	        Logc.e("WXEntryActivity....onResp", false);
	        if (resp instanceof SendAuth.Resp) {
	            WeiXinLogin.getInstance().onResp(this, (SendAuth.Resp) resp);
	            return;
	        }
	        this.finish();
	    }
	}

注意：wxapi和WXEntryActivity的位置和名字都不能改变，否则不能回调到app中来。例如:DEMO APP的包名是com.het.sdk.demo，那WXEntryActivity的完整名称就是com.het.sdk.demo.wxapi.WXEntryActivity。  
新浪微博分享回调SDK已经集成，com.het.open.lib.wb.WBEntryActivity,开发者不需要关注。

# 通用的业务接口

开发平台其他的业务接口可以调用 HetHttpApi 来发起请求

    /**
     * post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public void hetPost(@NonNull String url, TreeMap params, IHetCallback callback){
        ..........................
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public void hetGet(@NonNull String url,  IHetCallback callback){
        ..........................
    }

    /**
     * get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求结果回调
     */
    public void hetGet(@NonNull String url, TreeMap params,  IHetCallback callback){
        ..........................
    }

带参数的GET请求调用实例：

    /**
     * 带参数的通用get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     */
    private void commonGet(url,param1) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("param1", param1);
        HetHttpApi.getInstance().hetGet(url, params, new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
				//成功
            }

            @Override
            public void onFailed(int code, String msg) {
				//失败
            }
        });
    }

# 全局返回码
全局返回码说明

	每次调用接口时，可能获得正确或错误的返回码，可以根据返回码信息调试接口，排查错误。
全局返回码说明如下：  

| 返回码 | 说明 |
|---------|---------|
| 0 | 请求成功 |
| 100010100 | 缺少授权信息 |
| 100010101 | AccessToken错误或已过期 |
| 100010102 | RefreshToken错误或已过期 |
| 100010103 | AppId不合法 |
| 100010104 | timestamp过期 |
| 100010105 | 签名错误 |
| 100010200 | 失败 |
| 100010201 | 缺少参数 |
| 100010202 | 参数错误 |
| 100010203 | 必须使用https |
| 100010208 | 产品不存在 |
| 100021000 | 帐号已注册 |
| 100021001 | 帐号未注册 |
| 100021007 | 帐号已邀请 |
| 100021008 | 邀请你的用户已解绑该设备 |
| 100021010 | 邀请已被接受 |
| 100021301 | 验证码错误 |
| 100021302 | 随机码错误 |
| 100021303 | 您的访问太过频繁，请15分钟之后再尝试 |
| 100021304 | 重新获取验证码成功 |
| 100021401 | 用户不存在 |
| 100021500 | 密码错误 |
| 100021603 | 数据不存在 |
| 100022000 | 设备不存在 |
| 100022001 | 设备未绑定 |
| 100022002 | 设备已绑定 |
| 100022003 | 设备解绑失败 |
| 100022004 | MAC地址已绑定另一种设备 |
| 100022005 | 设备控制JSON错误 |
| 100022006 | 设备不在线 |
| 10002208  | 不能邀请自己控制 |
| 100022011 | 设备已授权 |
| 100022012 | 待更换MAC与原MAC相同 |
| 100022013 | appId与产品未做关联 |
| 100022014 | 待绑定MAC未进行服务注册 |
| 106000021 | 应用无权限查看该设备信息 |
| 106000026 | 产品不存在 |
| 106000031 | 应用包名错误 |
| 106000036 | openId错误 |
| 106000037 | 手机号码错误 |
| 106000041 | 帐号错误，请使用开放平台账号登录 |

# H5+Native混合框架

为了适应APP不断添加新的设备和动态更新，clife平台结合APP开发一套动态的插件更新框架。基于这套框架可以实现app功能的快速开发迭代，减少产品的上线周期。
## 1.H5开发框架

请参考 [基于React的JS-SDK框架](%E5%8F%82%E8%80%83H5%E5%BC%80%E5%8F%91%E6%A1%86%E6%9E%B6JSSDK)
## 2.Android和H5通讯流程图

![](https://i.imgur.com/drm1OoC.png)

## 3.H5设备控制集成流程

SDK封装了H5插件下载和原生与H5通讯接口，开发者可以轻松实现H5插件的设备控制。 
 
### 3.1.上传H5开发包

开发者需要在开放平台上传完整的H5开发包待审核，审核通过之后才可以正常使用。

### 3.2.初始化webView和H5交互接口

#### 3.2.1.初始化webView

SDK采用了X5内核的浏览服务，添加方式有2种：  
第一种：通过xml方式创建布局  

	<com.tencent.smtt.sdk.WebView
	        android:id="@+id/device_h5_web"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        />
	
	WebView webView = (WebView) findViewById(R.id.device_h5_web);
第二种：代码动态创建  
		WebView webView = new WebView(mContext);


注意：将源码和XML里的系统包和类替换为SDK里的包和类，如：  
android.webkit.WebChromeClient 替换成 com.tencent.smtt.sdk.WebChromeClient 。

#### 3.2.2.初始化H5交互接口

	HtmlFiveManager htmlFiveManager = new HtmlFiveManager(activity, webView, iAppJavaScriptsInterface);

HtmlFiveManager是封装了H5与Android原生的交互接口，通过IAppJavaScriptsInterface来暴露H5接口给原生和从原生获取数据。

	IAppJavaScriptsInterface iAppJavaScriptsInterface = new IAppJavaScriptsInterface() {
    	@Override
    	public void send(String data, final IMethodCallBack methodCallBack) {
        	//H5 het.send()调用的原生接口  这里可以实现设备发送控制命令
    	}

    	@Override
    	public String getModeJson() {
        	//H5 het.ready() 获取的原生信息
        	return null;
    	}

    	@Override
    	public void onWebViewCreate() {
        	//界面加载完成时回调
    	}

    	@Override
    	public void tips(String str) {
        	//H5 het.toast()调用的原生接口
    	}

    	@Override
    	public void setTitle(String title) {
        	//H5 het.setTitle()调用的原生接口
    	}

    	@Override
    	public void onLoadH5Failed(int errCode, String errMsg) {
        	//界面加载失败时回调
    	}

    	@Override
    	public void h5SendDataToNative(int i, String s, String s1, IH5CallBack ih5CallBack) {
        	//H5 发送数据到 App 端
    	}

    	@Override
    	public void h5GetDataFromNative(int i, String s, IH5CallBack ih5CallBack) {
    	    //H5 从 App 端获取数据
    	}
	};

### 3.3.加载H5插件

	HetH5Api.getInstance().getH5ControlPlug(context,deviceBean);

SDK会加载最新的H5插件（下载和检查更新）。加载成功会抛出HetH5PlugEvent.HET_EVENT_H5_PLUG_GET_LOCAL_URL_SUCCESS事件，加载失败会抛出HetH5PlugEvent.HET_EVENT_H5_PLUG_GET_LOCAL_URL_FAILED事件。

### 3.4.监听H5插件加载成功与失败

     RxManage.getInstance().register(HetH5PlugEvent.HET_EVENT_H5_PLUG_GET_LOCAL_URL_SUCCESS + model.getProductId(), o -> {
            if (htmlFiveManager != null) {
                String localPath = (String) o;
                String path = Uri.fromFile(new File(localPath)).toString();
                path += "/index.html";
                htmlFiveManager.loadUrl(path);
            }
        });

H5插件加载成功，调用htmlFiveManager.loadUrl(path); 加载H5页面展示UI。


### 3.5.上传设备数据给H5

上传运行数据：

	htmlFiveManager.updateRunData(json);
上传控制数据：

	htmlFiveManager.updateConfigData(json);
上传异常数据：

	htmlFiveManager.updateErrorData(json);

### 3.6.退出释放资源

	@Override
    public void onDestroy() {
		if (webView != null) {
    		webView.removeJavascriptInterface("bindJavaScript");
    		if (webView.getSettings() != null) {
        		webView.getSettings().setJavaScriptEnabled(false);
    		}
    		webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
    		webView.clearHistory();
    		((ViewGroup)  webView.getParent()).removeView(mDevice_h5_web);
    		webView.destroy();
    		webView = null;
		}
	}


# 补充说明
## 1.SDK 第三方库支持

### 1.1.RxJava 函数式编程  

开放平台SDK集成了RxJava，并且使用RxJava封装了Android 6.0+的动态权限申请接口和网络请求库等。
### 1.2.RxBus 事件传递总线 

开放平台SDK提供了RxBus事件总线的支持，用于事件的发布和订阅，实现数据的传递。  
RxBus事件的发布：  

	//发布 HetCodeConstants.Login.LOGIN_SUCCESS 事件
	RxManage.getInstance().post(HetCodeConstants.Login.LOGIN_SUCCESS, object);
RxBus事件的订阅：  

    RxManage.getInstance().register(HetCodeConstants.Login.LOGIN_SUCCESS, o -> {
         //订阅 HetCodeConstants.Login.LOGIN_SUCCESS 事件
    });
RxBus事件的取消订阅：

    RxManage.getInstance().unregister(HetCodeConstants.Login.LOGIN_SUCCESS);

### 1.3.retrofit+okhttp 网络库

开放平台SDK集成了retrofit+okhttp的网络库，用于请求服务器数据。
### 1.4.ActiveAndroid 数据库

开放平台SDK集成了 ActiveAndroid 第三方的轻量级的数据库,方便轻量级数据的存取。
### 1.5.X5内核 浏览服务

开放平台SDK集成了X5内核的浏览服务，来提高移动端webview的加载性能和兼容性。  
使用实例：

	<com.tencent.smtt.sdk.WebView
		android:id="@+id/forum_context"
		android:layout_width="fill_parent"
		android:layout_height="fill_parent"
		android:paddingLeft="5dp"
		android:paddingRight="5dp" />

注意：将源码和XML里的系统包和类替换为SDK里的包和类，如：  
android.webkit.WebChromeClient 替换成 com.tencent.smtt.sdk.WebChromeClient 。

## 2.SDK 混淆说明
	
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

    #===========okhttp===========
    -dontwarn com.squareup.okhttp3.**
    -keep class com.squareup.okhttp3.** { *;}
    -dontwarn okio.**
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
    -dontwarn okio.**
    -dontwarn com.squareup.okhttp.**
    -dontwarn okhttp3.**
    -dontwarn javax.annotation.**
    -dontwarn com.android.volley.toolbox.**
    -dontwarn com.facebook.infer.**

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

    #====xstream库====
    -dontwarn com.thoughtworks.xstream.**
    -keep class com.thoughtworks.xstream.io.xml.** { *; }

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

    # OkHttp
    -dontwarn okhttp3.**
    -dontwarn okio.**
    -dontwarn com.squareup.okhttp.**
    -keep class okio.**{*;}
    -keep class com.squareup.okhttp.** { *; }
    -keep interface com.squareup.okhttp.** { *; }

    -dontwarn java.nio.file.*
    -dontwarn javax.annotation.**
    -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

	
	
