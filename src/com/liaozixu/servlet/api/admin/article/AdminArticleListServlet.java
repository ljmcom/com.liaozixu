package com.liaozixu.servlet.api.admin.article;

import com.liaozixu.dao.ArticleDao;
import com.liaozixu.entity.Article;
import com.liaozixu.entity.Page;
import com.liaozixu.util.GatewayUtils;
import com.liaozixu.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "AdminArticleListServlet", urlPatterns = {"/api/admin/article/list"})
public class AdminArticleListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) request.getAttribute("postMap");
        int pageNum = ServletUtils.pageCheckGateway(postMap, response);
        if (pageNum == 0) {
            return;
        }
        Page<Article> articleList = ArticleDao.getList(pageNum, 1);
        if (articleList == null) {
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        GatewayUtils.showJson(true, 0, articleList, response);
    }
}
