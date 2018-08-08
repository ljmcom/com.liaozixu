<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/9
  Time: 上午3:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="cpi" uri="http://liaozixu.com/tld/cpi" %>
<%@include file="/WEB-INF/view/client/header.jsp" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.liaozixu.util.DateUtils" %>
<section class="main centreGrid">
    <div class="classifyBg grid_Bg partBomMg grid_pd partTopMg">
        <div class="titleBox">
            <i class="iconfont icon-headline"></i>
            <span>Archive</span>
        </div>
        <div class="classifyBox">
            <ul>
                <c:set var="year" value="2000"></c:set>
                <c:set var="month" value="1"></c:set>
                <c:forEach items="${articleList.row}" var="item" varStatus="st">
                <c:if test="${DateUtils.dateToStrYear(item.postTime) != year || DateUtils.dateToStrMonth(item.postTime) != month}">
                <c:set var="year" value="${DateUtils.dateToStrYear(item.postTime)}"></c:set>
                <c:set var="month" value="${DateUtils.dateToStrMonth(item.postTime)}"></c:set>
                    <c:if test="${!st.first}">
                        </ul>
                        </div>
                        </div>
                        </li>
                    </c:if>
                        <li>
                        <div class="archiveList row">
                        <div class="pot">
                        <i></i>
                        </div>
                        <div class="archiveRight column">
                        <div class="time">${year}-${month}</div>
                        <ul>
                </c:if>
                <c:if test="${DateUtils.dateToStrYear(item.postTime) == year && DateUtils.dateToStrMonth(item.postTime) == month}">
                    <li class="row">
                        <span>${DateUtils.dateToStr(item.postTime)}</span>
                        <p>${item.title}</p>
                    </li>
                </c:if>
                <c:if test="${st.last}">
                        </ul>
                        </div>
                        </div>
                        </li>
                </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
    <cpi:show page="${articleList}" isEndPageJsFunc="isEndPage" isFirstPageJsFunc="isFirstPage" maxBtn="10"/>
</section>
<%@include file="/WEB-INF/view/client/footer.jsp"%>
<script src="${webUrl}/static/script.js"></script>
<script>
    var authKey = '${authKey}';
    var client = new client();
    client.archive();
</script>
