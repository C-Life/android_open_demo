package com.het.open.lib.biz.thirdlogin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.het.open.lib.R.string;
import com.het.share.listener.ICommonShareListener;
import com.het.share.listener.IShareOperate;
import com.het.share.manager.CommonSharePlatform;
import com.het.share.model.CommonShareBaseBean;
import com.het.share.model.CommonShareMusic;
import com.het.share.model.CommonShareTextOnly;
import com.het.share.model.CommonShareVideo;
import com.het.share.model.CommonShareWebpage;
import com.het.share.model.ThirdPlatformKey;
import com.het.share.utils.AccessTokenKeeper;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.tauth.Tencent;

import static com.het.basic.utils.StringUtils.getString;

/**
 * Created by Administrator on 2017-09-01.
 *
 */

public class HetSdkThirdDelegate {
    private Context mContext;
    private static HetSdkThirdDelegate mInstance;
    private ThirdPlatformKey mPlatformKey;
    public IShareOperate mShareOperate;
    private IWXAPI mIWeixinApi;
    public Tencent mTencent;
    public IWeiboShareAPI mWeibo;
    public SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;
    private Oauth2AccessToken mAccessToken;

    private String mWXAppId;
    private String mWXAppSecret;
    private String mSinaAppKey;
    private String mSinaAppSecret;
    private String mSinaRedirectUrl;
    private String mQQAppId;
    private String mQQAppSecret;
    public String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";

    private static CommonSharePlatform mSharePlatform;
    private static ICommonShareListener listener;
    public static ICommonShareListener getListener() {
        return listener;
    }

    public void setShareListener(ICommonShareListener listener) {
        this.listener = listener;
    }

    public void setSsoHandler(SsoHandler mSsoHandler) {
        this.mSsoHandler = mSsoHandler;
    }

    public void setSinaAppKey(String mSinaAppKey) {
        this.mSinaAppKey = mSinaAppKey;
    }

    public void setSinaRedirectUrl(String mSinaRedirectUrl) {
        this.mSinaRedirectUrl = mSinaRedirectUrl;
    }

    public String getSinaAppKey() {
        return this.mSinaAppKey;
    }

    public String getSinaRedirectUrl() {
        return this.mSinaRedirectUrl;
    }

    public String getWXAppId() {
        return this.mWXAppId;
    }

    public void setWXAppId(String WXAppId) {
        this.mWXAppId = WXAppId;
    }

    public String getWXAppSecret() {
        return this.mWXAppSecret;
    }

    public void setWXAppSecret(String WXAppSecret) {
        this.mWXAppSecret = WXAppSecret;
    }

    public String getSinaAppSecret() {
        return this.mSinaAppSecret;
    }

    public void setSinaAppSecret(String sinaAppSecret) {
        this.mSinaAppSecret = sinaAppSecret;
    }

    public String getQQAppId() {
        return this.mQQAppId;
    }

    public void setQQAppId(String QQAppId) {
        this.mQQAppId = QQAppId;
    }

    public String getQQAppSecret() {
        return this.mQQAppSecret;
    }

    public void setQQAppSecret(String QQAppSecret) {
        this.mQQAppSecret = QQAppSecret;
    }

   public void setIWeixinApi(IWXAPI mIWeixinApi) {
        this.mIWeixinApi = mIWeixinApi;
    }

    public void setTencent(Tencent mTencent) {
        this.mTencent = mTencent;
    }

    public void setWeibo(IWeiboShareAPI mWeibo) {
        this.mWeibo = mWeibo;
    }

    public void setShareOperate(IShareOperate mShareOperate) {
        if(mShareOperate != null) {
            this.mShareOperate = mShareOperate;
        }

    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }


    private HetSdkThirdDelegate() {
    }

    public void initThirdPlatformKey() {
        if (this.mPlatformKey == null) {
            this.mPlatformKey = ThirdPlatformKey.getInstance();
        }
        this.mPlatformKey.setQQAppId(this.getQQAppId());
        this.mPlatformKey.setQQAppSecret(this.getQQAppSecret());
        this.mPlatformKey.setSinaAppSecret(this.getSinaAppSecret());
        this.mPlatformKey.setSinaAppKey(this.getSinaAppKey());
        this.mPlatformKey.setSinaDirectUrl(this.getSinaRedirectUrl());
        this.mPlatformKey.setWeixinAppId(this.getWXAppId());
        this.mPlatformKey.setWeixinAppSecret(this.getWXAppSecret());
    }

