package com.het.sdk.demo.ui.activity.sidebarlayout;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.het.basic.AppDelegate;
import com.het.basic.utils.StringUtils;
import com.het.basic.utils.permissions.RxPermissions;
import com.het.log.Logc;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;

public class SplashActivity extends BaseHetActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        Intent ivalue = getIntent();
        String action = ivalue.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = ivalue.getData();
            if (uri != null) {
                // 完整的url信息
                String url = uri.toString();
                Logc.e("url: " + url);
                if (!StringUtils.isNull(url)) {
                    String[] urlString = url.split("shareCode=");
                    if (urlString != null && urlString.length == 2) {
                        Logc.e("shareCode =  " + urlString[1]);
                        jumpToShareCode(urlString[1]);
                        return;
                    }
                }
            }
        }

        getPermission();

    }

    /**
     * 获取权限
     */
    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions.getInstance(AppDelegate.getAppContext())
                    .request(Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .subscribe(grant -> {
                        if (!grant) {
                            finish();
                        } else {
                            //延迟2S跳转
                            new Handler().postDelayed(() -> {
                                Intent intent = new Intent(SplashActivity.this, SidebarMainActivity.class);
                                startActivity(intent);
                                finish();
                            }, 2000);
                        }
                    });
        } else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, SidebarMainActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        }

    }

    private void jumpToShareCode(String shareCode) {
        Intent intent = new Intent(SplashActivity.this, SidebarMainActivity.class);
        intent.putExtra("shareCode", shareCode);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initTopBarView() {
        mTitleView.setVisibility(View.GONE);
    }
}
