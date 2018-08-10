package com.liaozixu.filter;

import com.liaozixu.dao.AdminUserDao;
import com.liaozixu.entity.AdminUser;
import com.liaozixu.redis.RedisOperationManager;
import com.liaozixu.system.Config;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.CookieUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    private boolean isException(ServletRequest req) {
        String[] path = new String[]{
                "/admin/login",
                "/admin/markdownEditor/upload"
        };
        HttpServletRequest request = (HttpServletRequest) req;
        String nPath = request.getRequestURI();
        return CommonUtils.isArrayString(nPath, path);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (isException(req)) {
            chain.doFilter(req, resp);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Config config = new Config();
        String cookieKey = config.get("pre") + "admin_user_login_token";
        String token = CookieUtil.getCookieByName(request, cookieKey);
        if (token == null) {
            response.sendRedirect("/admin/login");
            return;
        }
        String redisKey = "admin_user_login;token=" + token;
        HashMap<String, String> adminUserMap = RedisOperationManager.getMap(redisKey);
        if (adminUserMap == null) {
            response.sendRedirect("/admin/login");
            return;
        }
        AdminUser adminUser = AdminUserDao.toEntity(adminUserMap);
        req.setAttribute("adminUser", adminUser);
        req.setAttribute("adminToken", token);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
