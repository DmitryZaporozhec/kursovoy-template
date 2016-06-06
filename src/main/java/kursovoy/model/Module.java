package kursovoy.model;


import java.io.Serializable;
import java.util.List;

public class Module implements Serializable {
    private int id;
    private int courseId;
    private int displaOrder;
    private List<FroalaModel> content;

    public List<FroalaModel> getContent() {
        return content;
    }

    public void setContent(List<FroalaModel> content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getDisplaOrder() {
        return displaOrder;
    }

    public void setDisplaOrder(int displaOrder) {
        this.displaOrder = displaOrder;
    }
}
