package kursovoy.jdbc;

import kursovoy.model.Discipline;
import kursovoy.model.News;
import kursovoy.utils.CookieUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCNewsUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = CookieUtil.getUserName();;
    final static String password = CookieUtil.getPassword();

    public List<News> getAllDisciplines() {
        return getNews(null, null);
    }

    public News getNews(String disciplineId) {
        return getNews("ID", disciplineId).get(0);
    }

    public void saveNews(News d) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (d.getId() == 0) {
                sql = "INSERT INTO NEWS(CREATE_DATE,CAPTION,TEXT) VALUES (?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
                stmt.setString(2, d.getCaption());
                stmt.setString(3, d.getText());
                stmt.executeUpdate();
                //add user
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
    }

    public void delete(int disciplineId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            String sql = "DELETE FROM NEWS WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, disciplineId);
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

    public List<News> getNews(String attrName, String attrVal) {
        List<News> userList = new ArrayList<News>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql;
            if (attrName != null && attrVal != null) {
                sql = "SELECT * FROM NEWS WHERE " + attrName + "='" + attrVal + "'";
            } else {
                sql = "SELECT * FROM NEWS";
            }
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                News u = new News();
                u.setId(rs.getInt("ID"));
                u.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                u.setCaption(rs.getString("CAPTION"));
                u.setText(rs.getString("TEXT"));
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

    public List<News> getTop10News() {
        List<News> userList = new ArrayList<News>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM NEWS ORDER BY CREATE_DATE DESC LIMIT 10";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                News u = new News();
                u.setId(rs.getInt("ID"));
                u.setCreateDate(rs.getTimestamp("CREATE_DATE"));
                u.setCaption(rs.getString("CAPTION"));
                u.setText(rs.getString("TEXT"));
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
