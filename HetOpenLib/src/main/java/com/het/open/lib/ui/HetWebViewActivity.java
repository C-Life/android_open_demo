package com.het.open.lib.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.het.log.Logc;
import com.het.open.lib.auth.biz.AuthRequestParam;
import com.het.open.lib.auth.biz.BrowserRequestParamBase;
import com.het.open.lib.auth.biz.HetCallbackManager;
import com.het.open.lib.auth.callback.BrowserRequestCallBack;
import com.het.open.lib.auth.view.LoadingBar;
import com.het.open.lib.auth.webviewclient.AuthHetWebViewClient;
import com.het.open.lib.utils.NetworkHelper;
import com.sina.weibo.sdk.utils.ResourceManager;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;


/**
 * 开放平台授权登录主界面
 *
 * @author xuchao
 *
 */
public class HetWebViewActivity extends Activity implements
		BrowserRequestCallBack {

	private final String TAG = HetWebViewActivity.class.getName();
	private AuthRequestParam mRequestParam;
	private String mUrl; // 请求url
	private String mSpecifyTitle;
	private WebView mWebView;
	private AuthHetWebViewClient mWeiboWebViewClient;
	private TextView mLeftBtn;
	private TextView mTitleText;
	private LinearLayout mLoadErrorView;
	private LoadingBar mLoadingBar;
	private Button mLoadErrorRetryBtn;
	private boolean isErrorPage = false;
	private boolean isLoading = false;
	private String mHtmlTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initDataFromIntent(getIntent());
		if (!(initDataFromIntent(getIntent()))) {
			finish();
			return;
		}
		setContentView();
		initWebView();
		openUrl(this.mUrl);
	}

	/**
	 * 获取请求参数
	 *
	 * @param data
	 * @return
	 */
	private boolean initDataFromIntent(Intent data) {

		this.mRequestParam = (AuthRequestParam) createBrowserRequestParam(data
				.getExtras());
		if (this.mRequestParam == null) {
			return false;
		}
		this.mUrl = this.mRequestParam.getUrl();
		if (TextUtils.isEmpty(this.mUrl)) {
			return false;
		}
		this.mSpecifyTitle = this.mRequestParam.getSpecifyTitle();
		return true;
	}

	private void openUrl(String url) {
		Logc.e(TAG,url);
		if (mWebView != null) {
			this.mWebView.loadUrl(url);
		}

	}

	/**
	 * 初始界面
	 */
	private void setContentView() {
		RelativeLayout contentLy = new RelativeLayout(this);
		contentLy.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		contentLy.setBackgroundColor(-1);
		LinearLayout titleBarLy = new LinearLayout(this);
		//titleBarLy.setId(1);
		titleBarLy.setVisibility(View.VISIBLE);
		titleBarLy.setOrientation(LinearLayout.VERTICAL);
		titleBarLy.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

		RelativeLayout titleBar = new RelativeLayout(this);
		titleBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ResourceManager
				.dp2px(this, 45)));
		titleBar.setBackgroundColor(0xFFF0EFF5);
		this.mLeftBtn = new TextView(this);
		this.mLeftBtn.setClickable(true);
		this.mLeftBtn.setTextSize(2, 17.0F);
		// this.mLeftBtn.setTextColor(ResourceManager.createColorStateList(-32256,
		// 1728020992));
		this.mLeftBtn.setText("关闭");
		RelativeLayout.LayoutParams leftBtnLp = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		leftBtnLp.addRule(5);
		leftBtnLp.addRule(15);
		leftBtnLp.leftMargin = ResourceManager.dp2px(this, 10);
		leftBtnLp.rightMargin = ResourceManager.dp2px(this, 10);
		this.mLeftBtn.setLayoutParams(leftBtnLp);
		titleBar.addView(this.mLeftBtn);
		this.mTitleText = new TextView(this);
		this.mTitleText.setTextSize(2, 18.0F);
		this.mTitleText.setTextColor(0xffffffff);
		this.mTitleText.setEllipsize(TextUtils.TruncateAt.END);
		this.mTitleText.setSingleLine(true);
		this.mTitleText.setGravity(17);
		this.mTitleText.setMaxWidth(ResourceManager.dp2px(this, 160));
		RelativeLayout.LayoutParams titleTextLy = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		titleTextLy.addRule(13);
		this.mTitleText.setLayoutParams(titleTextLy);
		titleBar.addView(this.mTitleText);
		TextView shadowBar = new TextView(this);
		shadowBar.setLayoutParams(new LinearLayout.LayoutParams(-1,
				ResourceManager.dp2px(this, 2)));
		// shadowBar.setBackgroundDrawable(ResourceManager.getNinePatchDrawable(
		// this, "weibosdk_common_shadow_top.9.png"));

		this.mLoadingBar = new LoadingBar(this);
		this.mLoadingBar.setBackgroundColor(0x535fe90);
		this.mLoadingBar.drawProgress(0);
		LinearLayout.LayoutParams loadingBarLy = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ResourceManager.dp2px(this, 3));
		this.mLoadingBar.setLayoutParams(loadingBarLy);

		titleBarLy.addView(titleBar);
		titleBarLy.addView(shadowBar);
		titleBarLy.addView(this.mLoadingBar);

		this.mWebView = new WebView(this);
		this.mWebView.setBackgroundColor(-1);
		RelativeLayout.LayoutParams webViewLp = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		webViewLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, titleBarLy.getId());
		this.mWebView.setLayoutParams(webViewLp);
		this.mLoadErrorView = new LinearLayout(this);
		this.mLoadErrorView.setVisibility(View.GONE);
		this.mLoadErrorView.setOrientation(LinearLayout.VERTICAL);
		this.mLoadErrorView.setGravity(17);
		RelativeLayout.LayoutParams mLoadErrorViewLp = new RelativeLayout.LayoutParams(
				-1, -1);
		mLoadErrorViewLp.addRule(3, 1);
		this.mLoadErrorView.setLayoutParams(mLoadErrorViewLp);
		ImageView loadErrorImg = new ImageView(this);
		// loadErrorImg.setImageDrawable(ResourceManager.getDrawable(this,
		// "weibosdk_empty_failed.png"));
		LinearLayout.LayoutParams loadErrorImgLp = new LinearLayout.LayoutParams(
				-2, -2);
		loadErrorImgLp.leftMargin = (loadErrorImgLp.topMargin = loadErrorImgLp.rightMargin = loadErrorImgLp.bottomMargin = ResourceManager
				.dp2px(this, 8));
		loadErrorImg.setLayoutParams(loadErrorImgLp);
		this.mLoadErrorView.addView(loadErrorImg);

		TextView loadErrorContent = new TextView(this);
		loadErrorContent.setGravity(1);
		loadErrorContent.setTextColor(0xffffffff);
		loadErrorContent.setTextSize(2, 14.0F);
		loadErrorContent.setText("网络出错啦，请点击按钮重新加载");
		LinearLayout.LayoutParams loadErrorContentLp = new LinearLayout.LayoutParams(
				-2, -2);
		loadErrorContent.setLayoutParams(loadErrorContentLp);
		this.mLoadErrorView.addView(loadErrorContent);
		this.mLoadErrorRetryBtn = new Button(this);
		this.mLoadErrorRetryBtn.setGravity(17);
		this.mLoadErrorRetryBtn.setTextColor(0xffffffff);
		this.mLoadErrorRetryBtn.setTextSize(2, 16.0F);
		this.mLoadErrorRetryBtn.setText("重新加载");
		// this.mLoadErrorRetryBtn.setBackgroundDrawable(ResourceManager
		// .createStateListDrawable(this,
		// "weibosdk_common_button_alpha.9.png",
		// "weibosdk_common_button_alpha_highlighted.9.png"));
		LinearLayout.LayoutParams loadErrorRetryBtnLp = new LinearLayout.LayoutParams(
				ResourceManager.dp2px(this, 142), ResourceManager.dp2px(this,
				46));
		loadErrorRetryBtnLp.topMargin = ResourceManager.dp2px(this, 10);
		this.mLoadErrorRetryBtn.setLayoutParams(loadErrorRetryBtnLp);
		this.mLoadErrorRetryBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				HetWebViewActivity.this.openUrl(HetWebViewActivity.this.mUrl);
				HetWebViewActivity.this.isErrorPage = false;
			}
		});
		this.mLoadErrorView.addView(this.mLoadErrorRetryBtn);
		contentLy.addView(titleBarLy);
		contentLy.addView(this.mWebView);
		contentLy.addView(this.mLoadErrorView);
		setContentView(contentLy);
		setTopNavTitle();
	}

	private void setTopNavTitle() {
		// TODO Auto-generated method stub
		this.mTitleText.setText(this.mSpecifyTitle);
		this.mLeftBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				HetWebViewActivity.this.mRequestParam.execRequest(
						HetWebViewActivity.this, 3);
				HetWebViewActivity.this.finish();
			}
		});
	}

	@SuppressLint({ "SetJavaScriptEnabled" })
	private void initWebView() {
		this.mWebView.getSettings().setJavaScriptEnabled(true);
		this.mWebView.getSettings().setSavePassword(false);
		this.mWebView.setWebViewClient(this.mWeiboWebViewClient);
		// 加载页面进度条
		this.mWebView.setWebChromeClient(new HetChromeClient());
		this.mWebView.requestFocus();
		//this.mWebView.setScrollBarStyle();
	}

	private BrowserRequestParamBase createBrowserRequestParam(Bundle data) {
		AuthRequestParam authRequestParam = new AuthRequestParam(this);
		authRequestParam.setupRequestParam(data);
		installAuthWeiboWebViewClient(authRequestParam);
		return authRequestParam;
	}

	private void installAuthWeiboWebViewClient(AuthRequestParam param) {
//		this.mWeiboWebViewClient = new AuthHetWebViewClient(this, param);
		this.mWeiboWebViewClient.setBrowserRequestCallBack(this);
	}

	/**
	 * 关闭登录界面
	 *
	 * @param act
	 * @param authListenerKey
	 */
	public static void closeBrowser(Activity act, String authListenerKey) {
		HetCallbackManager manager = HetCallbackManager.getInstance(act
				.getApplicationContext());
		if (!(TextUtils.isEmpty(authListenerKey))) {
			manager.removeHetAuthListener(authListenerKey);
			act.finish();
		}
	}

	@Override
	public void onPageStartedCallBack(WebView paramWebView, String paramString,
									  Bitmap paramBitmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean shouldOverrideUrlLoadingCallBack(WebView paramWebView,
													String paramString) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPageFinishedCallBack(WebView paramWebView, String paramString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceivedErrorCallBack(WebView paramWebView, int paramInt,
										String paramString1, String paramString2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReceivedSslErrorCallBack(WebView paramWebView,
										   SslErrorHandler handler, SslError error) {
		// TODO Auto-generated method stub
		// 证书错误继续访问
		handler.proceed();

	}

	private class HetChromeClient extends WebChromeClient {
		public void onProgressChanged(WebView view, int newProgress) {
			HetWebViewActivity.this.mLoadingBar.drawProgress(newProgress);
			if (newProgress == 100) {
				HetWebViewActivity.this.isLoading = false;
				HetWebViewActivity.this.refreshAllViews();
			} else if (!(HetWebViewActivity.this.isLoading)) {
				HetWebViewActivity.this.isLoading = true;
				HetWebViewActivity.this.refreshAllViews();
			}
		}

		public void onReceivedTitle(WebView view, String title) {

			// if (HetWebViewActivity.this
			// .isWeiboCustomScheme(HetWebViewActivity.this.mUrl) == 0) {
			// HetWebViewActivity.this.mHtmlTitle = title;
			// HetWebViewActivity.this.updateTitleName();
			// }
		}
	}

	protected void refreshAllViews() {
		if (this.isLoading) {
			this.mTitleText.setText("加载中....");
			this.mLoadingBar.setVisibility(View.VISIBLE);
		} else {
			updateTitleName();
			this.mLoadingBar.setVisibility(View.GONE);
		}

	}

	private void updateTitleName() {
		String showTitle = "";
		if (!(TextUtils.isEmpty(this.mHtmlTitle)))
			showTitle = this.mHtmlTitle;
		else if (!(TextUtils.isEmpty(this.mSpecifyTitle))) {
			showTitle = this.mSpecifyTitle;
		}

		this.mTitleText.setText(showTitle);
	}

	private boolean isWeiboCustomScheme(String url) {
		if (TextUtils.isEmpty(url)) {
			return false;
		}

		return ("sinaweibo".equalsIgnoreCase(Uri.parse(url).getAuthority()));
	}

	protected void onDestroy() {
		NetworkHelper.clearCookies(this);
		super.onDestroy();
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.mRequestParam.execRequest(this, 3);
			finish();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

}
