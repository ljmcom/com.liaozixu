<%--
  Created by IntelliJ IDEA.
  User: liaozixu
  Date: 2018/8/9
  Time: 上午5:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理后台</title>
    <link rel="stylesheet" href="${webUrl}/static/admin/css/public.css">
</head>
<body id="body">
<header>
    <div class="headerLogo mg-t-16">
        <img src="${webUrl}/static/client/images/logo.svg" alt="">
    </div>
    <div class="headerRight fr">
        <div class="userBox fl">
            <div class="userName ">
                <span>您好:</span>
                <span>${adminUser.username}</span>
            </div>
        </div>
    </div>
</header>

<nav ng-app="menu" ng-view>
    <div class="navLeft fl">
        <ul>
            <li ng-repeat="item in menuObj" class="{{(item.select) ? 'selected' : ''}}"
                ng-click="titleSetSecond(item.title)">
                <a ng-href="{{ (item.url != null) ? '#'+item.url : 'javascript:;' }}">
                    <i class="iconfont icon-{{item.icon}}"></i>
                    <span>{{item.title}}</span>
                </a>
            </li>
        </ul>

    </div>

    <div class="navRight fl">

        <ul>
            <li ng-repeat="item in secondObj" class="{{(item.select) ? 'selected' : ''}}">
                <a href="{{ (item.url != null) ? '#'+item.url : 'javascript:;' }}">{{item.title}}</a>
            </li>
        </ul>

    </div>
</nav>

<main id="app" ng-app="app" ng-view>

</main>

</body>

