<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/5
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <style>
        body,
        body div,
        p,
        html,
        span,
        ul,
        li,
        input {
            margin: 0;
            padding: 0;
            border: 0;
            background: 0 0;
            vertical-align: baseline;
            font-weight: normal;
            font-size: 100%;
        }

        html {
            box-sizing: border-box;
            overflow: hidden;
            width: 100%;
            height: 100%;

        }

        body {
            font-family: 'Microsoft YaHei', Tahoma, Arial, 'Lantinghei SC', 'Microsoft YaHei', sans-serif;
            font-size: 14px;
            color: #000;
            overflow: hidden;
            position: relative;
            width: 100%;
            height: 100%;
            max-height: 100%;
        }

        body::before {
            content: '';
            display: block;
            position: absolute;
            left: 0px;
            width: 100%;
            height: 100%;
            background-size: 100% 100%;
            background: url('${webUrl}/static/admin/login/images/backgroundImage.png') no-repeat;

        }

        *,
        :after,
        :before {
            box-sizing: border-box;
        }

        a {
            margin: 0;
            padding: 0;
            background: 0 0;
            vertical-align: baseline;
            font-size: 100%;
            text-decoration: none;
            color: #fff;
        }

        img {
            max-width: 100%;
            height: 100%;
        }

        input {
            outline: none;
            -webkit-appearance: none;
            background-color: transparent;
            border-radius: 0;
            border: none;
        }

        @font-face {
            font-family: 'iconfont';
            /* project id 699627 */
            src: url('${webUrl}/static/admin/login/fonts/iconfont.eot');
            src: url('${webUrl}/static/admin/login/fonts/iconfont.eot?#iefix') format('embedded-opentype'),
            url('${webUrl}/static/admin/login/fonts/iconfont.woff') format('woff'),
            url('${webUrl}/static/admin/login/iconfont.ttf') format('truetype'),
            url('${webUrl}/static/admin/login/iconfont.svg#iconfont') format('svg');
            font-display: auto;
        }

        .iconfont {
            font-family: "iconfont" !important;
            font-size: 16px;
            font-style: normal;
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
            vertical-align: middle;
        }

        .icon-mima:before {
            content: "\e61b";
        }

        .icon-zhanghao:before {
            content: "\e6b6";
        }

        .loginBox {
            width: 520px;
            background-color: rgba(255, 255, 255, .4);
            -webkit-border-radius: 6px;
            -moz-border-radius: 6px;
            -ms-border-radius: 6px;
            border-radius: 6px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            -webkit-transform: translate(-50%, -50%);
            -moz-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            padding: 48px 70px;
            text-align: center;
            margin-bottom: 22px;
        }

        .loginLogo {
            width: 144px;
            height: 60px;
            overflow: hidden;
            margin: 0 auto 22px;
        }

        .loginBox > p {
            line-height: 30px;
        }

        .username,
        .password {
            width: 380px;
            padding-bottom: 10px;
            border-bottom: 1px solid rgba(0, 0, 0, .1);
            position: relative;
            margin-top: 36px;
        }

        .username > input,
        .password > input {
            width: 100%;
            padding-left: 36px;
            letter-spacing: 2px;
            line-height: 24px;
            margin-top: 2px;
        }

        .username > i,
        .password > i {
            position: absolute;
            top: 2px;
            bottom: 0px;
            color: rgba(0, 0, 0, .54);
            font-size: 22px;
            vertical-align: middle;
        }

        .loginBtn {
            margin-top: 48px;
            width: 380px;
            height: 50px;
            background-color: rgba(255, 103, 0, 1);
            -webkit-border-radius: 6px;
            -moz-border-radius: 6px;
            -ms-border-radius: 6px;
            border-radius: 6px;
            overflow: hidden;
        }

        .loginBtn > input {
            width: 100%;
            color: #fff;
            line-height: 50px;
            text-align: center;
            cursor: pointer;
        }

        .loginBtn:hover {
            box-shadow: 0 4px 8px 0 rgba(199, 87, 10, 0.75);
            -webkit-box-shadow: 0 4px 8px 0 rgba(199, 87, 10, 0.75);
            -moz-box-shadow: 0 4px 8px 0 rgba(199, 87, 10, 0.75);
            -ms-box-shadow: 0px 4px 8px 0px rgba(199, 87, 10, 0.75);
            -webkit-transition: all .3s ease-in-out;
            -moz-transition: all .3s ease-in-out;
            transition: all .3s ease-in-out;
        }

        .loginInput:hover {
            border-bottom: 1px solid rgba(255, 103, 0, 1);
            -webkit-transition: all .3s ease-in-out;
            -moz-transition: all .3s ease-in-out;
            transition: all .3s ease-in-out;

        }

        .loginInput:hover i {
            color: rgba(255, 103, 0, 1);
            -webkit-transition: all .3s ease-in-out;
            -moz-transition: all .3s ease-in-out;
            transition: all .3s ease-in-out;
            transform: scale(1.1);
        }

        .loginAq {
            position: absolute;
            bottom: 30px;
            left: 50%;
            transform: translate(-50%, 0%);
            -webkit-transform: translate(-50%, 0%);
            -moz-transform: translate(-50%, 0%);
            -ms-transform: translate(-50%, 0%);
            color: #fff;
            font-size: 12px;
        }

        .loginAq > span:first-of-type {
            margin-right: 16px;
        }

        .errorTip {
            position: absolute;
            top: -26px;
            left: 36px;
            letter-spacing: 2px;
            color: #ff0000;
            -webkit-transition: all .3s ease-in-out;
            -moz-transition: all .3s ease-in-out;
            transition: all .3s ease-in-out;
        }
    </style>
    <style>
        .shadeBg {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, .1);
        }

        .loadingBox {
            position: fixed;
            top: 50%;
            left: 50%;
            -webkit-transform: translate(-50%, -50%);
            -moz-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            -o-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
            z-index: 21;
        }

        svg {
            height: 60px;
            width: 60px;
            overflow: visible;
        }

        .g-circles {
            -webkit-transform: scale(0.9) translate(7px, 7px);
            -ms-transform: scale(0.9) translate(7px, 7px);
            transform: scale(0.9) translate(7px, 7px);
        }

        circle {
            fill: #ff6700;
            fill-opacity: 0;
            -webkit-animation: opacity 1.2s linear infinite;
            animation: opacity 1.2s linear infinite;
        }

        circle:nth-child(12n + 1) {
            -webkit-animation-delay: -0.1s;
            animation-delay: -0.1s;
        }

        circle:nth-child(12n + 2) {
            -webkit-animation-delay: -0.2s;
            animation-delay: -0.2s;
        }

        circle:nth-child(12n + 3) {
            -webkit-animation-delay: -0.3s;
            animation-delay: -0.3s;
        }

        circle:nth-child(12n + 4) {
            -webkit-animation-delay: -0.4s;
            animation-delay: -0.4s;
        }

        circle:nth-child(12n + 5) {
            -webkit-animation-delay: -0.5s;
            animation-delay: -0.5s;
        }

        circle:nth-child(12n + 6) {
            -webkit-animation-delay: -0.6s;
            animation-delay: -0.6s;
        }

        circle:nth-child(12n + 7) {
            -webkit-animation-delay: -0.7s;
            animation-delay: -0.7s;
        }

        circle:nth-child(12n + 8) {
            -webkit-animation-delay: -0.8s;
            animation-delay: -0.8s;
        }

        circle:nth-child(12n + 9) {
            -webkit-animation-delay: -0.9s;
            animation-delay: -0.9s;
        }

        circle:nth-child(12n + 10) {
            -webkit-animation-delay: -1s;
            animation-delay: -1s;
        }

        circle:nth-child(12n + 11) {
            -webkit-animation-delay: -1.1s;
            animation-delay: -1.1s;
        }

        circle:nth-child(12n + 12) {
            -webkit-animation-delay: -1.2s;
            animation-delay: -1.2s;
        }

        @-webkit-keyframes opacity {
            3% {
                fill-opacity: 1;
            }

            75% {
                fill-opacity: 0;
            }
        }

        @keyframes opacity {
            3% {
                fill-opacity: 1;
            }
            75% {
                fill-opacity: 0;
            }
        }
    </style>
