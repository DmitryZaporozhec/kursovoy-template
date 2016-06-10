package kursovoy.jdbc;

import kursovoy.model.FroalaModel;
import kursovoy.model.Module;
import kursovoy.utils.CookieUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCTemplateUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = CookieUtil.getUserName();
    final static String password = CookieUtil.getPassword();


    public String getTemplate(int moduleId) {
        String temp = "";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT TEMPLATE FROM TEMPLATE WHERE ID=" + moduleId;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                temp = rs.getString(1);
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
            return temp;
        }
    }
}
