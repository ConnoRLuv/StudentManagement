package dal.Entity;

public class StudentAndCourse {
    private String Sno;
    private String Sname;
    private String Course_ID;
    private String Course_name;
    private String Course_credit;
    private String Course_grades;
    private String Course_teacher;

    public void setSno(String Sno) {
        this.Sno = Sno;
    }

    public String getSno() {
        return this.Sno;
    }

    public void setSname(String Sname) {
        this.Sname = Sname;
    }

    public String getSname() {
        return this.Sname;
    }

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

    public void setCourse_grades(String Course_grades) {
        this.Course_grades = Course_grades;
    }

    public String getCourse_grades() {
        return Course_grades;
    }

    public void setCourse_teacher(String Course_teacher) {
        this.Course_teacher = Course_teacher;
    }

    public String getCourse_teacher() {
        return Course_teacher;
    }
}
