package kursovoy.utils;

import java.util.List;
import java.util.Random;

/**
 * Created by zaporozhec on 6/10/16.
 */
public class Catalogue {
    private Random randomGenerator;

    public Catalogue() {
        randomGenerator = new Random();
    }

    public String anyItem(List<String> catalogue) {
        int index = randomGenerator.nextInt(catalogue.size());
        String item = catalogue.get(index);
        return item;
    }
}