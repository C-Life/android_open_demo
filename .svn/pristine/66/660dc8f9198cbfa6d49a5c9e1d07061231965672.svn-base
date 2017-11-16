//package com.het.open.lib.biz.network;
//
//import com.android.volley.VolleyError;
//import com.het.open.lib.api.HetSdk;
//import com.het.open.lib.auth.HetAuthMess;
//import com.het.open.lib.biz.constans.ComParamContant;
//import com.het.open.lib.biz.manager.NetworkQueueManager;
//import com.het.open.lib.biz.manager.TokenManager;
//import com.het.open.lib.utils.MD5Utils;
//import com.het.open.lib.utils.SystemInfoUtils;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.TreeMap;
//
///**
// * Created by weatherfish on 2015/9/24.
// */
//public class HetBaseNetwork extends BaseNetwork implements IHetBaseNetwork {
//
//    protected boolean mNoAccessToken;
//    protected boolean mNoTimestamp;
//    protected boolean mSign;
//    protected boolean mVersion=false;
//    private boolean mTokenExpired = false;
//
//    @Override
//    public void setSign() {
//        mSign = true;
//    }
//
//    @Override
//    public void setNoTimestamp() {
//        mNoTimestamp = true;
//    }
//
//    @Override
//    public void setNoAccessToken() {
//        mNoAccessToken = true;
//    }
//
//    @Override
//    public void setVersion() {
//        mVersion=true;
//    }
//
//
//    /**
//     * 参数签名
//     *
//     * @return
//     */
//    public String params2sign() {
//        String tmp;
//        StringBuilder sb = new StringBuilder("POST");
//        sb.append(Url2Uri());
//        Iterator<String> it = mParams.keySet().iterator();
//        while (it.hasNext()) {
//            tmp = it.next();
//            sb.append(tmp).append("=").append(mParams.get(tmp)).append("&");
//        }
//        sb.append(HetAuthMess.getInstance().getAppKey());
//        // LogUtils.d("sign param", sb.toString());
//        return MD5Utils.getMD5(sb.toString());
//    }
//
//    /**
//     * 补全其他参数
//     *
//     * @return
//     */
//    @Override
//    protected void completeOtherParams() {
//
//        if (mParams == null) {
//            mParams = new TreeMap<String, String>();
//        }
//        if (mParams.containsKey(ComParamContant.AppSecret.APP_ID))
//            mParams.remove(ComParamContant.AppSecret.APP_ID);
//        mParams.put(ComParamContant.AppSecret.APP_ID, HetAuthMess.getInstance().getAppId());
//
//        if (!mNoAccessToken) {
//            if (mParams.containsKey(ComParamContant.Token.ACCESS_TOKEN))
//                mParams.remove(ComParamContant.Token.ACCESS_TOKEN);
//            mParams.put(ComParamContant.Token.ACCESS_TOKEN, TokenManager.getInstance().getAuthModel().getAccessToken());
//
//        }
//        if (!mNoTimestamp) {
//            if (mParams.containsKey(ComParamContant.TIMESTAMP))
//                mParams.remove(ComParamContant.TIMESTAMP);
//            mParams.put(ComParamContant.TIMESTAMP, String.valueOf(System.currentTimeMillis() + TimeDiffDeal.getTimeDiff()));
//
//        }
//        if (mVersion){
//            if (mParams.containsKey(ComParamContant.VERSION))
//                mParams.remove(ComParamContant.VERSION);
//            mParams.put(ComParamContant.VERSION, "1.1");
//        }
//        if (mSign) {
//            if (mParams.containsKey(ComParamContant.AppSecret.SIGN))
//                mParams.remove(ComParamContant.AppSecret.SIGN);
//            mParams.put(ComParamContant.AppSecret.SIGN, params2sign());
//
//        }
//
//        //LogUtils.d("BaseNetwork", Url2Uri() + " the params is " + mParams.toString());
//    }
//
//    @Override
//    protected void dealSuccess(String json, int id) {
//        if (mIJsonCodeParse == null) {
//            mIJsonCodeParse = new HetJsonCodeParse();
//            //  LogUtils.e("use default IJsonCodeParse HetJsonCodeParse");
//        }
//        Boolean flag = mIJsonCodeParse.parse(mCallBack, json, id);
//        if (flag) {
//            mTokenExpired = true;
//        }
//        if (!mNoAccessToken && !flag) {
//            // LogUtils.e("remove from queue" + this.toString());
//            NetworkQueueManager.getInstance().remove(this);
//        }
//    }
//
//    @Override
//    protected void dealError(VolleyError volleyError, int id) {
//        super.dealError(volleyError, id);
//        if (!mNoAccessToken) {
//            // LogUtils.e("remove from queue" + this.toString());
//            NetworkQueueManager.getInstance().remove(this);
//        }
//    }
//
//    @Override
//    protected void dealUserAgent() {
//        if (mHeader == null) {
//            mHeader = new HashMap<String, String>(4);
//            mHeader.put("User-Agent", SystemInfoUtils.getUserAgent2(HetSdk.getInstance().getApplication(), HetAuthMess.getInstance().getAppId()));
//        }
//    }
//
//    @Override
//    public void addRequest2Queue() {
//        if (!mNoAccessToken && !mTokenExpired) {
//            //   LogUtils.e("add to queue" + this.toString());
//            NetworkQueueManager.getInstance().add(this);
//        }
//    }
//}