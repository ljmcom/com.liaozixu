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
        var loadGithubEvents = function(){
            httpRequest({
                url: "https://api.github.com/users/liaozixu/events",
                success: function (res) {
                //    github
                    console.log(res)
                }
            });
        };
        loadGithubEvents();
    }
}