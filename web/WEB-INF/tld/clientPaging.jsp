<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/5
  Time: 1:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pageBox grid_Bg partBomMg">
    <div class="pageList row justifyCenter">
        <ul>
            <c:choose>
                <c:when test="${clientPagingAttr.isFirstPage}">
                    <a href="javascript:${clientPagingAttr.isFirstPageJsFunc}();">
                        <li>上一页</li>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="page-${clientPagingAttr.nowPage-1}.html">
                        <li>上一页</li>
                    </a>
                </c:otherwise>
            </c:choose>
            <c:forEach var="x" begin="0" end="${clientPagingAttr.renderingBtn -1}">
                <c:choose>
                <c:when test="${(clientPagingAttr.nowPage == (clientPagingAttr.startNum+x))}">
                    <a href="page-${clientPagingAttr.startNum+x}.html" class="select">
                        <li>${clientPagingAttr.startNum+x}</li>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="page-${clientPagingAttr.startNum+x}.html">
                        <li>${clientPagingAttr.startNum+x}</li>
                    </a>
                </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${clientPagingAttr.isEndPage}">
                        <a href="javascript:${clientPagingAttr.isEndPageJsFunc}();">
                            <li>下一页</li>
                        </a>
                </c:when>
                <c:otherwise>
                    <a href="page-${clientPagingAttr.nowPage+1}.html">
                        <li>下一页</li>
                    </a>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>