</head>
<body>

<div class="loginBox">
    <div class="loginLogo">
        <img src="${webUrl}/static/logo.svg" alt="">
    </div>
    <p>登陆到liaozixu.com</p>
    <div class="username loginInput">
        <p class="errorTip" style="display: none" id="userNameError">请输入正确的用户名</p>
        <i class="iconfont icon-zhanghao"></i>
        <input type="text" placeholder="请输入用户名" id="username">

    </div>
    <div class="password loginInput">
        <p class="errorTip" style="display: none" id="passwordError">请输入正确的密码</p>
        <i class="iconfont icon-mima"></i>
        <input type="password" placeholder="请输入密码" id="password">
    </div>
    <div class="password loginInput">
        <p class="errorTip" style="display: none" id="securityCodeError">请输入正确的安全码</p>
        <i class="iconfont icon-mima"></i>
        <input type="password" placeholder="请输入安全码" id="securityCode">
    </div>
    <div class="loginBtn">
        <input type="button" value="登录" id="userLogin">
    </div>
</div>
<div class="loginAq">
    <span>粤ICP备17022468号-1</span>
</div>
<div class="loadingBox" style="display:none" id="loadingBox">
    <svg viewBox="0 0 120 120" version="1.1">
        <g class="g-circles">
            <circle transform="translate(35, 16.698730) rotate(-30) translate(-35, -16.698730) " cx="35" cy="16.6987298"
                    r="8"></circle>
            <circle transform="translate(16.698730, 35) rotate(-60) translate(-16.698730, -35) " cx="16.6987298" cy="35"
                    r="8"></circle>
            <circle transform="translate(10, 60) rotate(-90) translate(-10, -60) " cx="10" cy="60" r="8"></circle>
            <circle transform="translate(16.698730, 85) rotate(-120) translate(-16.698730, -85) " cx="16.6987298"
                    cy="85" r="8"></circle>
            <circle transform="translate(35, 103.301270) rotate(-150) translate(-35, -103.301270) " cx="35"
                    cy="103.30127" r="8"></circle>
            <circle cx="60" cy="110" r="8"></circle>
            <circle transform="translate(85, 103.301270) rotate(-30) translate(-85, -103.301270) " cx="85"
                    cy="103.30127" r="8"></circle>
            <circle transform="translate(103.301270, 85) rotate(-60) translate(-103.301270, -85) " cx="103.30127"
                    cy="85" r="8"></circle>
            <circle transform="translate(110, 60) rotate(-90) translate(-110, -60) " cx="110" cy="60" r="8"></circle>
            <circle transform="translate(103.301270, 35) rotate(-120) translate(-103.301270, -35) " cx="103.30127"
                    cy="35" r="8"></circle>
            <circle transform="translate(85, 16.698730) rotate(-150) translate(-85, -16.698730) " cx="85"
                    cy="16.6987298" r="8"></circle>
            <circle cx="60" cy="10" r="8"></circle>
        </g>
    </svg>
