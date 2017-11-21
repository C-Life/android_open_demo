package com.het.open.lib.model;

/**分页信息
 * Created by xuchao on 2016/5/6.
 */
public class PagerModel {

   private int pageIndex;//请求的页（从1开始），不设则默认为1
    private int pageRows;//请求的每页行数，不设则默认为defaultPageRows
    private String totalRows;//总行数
    private int totalPages;//总页数
    private int defaultPageRows;//默认每页行数：20
    private int currPageRows;//当前页的实际行数
    private int pageStartRow;//当前页的起始行（从0开始，有可能超出总行数）
    private int pageEndRow;//当前页的结束行（从0开始，有可能超出总行数）
    private boolean hasPrevPage;//是否有前一页
    private boolean hasNextPage;//是否有后一页

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }

    public String getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(String totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getDefaultPageRows() {
        return defaultPageRows;
    }

    public void setDefaultPageRows(int defaultPageRows) {
        this.defaultPageRows = defaultPageRows;
    }

    public int getCurrPageRows() {
        return currPageRows;
    }

    public void setCurrPageRows(int currPageRows) {
        this.currPageRows = currPageRows;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public boolean isHasPrevPage() {
        return hasPrevPage;
    }

    public void setHasPrevPage(boolean hasPrevPage) {
        this.hasPrevPage = hasPrevPage;
    }

    @Override
    public String toString() {
        return "PagerModel{" +
                "pageIndex=" + pageIndex +
                ", pageRows=" + pageRows +
                ", totalRows='" + totalRows + '\'' +
                ", totalPages=" + totalPages +
                ", defaultPageRows=" + defaultPageRows +
                ", currPageRows=" + currPageRows +
                ", pageStartRow=" + pageStartRow +
                ", pageEndRow=" + pageEndRow +
                ", hasPrevPage=" + hasPrevPage +
                ", hasNextPage=" + hasNextPage +
                '}';
    }
}
