package kursovoy.jdbc;

import kursovoy.constants.UserType;
import kursovoy.model.User;
import kursovoy.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.*;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCUserUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = CookieUtil.getUserName();
    final static String password = CookieUtil.getPassword();

    public List<User> getAllUsers() {
        return getUser(null, null);
    }

    public List<User> getUser(String userId) {
        return getUser("USER_ID", userId);
    }

    public User saveUser(User u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (u.getUserId() == 0) {
                sql = "INSERT INTO USERS(FIRST_NAME,LAST_NAME,AGE,LOGIN,PASSWORD,USER_TYPE, GROUP_ID) VALUES (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getFirstName());
                stmt.setString(2, u.getLastName());
                stmt.setInt(3, u.getAge());
                stmt.setString(4, u.getLogin());
                stmt.setString(5, u.getPassword());
                stmt.setString(6, u.getUserType().name());
                stmt.setInt(7, u.getGroupId());
                stmt.executeUpdate();
                ResultSet resultSet = stmt.getGeneratedKeys();
                {
                    while (resultSet.next()) {
                        u.setUserId(resultSet.getInt(1));
                        break;
                    }
                }
                //add user
            } else {
                sql = "UPDATE USERS SET FIRST_NAME =?, LAST_NAME=?, AGE=?, LOGIN=?, PASSWORD=?,USER_TYPE=?,GROUP_ID=? WHERE USER_ID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getFirstName());
                stmt.setString(2, u.getLastName());
                stmt.setInt(3, u.getAge());
                stmt.setString(4, u.getLogin());
                stmt.setString(5, u.getPassword());
                stmt.setString(6, u.getUserType().name());
                stmt.setInt(7, u.getGroupId());
                stmt.setInt(8, u.getUserId());
                stmt.executeUpdate();
                //update user
            }
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
        }
        return u;
    }

    public void delete(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql = "DELETE FROM USERS WHERE USER_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
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
        }
    }

    public List<User> getUser(String attrName, String attrVal) {
        List<User> userList = new ArrayList<User>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT u.*, g.NAME as 'grpName' FROM USERS u JOIN KURSOVOY.GROUP g ON g.ID = u.GROUP_ID";
            if (attrName != null && attrVal != null) {
                sql = sql + " WHERE " + attrName + "='" + attrVal + "'";
            }
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("USER_ID"));
                u.setFirstName(rs.getString("FIRST_NAME"));
                u.setLastName(rs.getString("LAST_NAME"));
                u.setAge(rs.getInt("AGE"));
                u.setLogin(rs.getString("LOGIN"));
                u.setPassword(rs.getString("PASSWORD"));
                u.setGroupId(rs.getInt("GROUP_ID"));
                u.setGroupName(rs.getString("grpName"));
                if (rs.getString("USER_TYPE") != null)
                    u.setUserType(UserType.valueOf(rs.getString("USER_TYPE")));
                userList.add(u);
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
