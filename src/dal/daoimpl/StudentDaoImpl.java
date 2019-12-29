package dal.daoimpl;

import JDBCUtils.Jdbc;
import dal.Entity.*;
import dal.dao.StudentDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDaoImpl implements StudentDao {
    @Override
    public boolean certifyStudent(String Sno, String Spassword) {
        String sql = "select Student_no from Student where Student_no='" + Sno +
                "' and Student_password='" + Spassword + "'";
        boolean isCertifyStudent = false;
        try {
            ResultSet result = Jdbc.runQuery(sql);
            if (result != null) {
                isCertifyStudent = result.next();
                Jdbc.realeaseAll();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isCertifyStudent;
    }

    @Override
    public List<Student> selectStudent(String sql) {
        try {
            List<Student> students = new ArrayList<Student>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Student student = new Student();
                student.setSno(resultSet.getString("Student_no"));
                student.setSname(resultSet.getString("Student_name"));
                student.setSsex(resultSet.getString("Student_sex"));
                student.setSlocation(resultSet.getString("Student_location"));
                student.setSbirth(resultSet.getString("Student_birth"));
                student.setSjoin(resultSet.getString("Student_join"));
                student.setSCollege(resultSet.getString("College_name"));
                student.setSMajor(resultSet.getString("Major_name"));
                student.setSclass(resultSet.getString("Class_name"));
                student.setSgraduate(resultSet.getString("Student_graduate"));
                students.add(student);
            }
            Jdbc.realeaseAll();
            return students;
        } catch (SQLException ex) {
            Logger.getLogger(AdministratorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Student> selectStudent1(String sql) {
        try {
            List<Student> students = new ArrayList<Student>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Student student = new Student();
                student.setSno(resultSet.getString("Student_no"));
                student.setSname(resultSet.getString("Student_name"));
                student.setSsex(resultSet.getString("Student_sex"));
                student.setSlocation(resultSet.getString("Student_location"));
                student.setSbirth(resultSet.getString("Student_birth"));
                student.setSjoin(resultSet.getString("Student_join"));
                student.setSCollege(resultSet.getString("College_name"));
                student.setSMajor(resultSet.getString("Major_name"));
                student.setSclass(resultSet.getString("Class_name"));
                student.setSgraduate(resultSet.getString("Student_graduate"));
                student.setSpassword(resultSet.getString("Student_password"));
                students.add(student);
            }
            Jdbc.realeaseAll();
            return students;
        } catch (SQLException ex) {
            Logger.getLogger(AdministratorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean addStudent(Student student) {
        try {
            String sql = "insert into student " +
                    "values(" + student.getSno() +
                    ", " + student.getSname() +
                    ", " + student.getSsex() +
                    ", " + student.getSlocation() +
                    ", " + student.getSbirth() +
                    ", " + student.getSjoin() +
                    ", " + student.getSpassword() +
                    ");";
            Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param sql
     * @return List<Course>
     * <p>
     * 在这里自己返回查询到的三类课程：
     * 1.已选课程所有有分数的课程
     * 2.已选课程但考试成绩不及格的
     * 3.已选但还没有登记分数的课程
     */
    public List<Course> selectCourse(String sql) {
        try {
            List<Course> courses = new ArrayList<Course>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourse_ID(resultSet.getString("Course_no"));
                course.setCourse_name(resultSet.getString("Course_name"));
                course.setCourse_credit(resultSet.getString("Course_credit"));
                course.setCourse_teacher(resultSet.getString("Teacher_name"));
                course.setCourse_time(resultSet.getString("Course_time"));
                course.setCourse_grades(resultSet.getString("Course_grades"));
                courses.add(course);
            }
            Jdbc.realeaseAll();
            return courses;
        } catch (SQLException ex) {
            /**在学生具体功能实现类中填写错误日志*/
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @param sql
     * @return List<Course>
     * 说一说自己实现选课的逻辑思路
     * 首先把数据库中所有课程查询到页面
     * 然后用鼠标选中想要选的课程
     * 最后点击添加按钮就把选课记录更新到数据库中了
     */
    public Vector<Vector<String>> WaitingSelectOrSelectCourse(String sql) {
        try {
            Vector<Vector<String>> addcourses = new Vector<>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Vector<String> addcourse = new Vector<>();
                addcourse.add(0, resultSet.getString("Course_no"));
                addcourse.add(1, resultSet.getString("Course_name"));
                addcourse.add(2, resultSet.getString("Course_credit"));
                addcourse.add(3, resultSet.getString("Teacher_name"));
                addcourse.add(4, resultSet.getString("College_name"));
                addcourse.add(5, resultSet.getString("Course_time"));
                addcourses.add(addcourse);
            }
            Jdbc.realeaseAll();
            return addcourses;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int AddCourse(String sql) {
        /**
         * 向学生表中加入选课记录
         *     需要向数据表中添加(学号,课程号)
         *     成绩目前暂时为空,等待老师添加
         */
        try {
            int AffectedRows;
            AffectedRows = Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
            return AffectedRows;
        } catch (SQLException ex) {
            return 0;
        }
    }

    public String isReadyExam(String sql) {
        try {
            ResultSet resultSet = Jdbc.runQuery(sql);
            String courseName = null;
            while (resultSet.next()) {
                courseName = resultSet.getString("Course_name");
            }
            return courseName;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int DeleteCourse(String sql) {
        /**
         * 向学生表中删除选课记录
         *     删除所匹配(学号,课程号)的选课记录
         */
        try {
            int AffectedRows;
            AffectedRows = Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
            return AffectedRows;
        } catch (SQLException ex) {
            return 0;
        }
    }


    public int AddActivity(String sql) {
        try {
            /**
             *  因为级联更新语句比较复杂，自己在此作了简化
             *  因为要一方面要更新活动表，一方面要更新学生参与活动表
             *  则自己在此次连续两次调用该更新语句来简化处理。。。
             */
            int AffectedRows;
            AffectedRows = Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
            return AffectedRows;
        } catch (SQLException ex) {
            return 0;
        }
    }

    public Vector<Vector<String>> selectActivity(String sql) {
        try {
            Vector<Vector<String>> activities = new Vector<>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Vector<String> activity = new Vector<>();
                activity.add(0, resultSet.getString("Activity_no"));
                activity.add(1, resultSet.getString("Activity_time"));
                activity.add(2, resultSet.getString("Activity_content"));
                activities.add(activity);
            }
            Jdbc.realeaseAll();
            return activities;
        } catch (SQLException ex) {
            /**在学生具体功能实现类中填写错误日志*/
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Vector<String> selectClassName(String sql) {
        try {
            Vector<String> classes = new Vector<>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                classes.add(resultSet.getString("Class_name"));
            }
            Jdbc.realeaseAll();
            return classes;
        } catch (SQLException ex) {
            /**在学生具体功能实现类中填写错误日志*/
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Vector<String> selectMajorName(String sql) {
        try {
            Vector<String> majors = new Vector<>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                majors.add(resultSet.getString("Major_name"));
            }
            Jdbc.realeaseAll();
            return majors;
        } catch (SQLException ex) {
            /**在学生具体功能实现类中填写错误日志*/
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Vector<String> selectCollegeName(String sql) {
        try {
            Vector<String> colleges = new Vector<>();
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {

                colleges.add(resultSet.getString("College_name"));

            }
            Jdbc.realeaseAll();
            return colleges;
        } catch (SQLException ex) {
            /**在学生具体功能实现类中填写错误日志*/
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public College getCollegeByName(String collegeName) {
        try {
            College college = new College();
            String sql = "select * from college where College_name = '" + collegeName + "'";
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                college.setCollegeNo(resultSet.getString("College_no"));
                college.setCollege(resultSet.getString("College_name"));
            }
            return college;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getMajorByName(String selectedItem) {
        try {
            String Major_no = "";
            String sql = "select Major_no from major where Major_name = '" + selectedItem + "'";
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Major_no = resultSet.getString("Major_no");
            }
            return Major_no;
        } catch (SQLException e) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public String getClassByName(String class_name) {
        try {
            String Class_no = "";
            String sql = "select Class_no from class " +
                    "where Class_name = '" + class_name + "' ";
//                    "and Major_no = '" + major_no +"'";
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Class_no = resultSet.getString("Class_no");
            }
            return Class_no;
        } catch (SQLException e) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }


    public boolean insertStudent(String sql) {
        try {
            Jdbc.runUpdate(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteStudentBySno(String Sno) {
        try {
            String sql = "delete from student where Student_no = '" + Sno + "'";
            Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

//    public Student selectStudentBySno(String Sno){
//        try{
//            Student student = new Student();
//            String sql = "select * from student where Student_no = '"+Sno+"'";
//            ResultSet resultSet = Jdbc.runQuery(sql);
//            while(resultSet.next()){
//                student.setSno(resultSet.getString("Student_no"));
//                student.setSname(resultSet.getString("Student_name"));
//                student.setSsex(resultSet.getString("Student_sex"));
//                student.setSlocation(resultSet.getString("Student_location"));
//                student.setSbirth(resultSet.getString("Student_birth"));
//                student.setSjoin(resultSet.getString("Student_join"));
//                student.setSclass(resultSet.getString("Class_no"));
//            }
//            return student;
//        } catch (SQLException ex) {
//            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }

    public boolean updateStudent(String sql) {
        try {
            Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getRPCount(String ID) {
        try {
            String sql = "select count(*) " +
                    "from rewardandpublish " +
                    "where Student_no = " + ID;
            ResultSet resultSet = Jdbc.runQuery(sql);
            Jdbc.realeaseAll();
            resultSet.next();
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addPublish(String sql) {
        try {
            int resultSet = Jdbc.runUpdate(sql);
            Jdbc.realeaseAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Vector<Vector<String>> selectRewardandpublish(String ID) {
        try {
            Vector<Vector<String>> rewardandpublish = new Vector<>();
            String sql = "SELECT asrp.RP_no,RP_content,Admin_name,RP_time FROM `rewardandpublish` , asrp  LEFT JOIN administrator ON administrator.Admin_no = asrp.Admin_no WHERE asrp.RP_no =  rewardandpublish.RP_no and\n" +
                    "Student_no = '" + ID + "'";
            ResultSet resultSet = Jdbc.runQuery(sql);
            while (resultSet.next()) {
                Vector<String> RP = new Vector<>();
                RP.add(0, resultSet.getString("RP_no"));
                RP.add(1, resultSet.getString("RP_content"));
                RP.add(2, resultSet.getString("Admin_name"));
                RP.add(3, resultSet.getString("RP_time"));

                rewardandpublish.add(RP);
            }
            Jdbc.realeaseAll();
            return rewardandpublish;
        } catch (SQLException ex) {
            /**在学生具体功能实现类中填写错误日志*/
            Logger.getLogger(StudentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getActivityCount() {
        String sql = "select COUNT(*) from activity where Activity_no like '" + Calendar.getInstance().get(Calendar.YEAR) + "%'";

        try {
            ResultSet resultSet = Jdbc.runQuery(sql);
            resultSet.next();
            return resultSet.getInt("COUNT(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}