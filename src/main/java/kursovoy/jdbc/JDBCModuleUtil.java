package kursovoy.jdbc;

import kursovoy.model.Course;
import kursovoy.model.FroalaModel;
import kursovoy.model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCModuleUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = "root";
    final static String password = "root";

    public List<Module> getAllModules() {
        return getModule(null, null);
    }

    public Module getModule(String userId) {
        List<Module> courses = getModule("ID", userId);
        if (courses != null && courses.size() > 0)
            return courses.get(0);
        return null;
    }

    public Module saveModule(Module u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (u.getId() == 0) {
                sql = "INSERT INTO MODULE(COURSE_ID,DISPLAY_ORDER) VALUES (?,?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, u.getCourseId());
                stmt.setInt(2, u.getDisplaOrder());
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
                sql = "UPDATE MODULE SET COURSE_ID=?, DISPLAY_ORDER=?  WHERE ID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, u.getCourseId());
                stmt.setInt(2, u.getDisplaOrder());
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

            String sql = "DELETE FROM MODULE WHERE ID=?";
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

    public List<Module> getModule(String attrName, String attrVal) {
        List<Module> userList = new ArrayList<Module>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT ID, COURSE_ID, DISPLAY_ORDER FROM MODULE";
            if (attrName != null && attrVal != null) {
                sql = sql + " WHERE " + attrName + "='" + attrVal + "'";
            }
            sql += " ORDER BY DISPLAY_ORDER";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Module u = new Module();
                u.setId(rs.getInt("ID"));
                u.setCourseId(rs.getInt("COURSE_ID"));
                u.setDisplaOrder(rs.getInt("DISPLAY_ORDER"));
                List<FroalaModel> content = new JDBCContentUtil().get("COURSE_ID", String.valueOf(u.getId()));
                u.setContent(content);
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

    public List<Module> getMenu(int moduleId) {
        List<Module> userList = new ArrayList<Module>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM MODULE WHERE COURSE_ID IN (SELECT COURSE_ID FROM MODULE WHERE ID=" + moduleId + ")";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Module u = new Module();
                u.setId(rs.getInt("ID"));
                u.setCourseId(rs.getInt("COURSE_ID"));
                u.setDisplaOrder(rs.getInt("DISPLAY_ORDER"));
                List<FroalaModel> content = new JDBCContentUtil().get("COURSE_ID", String.valueOf(u.getId()));
                u.setContent(content);
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
