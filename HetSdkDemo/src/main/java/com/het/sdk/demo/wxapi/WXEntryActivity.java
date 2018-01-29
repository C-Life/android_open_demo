package com.het.sdk.demo.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.het.log.Logc;
import com.het.open.lib.biz.thirdlogin.HetSdkThirdDelegate;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.thirdlogin.WeiXinLogin;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by sunny on 2016/1/19.
 * Annotion:
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logc.e("Weixin", "WXEntryActivity....onCreate", false);
        api = WXAPIFactory.createWXAPI(this, UIJsonConfig.getInstance(this).getWechatAppId(), true);
        api.registerApp(UIJsonConfig.getInstance(this).getWechatAppId());
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
        HetSdkThirdDelegate.getInstance().wxOnResp(resp);
        this.finish();
    }
}