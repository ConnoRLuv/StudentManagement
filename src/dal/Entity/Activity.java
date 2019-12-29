package dal.Entity;

public class Activity {
    private String SID;
    private String Activity_no;
    private String Activity_time;
    private String Activity_content;

    public void setSID(String SID) {
        this.SID = SID;
    }

    public String getSID() {
        return SID;
    }

    public void setActivity_no(String Activity_no) {
        this.Activity_no = Activity_no;
    }

    public String getActivity_no() {
        return Activity_no;
    }

    public void setActivity_time(String Activity_time) {
        this.Activity_time = Activity_time;
    }

    public String getActivity_time() {
        return Activity_time;
    }

    public void setActivity_content(String Activity_content) {
        this.Activity_content = Activity_content;
    }

    public String getActivity_content() {
        return Activity_content;
    }
}
