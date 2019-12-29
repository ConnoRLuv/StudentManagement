package dal.dao;

import dal.Entity.Teacher;

import java.util.List;

/**
 * 关于教师的功能接口说明:
 * 教师入职是由管理员来操作即addTeacher(Teacher teacher)
 * 教师自己的操作:
 * 1.通过学号查看选课学生的信息;
 * 2.通过查看自己的信息;
 * 3.修改自己的信息;
 * 4.为选课学生登录考试成绩。
 */
public interface TeacherDao {
    boolean certifyTeacher(String Id, String Password);

    List<Teacher> selectTeacher(String sql);

    List<Teacher> selectAllTeacher();

    List<Teacher> selectTeacherByTno(String Tno);
//    boolean updateTeacher(Teacher teacher);
//    boolean updateGrade(Double Grade);
}
