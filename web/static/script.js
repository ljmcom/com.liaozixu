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
        //    首页需处理github事件
        var loadGithubEvents = function () {
            httpRequest({
                url: "/api/github/getEvents",
                success: function (res) {
                    console.log(res)
                },
                error: function () {

                },
                complete: function () {

                }
            });
        };
        loadGithubEvents();
    }
}