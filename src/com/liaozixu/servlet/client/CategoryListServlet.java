package com.liaozixu.servlet.client;

import com.liaozixu.dao.CategoryDao;
import com.liaozixu.entity.Category;
import com.liaozixu.entity.Page;
import com.liaozixu.util.CommonUtils;
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
        int pageNum = ServletUtils.pageCheck(request,response);
        if(pageNum == 0){
            return;
        }
        Page<Category> categoryList = CategoryDao.getList(pageNum, 1);
        if(categoryList == null){
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        if(categoryList.getNowPageCount() == 0){
            return;
        }
        request.setAttribute("categoryList",categoryList);
        request.getRequestDispatcher("/WEB-INF/view/client/category/list.jsp").forward(request, response);
    }
}
