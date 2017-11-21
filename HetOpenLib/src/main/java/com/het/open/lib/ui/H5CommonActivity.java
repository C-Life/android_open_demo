package com.het.open.lib.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.het.basic.base.BaseActivity;
import com.het.basic.data.api.token.TokenManager;
import com.het.basic.data.api.token.model.AuthModel;
import com.het.basic.data.http.retrofit2.exception.ApiException;
import com.het.basic.dlg.HetCommPrompDialog;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.StringUtils;
import com.het.basic.utils.ToastUtil;
import com.het.h5.sdk.callback.IAppJavaScriptsInterface;
import com.het.h5.sdk.callback.IH5CallBack;
import com.het.h5.sdk.callback.IMethodCallBack;
import com.het.h5.sdk.manager.HtmlFiveManager;
import com.het.log.Logc;
import com.het.open.lib.R;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.auth.HetAuthMess;
import com.het.open.lib.auth.biz.BaseWebParams;
import com.het.open.lib.auth.biz.HetCallbackManager;
import com.het.open.lib.auth.webviewclient.AuthHetWebViewClient;
import com.het.open.lib.biz.constans.GlobalAddr;
import com.het.open.lib.model.ModifyPwdModel;
import com.het.open.lib.biz.thirdlogin.IthirdFactoryImp;
import com.het.open.lib.utils.BuildManager;
import com.het.open.lib.utils.H5FileUtils;
import com.het.open.lib.utils.NetworkHelper;
import com.het.share.model.ThirdPlatformKey;
import com.het.thirdlogin.biz.HetThirdLoginApi;
import com.het.thirdlogin.callback.IHetThirdLogin;
import com.het.thirdlogin.model.HetThirdLoginInfo;
import com.het.ui.sdk.CommonTopBar;
import com.tencent.smtt.sdk.WebView;

public class H5CommonActivity extends BaseActivity implements IAppJavaScriptsInterface {

