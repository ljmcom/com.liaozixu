<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/8
  Time: 下午10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@include file="/WEB-INF/view/client/header.jsp"%>
<%response.setStatus(404);%>
<div class="column errorBox">
    <img src="${webUrl}/static/client/images/404.png" alt="">
    <p>找不到你访问的页面</p>
    <a href="/index.html" class="btn">返回首页</a>
</div>
<script>
    <%
    String strBackUrl = "//" + request.getServerName()
                    + ":"
                    + request.getServerPort()
                    + request.getContextPath()
                    + request.getServletPath()
                + "?" + (request.getQueryString());
    %>
    console.log('<%=strBackUrl%>');
</script>