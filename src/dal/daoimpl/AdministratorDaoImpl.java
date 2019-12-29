package dal.daoimpl;

import JDBCUtils.Jdbc;
import dal.Entity.Administrator;
import dal.Entity.College;
import dal.dao.AdministratorDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministratorDaoImpl implements AdministratorDao {
    public boolean certifyAdministrator(String Id, String Password) {
        try {
            String sql = "select Admin_password from administrator where Admin_no = " + Id;
            ResultSet rs = Jdbc.runQuery(sql);
            if (rs.next())
                return Password.equals(rs.getString("Admin_password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public List<Administrator> selectAdministrator(String sql) {
        try {
            List<Administrator> administrators = new ArrayList<>();
            ResultSet rs = Jdbc.runQuery(sql);
            while (rs.next()) {
                Administrator administrator = new Administrator();
                administrator.setAdministrator_no(rs.getString("Admin_no"));
                administrator.setAdministrator_name(rs.getString("Admin_name"));
                administrator.setAdministrator_sex(rs.getString("Admin_sex"));
                administrator.setAdministrator_birth(rs.getString("Admin_birth"));
                administrator.setAdministrator_location(rs.getString("Admin_location"));
                administrator.setAdministrator_join(rs.getString("Admin_join"));
                administrators.add(administrator);
            }
            Jdbc.realeaseAll();
            return administrators;
        } catch (Exception e) {
            Logger.getLogger(AdministratorDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<College> selectColleges(String sql) {
        try {
            List<College> colleges = new ArrayList<>();
            ResultSet rs = Jdbc.runQuery(sql);
            while (rs.next()) {
                College college = new College();
                college.setCollege(rs.getString("College_name"));
                colleges.add(college);
            }
            Jdbc.realeaseAll();
            return colleges;
        } catch (Exception e) {
            Logger.getLogger(AdministratorDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Vector<Vector<String>> selectStudentCourse(String sql) {
        try {
            Vector<Vector<String>> studentAndCourses = new Vector<>();

            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Vector<String> studentAndCourse = new Vector<>();
                studentAndCourse.add(0, resultSet.getString("Student_no"));
                studentAndCourse.add(1, resultSet.getString("Student_name"));
                studentAndCourse.add(2, resultSet.getString("Class_name"));
                studentAndCourse.add(3, resultSet.getString("Major_name"));
                studentAndCourse.add(4, resultSet.getString("College_name"));
                if (resultSet.getString("Student_credit") == null) {
                    studentAndCourse.add(5, "0");
                } else {
                    studentAndCourse.add(5, resultSet.getString("Student_credit"));
                }
                studentAndCourse.add(6, resultSet.getString("Student_grades"));
                studentAndCourse.add(7, resultSet.getString("Student_graduate"));
                studentAndCourses.add(studentAndCourse);
            }
            Jdbc.realeaseAll();
            return studentAndCourses;
        } catch (SQLException ex) {
            /**在学生具体功能实现类中填写错误日志*/
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String selectAdministratorByAno(String Ano) {
        String sql = "select Admin_name from administrator where Admin_no = '" + Ano + "'";
        try {
            ResultSet resultSet = Jdbc.runQuery(sql);
            resultSet.next();
            return resultSet.getString("Admin_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //下面两个不要用了
    @Override
    public List<Administrator> selectAllAdministrator() {
        String sql = "select * from administrator";
        return selectAdministrator(sql);
    }


}