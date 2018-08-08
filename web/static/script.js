/**
 * @return {boolean}
 */
function IEVersion() {
    var userAgent = navigator.userAgent;
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1;
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE;
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    return (isIE || isEdge || isIE11);
}

if (IEVersion()) {
    window.location.href = "/isie.jsp";
}

function httpRequest(obj) {
    if (obj.error === undefined) {
        obj.error = function () {
        };
    }
    if (obj.success === undefined) {
        obj.success = function () {
        };
    }
    if (obj.data === undefined) {
        obj.data = {};
    }
    obj.data.authKey = authKey;
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
    httpRequestObj.open("POST", obj.url);
    httpRequestObj.send(JSON.stringify(obj.data));
    httpRequestObj.onreadystatechange = function () {
        if (httpRequestObj.readyState === 4 && httpRequestObj.status === 200) {
            var data = httpRequestObj.responseText;
            obj.complete();
            obj.success(JSON.parse(data));
        } else if (httpRequestObj.readyState === 4 && httpRequestObj.status !== 200) {
            obj.complete();
            obj.error();
        }
    }
}

function convertUTCTimeToLocalTime(UTCDateString) {
    if (!UTCDateString) {
        return '-';
    }

    function formatFunc(str) {
        return str > 9 ? str : '0' + str
    }

    var date2 = new Date(UTCDateString);
    var year = date2.getFullYear();
    var mon = formatFunc(date2.getMonth() + 1);
    var day = formatFunc(date2.getDate());
    var hour = date2.getHours();
    var noon = hour >= 12 ? 'PM' : 'AM';
    hour = hour >= 12 ? hour - 12 : hour;
    hour = formatFunc(hour);
    var min = formatFunc(date2.getMinutes());
    return year + '-' + mon + '-' + day + ' ' + noon + ' ' + hour + ':' + min;
}

function client() {
    client.prototype.index = function () {
        document.getElementsByClassName("nav")[0].getElementsByTagName("li")[0].classList.add("select");
        localStorage.setItem("navSelect", "index");
        var loadGithubEvents = function () {
            httpRequest({
                url: "/api/github/getEvents",
                success: function (res) {
                    if (res.resultCode) {
                        var row = res.data;
                        var outHtml = "<ul>";
                        for (var i = 0; i < 4; i++) {
                            var item = row[i];
                            outHtml += "<li>" +
                                "<div class=\"row\">" +
                                "<div class=\"breadcrumb\">" +
                                "<i class=\"iconfont icon-subject\"></i>" +
                                "</div>" +
                                "<div class=\"activityBox\">" +
                                "<p class=\"activityTime\">" + convertUTCTimeToLocalTime(item.createdAt) + "</p>" +
                                "<div class=\"activityTitle\"><a href='https://github.com/" + item.repo.name + "' target=\"_blank\">" + item.repo.name + "</a>" +
                                "<i class=\"iconfont icon-" + (item.type === 'PushEvent' ? 'submit' : 'establish') + "\"></i></div>" +
                                "<div class=\"activityDec textColor1\">" + item.message + "</div>" +
                                "</div>" +
                                "</div>" +
                                "</li>";
                        }
                        outHtml += "</ul>";
                        document.getElementsByClassName("gitHubList")[0].innerHTML = outHtml;
                    } else {
                        document.getElementsByClassName("gitHubList")[0].innerHTML = "加载失败，服务端返回原因：" + res.errCodeDes+"，本服务器置于中国境内，访问Github时常抽风，请见谅。";
                    }
                },
                error: function () {
                    document.getElementsByClassName("gitHubList")[0].innerHTML = "加载失败，可能是Github的问题，也可能是众所周知的问题。请刷新刷新重试。";
                },
                complete: function () {
                    console.log("github动态加载完毕");
                }
            });
        };
        if (nowPage === 1) {
            loadGithubEvents();
        } else {
            document.getElementsByClassName("articleList")[0].classList.add("partTopMg");
        }
    };
    client.prototype.article = function () {
        var tempMenuSelect = localStorage.getItem("navSelect");
        if(tempMenuSelect === "category"){
            document.getElementsByClassName("nav")[0].getElementsByTagName("li")[1].classList.add("select");
        }else if(tempMenuSelect === "archive"){
            document.getElementsByClassName("nav")[0].getElementsByTagName("li")[2].classList.add("select");
        }else{
            document.getElementsByClassName("nav")[0].getElementsByTagName("li")[0].classList.add("select");
        }
    };
    client.prototype.category = function () {
        document.getElementsByClassName("nav")[0].getElementsByTagName("li")[1].classList.add("select");
        localStorage.setItem("navSelect", "category");
        if(pageType === undefined){
            return;
        }
        if(pageType==="categoryArticle"){
            document.getElementsByClassName("articleList")[0].classList.add("partTopMg");
        }
    };
    client.prototype.archive = function () {
        document.getElementsByClassName("nav")[0].getElementsByTagName("li")[2].classList.add("select");
        localStorage.setItem("navSelect", "archive");
    };
}

function msgAlert(msg, time) {
    if (time === undefined) time = 3000;
    document.body.innerHTML += '<div id="modalAlert" class="modal modalAlert radius"><div class="modalAlertInfo">' + msg + "</div></div>";
    setTimeout(function () {
        document.getElementById("modalAlert").remove();
    }, 3000);
}

function isEndPage() {
    msgAlert("已经是最后一页");
}

function isFirstPage() {
    msgAlert("已经是第一页");
}