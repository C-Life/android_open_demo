package com.het.sdk.demo.ui.activity.bind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.widget.TextView;

import com.het.basic.utils.ActivityManager;
import com.het.bind.logic.api.ApiBind;
import com.het.bind.logic.bean.device.DeviceProductBean;
import com.het.log.Logc;
import com.het.open.lib.api.HetGprsBindApi;
import com.het.open.lib.callback.IHetCallback;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.model.QrCodeModel;
import com.het.sdk.demo.utils.MacIMEIBindHelper;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.ClearEditText;
import com.het.ui.sdk.CommonToast;

import butterknife.Bind;
import butterknife.OnClick;

public class MacImeiBindActivity extends BaseHetActivity {

    private static final String TAG = MacImeiBindActivity.class.getSimpleName();
    private static final String DEVICE_PRODUCT = "device_product_bean";
    private static final String DEVICE_QR_MODEL = "device_qr_model";

    @Bind(R.id.tv_activity_bind_tip1)
    TextView mTvTip1;
    @Bind(R.id.tv_activity_bind_tip2)
    TextView mTvTip2;
    @Bind(R.id.tv_activity_bind_input_tip)
    TextView mTvEditTopTip;
    @Bind(R.id.et_activity_bind_macorimei)
    ClearEditText mClearEditText;

    private int mBindType;//默认为绑定IMEI码模式
    private String mDeviceName;

    private int mProductId = -1;
    private String mMac;
    private String mImei;

    public static void startBindAty(Context context, DeviceProductBean bean, QrCodeModel qrCodeModel) {
        Intent intent = new Intent(context, MacImeiBindActivity.class);
        intent.putExtra(DEVICE_PRODUCT, bean);
        if (qrCodeModel != null) {
            intent.putExtra(DEVICE_QR_MODEL, qrCodeModel);
        }
        context.startActivity(intent);
    }

    public static void startBindAty(Context context, DeviceProductBean bean) {
        Intent intent = new Intent(context, MacImeiBindActivity.class);
        intent.putExtra(DEVICE_PRODUCT, bean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mac_imei_bind;
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(DEVICE_PRODUCT)) {
            initInfo((DeviceProductBean) extras.getSerializable(DEVICE_PRODUCT));
        }
        if (extras != null && extras.containsKey(DEVICE_QR_MODEL)) {
            initInfoQr((QrCodeModel) extras.getSerializable(DEVICE_QR_MODEL));
        }

        initDeviceName();
    }


    private void initInfoQr(QrCodeModel qrCodeModel) {
        if (qrCodeModel == null) {
            return;
        }
        if (MacIMEIBindHelper.getInstance().isBindTypeIMEI(mBindType)) {
            setEditTextContent(qrCodeModel.getI());
        } else if (MacIMEIBindHelper.getInstance().isBindTypeMac(mBindType)) {
            setEditTextContent(qrCodeModel.getM());
        }
    }

    private void setEditTextContent(String contentString) {
        if (!TextUtils.isEmpty(contentString)) {
            mClearEditText.setText(contentString);
            mClearEditText.setSelection(contentString.length());
        }
    }

    private void initInfo(DeviceProductBean bean) {
        if (bean == null) {
            return;
        }
        mProductId = bean.getProductId();
        mDeviceName = bean.getProductName();
        mBindType = bean.getBindType();
        if (!TextUtils.isEmpty(mDeviceName)) {
            mTvTip1.setText(getResString(R.string.str_aty_macorimei_tip11) + mDeviceName + getResString(R.string.str_aty_macorimei_tip12));
            mTvTip2.setText(getResString(R.string.str_aty_macorimei_tip21) + mDeviceName + getResString(R.string.str_aty_macorimei_tip22));
        }
        mTvEditTopTip.setText(MacIMEIBindHelper.getInstance().isBindTypeIMEI(mBindType) ? getResString(R.string.str_aty_macorimei_toptip_imei) : getResString(R.string.str_aty_macorimei_toptip_mac));
        mTvEditTopTip.setKeyListener(DigitsKeyListener.getInstance(MacIMEIBindHelper.getInstance().isBindTypeIMEI(mBindType) ? "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM" : "0123456789ABCDEFabcdef"));
    }

    private void initDeviceName() {
        if (mProductId == -1 || !TextUtils.isEmpty(mDeviceName)) {
            return;
        }
        showDialog(getResString(R.string.str_aty_macorimei_initing));
        ApiBind.getInstance().getProductByIdOrCode(String.valueOf(mProductId), null).subscribe(s -> {
            initInfo(s.getData());
            hideDialog();
        }, e -> {
            hideDialog();
            Logc.d("e-->" + e);
        });

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.btn_device_bind);
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @OnClick({R.id.btn_activity_bind_macorimei})
    public void onclick() {
        startBind();
    }

    private void startBind() {
        //我的IMEI:862151031300823 韩的：8621 5103 1312 638
        //                  86215 10313 00823
        //Mac 38A28C1DF284
        String inputStr = mClearEditText.getText().toString().trim();
        if (TextUtils.isEmpty(inputStr)) {
            CommonToast.showShortToast(MacImeiBindActivity.this, getResString(R.string.str_aty_macorimei_input_nonull));
            return;
        }
        if (MacIMEIBindHelper.getInstance().isBindTypeIMEI(mBindType)) {
            mImei = inputStr;
            if (mImei.length() != 15) {
                CommonToast.showShortToast(MacImeiBindActivity.this, getResString(R.string.str_aty_macorimei_input_imei));
                return;
            }
        } else {
            mMac = inputStr;
            if (mMac.length() != 12) {
                CommonToast.showShortToast(MacImeiBindActivity.this, getResString(R.string.str_aty_macorimei_input_mac));
                return;
            }
        }
        showDialog(getResString(R.string.str_aty_macorimei_binding));
        HetGprsBindApi.getInstance().startBind(new IHetCallback() {
            @Override
            public void onSuccess(int code, String msg) {
                hideDialog();
                if (MacIMEIBindHelper.getInstance().hasBindListener()) {
                    MacIMEIBindHelper.getInstance().getOnBindListener().bindSuccess(msg);
                }
                ActivityManager.getInstance().finishActivity(DeviceTypeListActivity.class);
                ActivityManager.getInstance().finishActivity(DeviceSubTypeListActivity.class);
                MacImeiBindActivity.this.finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                CommonToast.showToast(MacImeiBindActivity.this, msg);
                hideDialog();
            }
        }, mMac, mImei, mProductId);
    }

    private String getResString(int id) {
        return MacImeiBindActivity.this.getString(id);
    }

}
