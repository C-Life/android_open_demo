package com.het.open.lib.biz.network;

import com.het.open.lib.callback.ICallback;

import java.util.Map;
import java.util.TreeMap;

/**
 * interface for all Business
 * update by
 * Created by weatherfish on 2015/1/25.
 */
public interface IBaseNetwork {

    //请求方法：GET/POST等，不设置则默认POST
    void setMethod(int method) ;
    //请求类型，设置则表示使用https，默认http
    void setHttps();
    //设置tag，可以在cancel的时候通过tag取消网络请求
    void setTag(Object tag);




    Object getTag();
    //除去Http(s)://以外url
    void setUrl(String mUrl);
    //参数Map
    void setParams(TreeMap<String, String> mParams);
    //请求头
    void setHeader(Map<String, String> mHeader) ;
    //资源id
    void setId(int mId);
    //设置回调
    void setCallBack(ICallback iCallback);
    //解析json头部
    void setIJsonCodeParse(IJsonCodeParse jsonCodeParse);

    //设置超时时间
    void setTimeOut(int timeOut);

    //设置超时时间和重试次数等，会覆盖setTimeOut设置的值
    //void setRetryPolicy(RetryPolicy retryPolicy);

    void commit();

}
