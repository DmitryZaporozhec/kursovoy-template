package kursovoy.model.jdbc;

import kursovoy.model.constants.DataConstants;

/**
 * Created by zaporozhec on 5/22/15.
 */
public class ColumnModel {
    String name;
    DataConstants type;

    public ColumnModel(String name, DataConstants type) {
        this.name = name;
        this.type = type;
    }


    public ColumnModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataConstants getType() {
        return type;
    }

    public void setType(DataConstants type) {
        this.type = type;
    }
}
