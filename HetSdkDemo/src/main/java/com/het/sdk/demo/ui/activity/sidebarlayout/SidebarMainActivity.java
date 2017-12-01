package com.het.sdk.demo.ui.activity.sidebarlayout;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.het.basic.AppDelegate;
import com.het.basic.base.RxManage;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.StringUtils;
import com.het.basic.utils.permissions.RxPermissions;
import com.het.bind.logic.bean.UserInfoBean;
import com.het.log.Logc;
import com.het.open.lib.api.HetCodeConstants;
import com.het.open.lib.api.HetDeviceShareApi;
import com.het.open.lib.api.HetSdk;
import com.het.open.lib.api.HetUserApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.event.HetShareEvent;
import com.het.sdk.demo.manager.HetUserManager;
import com.het.sdk.demo.push.BdPushService;
import com.het.sdk.demo.push.HetPushManager;
import com.het.sdk.demo.ui.activity.bind.DeviceTypeListActivity;
import com.het.sdk.demo.ui.fragment.NavigationDrawerFragment;
import com.het.sdk.demo.ui.fragment.device.DeviceListFragment;
import com.het.sdk.demo.ui.fragment.device.LoginPresenter;
import com.het.sdk.demo.ui.fragment.device.NoLoginFragment;
import com.het.sdk.demo.ui.fragment.device.SwitchFragmentInterface;
import com.het.sdk.demo.utils.StatusBarUtil;
import com.het.sdk.demo.utils.UIJsonConfig;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 首页
 */

