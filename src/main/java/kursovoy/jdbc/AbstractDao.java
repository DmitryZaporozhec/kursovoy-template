package kursovoy.jdbc;

import kursovoy.model.AbstractModel;
import kursovoy.model.jdbc.ColumnModel;

import java.sql.*;
import java.util.*;

/**
 * Created by zaporozhec on 5/6/15.
 */
public abstract class AbstractDao<Entity extends AbstractModel> {
    final static String jdbcDriver = "com.mysql.jdbc.Driver";
    final static String connectionString = "jdbc:mysql://localhost/KURSOVOY";
    final static String userName = "root";
    final static String password = "root";


    protected abstract String getIdColumnName();

    protected abstract String getTableName();

    protected abstract List<ColumnModel> getColumns();

    protected abstract Entity toEntity(ResultSet rs) throws Exception;

    protected abstract void fillSaveUpdatePreparedStatement(PreparedStatement ps, Entity model, boolean isAdd) throws Exception;

    public List<Entity> getAllRecords() {
        return get(null, null);
    }

    public List<Entity> getRecordById(String userId) {
        return get(this.getIdColumnName(), userId);
    }

    private String createAddSQL() {
        String sql = "INSERT INTO %s (%s) VALUES (%s)";
        sql = String.format(sql, this.getTableName(), this.columnsAsString(), this.addQuestionsSigns());
        return sql;
    }


    private String createUpdateSQL() {
        String sql = "UPDATE %s SET %s WHERE %s = ?";
        StringBuilder sb = new StringBuilder();
        for (ColumnModel col : this.getColumns()) {
            sb.append(col.getName());
            sb.append("=");
            sb.append("?");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sql = String.format(sql, this.getTableName(), sb.toString(), this.getIdColumnName());
        return sql;
    }

    private String createGetSQL(String columnName, String columnValue) {
        String sql = null;
        if (columnName == null || columnValue == null) {
            sql = "SELECT * FROM " + this.getTableName();
        } else {
            sql = "SELECT * FROM " + this.getTableName() + " WHERE " + columnName + " = '" + columnValue + "' ";
        }
        return sql;
    }

    private String deleteSQL() {
        String sql = "DELETE FROM " + this.getTableName() + " WHERE " + this.getIdColumnName() + " = ?";
        return sql;
    }


    private String addQuestionsSigns() {
        StringBuilder columns = new StringBuilder();
        for (int i = 0; i < this.getColumns().size(); i++) {
            columns.append("?,");
        }
        columns.deleteCharAt(columns.length() - 1);
        return columns.toString();
    }


    private String columnsAsString() {
        StringBuilder columns = new StringBuilder();
        for (ColumnModel col : getColumns()) {
            columns.append(col.getName());
            columns.append(",");
        }
        columns.deleteCharAt(columns.length() - 1);
        return columns.toString();
    }

    public void save(Entity u) throws Exception{
        Connection conn = null;
        PreparedStatement stmt = null;
            conn = this.connect();
            String sql;
            if (u.getId() == 0) {
                sql = this.createAddSQL();
                stmt = conn.prepareStatement(sql);
                this.fillSaveUpdatePreparedStatement(stmt, u, true);
                stmt.executeUpdate();
            } else {
                sql = this.createUpdateSQL();
                stmt = conn.prepareStatement(sql);
                this.fillSaveUpdatePreparedStatement(stmt, u, false);
                stmt.executeUpdate();
            }
            stmt.close();
            conn.close();
    }

    public void delete(int userId) {
        Connection conn = this.connect();
        PreparedStatement stmt = null;
        try {
            String sql = this.deleteSQL();
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

    private Connection connect() {
        Connection conn = null;
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(connectionString, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public List<Entity> get(String attrName, String attrVal) {
        List<Entity> list = new ArrayList<Entity>();
        Connection conn = this.connect();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = this.createGetSQL(attrName, attrVal);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(toEntity(rs));
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
            return list;
        }
    }
}
