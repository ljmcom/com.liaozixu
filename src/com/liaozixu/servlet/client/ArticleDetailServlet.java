package com.liaozixu.servlet.client;

import com.liaozixu.dao.ArticleDao;
import com.liaozixu.entity.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ArticleDetailServlet", urlPatterns = {"/do/article/detail"})
public class ArticleDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("categoryAlias") == null || request.getParameter("articleAlias") == null) {
//            转错误页
            return;
        }
        String categoryAlias = request.getParameter("categoryAlias");
        String articleAlias = request.getParameter("articleAlias");
        Article details = ArticleDao.details(categoryAlias, articleAlias);
        if (details == null) {
            return;
        }
        request.setAttribute("details", details);
        request.getRequestDispatcher("/WEB-INF/view/client/articleDetail.jsp").forward(request, response);
    }
}
