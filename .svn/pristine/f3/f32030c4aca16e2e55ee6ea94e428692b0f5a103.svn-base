package com.het.sdk.demo.ui.activity.share;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.het.basic.utils.GsonUtil;
import com.het.basic.utils.StringUtils;
import com.het.basic.utils.ToastUtil;
import com.het.open.lib.biz.thirdlogin.HetSdkThirdDelegate;
import com.het.open.lib.model.share.DeviceAuthUserModel;
import com.het.open.lib.model.share.ShareCodeModel;
import com.het.recyclerview.ProgressStyle;
import com.het.recyclerview.swipemenu.SwipeMenuRecyclerView;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.ShareUserDevicesAdapter;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.MultipleStatusView;
import com.het.sdk.demo.widget.QMUIBottomSheet;
import com.het.share.listener.ICommonShareListener;
import com.het.share.manager.CommonShareOperate;
import com.het.share.manager.CommonSharePlatform;
import com.het.share.model.CommonShareWebpage;
import com.het.share.utils.CommonShareProxy;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class UserMessShareActivity extends BaseHetActivity<ShareDevicePersenter> implements DeviceShareView {

    @Bind(R.id.device_list)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView mMultipleStatusView;
    private ShareUserDevicesAdapter mAdapter;
    private String deviceId;
    private List<DeviceAuthUserModel> authUserModel = new ArrayList<>();
    private HetSdkThirdDelegate mShareManager;
    private CommonShareProxy mShareProxy;
    private String shareUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_mess_share;
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.share_device_user);
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
        mTitleView.setUpImgOption(R.mipmap.add, v -> {
            //把设备分享出去  select 1、面对面分享 2、第三方分享
            new QMUIBottomSheet.BottomListSheetBuilder(mContext)
                    .addItem("面对面分享")
                    .addItem("远程分享")
                    .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                        dialog.dismiss();
                        mPresenter.getShareCode(deviceId, position);
                    })
                    .build()
                    .show();
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(1);
        this.mSwipeMenuRecyclerView.setLayoutManager(layoutManager);
        this.mSwipeMenuRecyclerView.setPullRefreshEnabled(false);
        this.mSwipeMenuRecyclerView.setLoadingMoreEnabled(false);
        this.mSwipeMenuRecyclerView.setSwipeDirection(1);
        this.mSwipeMenuRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        this.mSwipeMenuRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        this.mSwipeMenuRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        mAdapter = new ShareUserDevicesAdapter(mContext, R.layout.share_device_item);
        mSwipeMenuRecyclerView.setAdapter(mAdapter);
        mAdapter.setISwipeMenuClickListener((var1, var2) -> {
            DeviceAuthUserModel authUser = authUserModel.get(var2);
            if (authUser != null && !StringUtils.isNull(authUser.getUserId())) {
                mPresenter.delshareDevice(deviceId, authUser.getUserId());
            } else {
                showTipText("设备分享的好友信息不全，不能删除");
            }
        });
    }

    private ICommonShareListener mICommonShareListener = new ICommonShareListener() {
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

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        deviceId = bundle.getString("DeviceModel");
        mShareManager = HetSdkThirdDelegate.getInstance();
        mShareProxy = new CommonShareProxy(this);
        mShareManager.setShareOperate(new CommonShareOperate(mContext));
        mShareManager.setShareListener(mICommonShareListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isNull(deviceId)) {
            mPresenter.getUserShareList(deviceId);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
        mMultipleStatusView.setOnRetryClickListener(onRetryClickListener);
        mMultipleStatusView.setOnRetryClickErrListener(onRetryClickErrListener);
    }

    private final View.OnClickListener onRetryClickListener = v -> {
        if (!StringUtils.isNull(deviceId)) {
            mPresenter.getUserShareList(deviceId);
        }
    };

    private final View.OnClickListener onRetryClickErrListener = v -> {
        if (!StringUtils.isNull(deviceId)) {
            mPresenter.getUserShareList(deviceId);
        }
    };

    @Override
    public void getUserShareList(String list) {
        if (!isFinishing()){
            Type type = new TypeToken<List<DeviceAuthUserModel>>() {
            }.getType();
            List<DeviceAuthUserModel> models = GsonUtil.getInstance().getGson().fromJson(list, type);
            if (models != null && models.size() > 0) {
                authUserModel.clear();
                authUserModel.addAll(models);
                mAdapter.setListAll(authUserModel);
                if (mMultipleStatusView != null) {
                    mMultipleStatusView.showContent();
                }
            } else {
                showTipText("您还未分享设备给好友!");
            }
        }
    }

    @Override
    public void getShareCode(String code, int type) {
        if (!isFinishing()){
            if (StringUtils.isNull(code)) {
                showToast("获取分享码失败");
                return;
            }
            Type treeType = new TypeToken<ShareCodeModel>() {
            }.getType();
            ShareCodeModel codeModel =GsonUtil.getInstance().getGson().fromJson(code, treeType);
            if (codeModel == null || StringUtils.isNull(codeModel.getShareCode())) {
                showToast("获取分享码失败");
                return;
            }
            if (type == 0) {//面对面分享  跳转到二维码的界面
                Bundle bundle = new Bundle();
                bundle.putSerializable(ShareCodeActivity.SHARE_CODE, codeModel.getShareCode());
                jumpToTarget(ShareCodeActivity.class, bundle);
            } else if (type == 1) {//远程分享 展示QQ 微信的分享界面
                showShareDialog(codeModel);
            }
        }
    }

    /**
     * 第三方分享 微信 QQ
     *
     * @param codeModel 分享的是一个连接 h5Url
     */
    private void showShareDialog(ShareCodeModel codeModel) {
        shareUrl = codeModel.getH5Url();
        if (StringUtils.isNull(shareUrl)) {
            ToastUtil.showToast(mContext, "分享的地址不能为空");
            return;
        }
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_QQ_MOMENT = 1;
        QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(this);
        builder.addItem(R.mipmap.common_share_logo_wechat, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.common_share_logo_qq, "分享到QQ", TAG_SHARE_QQ_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    switch (tag) {
                        case TAG_SHARE_WECHAT_FRIEND:
                            if (mICommonShareListener != null) {
                                mICommonShareListener.onStartShare(CommonSharePlatform.WeixinFriend);
                            }
                            break;
                        case TAG_SHARE_QQ_MOMENT:
                            if (mICommonShareListener != null) {
                                mICommonShareListener.onStartShare(CommonSharePlatform.QQ_Friend);
//                                mShareManager.authToSina(UserMessShareActivity.this);//新浪微博 分享
                            }
                            break;
                    }
                }).build().show();
    }

    @Override
    public void showTipText(String tips) {
        mMultipleStatusView.showError();
        mMultipleStatusView.setErrorText(tips);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mShareManager != null && mShareProxy != null) {
            mShareProxy.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mShareManager != null) {
            mShareManager.releaseResource();
            mContext = null;
            mShareManager = null;
        }
    }

}
