package com.liaozixu.servlet.client;

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

@WebServlet(name = "CategoryArticleListServlet", urlPatterns = {"/do/category/article/list"})
public class CategoryArticleListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("categoryAlias") == null || request.getParameter("page") == null) {
            GatewayUtils.showJson(false, 100005, null, response);
            return;
        }
        int pageNum = ServletUtils.pageCheck(request, response);
        if (pageNum == 0) {
            return;
        }
        String categoryAlias = request.getParameter("categoryAlias");
        Page<Article> articleList = ArticleDao.getList(pageNum, 1, categoryAlias);
        if(articleList == null){
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        if(articleList.getRow().size() == 0){
//            该分类下没有文章
            return;
        }
        request.setAttribute("articleList", articleList);
        request.getRequestDispatcher("/WEB-INF/view/client/category/article/list.jsp").forward(request, response);
    }
}
