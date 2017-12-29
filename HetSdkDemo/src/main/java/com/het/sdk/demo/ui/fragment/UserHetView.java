package com.het.sdk.demo.ui.fragment;

import com.het.sdk.demo.base.BaseHetView;
import com.het.sdk.demo.model.HetUserInfoBean;

/**
 * Created by Administrator on 2017-09-08.
 */

public interface UserHetView extends BaseHetView {

    void showUser(HetUserInfoBean user);
}
