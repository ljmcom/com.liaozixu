package com.liaozixu.servlet.api.admin.category;

import com.liaozixu.dao.CategoryDao;
import com.liaozixu.entity.Category;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.GatewayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "AdminCategorySaveServlet", urlPatterns = {"/api/admin/category/save"})
public class AdminCategorySaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) request.getAttribute("postMap");
        Category category = new Category();
        if (postMap.get("id") != null) {
            if (!CommonUtils.checkRoundNum(postMap.get("id"))) {
                GatewayUtils.showJson(false, 100001, null, response);
                return;
            } else {
                category.setId(Integer.parseInt(postMap.get("id")));
            }
        }
        if (postMap.get("title") != null) {
            category.setTitle(postMap.get("title"));
        }
        if (postMap.get("alias") != null) {
            category.setAlias(postMap.get("alias"));
        }
        if (postMap.get("keywords") != null) {
            category.setKeywords(postMap.get("keywords"));
        }
        if (postMap.get("description") != null) {
            category.setDescription(postMap.get("description"));
        }
        if (postMap.get("type") != null) {
            if (!CommonUtils.checkRoundNum(postMap.get("type"))) {
                GatewayUtils.showJson(false, 100001, null, response);
                return;
            }
            category.setType(Integer.parseInt(postMap.get("type")));
        }
        if (CategoryDao.save(category)) {
            GatewayUtils.showJson(true, 0, null, response);
        } else {
            GatewayUtils.showJson(false, 100002, null, response);
        }
    }
}
