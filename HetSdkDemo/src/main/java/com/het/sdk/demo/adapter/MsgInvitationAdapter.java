package com.het.sdk.demo.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.het.open.lib.model.message.MessageBean;
import com.het.recyclerview.recycler.HelpRecyclerViewDragAdapter;
import com.het.recyclerview.recycler.HelperRecyclerViewHolder;
import com.het.recyclerview.swipemenu.SwipeMenuLayout;
import com.het.sdk.demo.R;

/**
 * Created by Administrator on 2017-10-11.
 */

public class MsgInvitationAdapter extends HelpRecyclerViewDragAdapter<MessageBean> {
    private MsgInvitationAdapter.ISwipeMenuClickListener mIDeleteBtnClickListener;

    public MsgInvitationAdapter(Context context) {
        super(context, new int[]{R.layout.het_message_item_message_device});
    }

    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, final int position, MessageBean item) {
        SwipeMenuLayout superSwipeMenuLayout = (SwipeMenuLayout) viewHolder.itemView;
        superSwipeMenuLayout.setSwipeEnable(true);
        viewHolder.setText(R.id.tv_title, item.getDescription())
                .setOnClickListener(R.id.btDelete, v -> MsgInvitationAdapter.this.mIDeleteBtnClickListener.onDeleteBtnCilck(item, position));
        ((SimpleDraweeView) viewHolder.getView(R.id.sv_msg_item)).setImageURI(Uri.parse(item.getIcon()));
    }

    public void setISwipeMenuClickListener(MsgInvitationAdapter.ISwipeMenuClickListener mIDeleteBtnClickListener) {
        this.mIDeleteBtnClickListener = mIDeleteBtnClickListener;
    }

    public interface ISwipeMenuClickListener {
        void onDeleteBtnCilck(MessageBean var1, int var2);
    }
}
