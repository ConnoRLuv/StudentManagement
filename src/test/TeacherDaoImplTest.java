package test;

import dal.daoimpl.TeacherDaoImpl;
import org.junit.Test;

public class TeacherDaoImplTest {

    @Test
    public void vertifyTeacher() {
        TeacherDaoImpl temp = new TeacherDaoImpl();
        if (temp.certifyTeacher("100101", "1234"))
            System.out.println("successful!");

    }

    @Test
    public void selectStudetBySno() {
    }

    @Test
    public void selectTeacherByTno() {
    }

    @Test
    public void updateTeacher() {
    }

    @Test
    public void updateGrade() {
    }
}