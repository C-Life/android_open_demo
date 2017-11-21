//package com.het.open.lib.biz.network;
//
//import com.android.volley.RetryPolicy;
//import com.het.open.lib.callback.ICallback;
//
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Created by weatherfish on 2015/9/24.
// */
//public class NetworkBuilder {
//
//    protected final IBaseNetwork baseNetwork;
//
//    /**
//     * @param baseNetwork
//     */
//    public NetworkBuilder(IBaseNetwork baseNetwork) {
//        this.baseNetwork = baseNetwork;
//    }
//
//    /**
//     * 请求方法：GET/POST等，不设置则默认POST
//     */
//    public NetworkBuilder setMethod(int method) {
//        baseNetwork.setMethod(method);
//        return this;
//    }
//
//    /**
//     * 请求类型，设置则表示使用https，默认http
//     *
//     * @return
//     */
//    public NetworkBuilder setHttps() {
//        baseNetwork.setHttps();
//        return this;
//    }
//
//    /**
//     * 设置tag，可以在cancel的时候通过tag取消网络请求
//     *
//     * @param tag
//     * @return
//     */
//    public NetworkBuilder setTag(Object tag) {
//        baseNetwork.setTag(tag);
//        return this;
//    }
//
//    public NetworkBuilder setRetryPolicy(RetryPolicy retryPolicy) {
//        baseNetwork.setRetryPolicy(retryPolicy);
//        return this;
//    }
//
//    public NetworkBuilder setTimeout(int timeout) {
//        baseNetwork.setTimeOut(timeout);
//        return this;
//    }
//
//    /**
//     * 除去域名以外url
//     *
//     * @param url
//     * @return
//     */
//    public NetworkBuilder setUrl(String url) {
//        baseNetwork.setUrl(url);
//        return this;
//    }
//
//    /**
//     * 参数Map
//     *
//     * @param params
//     * @return
//     */
//    public NetworkBuilder setParams(TreeMap<String, String> params) {
//        baseNetwork.setParams(params);
//        return this;
//    }
//
//    /**
//     * 请求头
//     *
//     * @param headers
//     * @return
//     */
//    public NetworkBuilder setHeader(Map<String, String> headers) {
//        baseNetwork.setHeader(headers);
//        return this;
//    }
//
//    /**
//     * 资源id,统一标识符
//     *
//     * @param id
//     * @return
//     */
//    public NetworkBuilder setId(int id) {
//        baseNetwork.setId(id);
//        return this;
//    }
//
//    /**
//     * token标识，默认都有，设置表示没有
//     *
//     * @return
//     */
//    public NetworkBuilder setCallBack(ICallback callBack) {
//        baseNetwork.setCallBack(callBack);
//        return this;
//    }
//
//    /**
//     * token标识，默认都有，设置表示没有
//     *
//     * @return
//     */
//    public NetworkBuilder setIJsonCodeParse(IJsonCodeParse jsonCodeParse) {
//        baseNetwork.setIJsonCodeParse(jsonCodeParse);
//        return this;
//    }
//
//    /**
//     * 提交网络请求
//     */
//    public void commit() {
//        baseNetwork.commit();
//    }
//
//}