    private static final String TAG = H5CommonActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initData();
    }

    protected void onDestroy() {
        NetworkHelper.clearCookies(this);
        super.onDestroy();
    }

    public static void startH5CommonActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, H5CommonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private WebView mWebView;
    private HtmlFiveManager htmlManger;
    private BaseWebParams mRequestParam;
    private String mUrl; // 请求url
    private String mSpecifyTitle;
    private IH5CallBack ih5CallBack;
    private HetThirdLoginInfo iHetThirdLoginInfo;
    private Activity context;
    private String preMessage;
    private IthirdFactoryImp ithirdFactoryImp;
    private View rootView;
    /**
     * 标题栏
     */
    protected CommonTopBar mTitleView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_h5_index;
    }

    @Override
    public void initView() {
        mTitleView = (CommonTopBar) findViewById(R.id.topbar);
        rootView = findViewById(R.id.activity_h5_index);
        RelativeLayout rl_h5_container = (RelativeLayout) findViewById(R.id.rl_h5_container);
        this.mWebView = new WebView(this);
        this.mWebView.setBackgroundColor(-1);
        mWebView.setFocusable(true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl_h5_container.addView(mWebView, layoutParams);
        htmlManger = new HtmlFiveManager(this, mWebView, this);
        initDefTopBarView();
    }


    /**
     * 默认的title 栏
     */
    private void initDefTopBarView() {
        mTitleView.setUpNavigateMode();
    }

    private void initData() {
        initDataFromIntent(getIntent());
        if (!(initDataFromIntent(getIntent()))) {
            finish();
            return;
        }
        Logc.e(TAG, this.mUrl);
        htmlManger.loadUrl(this.mUrl);
        initParams();
        initUI();
    }

    private void initUI() {
        mTitleView.setTitle(!StringUtils.isNull(mSpecifyTitle) ? mSpecifyTitle : getString(R.string.app_name));
        mTitleView.setTitleColor(mRequestParam.getParams().getNavigationBarTextColor());
        mTitleView.setBackground(mRequestParam.getParams().getNavBackground());
        BuildManager.setStatusTrans(this, 2, mTitleView, rootView, mRequestParam.getParams().getNavBackground());
    }

    private void initParams() {
        ithirdFactoryImp = new IthirdFactoryImp();
    }

    /**
     * 获取请求参数
     *
     * @param data
     * @return
     */
    private boolean initDataFromIntent(Intent data) {
        this.mRequestParam = new BaseWebParams.Builder(this).create();
        if (this.mRequestParam == null) {
            return false;
        }
        mRequestParam.setupRequestParam(data.getExtras());
        mWebView.setWebViewClient(new AuthHetWebViewClient(this, mRequestParam));
        this.mUrl = this.mRequestParam.getParams().getUrl();
        if (TextUtils.isEmpty(this.mUrl)) {
            return false;
        }
        this.mSpecifyTitle = this.mRequestParam.getParams().getSpecifyTitle();
        return true;
    }


    @Override
    public void send(String s, IMethodCallBack iMethodCallBack) {

    }

    @Override
    public String getModeJson() {
        return null;
    }

    @Override
    public void onWebViewCreate() {

    }

    @Override
    public void tips(String str) {
        ToastUtil.showToast(this, str);
    }

    @Override
    public void setTitle(String title) {
        mTitleView.setTitle(!StringUtils.isNull(title) ? title : getString(R.string.app_name));
    }

    @Override
    public void onLoadH5Failed(int i, String s) {

    }

    @Override
    public void h5SendDataToNative(int i, String s, String s1, com.het.h5.sdk.callback.IH5CallBack ih5CallBack) {

    }

    @Override
    public void h5GetDataFromNative(int code, String routeUrl, IH5CallBack ih5CallBack) {
        if (StringUtils.isNull(routeUrl)) return;
        if (routeUrl.contains("common/appSecret")) {
            ih5CallBack.onSucess(HetAuthMess.getInstance().getAppKey());
        } else if (routeUrl.contains("common/getIMEI")) {
            ih5CallBack.onSucess(H5FileUtils.getIMEI(this));
        } else if (routeUrl.contains("common/configurationFiles")) {
            ih5CallBack.onSucess(HetSdk.getInstance().getH5UIconfig());
        } else if (routeUrl.contains("common/weiboLogin")) {//微博登录
            showWeiboLogin(ih5CallBack);
        } else if (routeUrl.contains("common/weixinLogin")) {//微信登录
            showWeixinLogin(ih5CallBack);
        } else if (routeUrl.contains("common/qqLogin")) {//QQ登录
            showQQLogin(ih5CallBack);
        } else if (routeUrl.contains("common/bindinSuccess")) {
            if (H5CommonActivity.this.iHetThirdLoginInfo != null) {
                iHetThirdLoginInfo.setSync("0");
                startLoginCife(iHetThirdLoginInfo);
            }
        } else if (routeUrl.contains("common/closeWebPage")) {
            this.mRequestParam.execRequest(this, 3);
            finish();
        } else if (routeUrl.contains("ommon/alterPassword")) {//获取修改密码的参数
            if (TokenManager.getInstance().getAuthModel() != null) {
                ModifyPwdModel modifyPwdModel = new ModifyPwdModel();
                modifyPwdModel.setAppId(HetAuthMess.getInstance().getAppId());
                modifyPwdModel.setAccessToken(TokenManager.getInstance().getAuthModel().getAccessToken());
                modifyPwdModel.setAccount(this.mRequestParam.getParams().getPhone());
                ih5CallBack.onSucess(GsonUtil.getInstance().getGson().toJson(modifyPwdModel));
            }

        } else if (routeUrl.contains("common/alterSucceed")) {//修改密码成功
            this.mRequestParam.getParams().getHetAuthListener().onComplete(getString(R.string.modif_passwad_succee));
            finish();
        } else if (routeUrl.contains("common/alterFailed")) {//修改密码失败
            this.mRequestParam.getParams().getHetAuthListener().onHetException(null);
            finish();
        } else if (routeUrl.contains("common/thirdOpenId")) {//获取第三方的ID
            ih5CallBack.onSucess(iHetThirdLoginInfo.getOpenid());
        }
    }

    private void showQQLogin(IH5CallBack ih5CallBack) {
        String qqAPPID = ThirdPlatformKey.getInstance().getQQAppId();
        if (TextUtils.isEmpty(qqAPPID) || qqAPPID.contains("your_tencent_app_id")) {
            showAlertDialog(getString(R.string.TencentAPPID_Toast));
            return;
        }
        this.preMessage = getResources().getString(R.string.common_third_qq_logining);
        this.ih5CallBack = ih5CallBack;
        ithirdFactoryImp.initQQLogin(mContext, iHetThirdLogin).startLogin();
    }

    private void showWeixinLogin(IH5CallBack ih5CallBack) {
        String weixinAPPID = ThirdPlatformKey.getInstance().getWeixinAppId();
        if (TextUtils.isEmpty(weixinAPPID) || weixinAPPID.contains("your_wechat_app_id")) {
            showAlertDialog(getString(R.string.WechatAppID_Toast));
            return;
        }
        this.preMessage = getResources().getString(R.string.common_third_wx_logining);
        this.ih5CallBack = ih5CallBack;
        ithirdFactoryImp.initWeiXinLogin(mContext, iHetThirdLogin).startLogin();
    }

    private void showWeiboLogin(IH5CallBack ih5CallBack) {
        String sinaAPPID = ThirdPlatformKey.getInstance().getSinaAppKey();
        if (TextUtils.isEmpty(sinaAPPID) || sinaAPPID.contains("your_sina_app_id")) {
            showAlertDialog(getString(R.string.SinaAppID_Toast));
            return;
        }
        this.preMessage = getResources().getString(R.string.common_third_sina_logining);
        this.ih5CallBack = ih5CallBack;
        ithirdFactoryImp.initWeiboLogin(mContext, iHetThirdLogin).startLogin();
    }

    private void showAlertDialog(String preMessage) {
        HetCommPrompDialog.Builder mCommonBuilder = new HetCommPrompDialog.Builder(this.mContext);
        mCommonBuilder.setMessage(preMessage);
        mCommonBuilder.setPositiveButton(getResources().getString(R.string.common_basic_sure), (dialog, which) -> {
            dialog.dismiss();
        });
        HetCommPrompDialog mPromptDialog = mCommonBuilder.create();
        mPromptDialog.setCancelable(true);
        mPromptDialog.show();
    }

    private IHetThirdLogin iHetThirdLogin = new IHetThirdLogin() {
        @Override
        public void isFirstLogin(HetThirdLoginInfo thirdLoginInfo) {//第一次登录
            H5CommonActivity.this.iHetThirdLoginInfo = thirdLoginInfo;
            ih5CallBack.onSucess(thirdLoginInfo.getOpenid());
        }

        @Override
        public void notFirstLogin(HetThirdLoginInfo thirdLoginInfo) {//不是第一次登录
            //直接登录了
            H5CommonActivity.this.iHetThirdLoginInfo = thirdLoginInfo;
            thirdLoginInfo.setSync("0");
            startLoginCife(thirdLoginInfo);
        }

        @Override
        public void queryError(int code, String msg) {//错误
            if (code == 0) {
                context.runOnUiThread(() -> ToastUtil.showToast(context, msg));
            } else {
                context.runOnUiThread(() -> ToastUtil.showToast(context, getString(R.string.network_error)));
            }
        }

        @Override
        public void getThirdIdSuccess(String thirdId, String thirdType) { //第三方认证成功

        }

        @Override
        public void loginSuccess(String thirdId, String bindType) {

        }
    };

    private void startLoginCife(HetThirdLoginInfo thirdLoginInfo) {
        String query_path = "/v1/user/third/bind";
        String url = GlobalAddr.HOST;
        if (url.contains("dp") || url.contains("50")) {
            query_path = "/v1/app/open/user/third/bind";
        }
        HetThirdLoginApi.getInstance()
                .bind(query_path, thirdLoginInfo,
                        H5CommonActivity.this.preMessage,
                        context).subscribe(queryResultApiResult -> {
            if (queryResultApiResult != null) {
                AuthModel authLoginModel = queryResultApiResult.getData();
                TokenManager.getInstance().setAuthModel(authLoginModel);
                if (this.mRequestParam.getParams().getHetAuthListener() != null) {
                    this.mRequestParam.getParams().getHetAuthListener().onComplete(GsonUtil.getInstance().getGson().toJson(authLoginModel));
                }
                HetCallbackManager manager = HetCallbackManager.getInstance(context);
                if (!(TextUtils.isEmpty(H5CommonActivity.this.mRequestParam.getParams().getAuthListenerKey()))) {
                    manager.removeHetAuthListener(H5CommonActivity.this.mRequestParam.getParams().getAuthListenerKey());
                    H5CommonActivity.this.context.finish();
                }
            }
        }, throwable -> {
            if (throwable instanceof ApiException) {
                Logc.e(TAG, throwable.getMessage());
            }

        });
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();   //后退
            } else {
                this.mRequestParam.execRequest(this, 3);
                finish();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.ithirdFactoryImp.onActivityResult(requestCode, resultCode, data);
    }

}
