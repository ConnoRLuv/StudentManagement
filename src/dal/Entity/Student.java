package dal.Entity;

public class Student {
    private String Sno;
    private String Sname;
    private String Ssex;
    private String Slocation;
    private String Sbirth;
    private String Sjoin;
    private String Spassword;
    private String SCollege;
    private String Smajor;
    private String Sclass;
    private String Sgraduate;

    public String getSgraduate() {
        return Sgraduate;
    }

    public void setSgraduate(String sgraduate) {
        Sgraduate = sgraduate;
    }

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

    public void setSpassword(String Spassword) {
        this.Spassword = Spassword;
    }

    public String getSpassword() {
        return this.Spassword;
    }

    public void setSsex(String Ssex) {
        this.Ssex = Ssex;
    }

    public String getSsex() {
        return this.Ssex;
    }

    public void setSjoin(String Sjoin) {
        this.Sjoin = Sjoin;
    }

    public String getSjoin() {
        return this.Sjoin;
    }

    public void setSbirth(String Sbirth) {
        this.Sbirth = Sbirth;
    }

    public String getSbirth() {
        return this.Sbirth;
    }

    public void setSlocation(String Slocation) {
        this.Slocation = Slocation;
    }

    public String getSlocation() {
        return this.Slocation;
    }

    public String getSMajor() {
        return Smajor;
    }

    public void setSMajor(String Smajor) {
        this.Smajor = Smajor;
    }

    public String getSCollege() {
        return SCollege;
    }

    public void setSCollege(String SCollege) {
        this.SCollege = SCollege;
    }

    public String getSclass() {
        return Sclass;
    }

    public void setSclass(String sclass) {
        Sclass = sclass;
    }
}
