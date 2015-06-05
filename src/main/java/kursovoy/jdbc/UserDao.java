package kursovoy.jdbc;

import kursovoy.model.User;
import kursovoy.model.constants.UserStatus;
import kursovoy.model.jdbc.ColumnModel;
import kursovoy.model.constants.DataConstants;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 5/22/15.
 */
@Service
public class UserDao extends AbstractDao<User> {

    @Override
    protected String getIdColumnName() {
        return "USER_ID";
    }

    @Override
    protected String getTableName() {
        return "USERS";
    }

    @Override
    protected List<ColumnModel> getColumns() {
        List<ColumnModel> columns = new ArrayList<ColumnModel>();
        columns.add(new ColumnModel("FIRST_NAME", DataConstants.STRING));
        columns.add(new ColumnModel("LAST_NAME", DataConstants.STRING));
        columns.add(new ColumnModel("AGE", DataConstants.INT));
        columns.add(new ColumnModel("LOGIN", DataConstants.STRING));
        columns.add(new ColumnModel("PASSWORD", DataConstants.STRING));
        columns.add(new ColumnModel("FAIL_LOGIN_COUNT", DataConstants.INT));
        columns.add(new ColumnModel("STATUS", DataConstants.INT));
        columns.add(new ColumnModel("LAST_LOGIN_DATE", DataConstants.TIMESTAMP));
        columns.add(new ColumnModel("SMS_CODE", DataConstants.STRING));
        columns.add(new ColumnModel("PHONE", DataConstants.STRING));
        return columns;
    }


    @Override
    protected User toEntity(ResultSet rs) throws Exception {
        User u = new User();
        u.setId(rs.getLong(getIdColumnName()));
        u.setFirstName(rs.getString("FIRST_NAME"));
        u.setLastName(rs.getString("LAST_NAME"));
        u.setAge(rs.getInt("AGE"));
        u.setPassword(rs.getString("PASSWORD"));
        u.setLogin(rs.getString("LOGIN"));
        u.setStatus(UserStatus.values()[rs.getInt("STATUS")]);
        u.setFailLoginCount(rs.getLong("FAIL_LOGIN_COUNT"));
        java.sql.Timestamp timestamp = rs.getTimestamp("LAST_LOGIN_DATE");
        if (timestamp != null)
            u.setLastLogin(new Date(timestamp.getTime()));
        u.setSmsCode(rs.getString("SMS_CODE"));
        u.setPhone(rs.getString("PHONE"));
        return u;
    }

    @Override
    protected void fillSaveUpdatePreparedStatement(PreparedStatement ps, User model, boolean isAdd) throws Exception {
        ps.setString(1, model.getFirstName());
        ps.setString(2, model.getLastName());
        ps.setInt(3, model.getAge());
        ps.setString(4, model.getLogin());
        ps.setString(5, model.getPassword());
        ps.setLong(6, model.getFailLoginCount());
        if (model.getStatus() == null) {
            ps.setInt(7, 0);
        } else {
            ps.setInt(7, model.getStatus().ordinal());
        }
        if (model.getLastLogin() == null) {
            ps.setNull(8, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(8, new Timestamp(model.getLastLogin().getTime()));
        }
        ps.setString(9, model.getSmsCode());
        ps.setString(10, model.getPhone());
        if (!isAdd) {
            ps.setLong(11, model.getId());
        }
    }
}
