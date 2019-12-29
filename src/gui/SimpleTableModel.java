package gui;

import dal.Entity.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SimpleTableModel<T> extends AbstractTableModel {
    protected List<String> cols;
    protected List<T> rows;
    protected String type;

    public SimpleTableModel(List<String> cols, List<T> rows, String type) {
        this.cols = cols;
        this.rows = rows;
        this.type = type;
    }

    public void setCols(List<String> cols) {
        this.cols = cols;
    }

    public List<String> getCols() {
        return cols;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    @Override
    public int getRowCount() {
        return rows.size();

    }

    @Override
    public int getColumnCount() {
        return cols.size();
    }

    @Override
    public String getColumnName(int colum) {
        return cols.get(colum);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (this.type) {
            case "Student":
                Student stu = (Student) rows.get(rowIndex);
                if (columnIndex == 0) {
                    return stu.getSno();
                } else if (columnIndex == 1) {
                    return stu.getSname();
                } else if (columnIndex == 2) {
                    return stu.getSsex();
                } else if (columnIndex == 3) {
                    return stu.getSlocation();
                } else if (columnIndex == 4) {
                    return stu.getSbirth();
                } else if (columnIndex == 5) {
                    return stu.getSjoin();
                } else if (columnIndex == 6) {
                    return stu.getSMajor();
                } else if (columnIndex == 7) {
                    return stu.getSclass();
                } else if (columnIndex == 8) {
                    return stu.getSCollege();
                } else {
                    return stu.getSgraduate();
                }
            case "Course": {
                Course course = (Course) rows.get(rowIndex);
                if (columnIndex == 0) {
                    return course.getCourse_ID();
                } else if (columnIndex == 1) {
                    return course.getCourse_name();
                } else if (columnIndex == 2) {
                    return course.getCourse_credit();
                } else if (columnIndex == 3) {
                    return course.getCourse_teacher();
                } else if (columnIndex == 4) {
                    return course.getCourse_time();
                } else {
                    return course.getCourse_grades();
                }
            }
            case "AddOrDeleteCourse": {
                AddCourse addcourse = (AddCourse) rows.get(rowIndex);
                if (columnIndex == 0) {
                    return addcourse.getCourse_ID();
                } else if (columnIndex == 1) {
                    return addcourse.getCourse_name();
                } else if (columnIndex == 2) {
                    return addcourse.getCourse_credit();
                } else if (columnIndex == 3) {
                    return addcourse.getCourse_teacher();
                } else {
                    return addcourse.getCollege_name();
                }
            }
            case "Activity": {
                Activity activity = (Activity) rows.get(rowIndex);
                if (columnIndex == 0) {
                    return activity.getActivity_no();
                } else if (columnIndex == 1) {
                    return activity.getActivity_time();
                } else {
                    return activity.getActivity_content();
                }
            }
            case "StudentCourse": {
                StudentAndCourse studentAndCourse = (StudentAndCourse) rows.get(rowIndex);
                if (columnIndex == 0) {
                    return studentAndCourse.getSno();
                } else if (columnIndex == 1) {
                    return studentAndCourse.getSname();
                } else if (columnIndex == 2) {
                    return studentAndCourse.getCourse_ID();
                } else if (columnIndex == 3) {
                    return studentAndCourse.getCourse_name();
                } else if (columnIndex == 4) {
                    return studentAndCourse.getCourse_credit();
                } else if (columnIndex == 5) {
                    return studentAndCourse.getCourse_teacher();
                } else {
                    return studentAndCourse.getCourse_grades();
                }
            }

            default:
                return null;
        }
    }
}
