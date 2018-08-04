<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/5
  Time: 1:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${clientPagingAttr.isFirstPage}">
        <li><a href="javascript:${clientPagingAttr.isFirstPageJsFunc}();">上一页</a></li>
    </c:when>
    <c:otherwise>
        <li><a href="page-${clientPagingAttr.nowPage-1}.html">上一页</a></li>
    </c:otherwise>
</c:choose>


<c:forEach var="x" begin="0" end="${clientPagingAttr.renderingBtn -1}">
    <li><a href="page-${clientPagingAttr.startNum+x}.html">${clientPagingAttr.startNum+x}</a>${(clientPagingAttr.nowPage == (clientPagingAttr.startNum+x) ? "当前页" : "")}</li>
</c:forEach>


<c:choose>
    <c:when test="${clientPagingAttr.isEndPage}">
        <li><a href="javascript:${clientPagingAttr.isEndPageJsFunc}();">下一页</a></li>
    </c:when>
    <c:otherwise>
        <li><a href="page-${clientPagingAttr.nowPage+1}.html">下一页</a></li>
    </c:otherwise>
</c:choose>