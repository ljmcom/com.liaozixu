package com.liaozixu.servlet.api.admin.article;

import com.liaozixu.dao.ArticleDao;
import com.liaozixu.entity.Article;
import com.liaozixu.util.GatewayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@WebServlet(name = "AdminArticleDetailServlet", urlPatterns = {"/api/admin/article/detail"})
public class AdminArticleDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) request.getAttribute("postMap");
        if (postMap.get("categoryAlias") == null || postMap.get("articleAlias") == null) {
            GatewayUtils.showJson(false, 100005, null, response);
            return;
        }
        String categoryAlias = postMap.get("categoryAlias");
        String articleAlias = postMap.get("articleAlias");
        Article details = ArticleDao.details(categoryAlias, articleAlias);
        if (details == null || Objects.requireNonNull(details).getType() == 0) {
            GatewayUtils.showJson(false, 100011, null, response);
            return;
        }
        GatewayUtils.showJson(true, 0, details, response);
    }
}
