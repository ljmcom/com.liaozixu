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
<%@ page import="com.liaozixu.util.DateUtils" %>
<section class="main centreGrid">
<c:if test="${articleList.nowPage == 1}">
    <div class="aboutMe grid_Bg partBomMg partTopMg column grid_pd">
        <div class="aboutMeAvatar row">
            <img src="${webUrl}/static/client/images/avatar.png" alt="" title="avatar" />
        </div>
        <div class="aboutMeName">LIAO ZIXU</div>
        <div class="aboutMeDes">${aboutMeResume}</div>
        <div class="contact">
            <ul>
                <li class="wechat">
                    <a href="javascript:;" class="column">
                        <i class="iconfont icon-wechat"></i>
                        <p>WeChat</p>
                    </a>
                    <div class="qrCode">
                        <span class="trigon">
                            <i></i>
                        </span>
                        <div class="qrcodeImg">
                            <img src="${webUrl}/static/client/images/wechatQrcode.png" alt="">
                        </div>
                    </div>
                </li>
                <li>
                    <a href="mailto:admin@liaozixu.com" class="column">
                        <i class="iconfont icon-mailbox"></i>
                        <p>Email</p>
                    </a>
                </li>
            </ul>

        </div>
    </div>
    <div class="gitHubListBox grid_Bg partBomMg grid_pd">
        <div class="titleBox">
            <i class="iconfont icon-headline"></i>
            <span>GitHub Activity</span>
        </div>
        <div class="gitHubList">
            加载中...
        </div>
        <div class="row justifyCenter">
            <a href="https://github.com/liaozixu" class="btn" target="_blank">查看更多</a>
        </div>
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
                    <p class="textColor1 classifyLabel">分类<a href="${item.categoryUrl}" class="textColor2">${item.categoryTitle}</a></p>
                </div>
                <div class="articleDec">${item.description}</div>
                <div class="row justifyCenter">
                    <a href="${item.url}" class="btn">阅读全文</a>
                </div>
            </div>
        </div>
    </c:forEach>
    <cpi:show page="${articleList}" isEndPageJsFunc="isEndPage" isFirstPageJsFunc="isFirstPage" maxBtn="10"/>
</section>
<%@include file="/WEB-INF/view/client/footer.jsp"%>
<script src="${webUrl}/static/script.js"></script>
<script>
    var nowPage = ${articleList.nowPage};
    var authKey = '${authKey}';
    var client = new client();
    client.index();
</script>