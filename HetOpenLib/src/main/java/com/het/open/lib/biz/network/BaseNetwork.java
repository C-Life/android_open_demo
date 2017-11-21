//package com.het.open.lib.biz.network;
//
//import android.net.Uri;
//
//import com.android.volley.NetworkError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.manager.api.StringApi;
//import com.het.open.lib.api.HetSdk;
//import com.het.open.lib.biz.constans.ComErrorCode;
//import com.het.open.lib.biz.manager.NetworkQueueManager;
//import com.het.open.lib.callback.ICallback;
//import com.het.open.lib.utils.HetSharePreferencesUtil;
//import com.het.open.lib.utils.LogUtils;
//import com.het.open.lib.utils.NetworkUtils;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.TreeMap;
//
///**
// * Base Encapsulate for Business. Created by weatherfish on 2015/1/27.
// */
//public class BaseNetwork implements IBaseNetwork {
//
//    protected Response.Listener<String> mListener;
//    protected Response.ErrorListener mErrorListener;
//    protected TreeMap<String, String> mParams;
//    protected boolean mIsHttps;
//    protected String mUrl;
//    protected String mGetUrl;
//    protected Map<String, String> mHeader;
//    protected ICallback mCallBack;
//    protected int mId;
//    protected int mMethod = Request.Method.POST;
//    protected Object mTag;
//    protected IJsonCodeParse mIJsonCodeParse;
//    protected int mTimeOut = NetworkQueueManager.getInstance().getTimeOut() != HetSharePreferencesUtil.INT_KEY_NOT_FOUND ? NetworkQueueManager.getInstance().getTimeOut() : HetSharePreferencesUtil.KEY_NOT_FOUND;
//    protected RetryPolicy mRetryPolicy;
//    protected boolean mIsCompleGet = false;
//
//    @Override
//    public void setMethod(int method) {
//        mMethod = method;
//    }
//
//    @Override
//    public void setHttps() {
//        mIsHttps = true;
//    }
//
//    @Override
//    public void setTag(Object tag) {
//        mTag = tag;
//    }
//
//
//
//    @Override
//    public Object getTag() {
//        return mTag;
//    }
//
//    @Override
//    public void setUrl(String url) {
//        mUrl = url;
//    }
//
//    @Override
//    public void setParams(TreeMap<String, String> params) {
//        mParams = params;
//    }
//
//    @Override
//    public void setHeader(Map<String, String> header) {
//        mHeader = header;
//    }
//
//    @Override
//    public void setId(int id) {
//        mId = id;
//    }
//
//    @Override
//    public void setCallBack(ICallback iCallback) {
//        mCallBack = iCallback;
//    }
//
//    @Override
//    public void setIJsonCodeParse(IJsonCodeParse jsonCodeParse) {
//        mIJsonCodeParse = jsonCodeParse;
//    }
//
//    @Override
//    public void setTimeOut(int timeOut) {
//        this.mTimeOut = timeOut;
//    }
//
//    @Override
//    public void setRetryPolicy(RetryPolicy retryPolicy) {
//        this.mRetryPolicy = retryPolicy;
//    }
//
////    public static String getMetaUrl() {
////        ApplicationInfo appInfo = null;
////        try {
////            appInfo = AppContext.getInstance().getAppContext().getPackageManager().getApplicationInfo(AppContext.getInstance().getAppContext().getPackageName(), PackageManager.GET_META_DATA);
////        } catch (PackageManager.NameNotFoundException e) {
////            e.printStackTrace();
////        }
////       // return appInfo.metaData.getString("myMsg");
////        return appInfo.metaData.getString(ComParamContant.COM_URL_HEAD);
////
////    }
//
//    /**
//     * 处理url
//     * mUrl   填入不带http(s)://的url内容
//     *
//     * @return
//     */
//    protected String Url2Uri() {
////        if (!mUrl.contains("://")) {
////            if (mIsHttps) {
////                mUrl = "https://" + mUrl;
////            } else {
////                mUrl = "http://" + mUrl;
////            }
////        }
////        if (getMetaUrl() != null) {
////            mUrl = mUrl.replace(ComUrls.SERVER_HOST, getMetaUrl());
////        }
//        Uri.Builder uriBuilder = Uri.parse(mUrl).buildUpon();
//        return uriBuilder.build().toString();
//    }
//
//    /**
//     * 获取get方法的url参数
//     *
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    protected String completeGetUrl() throws UnsupportedEncodingException {
//        StringBuilder sb = new StringBuilder(Url2Uri());
//        if (mMethod == Methods.GET && mParams != null) {
//            String tmp;
//            Iterator<String> it = mParams.keySet().iterator();
//            if (it.hasNext()) {
//                sb.append("?");
//            }
//            while (it.hasNext()) {
//                tmp = it.next();
//                String value = mParams.get(tmp);
//                if (value == null)
//                    value = "";
//                sb.append(tmp).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
//            }
//            sb.deleteCharAt(sb.length() - 1);
//        }
//        mParams = null;
//        return sb.toString();
//    }
//
//    /**
//     * 完善提交参数信息
//     * 进行初步本地信息判断
//     */
//    @Override
//    public void commit() {
//        if (HetSdk.getInstance().getApplication() == null) {
//            LogUtils.d("Volley", "Volley not init!");
//            mCallBack.onFailure(ComErrorCode.VOLLEY_NOT_INIT, "Volley not init", mId);
//            NetworkQueueManager.getInstance().cancelAndrRemoveAllNetwork();
//        } else if (!NetworkUtils.isNetworkAvailable(HetSdk.getInstance().getApplication())) {
//            LogUtils.d("Volley", "NO NetWork Connected!");
//            mCallBack.onFailure(ComErrorCode.NO_NETWORK, "网络未连接", mId);
//            NetworkQueueManager.getInstance().cancelAndrRemoveAllNetwork();
//        } else {
//            dealNetworkArgs();
//            execCommit();
//        }
//    }
//
//    private void dealNetworkArgs() {
//        if (mTag == null) {
//            mTag = new Object();
//        }
//        dealUserAgent();
//        mListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String json, int id) {
//                dealSuccess(json, id);
//            }
//        };
//        mErrorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError, int id) {
//                dealError(volleyError, id);
//            }
//        };
//        completeOtherParams();
//        // TreeMap<String, String> param = (mMethod == Request.Method.GET ? null : mParams);
//        try {
//            mGetUrl = (mMethod == Request.Method.GET ? completeGetUrl() : Url2Uri());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            LogUtils.d("Volley", "UnsupportedEncodingException!");
//            mCallBack.onFailure(ComErrorCode.UnsupportedEncodingException, "UnsupportedEncodingException", mId);
//        }
//    }
//
//    /**
//     * 补全其他通用参数
//     */
//    protected void completeOtherParams() {
//
//    }
//
//    /**
//     * 处理user-agent
//     */
//    protected void dealUserAgent() {
//
//    }
//
//    /**
//     * 处理错误的结果解析
//     *
//     * @param volleyError
//     * @param id
//     */
//    protected void dealError(VolleyError volleyError, int id) {
//        dealVolleyError(volleyError, id);
//    }
//
//    /**
//     * 解析正常的结果
//     *
//     * @param json
//     * @param id
//     */
//    protected void dealSuccess(String json, int id) {
//        if (mIJsonCodeParse == null) {
//            mIJsonCodeParse = new BaseJsonCodeParse();
//            LogUtils.e("use default IJsonCodeParse BaseJsonCodeParse");
//        }
//        mIJsonCodeParse.parse(mCallBack, json, id);
//    }
//
//    /**
//     * 处理Volley抛出的异常
//     *
//     * @param volleyError
//     * @param id
//     */
//    private void dealVolleyError(VolleyError volleyError, int id) {
//        if (volleyError instanceof TimeoutError) {
//            mCallBack.onFailure(ComErrorCode.TIME_OUT, "TIME_OUT", id);
//
//        } else if (volleyError instanceof ServerError) {
//            mCallBack.onFailure(ComErrorCode.SERVER_ERROR, "SERVER_ERROR", id);
//        } else if (volleyError instanceof ParseError) {
//            mCallBack.onFailure(ComErrorCode.PARSE_ERROR, "PARSE_ERROR", id);
//        } else if (volleyError instanceof NetworkError) {
//            mCallBack.onFailure(ComErrorCode.NETWORK_ERROR, "NETWORK_ERROR", id);
//        } else {
//            mCallBack.onFailure(ComErrorCode.VOLLEY_ERROR, "VOLLEY_ERROR", id);
//        }
//        LogUtils.e(volleyError.getMessage() == null ? "volley error" : volleyError.getMessage());
//    }
//
//    /**
//     * 实际提交命令
//     */
//    public void execCommit() {
//
//      //  new StringApi(mParams, mMethod == Methods.GET ? mGetUrl : mUrl).submit( mHeader, mListener, mErrorListener, mId);
//
//       new StringApi(mParams, mMethod == Methods.GET ? mGetUrl : mUrl).setTag(mTag).setTimeOut(mTimeOut).setRetryPolicy(mRetryPolicy).submit(mMethod, mHeader, mListener, mErrorListener, mId);
//        addRequest2Queue();
//    }
//
//    public void addRequest2Queue() {
//
//    }
//}
