package com.het.sdk.demo.ui.activity.share;

import com.het.sdk.demo.base.BaseHetView;

/**
 * Created by Administrator on 2017-10-10.
 */

public interface DeviceShareView extends BaseHetView {

    void getUserShareList(String list);

    void getShareCode(String code, int type);

    void showTipText(String tips);
}
