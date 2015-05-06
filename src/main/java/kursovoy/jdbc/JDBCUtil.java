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
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(connectionString, userName, password);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM USERS";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                userList.add(new User(rs.getInt("USER_ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getInt("AGE")));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
        return userList;
    }
}
