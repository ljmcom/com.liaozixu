package com.liaozixu.dao;

import com.liaozixu.entity.Category;
import com.liaozixu.entity.Page;
import com.liaozixu.mysql.MysqlBaseContorManager;
import com.liaozixu.redis.RedisOperationManager;
import com.liaozixu.system.Config;
import com.liaozixu.util.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CategoryDao {
    private static Config config = new Config();
    private static int expires = Integer.parseInt(config.get("expires"));
    private static int pageNum = Integer.parseInt(config.get("pageNum"));

    public static Page<Category> getList(int page, int type) {
        if (page == 0) {
            return null;
        }
        String redisKey = "category_get_list_dao:page=" + page;
        if (type != 0) {
            redisKey = redisKey + ";type=" + type;
        }
        ArrayList<HashMap<String, String>> row = GsonUtils.jsonToArraylistHashMapString(RedisOperationManager.getString(redisKey));
        int totalRow;
        if (row == null) {
            int[] limit = new int[]{(page - 1) * pageNum, pageNum};
            MysqlBaseContorManager mysqlBaseContorManager = new MysqlBaseContorManager();
            mysqlBaseContorManager.setLimit(limit);
            mysqlBaseContorManager.setOrder(new String[]{"id", "DESC"});
            mysqlBaseContorManager.setPrefix(true);
            ArrayList<String[]> where = new ArrayList<>();
            if (type != 0) {
                where.add(new String[]{"type", "=", String.valueOf(type)});
            }
            mysqlBaseContorManager.setWhere(where);
            mysqlBaseContorManager.setTableName("category");
            row = mysqlBaseContorManager.select();
            if (row == null) {
                return null;
            }
            RedisOperationManager.setString(redisKey, GsonUtils.toJson(row), expires);
            totalRow = mysqlBaseContorManager.count();
            RedisOperationManager.setString(redisKey + "_count", String.valueOf(totalRow), expires);
        } else {
            totalRow = Integer.parseInt(Objects.requireNonNull(RedisOperationManager.getString(redisKey + "_count")));
        }
        Page<Category> pageArticle = new Page<>();
        pageArticle.setNowPage(page);
        pageArticle.setPageNum(pageNum);
        pageArticle.setTotalRow(totalRow);
        pageArticle.setRow(toEntity(row));
        return pageArticle;
    }

    public static boolean save(Category category){
        MysqlBaseContorManager mysqlBaseContorManager = new MysqlBaseContorManager();
        mysqlBaseContorManager.setPrefix(true);
        mysqlBaseContorManager.setTableName("category");
        HashMap<String,String> insert = new HashMap<>();
        if(category.getAlias() != null){
            insert.put("alias",category.getAlias());
        }
        if(category.getDescription() != null){
            insert.put("description",category.getDescription());
        }
        if(category.getKeywords() != null){
            insert.put("keywords",category.getKeywords());
        }
        if(category.getTitle() != null){
            insert.put("title",category.getTitle());
        }
        if(category.getType() != 0){
            insert.put("type",String.valueOf(category.getType()));
        }
        mysqlBaseContorManager.setInsert(insert);
        if(category.getId() != 0){
            ArrayList<String[]> where = new ArrayList<>();
            where.add(new String[]{"id","=",String.valueOf(category.getId())});
            mysqlBaseContorManager.setWhere(where);
            if(!mysqlBaseContorManager.update()){
                return false;
            }else{
                RedisOperationManager.batchDel("category_get_list_dao");
                return true;
            }
        }else{
            if (!mysqlBaseContorManager.insert()) {
                return false;
            } else {
                RedisOperationManager.batchDel("category_get_list_dao");
                return true;
            }
        }
    }

    private static List<Category> toEntity(ArrayList<HashMap<String, String>> row) {
        List<Category> list = new ArrayList<>();
        for (HashMap<String, String> item : row) {
            list.add(toEntity(item));
        }
        return list;
    }

    private static Category toEntity(HashMap<String, String> item){
        Category category = new Category();
        category.setAlias(item.get("alias"));
        category.setDescription(item.get("description"));
        category.setId(Integer.parseInt(item.get("id")));
        category.setKeywords(item.get("keywords"));
        category.setTitle(item.get("title"));
        category.setType(Integer.parseInt(item.get("type")));
        return category;
    }
}
