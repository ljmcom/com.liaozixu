package com.liaozixu.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class GsonUtils {
    /***
     * 生成json
     * @param list 传入任意类型
     * @return json字符串
     */
    public static String toJson(Object list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        try {
            String toJson = gson.toJson(list);
            return toJson;
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 解析json
     * @param json 字符串
     * @return HashMap<String , String>
     */
    public static HashMap<String, String> jsonToHashMapString(String json) {
        if (json == null) return null;
        Gson gson = new Gson();
        try {
            Type jsonType = new com.google.gson.reflect.TypeToken<HashMap<String, String>>() {
            }.getType();
            return gson.fromJson(json, jsonType);
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 解析json
     */
    public static ArrayList jsonToArrayListpObject(String json) {
        if (json == null) return null;
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, new com.google.gson.reflect.TypeToken<ArrayList>() {
            }.getType());
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 解析json
     * @param json 字符串
     * @return HashMap<String , Object>
     */
    public static HashMap<String, Object> jsonToHashMapObject(String json) {
        if (json == null || json.equals("")) return null;
        Gson gson = new Gson();
        try {
            Type jsonType = new com.google.gson.reflect.TypeToken<HashMap<String, Object>>() {
            }.getType();
            return gson.fromJson(json, jsonType);
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 解析json
     * @param json 字符串
     * @return ArrayList<Hashmap>
     */
    public static ArrayList<HashMap<String, String>> jsonToArraylistHashMapString(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        try {
            Type jsonType = new com.google.gson.reflect.TypeToken<ArrayList<HashMap<String, String>>>() {
            }.getType();
            return gson.fromJson(json, jsonType);
        } catch (Exception e) {
            return null;
        }
    }

    /***
     * 解析json
     * @param json 字符串
     * @return ArrayList<String>
     */
    public static ArrayList<String> jsonToArraylistString(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        try {
            Type jsonType = new com.google.gson.reflect.TypeToken<ArrayList<String>>() {
            }.getType();
            return gson.fromJson(json, jsonType);
        } catch (Exception e) {
            return null;
        }
    }


}
