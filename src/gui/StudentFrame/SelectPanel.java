package gui.StudentFrame;

import dal.Entity.Course;
import dal.daoimpl.StudentDaoImpl;
import gui.SimpleTableModel;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectPanel extends JPanel {
    private String SID;
    private StudentDaoImpl studentDaoImpl;

    public SelectPanel(String SID) {
        super();
        this.SID = SID;
        this.setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();

    }

    private void addMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("", "[20px][:48px:50px,grow][::120px,grow,center][:98px:200px,grow,center][20px:n:20px][::160px,grow,fill][73px,grow,fill][20px]", "[80px:n:80px][grow,fill][20px]"));

        Dimension preferSize = new Dimension(1610, 860);
        mainPanel.setPreferredSize(preferSize);
        mainPanel.setMaximumSize(preferSize);
        mainPanel.setMinimumSize(preferSize);

        Font font = new Font("黑体", Font.PLAIN, 20);

        JLabel labelPic = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/Picture/分析.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        labelPic.setIcon(imageIcon);
        mainPanel.add(labelPic, "cell 1 0,alignx left,aligny center");


        JLabel LabelSchool = new JLabel("查询方式");
        LabelSchool.setFont(font);
        mainPanel.add(LabelSchool, "cell 2 0,alignx center,aligny center");

        String[] CourseItem = {"所有已考试课程", "未及格课程"};
        JComboBox<String> comboBoxSelectCourse = new JComboBox<>(CourseItem);
        comboBoxSelectCourse.setFont(font);
        mainPanel.add(comboBoxSelectCourse, "cell 3 0,growx,aligny center");


        JTable table = new JTable();
        List<Course> rows;
        List<String> cols = new ArrayList<>();
        cols.add("课程编号");
        cols.add("课程名称");
        cols.add("课程学分");
        cols.add("任课教师");
        cols.add("开课学年");
        cols.add("课程分数");
        studentDaoImpl = new StudentDaoImpl();

        /**查询所有已考试课程的sql语句*/
        String sql = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,course.Course_time,studentcourse.Course_grades " +
                "FROM course,teachercourse,teacher,studentcourse " +
                "WHERE studentcourse.Student_no=" + SID + " AND " +
                "course.Course_no=teachercourse.Course_no " +
                "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                "AND course.Course_no=studentcourse.Course_no " +
                "AND Course_grades>0";
        rows = studentDaoImpl.selectCourse(sql);
        SimpleTableModel<Course> simpleTableModel = new SimpleTableModel<>(cols, rows, "Course");
        table.setModel(simpleTableModel);
        table.setFont(font);
        table.getTableHeader().setFont(font);
        table.setRowHeight(30);
        /**应该把字体调大并规定一页只能放置10条课程成绩记录,
         * 如果记录数超过15条,则应该分页*/
//          JScrollPane jScrollPane = new JScrollPane();
//          jScrollPane.setViewportView(table);
//          getContentPane().add(jScrollPane, BorderLayout.CENTER);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(table);
        mainPanel.add(jScrollPane, "cell 1 1 6 1,growx,aligny top");

        JButton buttonSelect = new JButton("查询");
        buttonSelect.setFont(font);
        buttonSelect.setForeground(new Color(255, 255, 255));
        buttonSelect.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonSelect.setBackground(new Color(70, 132, 195));
        buttonSelect.addActionListener(actionEvent -> {
            /**共有部分外放,避免代码冗余*/
            List<Course> rows2;
            List<String> cols2 = new ArrayList<>();
            cols2.add("课程编号");
            cols2.add("课程名称");
            cols2.add("课程学分");
            cols2.add("任课教师");
            cols2.add("开课学年");
            cols2.add("课程分数");
            /**我在这有个疑问？为什么我的表头并没有在表格中显示出来呢？*/
            studentDaoImpl = new StudentDaoImpl();
            if (Objects.equals(comboBoxSelectCourse.getSelectedItem(), "所有已考试课程")) {
//                /**查询所有已考试课程的sql语句*/
//                String sql2="SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_grades "+
//                        "FROM course,teachercourse,teacher,studentcourse "+
//                        "WHERE studentcourse.Student_no='"+ SID +"' AND "+
//                        "course.Course_no=teachercourse.Course_no "+
//                        "AND teachercourse.Teacher_no=teacher.Teacher_no "+
//                        "AND course.Course_no=studentcourse.Course_no "+
//                        "AND Course_grades>0";
                rows2 = studentDaoImpl.selectCourse(sql);
                SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<Course>(cols2, rows2, "Course");
                table.setModel(simpleTableModel2);

                setVisible(true);
            } else if (Objects.equals(comboBoxSelectCourse.getSelectedItem(), "未及格课程")) {
                /**查询所有已考试但未及格课程的sql语句*/
                String sql2 = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_time,Course_grades " +
                        "FROM course,teachercourse,teacher,studentcourse " +
                        "WHERE studentcourse.Student_no='" + SID + "' AND " +
                        "course.Course_no=teachercourse.Course_no " +
                        "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                        "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                        "AND course.Course_no=studentcourse.Course_no " +
                        "AND Course_grades<60 " +
                        "AND Course_grades>0";
                rows2 = studentDaoImpl.selectCourse(sql2);
                SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<>(cols2, rows2, "Course");
                table.setModel(simpleTableModel2);
                setVisible(true);
//            }else {
//                /**查询所有已考试但未及格课程的sql语句*/
//                String sql2="SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_grades "+
//                        "FROM course,teachercourse,teacher,studentcourse "+
//                        "WHERE studentcourse.Student_no="+ SID +" AND "+
//                        "course.Course_no=teachercourse.Course_no "+
//                        "AND teachercourse.Teacher_no=teacher.Teacher_no "+
//                        "AND course.Course_no=studentcourse.Course_no "+
//                        "AND Course_grades IS NULL";
//                rows2 = studentDaoImpl.selectCourse(sql2);
//                SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<>(cols2, rows2, "Course");
//                table.setModel(simpleTableModel2);
//                setVisible(true);
            }
        });
        mainPanel.add(buttonSelect, "cell 5 0,alignx left,aligny center");


        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("查询成绩");
        upPanel.buttonBack.addActionListener(e -> {
            StudentFrame.card.show(StudentFrame.contentPanelCenter, "bgPanel");
        });
        this.add(upPanel, BorderLayout.NORTH);
    }
}
