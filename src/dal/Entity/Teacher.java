package dal.Entity;

public class Teacher {
    private String Teacher_no;
    private String Teacher_name;
    private String Teacher_sex;
    private String Teacher_birth;
    private String Teacher_location;
    private String Teacher_join;

    public void setTeacher_no(String Teacher_no) {
        this.Teacher_no = Teacher_no;
    }

    public String getTeacher_no() {
        return this.Teacher_no;
    }

    public void setTeacher_name(String Teacher_name) {
        this.Teacher_name = Teacher_name;
    }

    public String getTeacher_name() {
        return this.Teacher_name;
    }

    public void setTeacher_sex(String Teacher_sex) {
        this.Teacher_sex = Teacher_sex;
    }

    public String getTeacher_sex() {
        return this.Teacher_sex;
    }

    public void setTeacher_birth(String Teacher_birth) {
        this.Teacher_birth = Teacher_birth;
    }

    public String getTeacher_birth() {
        return this.Teacher_birth;
    }

    public void setTeacher_join(String Teacher_join) {
        this.Teacher_join = Teacher_join;
    }

    public String getTeacher_join() {
        return Teacher_join;
    }

    public void setTeacher_location(String Teacher_location) {
        this.Teacher_location = Teacher_location;
    }

    public String getTeacher_location() {
        return this.Teacher_location;
    }
}
