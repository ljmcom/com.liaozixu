<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/5
  Time: 7:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" content="text/html;charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>${title}</title>
    <meta name="keywords" content="${keywords}"/>
    <meta name="description" content="${description}"/>
    <meta name="country" content="China"/>
    <meta name="copyright" content="All rights reserved."/>
    <meta name="author" content="ZiXuLiao"/>
    <meta name="copyright" content="ZiXuLiao">
    <meta name="robots" content="all"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="Robots" contect="all">
    <link rel="shortcut icon" href="${webUrl}/static/client/images/favicon.ico"/>
    <link rel="stylesheet" href="${webUrl}/static/client/css/style.css">
</head>
<body>
<header>
    <div class="headerBox grid_Bg centreGrid row">
        <a href="/index.html">
            <div class="logo">
                <img src="${webUrl}/static/client/images/logo.svg" alt="">
            </div>
        </a>
        <ul class="nav">
            <li><a href="${webUrl}/index.html">首页</a></li>
            <li><a href="${webUrl}/category/index.html">分类</a></li>
            <li><a href="${webUrl}/archive/index.html">归档</a></li>
            <%--<li><a href="${webUrl}/tools/index.html">工具</a></li>--%>
        </ul>
        <%--<div class="search fr">--%>
            <%--<input type="text" placeholder="search"/>--%>
            <%--<i class="iconfont icon-search"></i>--%>
        <%--</div>--%>
    </div>
</header>
