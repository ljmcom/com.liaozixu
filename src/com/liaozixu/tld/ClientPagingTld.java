package com.liaozixu.tld;

import com.liaozixu.entity.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;

public class ClientPagingTld extends TagSupport {
    private Page page;
    private int maxBtn;
    private String isFirstPageJsFunc;
    private String isEndPageJsFunc;

    public void setPage(Page page) {
        this.page = page;
    }

    public void setMaxBtn(int maxBtn) {
        this.maxBtn = maxBtn;
    }

    public void setIsEndPageJsFunc(String isEndPageJsFunc) {
        this.isEndPageJsFunc = isEndPageJsFunc;
    }

    public void setIsFirstPageJsFunc(String isFirstPageJsFunc) {
        this.isFirstPageJsFunc = isFirstPageJsFunc;
    }

    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        try {
            if (page == null) {
                try {
                    JspWriter writer = pageContext.getOut();
                    writer.append("生成翻页失败");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            int renderingBtn;
            if (page.getTotalPage() <= maxBtn) {
                renderingBtn = page.getTotalPage();
            } else {
                renderingBtn = maxBtn;
            }
            int centerNum = 0;
            int startNum;
            if (page.getTotalPage() > maxBtn) {
                centerNum = (renderingBtn % 2 == 0) ? (renderingBtn / 2) : ((renderingBtn / 2) + 1);
                if ((page.getTotalPage() - page.getNowPage()) <= centerNum) {
                    renderingBtn = (page.getTotalPage() - page.getNowPage());
                }
            }
            if (centerNum == 0 || page.getNowPage() <= centerNum) {
                startNum = 1;
            } else {
                startNum = page.getNowPage() - (centerNum - 1);
                if ((page.getTotalPage() - page.getNowPage()) < centerNum) {
                    renderingBtn = centerNum;
                }
            }
            HashMap<String, Object> clientPagingAttr = new HashMap<>();
            clientPagingAttr.put("isFirstPageJsFunc", isFirstPageJsFunc);
            clientPagingAttr.put("isEndPageJsFunc", isEndPageJsFunc);
            clientPagingAttr.put("isFirstPage", page.isFirstPage());
            clientPagingAttr.put("totalPage", page.getTotalPage());
            clientPagingAttr.put("totalRow", page.getTotalRow());
            clientPagingAttr.put("isEndPage", page.isLastPage());
            clientPagingAttr.put("nowPage", page.getNowPage());
            clientPagingAttr.put("renderingBtn", renderingBtn);
            clientPagingAttr.put("startNum", startNum);
            request.setAttribute("clientPagingAttr", clientPagingAttr);
            pageContext.include("/WEB-INF/tld/clientPaging.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

}
