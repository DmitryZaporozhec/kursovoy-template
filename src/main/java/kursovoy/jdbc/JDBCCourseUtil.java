package kursovoy.jdbc;

import kursovoy.constants.UserType;
import kursovoy.model.Course;
import kursovoy.model.User;
import kursovoy.utils.CookieUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCCourseUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = CookieUtil.getUserName();
    final static String password = CookieUtil.getPassword();

    public List<Course> getAllCoursed() {
        return getCourse(null, null);
    }

    public Course getCourse(String userId) {
        List<Course> courses = getCourse("ID", userId);
        if (courses != null && courses.size() > 0)
            return courses.get(0);
        return null;
    }

    public Course saveCourse(Course u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (u.getId() == 0) {
                sql = "INSERT INTO COURSE(DISCIPLINE_ID,USER_ID,NAME,DESCRIPTION,CREATE_DATE) VALUES (?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, u.getDisciplineId());
                stmt.setInt(2, u.getUserId());
                stmt.setString(3, u.getName());
                stmt.setString(4, u.getDescription());
                if (u.getCreateDate() == null) {
                    u.setCreateDate(new Date());
                }
                stmt.setTimestamp(5, new java.sql.Timestamp(u.getCreateDate().getTime()));
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
                sql = "UPDATE COURSE SET DISCIPLINE_ID =?, USER_ID=?, NAME=?, DESCRIPTION=?, CREATE_DATE=? WHERE ID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, u.getDisciplineId());
                stmt.setInt(2, u.getUserId());
                stmt.setString(3, u.getName());
                stmt.setString(4, u.getDescription());
                if (u.getCreateDate() == null) {
                    u.setCreateDate(new Date());
                }
                stmt.setTimestamp(5, new java.sql.Timestamp(u.getCreateDate().getTime()));
                stmt.setInt(6, u.getId());
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

            String sql = "DELETE FROM COURSE WHERE ID=?";
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

    public List<Course> getCourse(String attrName, String attrVal) {
        List<Course> userList = new ArrayList<Course>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT\n" +
                    "  c.USER_ID,\n" +
                    "  c.ID,\n" +
                    "  c.DISCIPLINE_ID,\n" +
                    "  c.NAME,\n" +
                    "  c.DESCRIPTION,\n" +
                    "  c.CREATE_DATE,\n" +
                    "  CONCAT(u.FIRST_NAME, ' ', u.LAST_NAME) AS 'userName',\n" +
                    "  d.NAME as 'disciplineName'\n" +
                    "FROM COURSE c JOIN USERS u ON c.USER_ID = u.USER_ID\n" +
                    "  JOIN DISCIPLINE d ON d.DISCIPLINE_ID = c.DISCIPLINE_ID";
            if (attrName != null && attrVal != null) {
                sql = sql + " WHERE " + attrName + "='" + attrVal + "'";
            }
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
                u.setUserDisplayName(rs.getString("userName"));

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
