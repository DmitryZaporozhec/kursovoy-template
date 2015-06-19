package kursovoy.core.intf;

import kursovoy.model.User;
import kursovoy.model.constants.Risk;

/**
 * Created by zaporozhec on 6/19/15.
 */
public interface RiskCalculator {
    public Risk process(User u) throws Exception;
}
