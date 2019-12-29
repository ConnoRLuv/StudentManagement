package gui.StudentFrame;

import dal.daoimpl.StudentDaoImpl;
import dal.daoimpl.TeacherDaoImpl;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class CoursePanel extends JPanel {
    private String SID;

    public CoursePanel(String SID) {
        super();
        this.SID = SID;
        setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();
    }

    private void addMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("",
                "[20px][grow][grow][grow,center][grow][grow][grow][grow][grow,center][grow,center][grow][20px]",
                "[grow 50][grow][grow][grow][grow][grow][grow][grow 40]"));


        Font font = new Font("黑体", Font.PLAIN, 20);

        JLabel labelPic1 = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/Picture/课程表.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        labelPic1.setIcon(imageIcon);
        mainPanel.add(labelPic1, "flowx,cell 3 0");

        JLabel labelCourse1 = new JLabel("已选课程");
        labelCourse1.setFont(font);
        labelCourse1.setBorder(new EmptyBorder(0, 10, 0, 0));
        mainPanel.add(labelCourse1, "cell 3 0");

        JLabel labelPic2 = new JLabel();
        imageIcon = new ImageIcon("src/Picture/课堂.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        labelPic2.setIcon(imageIcon);
        mainPanel.add(labelPic2, "flowx,cell 8 0");

        JLabel labelCourse2 = new JLabel("可选课程");
        labelCourse2.setFont(font);
        labelCourse2.setBorder(new EmptyBorder(0, 10, 0, 0));
        mainPanel.add(labelCourse2, "cell 8 0");


        Vector<String> cols = new Vector<>();
        cols.add("课程编号");
        cols.add("课程名称");
        cols.add("课程学分");
        cols.add("授课教师");
        cols.add("开课学院");
        cols.add("开课学年");

        StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
//选课页面(从左到右)：第一个表已选课程
        String sqlAlreadSelected = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,college.College_name,Course_time " +
                "FROM course,teachercourse,teacher,college,studentcourse " +
                "WHERE course.Course_no=teachercourse.Course_no " +
                "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                "AND teacher.College_no=college.College_no " +
                "AND studentcourse.Student_no = " + SID +
                " AND studentcourse.Course_no = course.Course_no " +
                "AND studentcourse.Teacher_no = teachercourse.Teacher_no ";

        Vector<Vector<String>> rowsAlreadSelected = studentDaoImpl.WaitingSelectOrSelectCourse(sqlAlreadSelected);
        DefaultTableModel defaultTableModel1 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel1.setDataVector(rowsAlreadSelected, cols);
        JTable tableAlreadSelected = new JTable();
        tableAlreadSelected.getTableHeader().setFont(font);
        tableAlreadSelected.setModel(defaultTableModel1);
        tableAlreadSelected.setFont(font);
        tableAlreadSelected.setRowHeight(40);
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(tableAlreadSelected);
        mainPanel.add(jScrollPane1, "cell 1 1 5 6,grow");

//选课页面(从左到右)：第二个表可选课程sqlCanbeSelect
        String sqlCanbeSelect = "select course.Course_no ,Course_name,Course_credit,Teacher_name,college.College_name,Course_time " +
                "from course,teacher,college,teachercourse  " +
                "where teachercourse.Teacher_no=teacher.Teacher_no " +
                "AND teacher.College_no=college.College_no " +
                "AND course.Course_no=teachercourse.Course_no " +
                "AND course.Course_no NOT IN ( " +
                "SELECT Course_no " +
                "FROM studentcourse " +
                "WHERE studentcourse.Student_no = '" + SID + "') " +
                "AND course.Course_name not in ( " +
                "SELECT course.Course_name " +
                "FROM studentcourse,course " +
                "WHERE studentcourse.Student_no = '" + SID + "' " +
                "AND studentcourse.Course_no = course.Course_no " +
                "AND Course_grades >= 60 ) " +
                "AND course.Course_name NOT IN ( " +
                "SELECT course.Course_name " +
                "FROM studentcourse,course " +
                "WHERE studentcourse.Student_no = '" + SID + "' " +
                "AND studentcourse.Course_no = course.Course_no " +
                "AND Course_grades is null ) ";


        Vector<Vector<String>> rowsCanbeSelected = studentDaoImpl.WaitingSelectOrSelectCourse(sqlCanbeSelect);
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.setDataVector(rowsCanbeSelected, cols);
        JTable tableCanbeSelect = new JTable();
        tableCanbeSelect.getTableHeader().setFont(font);
        tableCanbeSelect.setModel(defaultTableModel);
        tableCanbeSelect.setFont(font);
        tableCanbeSelect.setRowHeight(40);
        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setViewportView(tableCanbeSelect);
        mainPanel.add(jScrollPane2, "cell 6 1 5 6,grow");

        JButton buttonCourse1 = new JButton("删除选课信息");
        buttonCourse1.setFont(new Font("黑体", Font.PLAIN, 20));
        buttonCourse1.setForeground(new Color(255, 255, 255));
        buttonCourse1.setBackground(new Color(70, 132, 195));
        buttonCourse1.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonCourse1.addActionListener(e -> {
            //删除已经选择了的课
            int Index = tableAlreadSelected.getSelectedRow(); //鼠标获取table中选中的行的索引(从0开始至n-1)
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "错误!请先选择要删除的课程");
            } else {
                String WantToDeleteCorse_no = (String) tableAlreadSelected.getValueAt(tableAlreadSelected.getSelectedRow(), 0);
                String WantToDeleteCorse_name = (String) tableAlreadSelected.getValueAt(tableAlreadSelected.getSelectedRow(), 1);
                System.out.println(WantToDeleteCorse_name);
                String sql = "SELECT Course_name " +
                        "FROM studentcourse,course " +
                        "WHERE studentcourse.Student_no= " + SID + " " +
                        "AND studentcourse.Course_no= " + WantToDeleteCorse_no + " " +
                        "AND studentcourse.Course_no=course.Course_no " +
                        "AND studentcourse.Course_grades > '0' ";
                //没有考试的课程才能删除,所以得先查看选中的课程已经考试了没
                //如何上面的sql语句查到了，就说明选中的课程已经考完试，不能删除
                String courseName = studentDaoImpl.isReadyExam(sql);
                if (courseName != null) {
                    JLabel Massage = new JLabel("错误!该课程以及结课考试,不能删除！");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);

                } else {
                    String DeleteCoursesql = "DELETE FROM studentcourse " +
                            "WHERE Student_no= " + SID + " AND Course_no= " + WantToDeleteCorse_no;
                    int affectedFlag = studentDaoImpl.DeleteCourse(DeleteCoursesql);
                    if (affectedFlag == 1) {
                        defaultTableModel1.setDataVector(studentDaoImpl.WaitingSelectOrSelectCourse(sqlAlreadSelected), cols);
                        defaultTableModel.setDataVector(studentDaoImpl.WaitingSelectOrSelectCourse(sqlCanbeSelect), cols);
                        defaultTableModel1.fireTableDataChanged();
                        defaultTableModel.fireTableDataChanged();
                        tableAlreadSelected.setModel(defaultTableModel1);
                        tableCanbeSelect.setModel(defaultTableModel);

                        JLabel Massage = new JLabel("删除课程成功！");
                        Massage.setFont(font);
                        JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        mainPanel.add(buttonCourse1, "cell 3 7");

        JButton buttonCourse2 = new JButton("添加至已选课程");
        buttonCourse2.setFont(new Font("黑体", Font.PLAIN, 20));
        buttonCourse2.setForeground(new Color(255, 255, 255));
        buttonCourse2.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonCourse2.setBackground(new Color(70, 132, 195));
        buttonCourse2.setSize(200, 40);
        buttonCourse2.addActionListener(e -> {
            //添加选课记录
            int Index = tableCanbeSelect.getSelectedRow(); //鼠标获取table中选中的行的索引(从0开始至n-1)
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "错误!请先想要添加的课程");
            } else {
                String WantToSelectCorse_no = (String) tableCanbeSelect.getValueAt(tableCanbeSelect.getSelectedRow(), 0);
                String sql = "select teachercourse.Teacher_no from teachercourse,teacher where Course_no = " + WantToSelectCorse_no + " " +
                        "and teacher.Teacher_name = '" + tableCanbeSelect.getValueAt(tableCanbeSelect.getSelectedRow(), 3) + "' " +
                        "and teacher.Teacher_no = teachercourse.Teacher_no";
                TeacherDaoImpl teacherDao = new TeacherDaoImpl();
                String Tno = teacherDao.selectTno(sql);
                String AddCoursesql = "INSERT INTO studentcourse " +
                        "VALUES (" + SID + "," + WantToSelectCorse_no + ",null," + Tno + " )";
                System.out.println(AddCoursesql);
                int affectedFlag = studentDaoImpl.AddCourse(AddCoursesql);
                if (affectedFlag == 1) {

                    defaultTableModel1.setDataVector(studentDaoImpl.WaitingSelectOrSelectCourse(sqlAlreadSelected), cols);
                    defaultTableModel.setDataVector(studentDaoImpl.WaitingSelectOrSelectCourse(sqlCanbeSelect), cols);
                    defaultTableModel1.fireTableDataChanged();
                    defaultTableModel.fireTableDataChanged();
                    tableAlreadSelected.setModel(defaultTableModel1);
                    tableCanbeSelect.setModel(defaultTableModel);

                    JLabel Massage = new JLabel("选课成功！");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JLabel Massage = new JLabel("已经选修过该门课程！");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        mainPanel.add(buttonCourse2, "cell 8 7");
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("选课");
        upPanel.buttonBack.addActionListener(e -> {
            StudentFrame.card.show(StudentFrame.contentPanelCenter, "bgPanel");
        });
        this.add(upPanel, BorderLayout.NORTH);
    }
}
