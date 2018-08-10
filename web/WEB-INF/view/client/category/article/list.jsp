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
<%@include file="/WEB-INF/view/client/header.jsp" %>
<section class="main centreGrid">
    <c:if test="${articleList.getRow().size()==0}">
        <div class="articleList grid_Bg partBomMg grid_pd">
            本分类下没有文章
        </div>
    </c:if>
    <c:forEach items="${articleList.row}" var="item">
        <div class="articleList grid_Bg partBomMg grid_pd">
            <div class="articleBox">
                <a href="${item.url}" class="articleTitle">${item.title}</a>
                <div class="articleType row">
                    <c:if test="${item.original}">
                        <i class="iconfont icon-Originality"></i>
                    </c:if>
                    <span class="textColor1">${DateUtils.dateToStr(item.postTime)}</span>
                    <p class="textColor1 classifyLabel">分类<a href="${item.categoryUrl}"
                                                             class="textColor2">${item.categoryTitle}</a></p>
                </div>
                <div class="articleDec">${item.description}</div>
                <div class="row justifyCenter">
                    <a href="${item.url}" class="btn">阅读全文</a>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:if test="${articleList.getRow().size()!=0}">
        <cpi:show page="${articleList}" isEndPageJsFunc="isEndPage" isFirstPageJsFunc="isFirstPage" maxBtn="10"/>
    </c:if>
</section>
<%@include file="/WEB-INF/view/client/footer.jsp" %>
<script src="${webUrl}/static/script.js"></script>
<script>
    var pageType = "categoryArticle";
    var authKey = '${authKey}';
    var client = new client();
    client.category();
</script>