package com.het.open.lib.model.share;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-10-10.
 */

public class ShareCodeModel implements Serializable {
    private String shareCode;
    private String h5Url;

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    @Override
    public String toString() {
        return "ShareCodeModel{" +
                "shareCode='" + shareCode + '\'' +
                ", h5Url='" + h5Url + '\'' +
                '}';
    }
}
