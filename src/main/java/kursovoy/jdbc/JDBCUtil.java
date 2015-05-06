package kursovoy.jdbc;

import kursovoy.model.User;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.*;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY";
    final static String userName = "root";
    final static String password = "root";

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM USERS";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                userList.add(new User(rs.getInt("USER_ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getInt("AGE")));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return userList;
        }
    }


    public List<User> getUser(String userId) {
        List<User> userList = new ArrayList<User>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM USERS WHERE USER_ID='" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                userList.add(new User(rs.getInt("USER_ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getInt("AGE")));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return userList;
        }
    }
}