    public void shareTextOnly(CommonShareTextOnly shareTextBean) {
        Object shareType = this.getShareObject(shareTextBean.getSharePlatform());
        Log.e("===========", "shareBean:" + shareTextBean.toString());
        this.mShareOperate.shareText(shareTextBean, shareType);
    }

    public void sharePicOnly(CommonShareBaseBean sharePicBean) {
        this.mShareOperate.sharePic(sharePicBean, this.getShareObject(sharePicBean.getSharePlatform()));
    }

    public void sharePicText(CommonShareWebpage sharePicTextBean) {
        this.mShareOperate.shareWebPage(sharePicTextBean, this.getShareObject(sharePicTextBean.getSharePlatform()));
    }

    public void shareWebpage(CommonShareWebpage webPageBean) {
        this.mShareOperate.shareWebPage(webPageBean, this.getShareObject(webPageBean.getSharePlatform()));
    }

    public void shareMusic(CommonShareMusic shareBean) {
        this.mShareOperate.shareMusic(shareBean, this.getShareObject(shareBean.getSharePlatform()));
    }

    public void shareVideo(CommonShareVideo shareVideo) {
        this.mShareOperate.shareVideo(shareVideo, this.getShareObject(shareVideo.getSharePlatform()));
    }


    public void releaseResource() {
        if (this.mIWeixinApi != null) {
            this.mIWeixinApi.unregisterApp();
        }

        if (this.mTencent != null) {
            this.mTencent.releaseResource();
        }

        if (this.mWeibo != null) {
        }

    }

    public static HetSdkThirdDelegate getInstance() {
        if (mInstance == null) {
            synchronized (HetSdkThirdDelegate.class) {
                if (mInstance == null) {
                    mInstance = new HetSdkThirdDelegate();
                }
            }
        }

        return mInstance;
    }

    public void wxOnResp(BaseResp resp) {
        ICommonShareListener shareLinstener = getListener();
        if(shareLinstener != null) {
            int code = resp.errCode;
            Log.e("*********", "微信回调：" + code);
            if(code == 0) {
                shareLinstener.onShareSuccess(mSharePlatform, mContext.getString(string.share_result_success));
            } else if(code == -2) {
                shareLinstener.onShareCancel(mSharePlatform, mContext.getString(string.share_result_cancel));
            } else if(code == -4) {
                shareLinstener.onShareFialure(mSharePlatform, mContext.getString(string.share_result_refused));
            } else {
                shareLinstener.onShareFialure(mSharePlatform, mContext.getString(string.share_result_failure));
            }

        }
    }

    public static void sinaOnResp(BaseResponse response) {
        Log.e("share", "新浪回调");
        ICommonShareListener shareLinstener = getListener();
        if(shareLinstener != null) {
            int code = response.errCode;
            if(code == 0) {
                shareLinstener.onShareSuccess(mSharePlatform, getString(string.share_result_success));
            } else if(code == 1) {
                shareLinstener.onShareCancel(mSharePlatform, getString(string.share_result_cancel));
            } else {
                shareLinstener.onShareFialure(mSharePlatform, getString(string.share_result_failure));
            }

        }
    }

    public void authToSina(Activity activity) {
        this.mAuthInfo = new AuthInfo(mContext, getSinaAppKey(), getSinaRedirectUrl(), "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
        this.mSsoHandler = new SsoHandler(activity, this.mAuthInfo);
        this.setSsoHandler(this.mSsoHandler);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mContext);
        if(!accessToken.isSessionValid()) {
            this.mSsoHandler.authorize(new WeiboAuthListener() {
                public void onComplete(Bundle bundle) {
                    HetSdkThirdDelegate.this.mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
                    if(HetSdkThirdDelegate.this.mAccessToken.isSessionValid()) {
                        AccessTokenKeeper.writeAccessToken(getContext(), HetSdkThirdDelegate.this.mAccessToken);
                        HetSdkThirdDelegate.listener.onStartShare(CommonSharePlatform.SinaWeibo);
                    } else {
                        String errorCode = bundle.getString("code");
                        HetSdkThirdDelegate.listener.onShareFialure(HetSdkThirdDelegate.mSharePlatform, getContext().getString(string.share_result_sina_canel) + errorCode);
                    }

                }

                public void onWeiboException(WeiboException e) {
                    HetSdkThirdDelegate.listener.onShareFialure(HetSdkThirdDelegate.mSharePlatform, e.getMessage());
                }

                public void onCancel() {
                    HetSdkThirdDelegate.listener.onShareCancel(HetSdkThirdDelegate.mSharePlatform, getContext().getString(string.share_result_sina_canel));
                }
            });
        } else {
            listener.onStartShare(CommonSharePlatform.SinaWeibo);
        }

    }

