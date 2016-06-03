package kursovoy.jdbc;

import kursovoy.constants.UserType;
import kursovoy.model.Course;
import kursovoy.model.Photo;
import kursovoy.model.User;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 5/6/15.
 */
public class JDBCPhotoUtil {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY?useUnicode=yes&characterEncoding=UTF-8";
    final static String userName = "root";
    final static String password = "root";

    public Photo save(Photo u) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);

            String sql;
            if (u.getId() == 0) {
                sql = "INSERT INTO PHOTO(FILE_NAME,CONTENT,FILE_TYPE) VALUES (?,?,?)";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, u.getFileName());
                stmt.setBlob(2, u.getContent());
                stmt.setString(3, u.getFileType());
                stmt.executeUpdate();
                ResultSet resultSet = stmt.getGeneratedKeys();
                {
                    while (resultSet.next()) {
                        u.setId(resultSet.getInt(1));
                        break;
                    }
                }
            } else {
                sql = "UPDATE PHOTO SET FILE_NAME =?, CONTENT=?,FILE_TYPE=? WHERE ID = ?";
                stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, u.getFileName());
                stmt.setBlob(2, u.getContent());
                stmt.setString(3, u.getFileType());
                stmt.setInt(4, u.getId());
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

            String sql = "DELETE FROM PHOTO WHERE ID=?";
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


    public Photo getPhoto(String name) {
        Photo p = new Photo();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
            String sql = "SELECT * FROM PHOTO WHERE FILE_NAME=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                p.setContent(rs.getBlob("CONTENT").getBinaryStream());
                p.setFileName(rs.getString("FILE_NAME"));
                p.setFileType(rs.getString("FILE_TYPE"));
                p.setId(rs.getInt("ID"));
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
            return p;
        }
    }

}
