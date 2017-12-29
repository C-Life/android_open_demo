package com.het.sdk.demo.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

import com.het.sdk.demo.R;

/**
 * Created by Administrator on 2017-09-07.
 */

public class BindAlertDialog extends Dialog {
    private String mLoadingTip = "正在加载中";
    private TextView mLoadingTv, tv_close;
    private OnKeyBack onKeyBack;
    private int backCount;
    OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                backCount++;
                if (onKeyBack != null && backCount < 2) {
                    onKeyBack.onClose();
                }
                return true;
            } else {
                return false;
            }
        }
    };

    public BindAlertDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
        setOnKeyListener(keylistener);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backCount = 0;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {

    }

    public void setContent(String str) {
        mLoadingTv.setText(str);
    }

    private void initView() {
        setContentView(R.layout.bind_binding_loading);
        tv_close = (TextView) findViewById(R.id.bind_load_close);
        mLoadingTv = (TextView) findViewById(R.id.bindTextView);
        tv_close.setOnClickListener(v -> {
            if (onKeyBack != null) {
                onKeyBack.onClose();
            }
        });
    }

    public void showProgressDialog(String message) {
        show();
        setContent(message);
    }

    public void hideProgressDialog() {
        backCount = 0;
        hide();
    }

    public void setBackCount(int backCount) {
        this.backCount = backCount;
    }

    public void setOnKeyBack(OnKeyBack onKeyBack) {
        this.onKeyBack = onKeyBack;
    }

    public interface OnKeyBack {
        void onClose();
    }
}