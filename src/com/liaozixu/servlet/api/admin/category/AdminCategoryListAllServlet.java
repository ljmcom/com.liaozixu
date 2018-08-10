package com.liaozixu.servlet.api.admin.category;

import com.liaozixu.dao.CategoryDao;
import com.liaozixu.util.GatewayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminCategoryListAllServlet", urlPatterns = {"/api/admin/category/list/all"})
public class AdminCategoryListAllServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatewayUtils.showJson(true, 0, CategoryDao.getAll(), response);
    }
}
