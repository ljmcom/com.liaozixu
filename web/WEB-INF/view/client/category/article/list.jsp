<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/3
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="cpi" uri="http://liaozixu.com/tld/cpi" %>
<%@include file="/WEB-INF/view/client/header.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<br>
<%--文章开始--%>
<c:forEach items="${articleList.row}" var="item">
    <p>----</p>
    <a href="${item.url}">
        <p>${item.id}</p>
        <p>${item.title}</p>
    </a>
    <p>${item.description}</p>
    <p>${item.postTime}</p>
    <a href="${item.categoryUrl}">
        <p>${item.categoryTitle}</p>
    </a>
    <p>----</p>
</c:forEach>
<%--分页--%>
<cpi:show page="${articleList}" isEndPageJsFunc="abc" isFirstPageJsFunc="cba" maxBtn="10"/>
</body>
</html>
<script src="${webUrl}/static/script.js"></script>