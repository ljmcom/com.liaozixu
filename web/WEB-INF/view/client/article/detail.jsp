<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/3
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.liaozixu.util.DateUtils" %>
<%@include file="/WEB-INF/view/client/header.jsp"%>
<section class="main centreGrid">
    <div class="articleList grid_Bg partBomMg grid_pd partTopMg">
        <div class="articleBox articleInfo ">
            <div class="articleTitle">${details.title}</div>
            <div class="articleType row">
                <c:if test="${details.original}">
                    <i class="iconfont icon-Originality"></i>
                </c:if>
                <span class="textColor1">${DateUtils.dateToStr(details.postTime)}</span>
                <p class="textColor1 classifyLabel">分类 <a href="${details.categoryUrl}" class="textColor2">${details.categoryTitle}</a></p></div>
        </div>
        <div class="markdown-body">
            ${details.contentText}
        </div>
        <div class="archives column">
            <c:if test="${previousArticle != null}">
                <a href="${previousArticle.url}" class="row textColor2">
                    <span>上一篇：</span>
                    <span>${previousArticle.title}</span>
                </a>
            </c:if>
            <c:if test="${previousArticle == null}">
                <a href="javascript:;" class="row textColor2">
                    <span>上一篇：</span>
                    <span>没有了</span>
                </a>
            </c:if>
            <c:if test="${nextArticle != null}">
                <a href="${nextArticle.url}" class="row textColor2">
                    <span>下一篇：</span>
                    <span>${nextArticle.title}</span>
                </a>
            </c:if>
            <c:if test="${nextArticle == null}">
                <a href="javascript:;" class="row textColor2">
                    <span>下一篇：</span>
                    <span>没有了</span>
                </a>
            </c:if>
        </div>
    </div>
    <div class="catalogueBox">
        <div class="cataloguePot">
            <ul>
                <%--<li>--%>
                    <%--<i class="iconfont icon-catalogue"></i>--%>
                <%--</li>--%>
                <li>
                    <a href="javascript:window.scrollTo(0,0)">
                        <i class="iconfont icon-upward"></i>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</section>
<%@include file="/WEB-INF/view/client/footer.jsp"%>
<script src="${webUrl}/static/script.js"></script>
<script>
    var authKey = '${authKey}';
    var client = new client();
    client.article();
</script>
