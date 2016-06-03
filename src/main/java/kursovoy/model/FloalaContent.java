package kursovoy.model;

import kursovoy.constants.ContentType;

import java.io.Serializable;

/**
 * Created by zaporozhec on 6/3/16.
 */
public class FloalaContent implements Serializable {
    int id;
    String body;
    ContentType type;
    String contentName;
    int courseId;

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


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }
}
