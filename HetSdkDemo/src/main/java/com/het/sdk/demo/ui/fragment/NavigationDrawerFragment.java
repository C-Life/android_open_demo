package com.het.sdk.demo.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.het.basic.utils.StringUtils;
import com.het.bind.logic.bean.UserInfoBean;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.base.BaseHetFragment;
import com.het.sdk.demo.manager.BuildManager;
import com.het.sdk.demo.ui.activity.auth.ThirdAuthActivity;
import com.het.sdk.demo.ui.activity.feedback.FeedbackAddActivity;
import com.het.sdk.demo.ui.activity.message.MessageCenterActivity;
import com.het.sdk.demo.ui.activity.singlelayout.QuestionActivity;
import com.het.sdk.demo.ui.fragment.device.LoginPresenter;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.ItemView;
import com.het.ui.sdk.CommonTopBar;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017-09-06.
 */

public class NavigationDrawerFragment extends BaseHetFragment<LoginPresenter> implements UserHetView {

    @Bind(R.id.v_current_count)
    ItemView vCurrentCount;
    @Bind(R.id.v_update_password)
    ItemView vUpdatePassword;
    @Bind(R.id.itemview_mymsg)
    ItemView itemviewMymsg;
    @Bind(R.id.v_normal_question)
    ItemView vNormalQuestion;
    @Bind(R.id.v_feedback)
    ItemView vFeedback;
    @Bind(R.id.v_privacy)
    ItemView vPrivacy;
    @Bind(R.id.v_copyright)
    ItemView vCopyright;

    @Bind(R.id.iv_unread)
    ImageView ivUnread;
    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private WeakReference<BaseHetActivity> mActivity;
    private View mFragmentContainerView;

    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.logout)
    TextView logout;
    @Bind(R.id.topbar)
    CommonTopBar mTitleView;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_navigation_drawer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        BuildManager.setStatusTrans(getActivity(), 1, mTitleView, rootView);
        mTitleView.setTitle(getString(R.string.tab_my));
        mTitleView.setLeftClick(v -> {
            if (isDrawerOpen()) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
        setUserPhone();

    }

    /**
     *  获取完整的用户信息
     */
    public void setUserPhone(){
        mPresenter.getUserInfo();
    }

    @OnClick({R.id.logout, R.id.v_update_password,
            R.id.itemview_mymsg, R.id.v_normal_question,
            R.id.v_feedback, R.id.v_privacy, R.id.v_copyright})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_update_password://修改密码
                mPresenter.editPwd();
                break;
            case R.id.itemview_mymsg://我的消息
                ((BaseHetActivity) getActivity()).jumpToTarget(MessageCenterActivity.class);
                break;
            case R.id.v_normal_question://常见问题
                ((BaseHetActivity) getActivity()).jumpToTarget(QuestionActivity.class);
                break;
            case R.id.v_feedback://意见反馈
                ((BaseHetActivity) getActivity()).jumpToTarget(FeedbackAddActivity.class);
                break;
            case R.id.v_privacy://隐私政策
                break;
            case R.id.v_copyright://版权声明


                break;
            case R.id.logout://退出登录
                loginOut();
                break;
        }
    }



    /**
     * 退出登录
     */
    private void loginOut() {
        mPresenter.logout();
    }

    public void setConfigUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = mActivity.get().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                mActivity.get(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                toolbar,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                mActivity.get().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                mActivity.get().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.post(() -> mDrawerToggle.syncState());
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = new WeakReference<>((BaseHetActivity) context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showUser(UserInfoBean user) {
        if (user != null && !StringUtils.isNull(user.getPhone())) {
            vCurrentCount.setItemRightText(user.getPhone());
        }
    }
}
