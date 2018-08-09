package com.liaozixu.servlet.api.admin.category;

import com.liaozixu.dao.CategoryDao;
import com.liaozixu.entity.Category;
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

@WebServlet(name = "AdminCategoryListServlet", urlPatterns = {"/api/admin/category/list"})
public class AdminCategoryListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) request.getAttribute("postMap");
        int pageNum = ServletUtils.pageCheckGateway(postMap, response);
        if (pageNum == 0) {
            return;
        }
        Page<Category> categoryList = CategoryDao.getList(pageNum, 1);
        if (categoryList == null) {
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
        GatewayUtils.showJson(true, 0, categoryList, response);
    }
}
