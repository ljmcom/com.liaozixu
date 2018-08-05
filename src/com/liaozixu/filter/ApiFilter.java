package com.liaozixu.filter;

import com.liaozixu.system.Config;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.GatewayUtils;
import com.liaozixu.util.GsonUtils;
import com.liaozixu.util.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebFilter(filterName = "ApiFilter")
public class ApiFilter implements Filter {
    public void destroy() {
    }

    private boolean isException(ServletRequest req) {
        String[] path = new String[]{};
        HttpServletRequest request = (HttpServletRequest) req;
        String nPath = request.getRequestURI();
        return CommonUtils.isArrayString(nPath, path);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        if (!request.getMethod().equals("POST")) {
            GatewayUtils.showJson(false, 100003, null, response);
            return;
        }
        HashMap<String, String> postMap = GsonUtils.jsonToHashMapString(ServletUtils.requestString(request));
        if (postMap == null) {
            GatewayUtils.showJson(false, 100004, null, response);
            return;
        }
        Config config = new Config();
        if (postMap.get("authKey") == null || !postMap.get("authKey").toString().equals(config.get("authKey"))) {
            GatewayUtils.showJson(false, 100007, null, response);
            return;
        }
        request.setAttribute("postMap", postMap);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
