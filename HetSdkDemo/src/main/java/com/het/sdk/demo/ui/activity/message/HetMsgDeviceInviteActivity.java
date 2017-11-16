package com.het.sdk.demo.ui.activity.message;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.basic.base.RxManage;
import com.het.open.lib.model.message.DeviceDetailBean;
import com.het.open.lib.model.message.MessageBean;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.event.HetMessageEvent;
import com.het.sdk.demo.utils.MessageUtils;
import com.het.sdk.demo.utils.UIJsonConfig;

import butterknife.Bind;

public class HetMsgDeviceInviteActivity extends BaseHetActivity<MsgDevicePresenter> implements MsgInviteView {
    @Bind(R.id.sv_user)
    SimpleDraweeView svUser;
    @Bind(R.id.tv_user_name)
    TextView UserNameText;
    @Bind(R.id.sv_device)
    SimpleDraweeView svDevice;
    @Bind(R.id.tv_device_name)
    TextView deviceNameText;
    @Bind(R.id.rl_invite)
    RelativeLayout rlInvite;
    @Bind(R.id.tv_device_detail)
    TextView deviceDetailText;
    @Bind(R.id.btn_invite_agree)
    Button inviteAgreeBtn;

    private DeviceDetailBean deviceDetail;
    private MessageBean deviceMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_het_msg_device_invite;
    }

    @Override
    protected void initData() {
        this.deviceMessage = (MessageBean) this.getIntent().getExtras().getSerializable("deviceMsg");
        this.dimissvite();
        this.svUser.setImageURI(Uri.parse(this.deviceMessage.getIcon()));
        this.UserNameText.setText(MessageUtils.limitStrLength(this.deviceMessage.getTitle(), 16));
        //监听设备详情
        RxManage.getInstance().register(HetMessageEvent.HET_EVENT_MSG_GET_DEVICE_DETAIL, s -> {
                    if (s != null) {
                        deviceDetail = (DeviceDetailBean) s;
                        loadDeviceDetail(deviceDetail);
                    }
                }
        );
        this.inviteAgreeBtn.setOnClickListener(v -> {
            if (HetMsgDeviceInviteActivity.this.deviceDetail != null) {
                mPresenter.deviceAgree(HetMsgDeviceInviteActivity.this.deviceMessage.getBusinessParam());
            }

        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.tab_my_msg);
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    /**
     * 加载界面设备信息
     *
     * @param deviceDetail
     */
    private void loadDeviceDetail(DeviceDetailBean deviceDetail) {
        svDevice.setImageURI(Uri.parse(deviceDetail.getProductIcon()));
        deviceNameText.setText(MessageUtils.limitStrLength(deviceDetail.getProductName(), 10));
    }

    private void dimissvite() {
        if (deviceMessage == null) {
            return;
        }
        if (deviceMessage.getStatus() == 2) {
            deviceDetailText.setVisibility(View.VISIBLE);
            inviteAgreeBtn.setVisibility(View.GONE);
        } else {
            deviceDetailText.setVisibility(View.GONE);
            inviteAgreeBtn.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void success(int type) {
        switch (type) {
            case MsgDevicePresenter.MSG_DEVICE_ACCEPT_SUCCESS:
                mPresenter.updateMsg(String.valueOf(deviceMessage.getMessageId()));
                break;
            case MsgDevicePresenter.MSG_TYPE_LIST_UPDATE_SUCCESS:
                deviceDetailText.setVisibility(View.VISIBLE);
                inviteAgreeBtn.setVisibility(View.GONE);
                RxManage.getInstance().post(HetMessageEvent.HET_EVENT_MSG_AGREE_DEVICE_INVITE_SUCCESS, getString(R.string.common_msg_agree_invite_share_device));
                break;
            default:
        }
    }

    @Override
    public void Failed(int type) {
        switch (type) {
            case MsgDevicePresenter.MSG_DEVICE_ACCEPT_FAIL:
                tips(getString(R.string.common_msg_share_device_fail));
                break;
            case MsgDevicePresenter.MSG_TYPE_LIST_UPDATE_FAIL:
                tips(getString(R.string.common_msg_update_fail));
                break;
        }
    }
}
