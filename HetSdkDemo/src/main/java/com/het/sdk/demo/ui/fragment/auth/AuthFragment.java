package com.het.sdk.demo.ui.fragment.auth;

import android.os.Bundle;
import android.view.View;

import com.het.sdk.demo.R;
import com.het.sdk.demo.base.BaseHetFragment;

import butterknife.OnClick;


/**
 * 授权fragment
 * Created by xuchao on 2016/6/30.
 */

public class AuthFragment extends BaseHetFragment<AuthPresenter> implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_auth;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @OnClick({R.id.btn_auth,
            R.id.btn_auto_auth,
            R.id.btn_get_user_mess})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auth:
                mPresenter.auth();
                break;
            case R.id.btn_auto_auth:
              //  mPresenter.autoAuth();
                break;
            case R.id.btn_get_user_mess:
                mPresenter.getUserMess();
                break;
        }
    }

}
