package com.het.open.lib.biz.network;


import com.het.open.lib.biz.init.SdkManager;
import com.het.open.lib.utils.HetSharePreferencesUtil;

/**
 * Created by weatherfish on 2016/1/16.
 */
public final class TimeDiffDeal {

    public static final String TIME_DIFF_NAME = "timeDiff";

    public static void putTimeDiff(long diff) {
        HetSharePreferencesUtil.putLong(SdkManager.getInstance().getApplication(), TIME_DIFF_NAME, diff);
    }

    public static long getTimeDiff() {
        return HetSharePreferencesUtil.getLong(SdkManager.getInstance().getApplication(), TIME_DIFF_NAME);
    }
}
