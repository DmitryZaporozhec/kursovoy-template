package kursovoy.model;

import kursovoy.constants.ContentType;

import java.io.Serializable;

/**
 * Created by zaporozhec on 6/3/16.
 */
public class FroalaModel implements Serializable {
    int id;
    String body;
    ContentType type;
    String contentName;
    int moduleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }


    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
}
