package com.het.sdk.demo.widget;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.het.sdk.demo.R;


/**
 * 网络加载loading
 *
 */
public class CommonLoadingDialog extends BaseDialog {
	public static final String TAG = "CommonLoadingDialog";
	private static final int DISMISS_DELATY_FLAG = 1;
	private Context mContext;
	private TextView mTipTv;
	private View mProcessView;
	

	public interface DefaultDismissListener {
		void onDismiss();
	}

	private DefaultDismissListener mDissMissListener;

	public CommonLoadingDialog(Context context) {
		super(context, R.style.DialogFadeIn);
		mContext = context;
		initUI();
	}

	// 初始化dialog基础UI
	private void initUI() {
		View dlgView = LayoutInflater.from(mContext).inflate(
				R.layout.widget_process_loading_view, null);
		mTipTv = (TextView) dlgView
				.findViewById(R.id.process_loading_view_text);
		setContentView(dlgView);
		setCanceledOnTouchOutside(false);
	}

	public void setText(String tip) {
		mTipTv.setText(tip);
		mTipTv.setGravity(Gravity.LEFT);
	}

	public void setProcessViewVisible(boolean isVisible) {
		if (isVisible) {
			mProcessView.setVisibility(View.VISIBLE);
		} else {
			mProcessView.setVisibility(View.GONE);
		}
	}

	// HandlerUtil.MessageListener dismissListener = new
	// HandlerUtil.MessageListener() {
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case DISMISS_DELATY_FLAG:
	// if (mDissMissListener != null) {
	// mDissMissListener.onDismiss();
	// }
	// break;
	// default:
	// break;
	// }
	//
	// }
	// };
	// HandlerUtil.StaticHandler dismissHandler = new
	// HandlerUtil.StaticHandler(dismissListener);
	//
	// public void dimissToDo(DefaultDismissListener listener) {
	// dismiss();
	// if (listener != null) {
	// mDissMissListener = listener;
	// }
	// dismissHandler.sendEmptyMessageDelayed(DISMISS_DELATY_FLAG, 500);
	// }

	public void reset() {
		mProcessView.setVisibility(View.VISIBLE);
		//mTipTv.setText(mContext.getString(R.string.common_loading_prompt));
		mTipTv.setGravity(Gravity.CENTER);
	}

}
