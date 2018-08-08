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
<section class="main centreGrid">
    <div class="classifyBg grid_Bg partBomMg grid_pd partTopMg">
        <div class="titleBox">
            <i class="iconfont icon-headline"></i>
            <span>Category</span>
        </div>
        <div class="classifyBox">
        <c:forEach items="${categoryList.row}" var="item">
            <div class="classifyList row">
                <i></i>
                <a href="${item.url}">${item.title}</a>
            </div>
        </c:forEach>
        </div>
        <cpi:show page="${categoryList}" isEndPageJsFunc="isEndPage" isFirstPageJsFunc="isFirstPage" maxBtn="10"/>
    </div>
</section>
<%@include file="/WEB-INF/view/client/footer.jsp"%>
<script src="${webUrl}/static/script.js"></script>
<script>
    var pageType = "categoryList";
    var authKey = '${authKey}';
    var client = new client();
    client.category();
</script>