package kursovoy.jdbc;

import kursovoy.model.Course;
import kursovoy.model.Group;
import kursovoy.utils.CookieUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCUserCourseUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = CookieUtil.getUserName();
    final static String password = CookieUtil.getPassword();

    public void save(int userId, int courseId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            String sql;
            sql = "INSERT INTO USER_COURSE (USER_ID,COURSE_ID) VALUES (?,?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);
            stmt.setInt(2, courseId);
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

    public void delete(int userId, int courseId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql = "DELETE FROM USER_COURSE  WHERE USER_ID=? AND COURSE_ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, courseId);
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

    public List<Course> get(int userId) {
        List<Course> userList = new ArrayList<Course>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT c.*, d.NAME as 'disciplineName' FROM COURSE c JOIN USER_COURSE uc ON uc.COURSE_ID = c.ID" +
                    " JOIN  DISCIPLINE d ON c.DISCIPLINE_ID =d.DISCIPLINE_ID" +
                    " WHERE uc.USER_ID=" + userId;
            sql += " ORDER BY c.NAME";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Course u = new Course();
                u.setUserId(rs.getInt("USER_ID"));
                u.setId(rs.getInt("ID"));
                u.setDisciplineId(rs.getInt("DISCIPLINE_ID"));
                u.setName(rs.getString("NAME"));
                u.setDescription(rs.getString("DESCRIPTION"));
                u.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                u.setDisciplineName(rs.getString("disciplineName"));
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
