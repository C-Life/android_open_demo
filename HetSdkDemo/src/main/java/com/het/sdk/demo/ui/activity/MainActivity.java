package com.het.sdk.demo.ui.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.het.basic.AppDelegate;
import com.het.basic.utils.permissions.RxPermissions;
import com.het.open.lib.api.HetSdk;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.ui.fragment.BindFragment;
import com.het.sdk.demo.ui.fragment.CommonFragment;
import com.het.sdk.demo.ui.fragment.DeviceFragment;
import com.het.sdk.demo.ui.fragment.auth.AuthFragment;
import com.het.sdk.demo.utils.UIJsonConfig;

import butterknife.Bind;


/**
 * 和而泰开放平台主界面
 */
public class MainActivity extends BaseHetActivity {

    private AuthFragment authFragment;
    private BindFragment bindFragment;
    private DeviceFragment deviceFragment;
    private CommonFragment commonFragment;
    private Fragment mFragment;

    @Bind(R.id.tab_bottom)
    RadioGroup tabBottom;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            authFragment = new AuthFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, authFragment)
                    .commitAllowingStateLoss();
        } else {
            authFragment = (AuthFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
        }
        mFragment = authFragment;
    }

    @Override
    public void initTopBarView() {
        mTitleView.setTitle(R.string.app_name);
        mTitleView.HideLeftImage();
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @Override
    public void initData() {
        tabBottom.setOnCheckedChangeListener(onCheckedChangeListener);
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
                        }
                    });
        }
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_auth:

                    if (authFragment == null) {
                        authFragment = new AuthFragment();
                    }
                    switchContent(authFragment);

                    break;
                case R.id.rb_bind:

                    if (bindFragment == null) {
                        bindFragment = new BindFragment();
                    }
                    switchContent(bindFragment);

                    break;
                case R.id.rb_device:
                    if (deviceFragment == null) {
                        deviceFragment = new DeviceFragment();
                    }
                    switchContent(deviceFragment);

                    break;
                case R.id.rb_common:
                    if (commonFragment == null) {
                        commonFragment = new CommonFragment();
                    }
                    switchContent(commonFragment);
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HetSdk.getInstance().destroy();
    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mPressedTime > 2000) {
            showToast(getString(R.string.press_again));
            mPressedTime = nowTime;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 修改显示的fragment 不会重新加载
     **/
    public void switchContent(Fragment to) {
        if (mFragment == null) {
            initView(null);
        }
        if (mFragment != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mFragment).add(R.id.fragment_container, to)
                        .commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个

            }
            mFragment = to;
        }
    }

}
