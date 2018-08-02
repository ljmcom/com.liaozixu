package com.liaozixu.mysql;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlConnPoolManager {
    private static DataSource dataSource;

    private MysqlConnPoolManager() {
    }

    static {
        try {
            Properties dbProperties = new Properties();
            dbProperties.load(MysqlConnPoolManager.class.getClassLoader().getResourceAsStream("dbcp.properties"));
            dataSource = BasicDataSourceFactory.createDataSource(dbProperties);
            Connection conn = getConn();
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Connection getConn() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            return null;
        }
        return conn;
    }

    public static void closeConn(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException e) {
            return;
        }
    }
}
