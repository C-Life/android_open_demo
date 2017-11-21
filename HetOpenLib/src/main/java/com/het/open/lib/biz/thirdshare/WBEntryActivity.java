package com.het.open.lib.biz.thirdshare;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.het.open.lib.biz.thirdlogin.HetSdkThirdDelegate;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;

/**
 * description:微博回调处理类(默认)
 * Author:  howard gong
 * Create:  2017/5/4 10:10
 */

public class WBEntryActivity extends Activity implements IWeiboHandler.Response {

    private HetSdkThirdDelegate mShareManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShareManager = HetSdkThirdDelegate.getInstance();
        mShareManager.mWeibo.handleWeiboResponse(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("WBEntryActivity","onNewIntent");
        if(mShareManager.mWeibo != null)
            mShareManager.mWeibo.handleWeiboResponse(intent, this);
    }

    @Override
    public void onResponse(BaseResponse resp) {
        HetSdkThirdDelegate.sinaOnResp(resp);
        this.finish();
    }
}
