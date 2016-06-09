package kursovoy.jdbc;

import kursovoy.model.Course;
import kursovoy.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCGroupUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = "root";
    final static String password = "root";

    public List<Group> getAll() {
        return get(null, null);
    }

    public Group get(String id) {
        List<Group> courses = get("ID", id);
        if (courses != null && courses.size() > 0)
            return courses.get(0);
        return null;
    }

    public Group save(Group u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (u.getId() == 0) {
                sql = "INSERT INTO KURSOVOY.GROUP (NAME,DESCRIPTION) VALUES (?,?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, u.getName());
                stmt.setString(2, u.getDescription());
                stmt.executeUpdate();
                ResultSet resultSet = stmt.getGeneratedKeys();
                {
                    while (resultSet.next()) {
                        u.setId(resultSet.getInt(1));
                        break;
                    }
                }
                //add user
            } else {
                sql = "UPDATE KURSOVOY.GROUP  SET NAME =?, DESCRIPTION=? WHERE ID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, u.getName());
                stmt.setString(2, u.getDescription());
                stmt.setInt(3, u.getId());
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

    public void delete(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql = "DELETE FROM KURSOVOY.GROUP  WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
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

    public List<Group> get(String attrName, String attrVal) {
        List<Group> userList = new ArrayList<Group>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM KURSOVOY.GROUP  ";
            if (attrName != null && attrVal != null) {
                sql = sql + " WHERE " + attrName + "='" + attrVal + "'";
            }
            sql += " ORDER BY NAME";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Group u = new Group();
                u.setId(rs.getInt("ID"));
                u.setName(rs.getString("NAME"));
                u.setDescription(rs.getString("DESCRIPTION"));
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
