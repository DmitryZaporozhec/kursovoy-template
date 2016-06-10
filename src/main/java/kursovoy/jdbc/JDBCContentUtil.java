package kursovoy.jdbc;

import kursovoy.constants.ContentType;
import kursovoy.model.FroalaModel;
import kursovoy.utils.CookieUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCContentUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = CookieUtil.getUserName();
    final static String password = CookieUtil.getPassword();

    public List<FroalaModel> getAll() {
        return get(null, null);
    }

    public FroalaModel get(String userId) {
        return get("ID", userId).get(0);
    }

    public FroalaModel save(FroalaModel u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (u.getId() == 0) {
                sql = "INSERT INTO CONTENT_STORAGE(COURSE_ID,TYPE,CONTENT,NAME) VALUES (?,?,?,?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, u.getModuleId());
                stmt.setString(2, u.getType().name());
                stmt.setString(3, u.getBody());
                stmt.setString(4, u.getContentName());
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
                sql = "UPDATE CONTENT_STORAGE SET COURSE_ID =?, TYPE=?, CONTENT=?, NAME=?  WHERE ID = ?";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, u.getModuleId());
                stmt.setString(2, u.getType().name());
                stmt.setString(3, u.getBody());
                stmt.setString(4, u.getContentName());
                stmt.setInt(5, u.getId());
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

            String sql = "DELETE FROM CONTENT_STORAGE WHERE ID=?";
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

    public List<FroalaModel> get(String attrName, String attrVal) {
        List<FroalaModel> userList = new ArrayList<FroalaModel>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql;
            if (attrName != null && attrVal != null) {
                sql = "SELECT ID,COURSE_ID,TYPE,NAME FROM CONTENT_STORAGE WHERE " + attrName + "='" + attrVal + "'";
            } else {
                sql = "SELECT ID,COURSE_ID,TYPE,NAME FROM CONTENT_STORAGE";
            }
            sql += " ORDER BY TYPE";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                FroalaModel u = new FroalaModel();
                u.setId(rs.getInt("ID"));
                u.setModuleId(rs.getInt("COURSE_ID"));
                u.setType(ContentType.valueOf(rs.getString("TYPE")));
                u.setContentName(rs.getString("NAME"));
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

    public FroalaModel getBody(int id) {
        FroalaModel model = new FroalaModel();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT CONTENT FROM CONTENT_STORAGE WHERE ID=" + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model.setBody(rs.getString(1));
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
            return model;
        }
    }

}
