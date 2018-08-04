package com.liaozixu.util;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;


public class GatewayUtils {
    public static String json(boolean state, int errCode, Object data, String errDes) {
        HashMap<String, Object> constJson = new HashMap<>();
        constJson.put("resultCode", state);
        if (state) {
            constJson.put("errCode", errCode);
            constJson.put("errCodeDes", "SUCCESS");
        } else {
            constJson.put("errCode", errCode);
            if (data != null) {
                constJson.put("errCodeDes", data);
            } else {
                if (errDes != null) {
                    constJson.put("errCodeDes", errDes);
                } else {
                    constJson.put("errCodeDes", errCodeDes(errCode));
                }
            }
        }
        if (data == null) {
            constJson.put("data", new HashMap<>());
        } else {
            constJson.put("data", data);
        }

        return GsonUtils.toJson(constJson);
    }

    public static String json(boolean state, int errCode, Object data) {
        return json(state, errCode, data, null);
    }

    public static void showJson(boolean state, int errCode, Object data, String errDes, HttpServletResponse response)
            throws IOException {
        ServletUtils.responseString(response, json(state, errCode, data, errDes));
    }

    public static void showJson(boolean state, int errCode, Object data, HttpServletResponse response)
            throws IOException {
        showJson(state, errCode, data, null, response);
    }

    public static String errCodeDes(int errCode) {
        String errText;
        switch (errCode) {
            case 100000:
                errText = "错误示例";
                break;
            case 100001:
                errText = "上送数据不合规范";
                break;
            case 100002:
                errText = "系统繁忙";
                break;
            case 100003:
                errText = "请使用post请求";
                break;
            case 100004:
                errText = "请使用json格式请求数据";
                break;
            case 100005:
                errText = "缺少必要参数";
                break;
            case 100006:
                errText = "上游网关有误";
                break;
            case 100007:
                errText = "认证失败";
                break;
            default:
                errText = String.valueOf(errCode);
        }
        return errText;
    }
}
