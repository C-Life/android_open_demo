package com.het.sdk.demo.ui.activity.share;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.het.basic.utils.StringUtils;
import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.RxQRCode;
import com.het.sdk.demo.utils.UIJsonConfig;

import butterknife.Bind;

public class ShareCodeActivity extends BaseHetActivity {

    @Bind(R.id.ll_code)
    LinearLayout mLlCode;
    @Bind(R.id.iv_qr_code)
    ImageView mIvQrCode;

    private String shareCode;
    public static final String SHARE_CODE = "shareCode";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_code;
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.face_to_faca);
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }

    @Override
    protected void initData() {
        Bundle bundle = this.getIntent().getExtras();
        shareCode = bundle.getString(SHARE_CODE);
        if (!StringUtils.isNull(shareCode)) {
            mLlCode.setVisibility(View.VISIBLE);
            RxQRCode.builder(shareCode).
                    backColor(getResources().getColor(R.color.white)).
                    codeColor(getResources().getColor(R.color.black)).
                    codeSide(800).
                    into(mIvQrCode);
        }
    }

    /**
     * 设置屏幕亮度  只针对这个Activity  离开就恢复
     *
     * @param context
     * @param brightness
     */
    private void setLight(Activity context, int brightness) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        context.getWindow().setAttributes(lp);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setLight(this, 200);
    }
}
