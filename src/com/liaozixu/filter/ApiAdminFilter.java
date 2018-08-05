package com.liaozixu.filter;

import com.liaozixu.dao.AdminUserDao;
import com.liaozixu.entity.AdminUser;
import com.liaozixu.redis.RedisOperationManager;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.GatewayUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebFilter(filterName = "ApiAdminFilter")
public class ApiAdminFilter implements Filter {
    public void destroy() {
    }

    private boolean isException(ServletRequest req) {
        String[] path = new String[]{
                "/api/admin/user/login"
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
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) req.getAttribute("postMap");
        if (postMap.get("token") == null) {
            GatewayUtils.showJson(false, 100007, null, (HttpServletResponse) resp);
            return;
        }
        String redisKey = "admin_user_login;token=" + postMap.get("token");
        HashMap<String, String> adminUserMap = RedisOperationManager.getMap(redisKey);
        if (adminUserMap == null) {
            GatewayUtils.showJson(false, 100007, null, (HttpServletResponse) resp);
            return;
        }
        AdminUser adminUser = AdminUserDao.toEntity(adminUserMap);
        req.setAttribute("adminUser", adminUser);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
