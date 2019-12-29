package dal.dao;

import dal.Entity.Student;

import java.util.List;

/**
 * 关于学生的功能接口说明:
 * 学生入学是由管理员来操作即addStudent(Student student)
 * 学生自己的操作:
 * 1.通过学号查看自己的信息;
 * 2.通过姓名查看自己的信息;
 * 2.修改自己的信息;
 * 3.添加(申报)自己的社会实践活动
 */

public interface StudentDao {
    boolean certifyStudent(String Id, String Password);

    List<Student> selectStudent(String sql);

    boolean addStudent(Student student);
//  public boolean addActivitices(String ActivityName, Date ActivityTime);
}
