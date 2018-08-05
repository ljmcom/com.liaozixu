<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/3
  Time: 10:56
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
<c:forEach items="${categoryList.row}" var="item">
    <p>----</p>
    <a href="${item.url}">
        <p>${item.title}</p>
    </a>
    <p>${item.description}</p>
    <p>----</p>
</c:forEach>


<cpi:show page="${categoryList}" isEndPageJsFunc="abc" isFirstPageJsFunc="cba" maxBtn="10"/>

</body>
</html>
