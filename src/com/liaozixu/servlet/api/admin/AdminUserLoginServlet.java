package com.liaozixu.servlet.api.admin;

import com.liaozixu.dao.AdminUserDao;
import com.liaozixu.entity.AdminUser;
import com.liaozixu.redis.RedisOperationManager;
import com.liaozixu.system.Config;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.CookieUtil;
import com.liaozixu.util.GatewayUtils;
import com.liaozixu.util.HmacSHA256Utills;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@WebServlet(name = "AdminUserLoginServlet", urlPatterns = {"/api/admin/user/login"})
public class AdminUserLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) request.getAttribute("postMap");
        if (postMap.get("username") == null || postMap.get("password") == null || postMap.get("securityCode") == null) {
            GatewayUtils.showJson(false, 100005, null, response);
            return;
        }
        AdminUser adminUser = AdminUserDao.username(postMap.get("username"));
        if (adminUser == null) {
            GatewayUtils.showJson(false, 100008, null, response);
            return;
        }
        if (!adminUser.getSecurityCode().equals(postMap.get("securityCode"))) {
            GatewayUtils.showJson(false, 100009, null, response);
            return;
        }
        if (!Objects.equals(HmacSHA256Utills.encode(postMap.get("password"), adminUser.getSecret()), adminUser.getPassword())) {
            GatewayUtils.showJson(false, 100010, null, response);
            return;
        }
        Config config = new Config();
        int expires = Integer.parseInt(config.get("expires"));
        String token = CommonUtils.getOutTradeNo();
        String redisKey = "admin_user_login;token=" + token;
        if (!RedisOperationManager.setMap(redisKey, AdminUserDao.toMapString(adminUser), expires)) {
            GatewayUtils.showJson(false, 100002, null, response);
            return;
        }
//        也写cookie一份
        String cookieKey = config.get("pre") + "admin_user_login_token";
        CookieUtil.addCookie(response, cookieKey, token);
        HashMap<String, Object> loginReturn = new HashMap<>();
        loginReturn.put("token", token);
        loginReturn.put("cookieKey", cookieKey);
        loginReturn.put("expires", expires);
        loginReturn.put("permission", adminUser.getPermission());
        GatewayUtils.showJson(true, 0, loginReturn, response);
    }
}
