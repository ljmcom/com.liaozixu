package com.liaozixu.util;

import com.liaozixu.system.Config;

public class UrlUtils {
    public static Config config = new Config();
    public static String webURL = config.get("webURL");

    public static String articleDetail(String categoryAlias, String articleAlias) {
        return webURL + "/article/" + categoryAlias + "/" + articleAlias + ".html";
    }

    public static String categoryList(String articleAlias) {
        return webURL + "/category/" + articleAlias + "/index.html";
    }

}