package com.het.open.lib.model;

import java.util.List;

/**蓝牙历史数据列表
 * Created by xuchao on 2016/5/6.
 */
public class ThirdBLeDataModel {

    private List<BleFileDataModel> list;  //蓝牙数据
    private PagerModel pager;  //分页信息


    public PagerModel getPager() {
        return pager;
    }

    public void setPager(PagerModel pager) {
        this.pager = pager;
    }

    public List<BleFileDataModel> getList() {
        return list;
    }

    public void setList(List<BleFileDataModel> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ThirdBLeDataModel{" +
                "list=" + list +
                ", pager=" + pager +
                '}';
    }
}
