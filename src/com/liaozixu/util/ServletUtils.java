package com.liaozixu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {
    /***
     * 提取post过来的数据
     * @param r request
     * @return 返回字符串
     * @throws IOException io流异常
     */
    public static String requestString(HttpServletRequest r) throws IOException {
        r.setCharacterEncoding("utf-8");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(r.getInputStream(), "UTF-8"))) {
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    /***
     * 返回字符串到客户端
     * @param r response
     * @param s String
     */
    public static void responseString(HttpServletResponse r, String s) throws IOException {
        r.setContentType("text/html;charset=utf-8");
        PrintWriter pw = null;
        pw = r.getWriter();
        pw.append(s);
        pw.flush();
        pw.close();
    }

    /***
     * 获得cookies
     * @param request request
     * @param cookieKey cookie键
     * @return cookie值
     */
    public static String getCookies(HttpServletRequest request, String cookieKey) {
        Cookie mycookies[] = request.getCookies();
        String cookieData = null;
        if (mycookies == null) {
            return null;
        }
        for (int i = 0; i < mycookies.length; i++) {
            if (cookieKey.equals(mycookies[i].getName())) {
                cookieData = mycookies[i].getValue();
            }
        }
        return cookieData;
    }

    /***
     * 获得客户端地址
     * @param r request
     * @return ip地址
     */

    public static String getIpAddr(HttpServletRequest r) {
        String ip = r.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = r.getHeader("X-Real-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = r.getHeader("Remote-User-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = r.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = r.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = r.getRemoteAddr();
        }
        return ip;
    }

    public static int pageCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pageNum = 1;
        if (request.getParameter("page") != null) {
            if (!CommonUtils.checkRoundNum(request.getParameter("page"))) {
                GatewayUtils.showJson(false, 100001, null, response);
                return 0;
            }
            pageNum = Integer.parseInt(request.getParameter("page"));
            if (pageNum == 0) {
                GatewayUtils.showJson(false, 100001, null, response);
                return 0;
            }
        }
        return pageNum;
    }


    public static int pageCheckGateway(HashMap<String, String> postMap, HttpServletResponse response) throws IOException {
        int pageNum = 1;
        if (postMap.get("page") != null) {
            if (!CommonUtils.checkRoundNum(postMap.get("page"))) {
                GatewayUtils.showJson(false, 100001, null, response);
                return 0;
            }
            pageNum = Integer.parseInt(postMap.get("page"));
            if (pageNum == 0) {
                GatewayUtils.showJson(false, 100001, null, response);
                return 0;
            }
        }
        return pageNum;
    }
}
