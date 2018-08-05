package com.liaozixu.servlet.client;

import com.liaozixu.dao.ArticleDao;
import com.liaozixu.entity.Article;
import com.liaozixu.util.GatewayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "ArticleDetailServlet", urlPatterns = {"/do/article/detail"})
public class ArticleDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("categoryAlias") == null || request.getParameter("articleAlias") == null) {
            GatewayUtils.showJson(false, 100005, null, response);
            return;
        }
        String categoryAlias = request.getParameter("categoryAlias");
        String articleAlias = request.getParameter("articleAlias");
        Article details = ArticleDao.details(categoryAlias, articleAlias);
        if (details == null) {
            return;
        }
        if (Objects.requireNonNull(details).getType() != 1) {
//            该文章不公开
            return;
        }
        request.setAttribute("details", details);
        request.getRequestDispatcher("/WEB-INF/view/client/article/detail.jsp").forward(request, response);
    }
}
