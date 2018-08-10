package com.liaozixu.entity;

import java.util.List;

public class Page<T> {
    private List<T> row;
    private int nowPage;
    private int pageNum;
    private int totalRow;
    private int nowPageCount;
    private boolean firstPage;
    private boolean lastPage;
    private int totalPage;

    public List<T> getRow() {
        return row;
    }

    //    ❤ 设置当前页
    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getNowPage() {
        return nowPage;
    }

    //    ❤ 设置单页页数
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    //    ❤ 设置总条目
    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setRow(List<T> row) {
        if (totalRow == 0) {
            totalPage = 0;
        } else {
            totalPage = (totalRow % pageNum == 0) ? (totalRow / pageNum) : ((totalRow / pageNum) + 1);
        }
        nowPageCount = row.size();
        firstPage = nowPage == 1;
        lastPage = nowPage >= getTotalPage();
        this.row = row;
    }

    //    获取总页数
    public int getTotalPage() {
        return totalPage;
    }

    //    获取当前页总条目
    public int getNowPageCount() {
        return nowPageCount;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "row=" + row +
                ", nowPage=" + nowPage +
                ", pageNum=" + pageNum +
                ", totalRow=" + totalRow +
                '}';
    }
}
