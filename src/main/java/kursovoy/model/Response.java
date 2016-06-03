package kursovoy.model;

import java.io.Serializable;

/**
 * Created by zaporozhec on 6/3/16.
 */
public class Response implements Serializable {
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
