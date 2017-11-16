package com.het.open.lib.model;

import com.het.open.lib.api.HetCodeConstants;

/**sdk配置类
 * Created by xuchao on 2016/5/5.
 */
public class ConfigModel {


    @Override
    public String toString() {
        return "ConfigModel{" +
                "isSupportH5Plug=" + isSupportH5Plug +
                ", isLog=" + isLog +
                ", timeout=" + timeout +
                ", host=" + host +
                ", H5UIconfig='" + H5UIconfig + '\'' +
                '}';
    }

    private boolean isSupportH5Plug=true;  //是否支持h5插件开发
    private boolean isLog=true;        //是否开启调试信息
    private int timeout=30000;        //网络请求超时时间
    private int host= HetCodeConstants.TYPE_PREVIEW_HOST; //服务器环境地址
    public String getH5UIconfig() {
        return H5UIconfig;
    }

    public void setH5UIconfig(String h5UIconfig) {
        H5UIconfig = h5UIconfig;
    }

    private String H5UIconfig;

    public boolean isLog() {
        return isLog;
    }

    public void setLog(boolean log) {
        isLog = log;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }


    public boolean isSupportH5Plug() {
        return isSupportH5Plug;
    }

    public void setSupportH5Plug(boolean supportH5Plug) {
        isSupportH5Plug = supportH5Plug;
    }

}
