package com.het.sdk.demo.ui.activity.message;

import com.het.open.lib.model.message.MessageListByPageBean;
import com.het.sdk.demo.base.BaseHetView;

/**
 * Created by Administrator on 2017-10-11.
 */

public interface MsgInviteView extends BaseHetView {
    void success(int type);

    void Failed(int type);
}
