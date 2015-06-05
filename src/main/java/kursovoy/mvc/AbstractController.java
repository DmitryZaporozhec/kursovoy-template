package kursovoy.mvc;

import kursovoy.jdbc.UserDao;
import kursovoy.jdbc.UserIpHistoryDao;
import kursovoy.model.User;
import kursovoy.model.UserIpHistory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zaporozhec on 5/25/15.
 */

public abstract class AbstractController {
    @Autowired
    protected UserDao userDao;
    @Autowired
    protected UserIpHistoryDao userIpHistoryDao;


    protected void save(User u, UserIpHistory history) throws Exception {
        userDao.save(u);
        userIpHistoryDao.save(history);
    }
}
