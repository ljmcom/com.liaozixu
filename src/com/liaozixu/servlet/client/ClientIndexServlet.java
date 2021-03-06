package com.liaozixu.servlet.client;

import com.liaozixu.dao.ArticleDao;
import com.liaozixu.entity.Article;
import com.liaozixu.entity.Page;
import com.liaozixu.system.Config;
import com.liaozixu.util.GatewayUtils;
import com.liaozixu.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClientIndexServlet", urlPatterns = {"/index", "/index.jsp"})
public class ClientIndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNum = ServletUtils.pageCheck(request, response);
        if (pageNum == 0) {
            return;
        }
        Config config = new Config();
        request.setAttribute("aboutMeResume", config.get("aboutMeResume"));
        Page<Article> articleList = ArticleDao.getList(pageNum, 1);
        if (articleList == null) {
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        if (articleList.getNowPageCount() == 0) {
            request.getRequestDispatcher("/WEB-INF/view/client/404.jsp").forward(request, response);
            return;
        }
        request.setAttribute("title", config.get("webTitle"));
        if (pageNum != 1) {
            request.setAttribute("title", config.get("webTitle") + "-第" + pageNum + "页");
        }
        request.setAttribute("keywords", config.get("webKeywords"));
        request.setAttribute("description", config.get("webDescription"));
        request.setAttribute("articleList", articleList);
        request.getRequestDispatcher("/WEB-INF/view/client/index.jsp").forward(request, response);
    }
}
