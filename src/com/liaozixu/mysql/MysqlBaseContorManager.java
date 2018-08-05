package com.liaozixu.mysql;

import com.liaozixu.system.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MysqlBaseContorManager {
    private int[] limit = null;
    private String[] order = null;
    private boolean prefix = false;
    private HashMap insert = null;
    private String tableName = null;
    private String[] tableKey = null;
    private ArrayList<String[]> where = null;
    private String[] conjunctiveTable = null;
    private ArrayList<String[]> conjunctiveRelation = null;

    public void setOrder(String[] order) {
        this.order = order;
    }

    public void setLimit(int[] limit) {
        this.limit = limit;
    }

    public void setWhere(ArrayList<String[]> where) {
        this.where = where;
    }

    public void setInsert(HashMap insert) {
        this.insert = insert;
    }

    public void setTableKey(String[] tableKey) {
        this.tableKey = tableKey;
    }

    public void setTableName(String tableName) {
        if (prefix) {
            this.tableName = new Config().get("pre");
        } else {
            this.tableName = "";
        }
        this.tableName += tableName;
    }

    public void setConjunctiveRelation(ArrayList<String[]> conjunctiveRelation) {
        this.conjunctiveRelation = conjunctiveRelation;
    }

    public void setConjunctiveTable(String[] conjunctiveTable) {
        this.conjunctiveTable = conjunctiveTable;
    }

    public void setPrefix(boolean prefix) {
        this.prefix = prefix;
    }

    public void reset() {
        limit = null;
        order = null;
        prefix = false;
        insert = null;
        tableName = null;
        tableKey = null;
        where = null;
        conjunctiveTable = null;
        conjunctiveRelation = null;
    }

    private String tableKeyFormat() {
        StringBuilder sql = new StringBuilder();
        if (tableKey != null) {
            for (int i = 0; i < tableKey.length; i++) {
                sql.append(" ").append(tableKey[i]).append(" ");
                if (i != (tableKey.length - 1)) {
                    sql.append(",");
                } else {
                    sql.append(" ");
                }
            }
            return sql.toString();
        }
        return sql.toString();
    }


    private String whereFormat() {
        StringBuilder sql = new StringBuilder();
        if (where != null) {
            for (int i = 0; i < where.size(); i++) {
                Object[] val = where.get(i);
                if (i == 0) {
                    sql.append(" WHERE ").append(val[0]).append(" ").append(val[1]).append(" ? ");
                } else {
                    sql.append(" AND ").append(val[0]).append(" ").append(val[1]).append(" ? ");
                }
            }
        }
        return sql.toString();
    }

    //        数量配置
    private String limitFormat() {
        StringBuilder sql = new StringBuilder();
        if (limit != null) {
            sql.append("LIMIT ").append(limit[0]).append(" , ").append(limit[1]).append(" ");
        }
        return sql.toString();
    }

    //排序配置
    private String orderFormat() {
        StringBuilder sql = new StringBuilder();
        if (order != null) {
            sql.append("ORDER BY ").append(order[0]).append(" ").append(order[1]).append(" ");
        }
        return sql.toString();
    }

    private void wherePreparedStatementSetObject(int Subscript, PreparedStatement ps) throws SQLException {
        if (where != null) {
            for (int i = 0; i < where.size(); i++) {
                Object[] val = where.get(i);
                ps.setObject(i + 1 + Subscript, val[2]);
            }
        }
    }

    private void wherePreparedStatementSetObject(PreparedStatement ps) throws SQLException {
        wherePreparedStatementSetObject(0, ps);
    }

    public ArrayList<HashMap<String, String>> select() {
        return select(false, false);
    }

    public int count() {

        ArrayList<HashMap<String, String>> list = select(true, false);
        if (list == null || list.size() == 0) {
            return 0;
        }
        HashMap<String, String> selectMap = list.get(0);
        StringBuilder key = new StringBuilder().append("COUNT( ");
//        if (this.tableKey != null) {
//            key.append(tableKeyFormat());
//        } else {
        key.append("* ");
//        }
        key.append(")");
        if (selectMap.get(key.toString()) != null) {
            return Integer.valueOf(selectMap.get(key.toString()));
        } else {
            return Integer.valueOf(selectMap.get("." + key.toString()));
        }
    }

    public String sum() {

        ArrayList<HashMap<String, String>> list = select(false, true);

        if (list == null || list.size() == 0) {
            return "0";
        }
        HashMap<String, String> selectMap = list.get(0);
        StringBuilder key = new StringBuilder().append("SUM( ");
        if (this.tableKey != null) {
            key.append(tableKeyFormat());
        } else {
            key.append("* ");
        }
        key.append(")");
        String sum = selectMap.get(key.toString());
        return sum.equals("null") ? "0" : sum;
    }


    public ArrayList<HashMap<String, String>> select(boolean count, boolean sum) {
        Connection conn = MysqlConnPoolManager.getConn();
        if (conn == null) {
            return null;
        }
        StringBuilder sql = new StringBuilder().append("SELECT ");
        if (count) {
            sql.append("COUNT( ");
        } else if (sum) {
            sql.append("SUM( ");
        }
        if (this.tableKey != null && !count && !sum) {
            sql.append(tableKeyFormat());
        } else {
            sql.append("* ");
        }
        if (count || sum) {
            sql.append(") ");
        }
        if (conjunctiveTable == null) {
            if (tableName == null) {
                return null;
            }
            sql.append("FROM ").append(tableName).append(" ");
        } else {
            sql.append("FROM ");
            for (int i = 0; i < conjunctiveTable.length; i++) {
                if (i != 0) {
                    sql.append(", ");
                }
                if (prefix) {
                    sql.append(new Config().get("pre")).append(conjunctiveTable[i]);
                } else {
                    sql.append(conjunctiveTable[i]);
                }
                sql.append(" ");
            }
        }

        if (where != null) {
            sql.append(whereFormat());
        }
        if (conjunctiveRelation != null) {
            for (int i = 0; i < conjunctiveRelation.size(); i++) {
                if (i == 0 && where == null) {
                    sql.append("WHERE ");
                } else {
                    sql.append("AND ");
                }
                sql.append(conjunctiveRelation.get(i)[0]).append(" = ").append(conjunctiveRelation.get(i)[1]).append(" ");
            }
        }
        if (order != null && !count) {
            sql.append(orderFormat());
        }
        if (limit != null && !count) {
            sql.append(limitFormat());
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            wherePreparedStatementSetObject(ps);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            ArrayList<HashMap<String, String>> query = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> list = new HashMap<>();
                for (int i = 1; i <= col; i++) {
                    if (rs.getObject(i) == null || String.valueOf(rs.getObject(i)).equals("null")) {
                        continue;
                    }
                    String keyNamePre = "";
                    if (conjunctiveTable != null) {
                        keyNamePre = rsmd.getTableName(i) + ".";
                    }
                    if (keyNamePre.equals("null.")) {
                        keyNamePre = "";
                    }
                    list.put(keyNamePre + rsmd.getColumnName(i), String.valueOf(rs.getObject(i)));
                }
                query.add(list);
            }
            rs.close();
            ps.close();
            return query;
        } catch (Exception e) {
            return null;
        } finally {
            MysqlConnPoolManager.closeConn(conn);
        }
    }

    public boolean insert() {
        if (insert == null || insert.size() == 0) {
            return false;
        }
        Connection conn = MysqlConnPoolManager.getConn();
        if (conn == null) {
            return false;
        }
        StringBuilder sql = new StringBuilder().append("INSERT INTO `").append(tableName).append("`");
        StringBuilder insertKey = new StringBuilder();
        StringBuilder insertVal = new StringBuilder();
        for (Object key : insert.keySet()) {
            insertKey.append(" `").append(key).append("` ,");
            insertVal.append(" ? ,");
        }
        insertKey.setLength(insertKey.length() - 1);
        insertVal.setLength(insertVal.length() - 1);
        sql.append("(").append(insertKey).append(")").append(" VALUES ").append("(").append(insertVal).append(")");
        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int i = 1;
            for (Object key : insert.keySet()) {
                ps.setObject(i, insert.get(key));
                i++;
            }
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            MysqlConnPoolManager.closeConn(conn);
        }
    }

    public boolean delect() {
        if (where == null || where.size() == 0) {
            return false;
        }
        Connection conn = MysqlConnPoolManager.getConn();
        if (conn == null) {
            return false;
        }
        StringBuilder sql = new StringBuilder().append("DELETE FROM `").append(tableName).append("` ").append(whereFormat());
        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            wherePreparedStatementSetObject(ps);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            MysqlConnPoolManager.closeConn(conn);
        }
    }

    public boolean update() {
        if (where == null || where.size() == 0) {
            return false;
        }
        Connection conn = MysqlConnPoolManager.getConn();
        if (conn == null) {
            return false;
        }
        StringBuilder set = new StringBuilder();
        for (Object key : insert.keySet()) {
            set.append(" `").append(key).append("` = ? ,");
        }
        set.setLength(set.length() - 1);
        StringBuilder sql = new StringBuilder().append("UPDATE `").append(tableName).append("` SET ").append(set).append(whereFormat());
        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int keySetInt = 1;
            for (Object key : insert.keySet()) {
                ps.setObject(keySetInt, insert.get(key));
                keySetInt = keySetInt + 1;
            }
            wherePreparedStatementSetObject(keySetInt - 1, ps);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            MysqlConnPoolManager.closeConn(conn);
        }
    }


}
