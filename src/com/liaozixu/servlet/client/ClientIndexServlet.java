package com.liaozixu.servlet.client;

import com.liaozixu.dao.ArticleDao;
import com.liaozixu.entity.Article;
import com.liaozixu.entity.Page;
import com.liaozixu.system.Config;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.GatewayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClientIndexServlet", urlPatterns = {"/index"})
public class ClientIndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNum = 1;
        Config config = new Config();
        request.setAttribute("aboutMeResume", config.get("aboutMeResume"));
        if (request.getParameter("page") != null) {
            if (!CommonUtils.checkRoundNum(request.getParameter("page"))) {
                GatewayUtils.showJson(false, 100001, null, response);
                return;
            }
            pageNum = Integer.parseInt(request.getParameter("page"));
            if(pageNum == 0){
                GatewayUtils.showJson(false, 100001, null, response);
                return;
            }
        }
        Page<Article> articleList = ArticleDao.getList(pageNum, 1);
        if(articleList == null){
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        if(articleList.getNowPageCount() == 0){
            return;
        }
        request.setAttribute("articleList",articleList);
        request.getRequestDispatcher("/WEB-INF/view/client/index.jsp").forward(request, response);
    }
}