</html>
<script src="https://static.zixu.hk/angular/1.4.6_angular.js"></script>
<script src="https://static.zixu.hk/angular/1.3.13_angular-route.js"></script>
<script>
    var authKey = '${authKey}';

    function httpRequest(obj) {
        if (obj.error === undefined) {
            obj.error = function () {
            };
        }
        if (obj.success === undefined) {
            obj.success = function () {
            };
        }
        if (obj.type === undefined) {
            obj.type = "GET";
        }
        if (obj.data === undefined) {
            obj.data = null;
        }
        if (obj.data === undefined) {
            obj.data = null;
        }
        if (obj.complete === undefined) {
            obj.complete = function () {
            };
        }
        if (obj.url === undefined) {
            obj.error();
            return;
        }
        var httpRequestObj;
        if (window.XMLHttpRequest) {
            httpRequestObj = new XMLHttpRequest();
        } else {
            httpRequestObj = new ActiveXObject("Microsoft.XMLHTTP");
        }
        httpRequestObj.open(obj.type, obj.url);
        httpRequestObj.send(obj.data);
        httpRequestObj.onreadystatechange = function () {
            if (httpRequestObj.readyState === 4 && httpRequestObj.status === 200) {
                var data = httpRequestObj.responseText;
                obj.complete();
                obj.success(JSON.parse(data));
                return;
            } else if (httpRequestObj.readyState === 4 && httpRequestObj.status !== 200) {
                obj.complete();
                obj.error();
                return;
            }
        }
    }

    function gatewayHttpRequest(url, data, succFunc, errFunc, completeFunc, isShowLoading) {
        if (isShowLoading === undefined) {
            isShowLoading = true;
        }
        if (errFunc === undefined) {
            errFunc = function () {
            };
        }
        if (completeFunc === undefined) {
            completeFunc = function () {
            };
        }
        var method = "get";
        var postStr = null;
        if (data !== null) {
            method = "post";
            var loginCookieKey = localStorage.getItem("loginCookieKey");
            data.token = getCookie(loginCookieKey);
            console.log(data.token)
            postStr = JSON.stringify(data);

        }
        if (isShowLoading) {
            showTips.showLoading(true);
        }
        httpRequest({
            url: url,
            type: method,
            data: postStr,
            success: function (res) {
                if (!res.resultCode) {
                    switch (res.errCode) {
                        case 100007:
                            window.location.href = "/user/login";
                            return;
                    }
                    showTips.showTopToast('error', res.errCodeDes);
                    errFunc(res.errCode, res.errCodeDes);
                    return;
                }
                succFunc(res);
            },
            error: function () {
                showTips.showTopToast('error', '请求失败，请检查你的网络是否有通畅！');
            },
            complete: function () {
                completeFunc();
                if (isShowLoading) {
                    showTips.showLoading(false);
                }
            }
        });
    }

    var showTips = {
        showLoading: function (state) {
            var html = '<div class="load loadingBox"><svg viewBox="0 0 120 120" version="1.1"> <g id="circle" class="g-circles g-circles--v1">'
            html += '<circle id="12" transform="translate(35, 16.698730) rotate(-30) translate(-35, -16.698730) " cx="35" cy="16.6987298" r="8"></circle>';
            html += '<circle id="11" transform="translate(16.698730, 35) rotate(-60) translate(-16.698730, -35) " cx="16.6987298" cy="35" r="8"></circle>';
            html += '<circle id="10" transform="translate(10, 60) rotate(-90) translate(-10, -60) " cx="10" cy="60" r="8"></circle>';
            html += '<circle id="9" transform="translate(16.698730, 85) rotate(-120) translate(-16.698730, -85) " cx="16.6987298" cy="85" r="8"></circle>';
            html += '<circle id="8" transform="translate(35, 103.301270) rotate(-150) translate(-35, -103.301270) " cx="35" cy="103.30127" r="8"></circle>';
            html += '<circle id="7" cx="60" cy="110" r="8"></circle><circle id="6" transform="translate(85, 103.301270) rotate(-30) translate(-85, -103.301270) " cx="85" cy="103.30127" r="8"></circle>';
            html += '<circle id="5" transform="translate(103.301270, 85) rotate(-60) translate(-103.301270, -85) " cx="103.30127" cy="85" r="8"></circle>';
            html += '<circle id="4" transform="translate(110, 60) rotate(-90) translate(-110, -60) " cx="110" cy="60" r="8"></circle>';
            html += '<circle id="3" transform="translate(103.301270, 35) rotate(-120) translate(-103.301270, -35) " cx="103.30127" cy="35" r="8"></circle>';
            html += '<circle id="2" transform="translate(85, 16.698730) rotate(-150) translate(-85, -16.698730) " cx="85" cy="16.6987298" r="8"></circle>';
            html += '<circle id="1" cx="60" cy="10" r="8"></circle></g><use xlink:href="#circle" class="use"/>';
            html += '</svg></div>';
            if (state) {
                document.getElementsByTagName('body')[0].insertAdjacentHTML("beforeend", html);
                showTips.showShadeBg(true);
            } else {
                var load = document.getElementsByClassName('load  loadingBox')[0];
                if (load == undefined) {
                    return;
                }
                load.remove();
                showTips.showShadeBg(false);
            }
        },
        showTopToast: function (type, msg, timeout) {
            if (timeout == undefined) {
                timeout = 3000;
            }
            var html = '';
            if (type == 'success') {
                html += '<div class="popoutSkip ">'
            } else if (type = 'error') {
                html += '<div class="popoutSkip popoutLoseBg">'
            }
            html += '<p class="popoutSkipText"><span>' + msg + '</span>';
            html += '</p><a href="javascript:showTips.hideTopToast();" class="iconfont icon-disable"></a></div>';
            document.getElementsByTagName('body')[0].insertAdjacentHTML("beforeend", html);
            showTips.showShadeBg(true);
            setTimeout(function () {
                showTips.hideTopToast();
            }, timeout);
        },
        hideTopToast: function () {
            var popoutSkip = document.getElementsByClassName('popoutSkip')[0];
            if (popoutSkip == undefined) {
                return;
            }
            popoutSkip.remove();
            showTips.showShadeBg(false);
        },
        showAlertComfirmFunc: function () {

        },
        showAlertcancelFunc: function () {
        },
        showAlert: function (msg, comfirmFunc, cancelFunc) {
            var t = this;
            if (comfirmFunc != undefined) {
                showTips.showAlertComfirmFunc = comfirmFunc;
            } else {
                showTips.showAlertComfirmFunc = showAlertComfirmFunc = function () {
                    showTips.hideAlert();
                };
            }
            if (cancelFunc != undefined) {
                showTips.showAlertcancelFunc = cancelFunc;
            } else {
                showTips.showAlertcancelFunc = showAlertComfirmFunc = function () {
                };
            }
            var html = '';
            html += '<div class="popoutBg border-radius" id="alertSys"><div class="row fr">';
            html += '<a href="javascript:showTips.hideAlert(\'cancelFunc\');" class="closeBtn"><i class="iconfont icon-disable"></i></a></div>';
            html += '<div class="textColor1 alertTitle"><h4>' + msg + '</h4></div><div class="row">';
            html += '<a href="javascript:showTips.hideAlert(\'comfirmFunc\');  " class="alertBtn btn select mg-r-8">确定</a>';
            html += '<a href="javascript:showTips.hideAlert(\'cancelFunc\');" class="alertBtn btn">取消</a></div></div>';
            document.getElementsByTagName('body')[0].insertAdjacentHTML("beforeend", html);
            showTips.showShadeBg(true);
        },


        hideAlert: function (func) {

            document.getElementById('alertSys').remove();
            showTips.showShadeBg(false);

            if (func == "cancelFunc") {
                showTips.showAlertcancelFunc();
            } else {
                showTips.showAlertComfirmFunc();
            }


        },
        showShadeBg: function (state) {
            var html = '<div class="shadeBg"></div>';
            if (state) {
                document.getElementsByTagName('body')[0].insertAdjacentHTML("beforeend", html);
            } else {
                var shadeBg = document.getElementsByClassName('shadeBg')[0];
                if (shadeBg == undefined) {
                    return;
                }
                shadeBg.remove();
            }
        }
    };
    var menuApp = angular.module('menu', []);
    String.prototype.parseURL = function () {
        var url = this.toString();
        var a = document.createElement('a');
        a.href = url;
        return {
            source: url,
            protocol: a.protocol.replace(':', ''),
            host: a.hostname,
            port: a.port,
            query: a.search,
            file: (a.pathname.match(/\/([^\/?#]+)$/i) || [, ''])[1],
            hash: a.hash.replace('#', ''),
            path: a.pathname.replace(/^([^\/])/, '/$1'),
            relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [, ''])[1],
            segments: a.pathname.replace(/^\//, '').split('/'),
            params: (function () {
                var ret = {};
                var seg = a.search.replace(/^\?/, '').split('&').filter(function (v, i) {
                    if (v !== '' && v.indexOf('=')) {
                        return true;
                    }
                });
                seg.forEach(function (element, index) {
                    var idx = element.indexOf('=');
                    var key = element.substring(0, idx);
                    var val = element.substring(idx + 1);
                    ret[key] = val;
                });
                return ret;
            })()
        };
    };
    menuApp.run(['$rootScope', '$location', '$sce', '$log', function ($rootScope, $location, $sce, $log) {
        $rootScope.menuObj = [
            {
                title: "首页",
                icon: "Store",
                url: "/index",
                second: [
                    {
                        title: "数据概况",
                        url: "/index"
                    }
                ]
            },
            {
                title: "分类",
                icon: "transaction",
                url: "/category/list",
                second: [
                    {
                        title: "分类管理",
                        url: "/category/list"
                    },
                    {
                        title: "新增分类",
                        url: "/category/add"
                    }
                ]
            },
            {
                title: "文章",
                icon: "order",
                url: "/article/list",
                second: [
                    {
                        title: "文章管理",
                        url: "/article/list"
                    },
                    {
                        title: "新增文章",
                        url: "/article/add"
                    }
                ]
            },
            {
                title: "配置",
                icon: "tubiao01",
                url: "/system/config",
                second: [
                    {
                        title: "系统配置",
                        url: "/system/config"
                    }
                ]
            }
        ];
        $rootScope.secondObj = [];
        $rootScope.secondObj = $rootScope.menuObj[0].second;
        $rootScope.titleSetSecond = function (title) {
            for (var i = 0; i < $rootScope.menuObj.length; i++) {
                $rootScope.menuObj[i].select = false;
                if ($rootScope.menuObj[i].title === title) {
                    $rootScope.secondObj = $rootScope.menuObj[i].second;
                    $rootScope.menuObj[i].select = true;
                }
            }
        };
        $rootScope.$on('$locationChangeSuccess', function (evt, current, previous) {
            var nowRoute = window.location.hash;
            var flag = false;

            function r() {
                for (var i = 0; i < $rootScope.menuObj.length; i++) {
                    var root = $rootScope.menuObj[i];
                    $rootScope.menuObj[i].select = false;
                    for (var j = 0; j < root.second.length; j++) {
                        var second = root.second[j];
                        var url = "#" + second.url;
                        $rootScope.menuObj[i].second[j].select = false;
                        if (nowRoute === url) {
                            $rootScope.menuObj[i].select = true;
                            $rootScope.menuObj[i].second[j].select = true;
                            $rootScope.secondObj = root.second;
                            localStorage.setItem("menuTempMenuObj", JSON.stringify($rootScope.menuObj));
                            localStorage.setItem("menuTempMenusecondObj", JSON.stringify($rootScope.secondObj));
                            flag = true;
                        }
                    }
                }
            };
            r();
            if (!flag) {
                nowRoute = '#' + arguments[2].parseURL().hash;
                r();
                if (!flag) {
                    $rootScope.menuObj = JSON.parse(localStorage.getItem("menuTempMenuObj"));
                    $rootScope.secondObj = JSON.parse(localStorage.getItem("menuTempMenusecondObj"));
                }
            }
        });
    }]);
    var app = angular.module('app', ['ngRoute']);
    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/index', {
                templateUrl: "/static/admin/template/index.html",
                controller: "index"
            })
            .when('/category/list', {
                templateUrl: "/static/admin/category/list.html",
                controller: "categoryList"
            })

            .otherwise({redirectTo: '/index'})
    }]);


    app.run(['$rootScope', '$location', '$sce', function ($rootScope, $location, $sce) {
        $rootScope.$on('$routeChangeStart', function (evt, next, current) {
            showTips.showShadeBg(false);
            showTips.showLoading(false);
            showTips.showLoading(true);
        });
        $rootScope.$on('$routeChangeSuccess', function (evt, current, previous) {
            showTips.showLoading(false);
            showTips.showShadeBg(false);
        });
    }]);

    app.controller('index', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
    }]);

    app.controller('categoryList', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {

    }]);

    angular.bootstrap(document.getElementById("app"), ['app']);
</script>
