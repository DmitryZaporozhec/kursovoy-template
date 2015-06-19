package kursovoy.core.impl;

import kursovoy.core.intf.RiskCalculator;
import kursovoy.model.User;
import kursovoy.model.constants.Risk;
import org.springframework.stereotype.Service;

/**
 * Created by zaporozhec on 6/19/15.
 */
@Service
public class RiskCalculatorImpl implements RiskCalculator {

    @Override
    public Risk process(User u) throws Exception {
        long count = u.getFailLoginCount();
        if (count < 2) {
            return Risk.NO;
        } else if (count < 4) {
            return Risk.LOW;
        } else if (count < 6) {
            return Risk.MEDIUM;
        } else {
            return Risk.HIGH;
        }

    }
}
