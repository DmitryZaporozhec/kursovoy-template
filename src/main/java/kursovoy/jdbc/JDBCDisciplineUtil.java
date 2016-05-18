package kursovoy.jdbc;

import kursovoy.constants.UserType;
import kursovoy.model.Discipline;
import kursovoy.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCDisciplineUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = "root";
    final static String password = "root";

    public List<Discipline> getAllDisciplines() {
        return getDiscipline(null, null);
    }

    public Discipline getDiscipline(String disciplineId) {
        return getDiscipline("DISCIPLINE_ID", disciplineId).get(0);
    }

    public void saveUser(Discipline d) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (d.getDisciplineId() == 0) {
                sql = "INSERT INTO DISCIPLINE(NAME,DESCRIPTION) VALUES (?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, d.getName());
                stmt.setString(2, d.getDescription());
                stmt.executeUpdate();
                //add user
            } else {
                sql = "UPDATE DISCIPLINE SET NAME =?, DESCRIPTION=? WHERE DISCIPLINE_ID = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, d.getName());
                stmt.setString(2, d.getDescription());
                stmt.setInt(3, d.getDisciplineId());
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
    }

    public void delete(int disciplineId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            String sql = "DELETE FROM DISCIPLINE WHERE DISCIPLINE_ID=?";
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

    public List<Discipline> getDiscipline(String attrName, String attrVal) {
        List<Discipline> userList = new ArrayList<Discipline>();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            stmt = conn.createStatement();
            String sql;
            if (attrName != null && attrVal != null) {
                sql = "SELECT * FROM DISCIPLINE WHERE " + attrName + "='" + attrVal + "'";
            } else {
                sql = "SELECT * FROM DISCIPLINE";
            }
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Discipline u = new Discipline();
                u.setDisciplineId(rs.getInt("DISCIPLINE_ID"));
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
