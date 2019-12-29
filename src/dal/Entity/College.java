package dal.Entity;

public class College {
    private String collegeName;
    private String collegeNo;

    public String getCollegeNo() {
        return collegeNo;
    }

    public void setCollegeNo(String collegeNo) {
        this.collegeNo = collegeNo;
    }

    public void setCollege(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeName() {
        return collegeName;
    }
}
