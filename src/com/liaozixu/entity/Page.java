package com.liaozixu.entity;

import java.util.List;

public class Page<T> {
    private List<T> row;
    private int nowPage;
    private int pageNum;
    private int totalRow;

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

    //    获取总页数
    public int getTotalPage() {
        if (totalRow == 0) {
            return 0;
        }
        return (totalRow % pageNum == 0) ? (totalRow / pageNum) : ((totalRow / pageNum) + 1);
    }

    //    获取当前页总条目
    public int getNowPageCount() {
        if (getTotalPage() == 0) {
            return 0;
        }
        if (getTotalPage() == 1 || totalRow % pageNum == 0) {
            return totalRow;
        } else {
            return totalRow - ((getTotalPage() - 1) * pageNum);
        }
    }

    public boolean isFirstPage() {
        return nowPage == 1;
    }

    public boolean isLastPage() {
        return nowPage >= getTotalPage();
    }

}