public class SidebarMainActivity extends BaseHetActivity<LoginPresenter> implements SwitchFragmentInterface {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_bg)
    RelativeLayout main_bg;
    @Bind(R.id.drawerlayout)
    DrawerLayout drawer;
    @Bind(R.id.btnRight)
    Button btnRight;
    @Bind(R.id.tv_toolbar)
    TextView tvToolbar;
    @Bind(R.id.container)
    FrameLayout container;


    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Fragment mCurrentFragment;
    public static final int FRAGMENT_SHOW_LIST = 1;
    public static final int FRAGMENT_NO_SHOW_LIST = 2;
    private DeviceListFragment deviceListFragment;
    private NoLoginFragment noLoginFragment = NoLoginFragment.newInstance();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sidebar_main;
    }

    @Override
    protected void initData() {
        getPermission();
        // Set up the drawer.
        mNavigationDrawerFragment.setConfigUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawerlayout), toolbar);
        initRxManage();
        jumpToShareCode();
        initPush();
    }

    private void initPush() {
//        初始化百度推送服务  不能在application 初始化
//        在主 Activiy 的 OnCreate 方法中，调用接口 startWork， 其中 loginValue 是 apiKey。（注意：不
//        要在 Application 的 onCreate 里去做 startWork 的操作，否则可能造成应用循环重启的问题，
//        将严重影响应用的功能和性能。）
        HetPushManager.getInstance().init(new BdPushService(this));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        jumpToShareCode();
    }

    private void jumpToShareCode() {
        String shareCode = getIntent().getStringExtra("shareCode");
        Logc.e("shareCode = " + shareCode);
        if (!StringUtils.isNull(shareCode)) {
            if (HetSdk.getInstance().isAuthLogin()) {
                //设备授权
                HetDeviceShareApi.getInstance().authShareDevice(new IHetCallback() {
                    @Override
                    public void onSuccess(int code, String msg) {
                        RxManage.getInstance().post(HetShareEvent.HET_EVENT_MAIN_SHARE_SUCCEE, null);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                    }
                }, shareCode, "6");
            }
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    /**
     * 初始化 fragment
     *
     * @param savedInstanceState 这里处理Activity被系统回收之后，Fragment重叠的问题。 需要再再次启动的时候初始化Fragment
     */
    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            if (deviceListFragment == null) {
                deviceListFragment = DeviceListFragment.newInstance();
            }
            if (noLoginFragment == null) {
                noLoginFragment = NoLoginFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.container, deviceListFragment, "device").add(R.id.container, noLoginFragment, "login")
                    .commitAllowingStateLoss();

            if (HetSdk.getInstance().isAuthLogin()) {
                getSupportFragmentManager().beginTransaction().hide(noLoginFragment).show(deviceListFragment)
                        .commitAllowingStateLoss();
                mCurrentFragment = deviceListFragment;
            } else {
                getSupportFragmentManager().beginTransaction().hide(deviceListFragment).show(noLoginFragment)
                        .commitAllowingStateLoss();
                mCurrentFragment = noLoginFragment;
            }
        } else {//系统回收了  再次启动app的时候 初始化Fragmentment
            deviceListFragment = (DeviceListFragment) getSupportFragmentManager()
                    .findFragmentByTag("device");
            noLoginFragment = (NoLoginFragment) getSupportFragmentManager()
                    .findFragmentByTag("login");
            if (HetSdk.getInstance().isAuthLogin()) {
                getSupportFragmentManager().beginTransaction().hide(noLoginFragment).show(deviceListFragment)
                        .commitAllowingStateLoss();
                mCurrentFragment = deviceListFragment;
            } else {
                getSupportFragmentManager().beginTransaction().hide(deviceListFragment).show(noLoginFragment)
                        .commitAllowingStateLoss();
                mCurrentFragment = noLoginFragment;
            }
        }
        setDrawerNav();
    }

    /**
     * onSaveInstanceState会保存Activity的状态，比如activity中各种UI的状态。调用的时，activity可能销毁，也可能没有销毁
     * onRestoreInstanceState 只有在activity销毁重建时才会调用。
     * 恢复数据有两种途径onCreate(Bundle), onRestoreInstanceState(Bundle)
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Logc.d("C-life", "mainActivity被系统回收了");
    }

    private void showFragment() {
        if (HetSdk.getInstance().isAuthLogin()) {
            switchToFragment(FRAGMENT_SHOW_LIST);
        } else {
            switchToFragment(FRAGMENT_NO_SHOW_LIST);
        }
    }


    private void initRxManage() {
        RxManage.getInstance().register(HetCodeConstants.Login.LOGIN_SUCCESS, o -> {
            //登录成功
            showFragment();
            //获取用户信息
            pushBind();
            mNavigationDrawerFragment.setUserPhone();
            deviceListFragment.getDeviveList();
        });
        RxManage.getInstance().register(HetCodeConstants.Login.LOGINOUT_SUCCESS, o -> {
            //退出登录
            showFragment();
            HetUserManager.getInstance().clearUserModel();
        });
        RxManage.getInstance().register(HetCodeConstants.Login.EC_LOGINOUT, s -> {
            //退出登录
            showToast(getString(R.string.other_login));
            showFragment();
            HetUserManager.getInstance().clearUserModel();
            startMainActivity();
        });

    }

    //获取userId  绑定推送服务
    private void pushBind() {
        if (!HetSdk.getInstance().isAuthLogin()) return;
        UserInfoBean userInfoBean = HetUserManager.getInstance().getUserModel();
        if (userInfoBean != null) {
            HetPushManager.getInstance().startPushService(userInfoBean.getUserId());
            return;
        }
        HetUserApi.getInstance().getUserMess(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                Type type = new TypeToken<UserInfoBean>() {
                }.getType();
                UserInfoBean users = GsonUtil.getInstance().toObject(msg, type);
                HetUserManager.getInstance().setUserModel(users);
                HetPushManager.getInstance().startPushService(users.getUserId());
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRx();
        HetSdk.getInstance().destroy();
        System.exit(0);

    }

    private void unregisterRx() {
        RxManage.getInstance().unregister(HetCodeConstants.Login.LOGIN_SUCCESS);
        RxManage.getInstance().unregister(HetCodeConstants.Login.LOGINOUT_SUCCESS);
        RxManage.getInstance().unregister(HetCodeConstants.Login.EC_LOGINOUT);
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, SidebarMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        btnRight.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.add));
        toolbar.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.login_ico);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);


    }

    @Override
    protected void initTopBarView() {
        mTitleView.setVisibility(View.GONE);
        StatusBarUtil.setTranslucentForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawerlayout));
        main_bg.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
        setTitle("设备列表");
    }

    public void setTitle(String title) {
        tvToolbar.setText(!StringUtils.isNull(title) ? title : getString(R.string.app_name));
    }

    //set DrawerLayout and Toolbar onclick event
    private void setDrawerNav() {
        if (HetSdk.getInstance().isAuthLogin()) {

            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toolbar.setNavigationOnClickListener(v -> {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            });
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toolbar.setNavigationOnClickListener(v -> {
                mPresenter.startLogin();
            });
        }
    }

    @Override
    public void switchToFragment(int fragmentId) {
        switch (fragmentId) {
            case FRAGMENT_SHOW_LIST:
                swtchContent(deviceListFragment);
                break;
            case FRAGMENT_NO_SHOW_LIST:
                swtchContent(noLoginFragment);
                break;
        }
        setDrawerNav();
    }


    /**
     * 修改显示的内容 不会重新加载
     **/
    public void swtchContent(Fragment to) {
        if (mCurrentFragment == null) {
            initFragment(null);
            return;
        }
        FragmentTransaction transaction;
        if (mCurrentFragment != to) {
            transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (mCurrentFragment.isAdded()) {
                transaction.hide(mCurrentFragment).commitAllowingStateLoss();
            }
            transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.add(R.id.container, to)
                        .commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
            mCurrentFragment = to;
        }
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

    @OnClick({R.id.btnRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRight:
                if (HetSdk.getInstance().isAuthLogin()) {
                    jumpToTarget(DeviceTypeListActivity.class);
                } else {
                    mPresenter.startLogin();
                }
                break;
        }
    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long nowTime = System.currentTimeMillis();
            if (nowTime - mPressedTime > 2000) {
                showToast(getString(R.string.press_again));
                mPressedTime = nowTime;
            } else {
                // System.exit(0);
                super.onBackPressed();
            }
        }
    }


}
