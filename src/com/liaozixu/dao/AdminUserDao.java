package com.liaozixu.dao;

import com.google.gson.Gson;
import com.liaozixu.entity.AdminUser;
import com.liaozixu.mysql.MysqlBaseContorManager;
import com.liaozixu.redis.RedisOperationManager;
import com.liaozixu.system.Config;
import com.liaozixu.util.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminUserDao {
    private static Config config = new Config();
    private static int expires = Integer.parseInt(config.get("expires"));

    public static AdminUser username(String username) {
        String redisKey = "admin_user_username:username=" + username;
        HashMap<String, String> item = RedisOperationManager.getMap(redisKey);
        if (item == null) {
            MysqlBaseContorManager mysqlBaseContorManager = new MysqlBaseContorManager();
            mysqlBaseContorManager.setPrefix(true);
            mysqlBaseContorManager.setTableName("admin_user");
            ArrayList<String[]> where = new ArrayList<>();
            where.add(new String[]{"username", "=", username});
            mysqlBaseContorManager.setWhere(where);
            ArrayList<HashMap<String, String>> select = mysqlBaseContorManager.select();
            if (select == null || select.size() == 0) {
                return null;
            }
            item = select.get(0);
            if (!RedisOperationManager.setMap(redisKey, item, expires)) {
                return null;
            }
        }
        return toEntity(item);
    }

    public static AdminUser toEntity(HashMap<String, String> item) {
        AdminUser adminUser = new AdminUser();
        adminUser.setPermission(GsonUtils.jsonToArraylistString(item.get("permission")).toArray(new String[]{}));
        adminUser.setSecurityCode(item.get("securityCode"));
        adminUser.setId(Integer.parseInt(item.get("id")));
        adminUser.setUsername(item.get("username"));
        adminUser.setPassword(item.get("password"));
        adminUser.setSecret(item.get("secret"));
        return adminUser;
    }


    public static HashMap<String, String> toMapString(AdminUser adminUser) {
        HashMap<String, String> item = new HashMap<>();
        item.put("permission", GsonUtils.toJson(adminUser.getPermission()));
        item.put("securityCode", adminUser.getSecurityCode());
        item.put("id", String.valueOf(adminUser.getId()));
        item.put("username", adminUser.getUsername());
        item.put("password", adminUser.getPassword());
        item.put("secret", adminUser.getSecret());
        return item;
    }

}
