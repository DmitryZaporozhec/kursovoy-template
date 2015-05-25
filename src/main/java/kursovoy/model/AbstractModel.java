package kursovoy.model;

/**
 * Created by zaporozhec on 5/22/15.
 */
public abstract class AbstractModel {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
