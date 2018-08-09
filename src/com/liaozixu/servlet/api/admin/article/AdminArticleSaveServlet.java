package com.liaozixu.servlet.api.admin.article;

import com.liaozixu.dao.ArticleDao;
import com.liaozixu.entity.Article;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.DateUtils;
import com.liaozixu.util.GatewayUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "AdminArticleSaveServlet", urlPatterns = {"/api/admin/article/save"})
public class AdminArticleSaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) request.getAttribute("postMap");
        Article article = new Article();
        if (postMap.get("type") != null) {
            if (!CommonUtils.checkRoundNum(postMap.get("type"))) {
                GatewayUtils.showJson(false, 100001, null, response);
                return;
            } else {
                article.setType(Integer.parseInt(postMap.get("type")));
            }
        }
        if (postMap.get("title") != null) {
            article.setTitle(postMap.get("title"));
        }
        if (postMap.get("description") != null) {
            article.setDescription(article.getDescription());
        }
        if (postMap.get("contentRaw") != null) {
            article.setContentRaw(postMap.get("contentRaw"));
        }
        if (postMap.get("contentText") != null) {
            article.setContentText(postMap.get("contentText"));
        }
        if (postMap.get("ip") != null) {
            article.setIp(postMap.get("ip"));
        }
        if (postMap.get("postTime") != null) {
            article.setPostTime(DateUtils.strToDateLong(postMap.get("postTime")));
        }
        if (postMap.get("keywords") != null) {
            article.setKeywords(postMap.get("keywords"));
        }
        if (postMap.get("alias") != null) {
            article.setAlias(postMap.get("alias"));
        }
        if (postMap.get("categoryID") != null) {
            if (!CommonUtils.checkRoundNum(postMap.get("categoryID"))) {
                GatewayUtils.showJson(false, 100001, null, response);
                return;
            } else {
                article.setCategoryID(Integer.parseInt(postMap.get("categoryID")));
            }
        }
        if (postMap.get("original") != null) {
            if (!postMap.get("original").equals("1") && !postMap.get("original").equals("0")) {
                GatewayUtils.showJson(false, 100001, null, response);
                return;
            } else {
                article.setOriginal(postMap.get("original").equals("1"));
            }
        }
        if (ArticleDao.save(article)) {
            GatewayUtils.showJson(true, 0, null, response);
        } else {
            GatewayUtils.showJson(false, 100002, null, response);
        }
    }
}
