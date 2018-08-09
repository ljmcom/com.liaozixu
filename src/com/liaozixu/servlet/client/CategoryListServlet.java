package com.liaozixu.servlet.client;

import com.liaozixu.dao.CategoryDao;
import com.liaozixu.entity.Category;
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

@WebServlet(name = "CategoryListServlet", urlPatterns = {"/do/category/list"})
public class CategoryListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNum = ServletUtils.pageCheck(request, response);
        if (pageNum == 0) {
            return;
        }
        Page<Category> categoryList = CategoryDao.getList(pageNum, 1);
        if (categoryList == null) {
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        if (categoryList.getNowPageCount() == 0) {
            request.getRequestDispatcher("/WEB-INF/view/client/404.jsp").forward(request, response);
            return;
        }
        Config config = new Config();
        if (pageNum == 1) {
            request.setAttribute("title", "分类-" + config.get("webTitle"));
        } else {
            request.setAttribute("title", "分类-第" + pageNum + "页-" + config.get("webTitle"));
        }
        request.setAttribute("title", "分类-" + config.get("webTitle"));
        request.setAttribute("keywords", "分类," + config.get("webKeywords"));
        request.setAttribute("description", config.get("webDescription"));
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("/WEB-INF/view/client/category/list.jsp").forward(request, response);
    }
}
