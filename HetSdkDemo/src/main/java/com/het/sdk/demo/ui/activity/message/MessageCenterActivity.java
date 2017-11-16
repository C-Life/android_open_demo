package com.het.sdk.demo.ui.activity.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.het.basic.utils.ToastUtil;
import com.het.open.lib.model.message.MessageBean;
import com.het.open.lib.model.message.MessageListByPageBean;
import com.het.open.lib.model.message.MessagePageBean;
import com.het.recyclerview.ProgressStyle;
import com.het.recyclerview.XRecyclerView;
import com.het.recyclerview.recycler.BaseRecyclerViewAdapter;
import com.het.recyclerview.swipemenu.SwipeMenuRecyclerView;
import com.het.sdk.demo.R;
import com.het.sdk.demo.adapter.MsgInvitationAdapter;
import com.het.sdk.demo.base.BaseHetActivity;
import com.het.sdk.demo.utils.UIJsonConfig;
import com.het.sdk.demo.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MessageCenterActivity extends BaseHetActivity<MsgListPresenter> implements BaseRecyclerViewAdapter.OnItemClickListener<MessageBean>, MsgInvitationAdapter.ISwipeMenuClickListener, XRecyclerView.LoadingListener, MesListView {

    @Bind(R.id.message_list)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView mMultipleStatusView;

    private MsgInvitationAdapter mAdapter;
    private String messageType = "2";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
        mMultipleStatusView.setOnRetryClickErrListener(onRetryClickErrListener);
    }

    private final View.OnClickListener onRetryClickErrListener = v -> {
        this.mPresenter.refreshList("", messageType, String.valueOf(10));
    };

    private void initRecyclerView() {
        this.mSwipeMenuRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        this.mSwipeMenuRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        this.mSwipeMenuRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(1);
        this.mSwipeMenuRecyclerView.setLayoutManager(layoutManager);
        this.mSwipeMenuRecyclerView.setPullRefreshEnabled(true);
        this.mSwipeMenuRecyclerView.setLoadingListener(this);
        this.mSwipeMenuRecyclerView.setSwipeDirection(1);
        this.mAdapter = new MsgInvitationAdapter(this.mContext);
        this.mSwipeMenuRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setISwipeMenuClickListener(this);
        this.mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(android.view.View view, MessageBean item, int position) {
//        this.mPresenter.getBydeviceId(item.getBusinessParam() == null ? "" : item.getBusinessParam());
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("deviceMsg", item);
//        this.jumpToTarget(HetMsgDeviceInviteActivity.class, bundle);
    }

    @Override
    protected void initTopBarView() {
        mTitleView.setTitle(R.string.tab_my_msg);
        mTitleView.setBackground(UIJsonConfig.getInstance(mContext).setNavBackground_color());
    }


    protected void onResume() {
        super.onResume();
        this.mPresenter.refreshList("", messageType, String.valueOf(10));
        this.mSwipeMenuRecyclerView.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        this.mPresenter.refreshList("", messageType, String.valueOf(10));
    }

    @Override
    public void onLoadMore() {
        this.mPresenter.loadList(String.valueOf(((MessageBean) this.mAdapter.getData(this.mAdapter.getItemCount() - 1)).getMessageId()), messageType, String.valueOf(10));
    }


    @Override
    public void onDeleteBtnCilck(MessageBean messageBean, int position) {
        this.mPresenter.deleteMessage(String.valueOf(messageBean.getMessageId()), position);
    }

    @Override
    public void success(int type, MessageListByPageBean listByPage, int position) {
        if (!isFinishing()) {
            Object list = new ArrayList();
            MessagePageBean pageBean = new MessagePageBean();
            if (listByPage != null) {
                list = listByPage.getList();
                pageBean = listByPage.getPage();
            }

            switch (type) {
                case MsgListPresenter.MSG_TYPE_LIST_REFRESH_SUCCESS:
                    this.mAdapter.setListAll((List) list);
                    this.mSwipeMenuRecyclerView.refreshComplete();
                    if (pageBean.isHasNextPage()) {
                        this.mSwipeMenuRecyclerView.setLoadingMoreEnabled(true);
                    } else {
                        this.mSwipeMenuRecyclerView.setLoadingMoreEnabled(false);
                    }

                    this.dismissView();
                case MsgListPresenter.MSG_TYPE_LIST_REFRESH_FAIL:
                case MsgListPresenter.MSG_TYPE_LIST_LOAD_FAIL:
                default:
                    break;
                case MsgListPresenter.MSG_TYPE_LIST_LOAD_SUCCESS:
                    this.mSwipeMenuRecyclerView.loadMoreComplete();
                    this.mAdapter.addItemsToLast((List) list);
                    if (pageBean.isHasNextPage()) {
                        this.mSwipeMenuRecyclerView.setLoadingMoreEnabled(true);
                    } else {
                        this.mSwipeMenuRecyclerView.setLoadingMoreEnabled(false);
                    }

                    this.dismissView();
                case MsgListPresenter.MSG_TYPE_LIST_DELETE_SUCCESS:
                    if (this.mAdapter.getItemCount() != 0) {
                        this.mAdapter.remove(this.mAdapter.getData(position));
                        ToastUtil.showToast(this.mContext, this.getString(R.string.common_msg_delete_success));
                    }

                    if (this.mAdapter.getItemCount() == 0) {
                        this.mPresenter.refreshList("", messageType, String.valueOf(10));
                    }

                    this.dismissView();
            }
        }
    }

    @Override
    public void Failed(int type, String msg) {
        if (!isFinishing()) {
            if (type == MsgListPresenter.MSG_TYPE_LIST_REFRESH_FAIL) {
                if (this.mSwipeMenuRecyclerView != null) {
                    this.mSwipeMenuRecyclerView.refreshComplete();
                }
                mMultipleStatusView.setErrorText("服务器错误,请稍后再试！");
                mMultipleStatusView.showError();
            } else if (type == MsgListPresenter.MSG_TYPE_LIST_LOAD_FAIL) {
                this.mSwipeMenuRecyclerView.loadMoreComplete();
            } else if (type == MsgListPresenter.MSG_TYPE_LIST_DELETE_FAIL) {
                ToastUtil.showToast(this.mContext, this.getString(R.string.common_msg_delete_fail));
            }
        }

    }

    private void dismissView() {
        if (this.mAdapter.getItemCount() == 0) {
            mMultipleStatusView.setErrorText("还没有消息哦！");
            mMultipleStatusView.showError();
        } else {
            mMultipleStatusView.showContent();
        }
    }


}
