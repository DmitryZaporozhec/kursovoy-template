package kursovoy.jdbc;

import kursovoy.model.UserIpHistory;
import kursovoy.model.constants.LoginStatus;
import kursovoy.model.constants.UserStatus;
import kursovoy.model.jdbc.ColumnModel;
import kursovoy.model.constants.DataConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zaporozhec on 5/22/15.
 */
public class UserIpHistoryDao extends AbstractDao<UserIpHistory> {

    @Override
    protected String getIdColumnName() {
        return "USER_IP_HISTORY_ID";
    }

    @Override
    protected String getTableName() {
        return "USER_IP_HISTORY";
    }

    @Override
    protected List<ColumnModel> getColumns() {
        List<ColumnModel> columns = new ArrayList<ColumnModel>();
        columns.add(new ColumnModel("USER_ID", DataConstants.INT));
        columns.add(new ColumnModel("IP_ADDRESS", DataConstants.STRING));
        columns.add(new ColumnModel("LOGIN_STATUS", DataConstants.INT));
        columns.add(new ColumnModel("LOGIN_TIME", DataConstants.TIMESTAMP));
        columns.add(new ColumnModel("USER_AGENT", DataConstants.STRING));
        columns.add(new ColumnModel("LOCATION", DataConstants.STRING));
        return columns;
    }


    @Override
    protected UserIpHistory toEntity(ResultSet rs) throws Exception {
        UserIpHistory u = new UserIpHistory();
        u.setId(rs.getLong(getIdColumnName()));
        u.setUserId(rs.getLong("USER_ID"));
        u.setIpAddress(rs.getString("IP_ADDRESS"));
        u.setStatus(LoginStatus.values()[rs.getInt("LOGIN_STATUS")]);
        java.sql.Timestamp timestamp = rs.getTimestamp("LOGIN_TIME");
        if (timestamp != null)
            u.setLoginDate(new Date(timestamp.getTime()));
        u.setLocale(rs.getString("LOCATION"));
        u.setUserAgent(rs.getString("USER_AGENT"));
        return u;
    }

    @Override
    protected void fillSaveUpdatePreparedStatement(PreparedStatement ps, UserIpHistory model, boolean isAdd) throws Exception {
        ps.setLong(1, model.getUserId());
        ps.setString(2, model.getIpAddress());
        if (model.getStatus() == null) {
            ps.setInt(3, 0);
        } else {
            ps.setInt(3, model.getStatus().ordinal());
        }
        if (model.getLoginDate() == null) {
            ps.setNull(4, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(4, new Timestamp(model.getLoginDate().getTime()));
        }

        ps.setString(5,model.getUserAgent());
        ps.setString(6,model.getLocale());
        if (!isAdd) {
            ps.setLong(7, model.getId());
        }
    }
}
