package kursovoy.mvc;

import kursovoy.jdbc.UserDao;
import kursovoy.jdbc.UserIpHistoryDao;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;

/**
 * Created by zaporozhec on 5/25/15.
 */

public abstract class AbstractController {
    protected  final static UserDao userDao = new UserDao();
    protected  final static UserIpHistoryDao userIpHistoryDao = new UserIpHistoryDao();


    protected void save(User u, UserIpHistory history) throws Exception{
        userDao.save(u);
        userIpHistoryDao.save(history);
    }
}
