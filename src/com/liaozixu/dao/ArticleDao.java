package com.liaozixu.dao;

import com.liaozixu.entity.Article;
import com.liaozixu.entity.Page;
import com.liaozixu.mysql.MysqlBaseContorManager;
import com.liaozixu.redis.RedisOperationManager;
import com.liaozixu.system.Config;
import com.liaozixu.util.DateUtils;
import com.liaozixu.util.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ArticleDao {
    private static Config config = new Config();
    private static String pre = config.get("pre");
    private static int expires = Integer.parseInt(config.get("expires"));
    private static int pageNum = Integer.parseInt(config.get("pageNum"));

    public static Page<Article> getList(int page, int type) {
        return getList(page, type, null);
    }

    public static Page<Article> getList(int page, int type, String categoryAlias) {
        if (page == 0) {
            return null;
        }
        String redisKey = "article_get_list_dao:page=" + page;
        if (type != 0) {
            redisKey = redisKey + ";type=" + type;
        }
        if (categoryAlias != null) {
            redisKey = redisKey + ";categoryAlias=" + categoryAlias;
        }
        ArrayList<HashMap<String, String>> row = GsonUtils.jsonToArraylistHashMapString(RedisOperationManager.getString(redisKey));
        int totalRow = 0;
        if (row == null) {
            int[] limit = new int[]{(page - 1) * pageNum, pageNum};
            MysqlBaseContorManager mysqlBaseContorManager = new MysqlBaseContorManager();
            mysqlBaseContorManager.setLimit(limit);
            mysqlBaseContorManager.setOrder(new String[]{"id", "DESC"});
            mysqlBaseContorManager.setPrefix(true);
            ArrayList<String[]> where = new ArrayList<>();
            if (type != 0) {
                where.add(new String[]{pre + "article.type", "=", String.valueOf(type)});
            }
            if (categoryAlias != null) {
                where.add(new String[]{pre + "category.alias", "=", categoryAlias});
            }
            mysqlBaseContorManager.setWhere(where);
            ArrayList<String[]> conjunctiveRelation = new ArrayList<>();
            conjunctiveRelation.add(new String[]{pre + "article.categoryID", pre + "category.id"});
            mysqlBaseContorManager.setConjunctiveRelation(conjunctiveRelation);
            mysqlBaseContorManager.setConjunctiveTable(new String[]{"article", "category"});
            mysqlBaseContorManager.setTableKey(new String[]{
                    pre + "article.id",
                    pre + "article.type",
                    pre + "article.title",
                    pre + "article.description",
                    pre + "article.postTime",
                    pre + "article.keywords",
                    pre + "article.alias",
                    pre + "article.categoryID",
                    pre + "article.original",
                    pre + "category.title",
                    pre + "category.alias",
                    pre + "category.keywords",
                    pre + "category.description",
                    pre + "category.type"
            });
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
        Page<Article> pageArticle = new Page<>();
        pageArticle.setNowPage(page);
        pageArticle.setPageNum(pageNum);
        pageArticle.setTotalRow(totalRow);
        pageArticle.setRow(toEntity(row));
        return pageArticle;
    }

    public static Article details(String categoryAlias, String articleAlias) {
        String redisKey = "article_get_list_dao:categoryAlias=" + categoryAlias + ";articleAlias=" + articleAlias;
        HashMap<String, String> item = RedisOperationManager.getMap(redisKey);
        if (item == null) {
            MysqlBaseContorManager mysqlBaseContorManager = new MysqlBaseContorManager();
            mysqlBaseContorManager.setPrefix(true);
            ArrayList<String[]> where = new ArrayList<>();
            where.add(new String[]{pre + "category.alias", "=", categoryAlias});
            where.add(new String[]{pre + "article.alias", "=", articleAlias});
            mysqlBaseContorManager.setWhere(where);
            ArrayList<String[]> conjunctiveRelation = new ArrayList<>();
            conjunctiveRelation.add(new String[]{pre + "article.categoryID", pre + "category.id"});
            mysqlBaseContorManager.setOrder(new String[]{"id", "DESC"});
            mysqlBaseContorManager.setConjunctiveRelation(conjunctiveRelation);
            mysqlBaseContorManager.setConjunctiveTable(new String[]{"article", "category"});
            mysqlBaseContorManager.setTableKey(new String[]{
                    pre + "article.id",
                    pre + "article.type",
                    pre + "article.title",
                    pre + "article.description",
                    pre + "article.contentText",
                    pre + "article.contentRaw",
                    pre + "article.ip",
                    pre + "article.postTime",
                    pre + "article.keywords",
                    pre + "article.alias",
                    pre + "article.categoryID",
                    pre + "article.original",
                    pre + "category.title",
                    pre + "category.alias",
                    pre + "category.keywords",
                    pre + "category.description",
                    pre + "category.type"
            });
            ArrayList<HashMap<String, String>> row = mysqlBaseContorManager.select();
            if (row == null || row.size() == 0) {
                return null;
            }
            item = row.get(0);
            if (!RedisOperationManager.setMap(redisKey, item, expires)) {
                return null;
            }
        }
        return toEntity(item);
    }

    private static List<Article> toEntity(ArrayList<HashMap<String, String>> row) {
        List<Article> list = new ArrayList<>();
        for (HashMap<String, String> item : row) {
            list.add(toEntity(item));
        }
        return list;
    }

    private static Article toEntity(HashMap<String, String> item) {
        Article article = new Article();
        if (item.get(pre + "article.id") != null) {
            article.setId(Integer.parseInt(item.get(pre + "article.id")));
        }
        if (item.get(pre + "article.type") != null) {
            article.setType(Integer.parseInt(item.get(pre + "article.type")));
        }
        if (item.get(pre + "article.title") != null) {
            article.setTitle(item.get(pre + "article.title"));
        }
        if (item.get(pre + "article.description") != null) {
            article.setDescription(item.get(pre + "article.description"));
        }
        if (item.get(pre + "article.contentText") != null) {
            article.setContentText(item.get(pre + "article.contentText"));
        }
        if (item.get(pre + "article.contentRaw") != null) {
            article.setContentRaw(item.get(pre + "article.contentRaw"));
        }
        if (item.get(pre + "article.ip") != null) {
            article.setIp(item.get(pre + "article.ip"));
        }
        if (item.get(pre + "article.postTime") != null) {
            article.setPostTime(DateUtils.strToDateLong(item.get(pre + "article.postTime")));
        }
        if (item.get(pre + "article.keywords") != null) {
            article.setKeywords(item.get(pre + "article.keywords"));
        }
        if (item.get(pre + "article.alias") != null) {
            article.setAlias(item.get(pre + "article.alias"));
        }
        if (item.get(pre + "article.categoryID") != null) {
            article.setCategoryID(Integer.parseInt(item.get(pre + "article.categoryID")));
        }
        if (item.get(pre + "article.original") != null) {
            article.setOriginal(item.get(pre + "article.original").equals("1"));
        }
        if (item.get(pre + "category.type") != null) {
            article.setCategoryType(Integer.parseInt(item.get(pre + "category.type")));
        }
        if (item.get(pre + "category.title") != null) {
            article.setCategoryTitle(item.get(pre + "category.title"));
        }
        if (item.get(pre + "category.alias") != null) {
            article.setCategoryAlias(item.get(pre + "category.alias"));
        }
        if (item.get(pre + "category.keywords") != null) {
            article.setCategoryKeywords(item.get(pre + "category.keywords"));
        }
        if (item.get(pre + "category.description") != null) {
            article.setCategoryDescription(item.get(pre + "category.description"));
        }
        return article;
    }
}
