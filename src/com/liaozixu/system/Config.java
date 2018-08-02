package com.liaozixu.system;

import com.liaozixu.mysql.MysqlBaseContorManager;
import com.liaozixu.redis.RedisOperationManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Config {
    private Properties prop = null;

    public Properties conn() {
        if (prop != null) {
            return prop;
        }
        Properties prop = new Properties();
        InputStream is = Config.class.getClassLoader().getResourceAsStream("system.config.properties");
        try {
            prop.load(is);
            is.close();
        } catch (IOException e) {
            return null;
        }
        this.prop = prop;
        return prop;
    }

    public String get(String k) {
        Properties prop = conn();
        if (prop == null) {
            return null;
        }
        String getProperty = prop.getProperty(k);
        return getProperty == null ? getDb(k) : getProperty;
    }

    public String getDb(String k) {
        String redisKey = this.get("pre") + "system_config";
        HashMap<String, String> redisData = RedisOperationManager.getMap(redisKey);
        if (redisData != null) {
            return redisData.get(k);
        }
        MysqlBaseContorManager mysqlBaseContorManager = new MysqlBaseContorManager();
        mysqlBaseContorManager.setPrefix(true);
        mysqlBaseContorManager.setTableName("config");
        mysqlBaseContorManager.setTableKey(new String[] {
                "`key`",
                "`value`"
        });
        ArrayList<HashMap<String, String>> res = mysqlBaseContorManager.select();
        if (res == null || res.size() == 0) {
            return null;
        }
        HashMap<String, String> configMap = new HashMap<>();
        for (HashMap<String, String> map : res) {
            String key = map.get("key");
            String value = map.get("value");
            configMap.put(key, value);
        }
        int expires = Integer.parseInt(get("expires"));
        boolean saveStatus = RedisOperationManager.setMap(redisKey, configMap, expires);
        if (!saveStatus) {
            return null;
        } else {
            return configMap.get(k);
        }
    }
}
