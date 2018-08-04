package com.liaozixu.servlet.api.qcloud;

import com.liaozixu.system.Config;
import com.liaozixu.util.CommonUtils;
import com.liaozixu.util.GatewayUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.tmt.v20180321.TmtClient;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "ApiQcloudTextTranslateServlet", urlPatterns = {"/api/qcloud/textTranslate"})
public class ApiQcloudTextTranslateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Config config = new Config();
        @SuppressWarnings("unchecked")
        HashMap<String, String> postMap = (HashMap<String, String>) request.getAttribute("postMap");
        if (postMap.get("sourceText") == null || postMap.get("target") == null) {
            GatewayUtils.showJson(false, 100005, null, response);
            return;
        }
        String[] targetList = new String[]{
                "zh", "en", "jp", "kr", "de",
                "fr", "es", "it", "tr", "ru",
                "pt", "vi", "id", "ms", "th"
        };
        if (!CommonUtils.isArrayString(postMap.get("target"), targetList)) {
            GatewayUtils.showJson(false, 100006, null, "不支持的语言类型", response);
            return;
        }
        Credential cred = new Credential(config.get("qcloudSecretID"), config.get("qcloudSecretKey"));
        TmtClient tmtClient = new TmtClient(cred,"ap-guangzhou");
        TextTranslateRequest textTranslateRequest = new TextTranslateRequest();
        textTranslateRequest.setSourceText(postMap.get("sourceText"));
        textTranslateRequest.setSource("auto");
        textTranslateRequest.setTarget(postMap.get("target"));
        textTranslateRequest.setProjectId(0);
        try {
            TextTranslateResponse textTranslateResponse = tmtClient.TextTranslate(textTranslateRequest);
            GatewayUtils.showJson(true,0,textTranslateResponse,response);
        } catch (Exception e) {
            GatewayUtils.showJson(false, 100006, null, e.getMessage(), response);
        }
    }
}
