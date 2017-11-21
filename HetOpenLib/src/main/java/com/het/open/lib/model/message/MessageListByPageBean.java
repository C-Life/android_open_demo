package com.het.open.lib.model.message;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-10-11.
 */

public class MessageListByPageBean implements Serializable{
    private MessagePageBean pager;
    private List<MessageBean> list;

    public MessageListByPageBean() {
    }

    public MessagePageBean getPage() {
        return this.pager;
    }

    public void setPage(MessagePageBean pager) {
        this.pager = pager;
    }

    public List<MessageBean> getList() {
        return this.list;
    }

    public void setList(List<MessageBean> list) {
        this.list = list;
    }
}
