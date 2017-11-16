package com.het.open.lib.model.message;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-10-11.
 */

public class MessagePageBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalRows;
    private int pageRows;
    private int pageIndex;
    private boolean paged;
    private int defaultPageRows;
    private int currPageRows;
    private int pageStartRow;
    private boolean hasPrevPage;
    private boolean hasNextPage;
    private int totalPages;
    private int pageEndRow;

    public MessagePageBean() {
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageRows() {
        return this.pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public boolean isPaged() {
        return this.paged;
    }

    public void setPaged(boolean paged) {
        this.paged = paged;
    }

    public int getDefaultPageRows() {
        return this.defaultPageRows;
    }

    public void setDefaultPageRows(int defaultPageRows) {
        this.defaultPageRows = defaultPageRows;
    }

    public int getCurrPageRows() {
        return this.currPageRows;
    }

    public void setCurrPageRows(int currPageRows) {
        this.currPageRows = currPageRows;
    }

    public int getPageStartRow() {
        return this.pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public boolean isHasPrevPage() {
        return this.hasPrevPage;
    }

    public void setHasPrevPage(boolean hasPrevPage) {
        this.hasPrevPage = hasPrevPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageEndRow() {
        return this.pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }
}