</div>
<div class="shadeBg" style="display:none" id="shadeBg"></div>
</body>
<script src="${webUrl}/static/script.js"></script>
<script>
    var authKey = '${authKey}';

    function loading(status) {
        if (status) {
            document.getElementById("loadingBox").style.display = "block";
            document.getElementById("shadeBg").style.display = "block";
        } else {
            document.getElementById("loadingBox").style.display = "none";
            document.getElementById("shadeBg").style.display = "none";
        }
    }

    function reset() {
        document.getElementById("securityCodeError").style.display = "none";
        document.getElementById("userNameError").style.display = "none";
        document.getElementById("passwordError").style.display = "none";
        document.getElementById("loadingBox").style.display = "none";
        document.getElementById("shadeBg").style.display = "none";
    }

    document.getElementById("userLogin").onclick = function (ev) {
        reset();
        var username = document.getElementById("username").value;
        if (username === null || username.replace(/ /g, '') === '') {
            document.getElementById("userNameError").style.display = "block";
            return;
        }
        var password = document.getElementById("password").value;
        if (password === null || password.replace(/ /g, '') === '') {
            document.getElementById("passwordError").style.display = "block";
            return;
        }
        var securityCode = document.getElementById("securityCode").value;
        if (securityCode === null || securityCode.replace(/ /g, '') === '') {
            document.getElementById("securityCodeError").style.display = "block";
            return;
        }
        //    请求后台
        loading(true);
        httpRequest({
            url: "/api/admin/user/login",
            data: {
                username: username,
                password: password,
                securityCode: securityCode
            },
            success: function (res) {
                if(!res.resultCode){
                    if(res.errCode === 100008){
                        document.getElementById("userNameError").style.display = "block";
                        return;
                    }
                    if(res.errCode === 100009){
                        document.getElementById("securityCodeError").style.display = "block";
                        return;
                    }
                    if(res.errCode === 100010){
                        document.getElementById("passwordError").style.display = "block";
                        return;
                    }

                }
                window.location.href = "/admin/main";
            },
            complete: function () {
                loading(false);
            }
        });

    }
</script>
</html>