    public Object getShareObject(CommonSharePlatform sharePlatform) {
        mSharePlatform = sharePlatform;
        Object shareObject = null;
        if(sharePlatform == CommonSharePlatform.SinaWeibo) {
            shareObject = this.mWeibo;
        } else if(sharePlatform != CommonSharePlatform.WeixinFriendCircle && sharePlatform != CommonSharePlatform.WeixinFriend) {
            if(sharePlatform == CommonSharePlatform.QQ_Friend || sharePlatform == CommonSharePlatform.QQ_Weibo || sharePlatform == CommonSharePlatform.QQ_Zone) {
                shareObject = this.mTencent;
            }
        } else {
            shareObject = this.mIWeixinApi;
        }

        return shareObject;
    }

//    public static class Builder {
//        private HetSdkThirdDelegate mLoginManger = HetSdkThirdDelegate.getInstance();
//        private Context context;
//        private String mWXAppId;
//        private String mWXAppSecret;
//        private String mSinaAppKey;
//        private String mSinaAppSecret;
//        private String mSinaRedirectUrl;
//        private String mQQAppId;
//        private IWXAPI mIWeixinApi;
//        private Tencent mTencent;
//        private IWeiboShareAPI mWeibo;
//
//        public Builder(Context cxt) {
//            this.context = cxt;
//            this.mLoginManger.setContext(cxt);
//        }
//
//        public Builder registerWeixin(@NonNull String weixinAppId, @NonNull String wxSeceret) {
//            this.mWXAppId = weixinAppId;
//            this.mWXAppSecret = wxSeceret;
//            this.mIWeixinApi = WXAPIFactory.createWXAPI(this.context, weixinAppId, false);
//            this.mIWeixinApi.registerApp(weixinAppId);
//            this.mLoginManger.setIWeixinApi(this.mIWeixinApi);
//            return this;
//        }
//
//        public Builder registerQQ(@NonNull String tencentAppId) {
//            this.mQQAppId = tencentAppId;
//            if (this.mTencent == null) {
//                this.mTencent = Tencent.createInstance(tencentAppId, this.context);
//            }
//
//            this.mLoginManger.setTencent(this.mTencent);
//            return this;
//        }
//
//        public Builder registerSinaWeibo(@NonNull String sinaAppKey, @NonNull String sinaAppSecret, @NonNull String redicrectUrl) {
//            this.mSinaAppKey = sinaAppKey;
//            this.mSinaAppSecret = sinaAppSecret;
//            this.mSinaRedirectUrl = redicrectUrl;
//            if (this.mWeibo == null) {
//                this.mWeibo = WeiboShareSDK.createWeiboAPI(this.context, sinaAppKey);
//            }
//
//            this.mWeibo.registerApp();
//            this.mLoginManger.setWeibo(this.mWeibo);
//            this.mLoginManger.setSinaAppKey(sinaAppKey);
//            this.mLoginManger.setSinaRedirectUrl(redicrectUrl);
//            return this;
//        }
//
//        public HetSdkThirdDelegate create() {
//            this.mLoginManger.setQQAppId(this.mQQAppId);
//            this.mLoginManger.setWXAppId(this.mWXAppId);
//            this.mLoginManger.setWXAppSecret(this.mWXAppSecret);
//            this.mLoginManger.setSinaAppKey(this.mSinaAppKey);
//            this.mLoginManger.setSinaAppSecret(this.mSinaAppSecret);
//            this.mLoginManger.setSinaRedirectUrl(this.mSinaRedirectUrl);
//            this.mLoginManger.initThirdPlatformKey();
//            return this.mLoginManger;
//        }
//    }
}
