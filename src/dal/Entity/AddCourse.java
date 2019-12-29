package dal.Entity;

public class AddCourse {
    private String Course_ID;
    private String Course_name;
    private String Course_credit;
    private String Course_teacher;
    private String College_name;

    public void setCourse_ID(String Course_ID) {
        this.Course_ID = Course_ID;
    }

    public String getCourse_ID() {
        return Course_ID;
    }

    public void setCourse_name(String Course_name) {
        this.Course_name = Course_name;
    }

    public String getCourse_name() {
        return Course_name;
    }

    public void setCourse_credit(String Course_credit) {
        this.Course_credit = Course_credit;
    }

    public String getCourse_credit() {
        return Course_credit;
    }


    public void setCourse_teacher(String Course_teacher) {
        this.Course_teacher = Course_teacher;
    }

    public String getCourse_teacher() {
        return Course_teacher;
    }

    public void setCollege_name(String College_name) {
        this.College_name = College_name;
    }

    public String getCollege_name() {
        return College_name;
    }
}
