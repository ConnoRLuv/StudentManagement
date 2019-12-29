package dal.daoimpl;

import JDBCUtils.Jdbc;
import dal.Entity.Teacher;
import dal.dao.TeacherDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherDaoImpl implements TeacherDao {
    @Override
    public boolean certifyTeacher(String Id, String Password) {
        try {
            String sql = "select Teacher_password from teacher where Teacher_no = " + Id;
            ResultSet rs = Jdbc.runQuery(sql);
            if (rs.next())
                return Password.equals(rs.getString("Teacher_password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<Teacher> selectTeacher(String sql) {
        try {
            List<Teacher> teachers = new ArrayList<Teacher>();
            ResultSet rs = Jdbc.runQuery(sql);
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacher_no(rs.getString("Teacher_no"));
                teacher.setTeacher_name(rs.getString("Teacher_name"));
                teacher.setTeacher_sex(rs.getString("Teacher_sex"));
                teacher.setTeacher_birth(rs.getString("Teacher_birth"));
                teacher.setTeacher_location(rs.getString("Teacher_location"));
                teacher.setTeacher_join(rs.getString("Teacher_join"));
                teachers.add(teacher);
            }
            Jdbc.realeaseAll();
            return teachers;
        } catch (Exception e) {
            Logger.getLogger(AdministratorDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Teacher> selectAllTeacher() {
        String sql = "select * from teacher";
        return selectTeacher(sql);
    }


    @Override
    public List<Teacher> selectTeacherByTno(String Tno) {
        String sql = "select * from teacher where Tno = " + Tno;
        return selectTeacher(sql);
    }

    public String selectTno(String sql) {
        String teacher = null;
        try {
            ResultSet rs = Jdbc.runQuery(sql);
            while (rs.next()) {
                teacher = rs.getString("Teacher_no");
            }
            Jdbc.realeaseAll();
            return teacher;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Vector<Vector<Object>> selectCourse(String SID) {
        String sql = "select course.Course_no,Course_name,Course_credit," +
                "COUNT(studentcourse.Student_no)" +
                "from course,teachercourse,studentcourse " +
                "where teachercourse.Teacher_no = '" + SID + "' " +
                "and course.Course_no = teachercourse.Course_no " +
                "and studentcourse.Course_no = teachercourse.Course_no " +
                "group by  course.Course_no";
        Vector<Vector<Object>> courses = new Vector<>();
        try {
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Vector<Object> course = new Vector<>();
                course.add(0, resultSet.getString("Course_no"));
                course.add(1, resultSet.getString("Course_name"));
                course.add(2, resultSet.getString("Course_credit"));
                course.add(3, resultSet.getString("COUNT(studentcourse.Student_no)"));
                courses.add(course);
            }

            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Vector<Vector<Object>> selectCourseGrades(String CID) {
        String sql = "select student.Student_no,Student_name,Class_name,Course_grades " +
                "from student,studentcourse,class " +
                "where studentcourse.Course_no =" + CID + " " +
                "and student.Student_no = studentcourse.Student_no " +
                "and student.Class_no = class.Class_no ";
        Vector<Vector<Object>> coursesgrades = new Vector<>();
        try {
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Vector<Object> coursegrade = new Vector<>();
                coursegrade.add(0, resultSet.getString("student.Student_no"));
                coursegrade.add(1, resultSet.getString("Student_name"));
                coursegrade.add(2, resultSet.getString("Class_name"));
                coursegrade.add(3, resultSet.getString("Course_grades"));
                coursesgrades.add(coursegrade);
            }

            return coursesgrades;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateGrades(String sql) {
        try {
            Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//    @Override
//    public boolean updateTeacher(Teacher teacher) {
//        return false;
//    }
//
//    @Override
//    public boolean updateGrade(Double Grade) {
//        return false;
//    }
}
