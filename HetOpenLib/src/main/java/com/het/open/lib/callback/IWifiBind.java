package com.het.open.lib.callback;

import com.het.open.lib.model.DeviceModel;

/**wifi设备绑定接口回调
 * Created by xuchao on 2016/3/15.
 */
public interface IWifiBind {

    /**
     * 绑定状态
     * @param hetWifiBindState  绑定状态
     * 0 初始 1 开始扫描 2 扫描到设备 3 提交设备信息成功
     * 4 开始绑定 5 绑定成功 6 异常
     * @param msg     绑定状态描述
     */
    void onStatus(HetWifiBindState hetWifiBindState, String msg);

    /**
     * 绑定失败
     * @param errId  绑定失败代码
     *  10001 未扫描到任何设备
     *  10002 绑定设备失败
     * @param msg    绑定失败描述
     */
    void onFailed(int errId,String msg);

    /**
     * 绑定成功
     * @param deviceModel 绑定成功的设备model
     */
    void onSuccess(DeviceModel deviceModel);


    /**
     * 扫描进度
     * @param type  类型
     * @param value 扫描设备进度值
     */
    void onProgress(int type,int value);
}
