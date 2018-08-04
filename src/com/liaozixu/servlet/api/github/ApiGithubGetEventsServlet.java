package com.liaozixu.servlet.api.github;

import com.google.gson.internal.LinkedTreeMap;
import com.liaozixu.redis.RedisOperationManager;
import com.liaozixu.system.Config;
import com.liaozixu.util.GatewayUtils;
import com.liaozixu.util.GsonUtils;
import com.liaozixu.util.HttpClientUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "ApiGithubGetEventsServlet", urlPatterns = {"/api/github/getEvents"})
public class ApiGithubGetEventsServlet extends HttpServlet {
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Config config = new Config();
        String redisKey = "github_events";
        int expires = Integer.parseInt(config.get("expires"));
        ArrayList row = GsonUtils.jsonToArrayListpObject(RedisOperationManager.getString(redisKey));
        if (row == null) {
            String url = "https://api.github.com/users/" + config.get("githubUserName") + "/events";
            String responseData = HttpClientUtils.sendGet(url);
            if (responseData == null) {
                GatewayUtils.showJson(false, 100006, null, response);
                return;
            }
            if (!responseData.substring(0, 1).equals("[")) {
                GatewayUtils.showJson(false, 100006, null, response);
                return;
            }
            row = new ArrayList<>();
            ArrayList eventsList = GsonUtils.jsonToArrayListpObject(responseData);
            for (Object items : eventsList) {
                LinkedTreeMap item = (LinkedTreeMap) items;
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("createdAt", item.get("created_at"));
                temp.put("type", item.get("type"));
                temp.put("repo", item.get("repo"));
                if (item.get("type").equals("PushEvent")) {
                    temp.put("message", ((ArrayList<LinkedTreeMap>) ((LinkedTreeMap) item.get("payload")).get("commits")).get(0).get("message"));
                } else {
                    temp.put("message", ((LinkedTreeMap) item.get("payload")).get("description"));
                }
                row.add(temp);
            }
//            redis缓存
            if (!RedisOperationManager.setString(redisKey, GsonUtils.toJson(row), expires)) {
                GatewayUtils.showJson(false, 100002, null, response);
                return;
            }
        }
        GatewayUtils.showJson(true, 0, row, response);
    }
}
