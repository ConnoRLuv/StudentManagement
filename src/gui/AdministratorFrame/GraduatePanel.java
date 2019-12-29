package gui.AdministratorFrame;

import dal.Entity.Course;
import dal.daoimpl.AdministratorDaoImpl;
import dal.daoimpl.StudentDaoImpl;
import gui.SimpleTableModel;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public class GraduatePanel extends JPanel {
    private String AID;
    private StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
    private JComboBox<String> comboBoxMajor;
    private JComboBox<String> comboBoxClass;
    private String sqlSelect = "";

    public GraduatePanel(String AID) {
        super();
        this.AID = AID;
        setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();
    }

    private void addMainPanel() {
        JPanel mainPanel = new JPanel();
        Dimension preferSize = new Dimension(1610, 860);
        mainPanel.setPreferredSize(preferSize);
        mainPanel.setMaximumSize(preferSize);
        mainPanel.setMinimumSize(preferSize);
        mainPanel.setLayout(new MigLayout("", "[][180px][][180px][][180px][20px][180px,center][20px][120px][20px][120px][20px][120px][20px][120px][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][20px,grow]"));

        Font font = new Font("黑体", Font.PLAIN, 20);


        JLabel label = new JLabel("年级");
        label.setFont(font);
        mainPanel.add(label, "cell 0 0,alignx trailing");

        String sqlCollegeName = "select College_name from college";
        Vector<String> colleges = studentDaoImpl.selectCollegeName(sqlCollegeName);
        colleges.add(0, "");
        JComboBox<String> comboBoxSchool = new JComboBox<>(colleges);
        comboBoxSchool.setFont(font);
        comboBoxSchool.addItemListener(e -> {
            String sqlMajorName = "select Major_name from major where major.College_no = " +
                    "(select college.College_no from college where College_name = '" + comboBoxSchool.getSelectedItem()
                    + "')";
            Vector<String> majors = studentDaoImpl.selectMajorName(sqlMajorName);
            majors.add(0, "");

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(majors);
            comboBoxMajor.setModel(model);
            String sqlClassName = "select Class_name from class where class.Major_no = " +
                    "(select major.Major_no from major where Major_name = '" + comboBoxMajor.getSelectedItem()
                    + "')";
            Vector<String> Classes = studentDaoImpl.selectClassName(sqlClassName);
            Classes.add(0, "");
            model = new DefaultComboBoxModel<>(Classes);
            comboBoxClass.setModel(model);
        });
        mainPanel.add(comboBoxSchool, "cell 1 0,growx");

        JLabel label_1 = new JLabel("专业");
        label_1.setFont(font);
        mainPanel.add(label_1, "cell 2 0,alignx trailing");

        comboBoxMajor = new JComboBox<>();
        comboBoxMajor.setFont(font);
        comboBoxMajor.setBounds(450, 60, 160, 30);
        comboBoxMajor.addItemListener(e -> {
            String sqlClassName = "select Class_name from class where class.Major_no = " +
                    "(select major.Major_no from major where Major_name = '" + comboBoxMajor.getSelectedItem()
                    + "')";
            Vector<String> Classes = studentDaoImpl.selectClassName(sqlClassName);
            Classes.add(0, "");
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(Classes);
            comboBoxClass.setModel(model);
        });
        mainPanel.add(comboBoxMajor, "cell 3 0,growx");

        JLabel labelGraduate1 = new JLabel("班级");
        labelGraduate1.setFont(font);
        mainPanel.add(labelGraduate1, "cell 4 0");


        String[] CourseItem2 = {"", "无未及格课程学生信息", "有未及格课程学生信息"};
        JComboBox<String> comboBoxSelectStudent2 = new JComboBox<>(CourseItem2);
        comboBoxSelectStudent2.setFont(font);
        comboBoxSelectStudent2.setSelectedItem("");
        comboBoxSelectStudent2.setBounds(220, 60, 160, 30);
        comboBoxSelectStudent2.addActionListener(e -> {

        });

        JLabel labelGraduate2 = new JLabel("学业情况");
        labelGraduate2.setFont(font);
        mainPanel.add(labelGraduate2, "cell 6 0");
        mainPanel.add(comboBoxSelectStudent2, "cell 7 0,growx");


        JLabel labelCount1 = new JLabel("共计：");
        labelCount1.setFont(font);
        mainPanel.add(labelCount1, "flowx,cell 1 9");

        JLabel labelCount2 = new JLabel("");
        labelCount2.setFont(font);
        mainPanel.add(labelCount2, "cell 1 9");

        JLabel labelCount3 = new JLabel("人");
        labelCount3.setFont(font);
        mainPanel.add(labelCount3, "cell 1 9");

        Vector<String> cols2 = new Vector<>();
        AdministratorDaoImpl administratorDao = new AdministratorDaoImpl();
        cols2.add("学生学号");
        cols2.add("学生姓名");
        cols2.add("学生班级");
        cols2.add("所在专业");
        cols2.add("所在学院");
        cols2.add("已获取学分");
        cols2.add("不及格课程数");
        cols2.add("学业情况");
        JTable table = new JTable();
        table.setFont(font);
        table.setRowHeight(40);
        table.getTableHeader().setFont(font);
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.setColumnIdentifiers(cols2);
        table.setModel(defaultTableModel);


        JButton btnNewButton_1 = new JButton("进入下一年级");
        btnNewButton_1.setEnabled(false);
        btnNewButton_1.setFont(font);
        btnNewButton_1.setForeground(new Color(255, 255, 255));
        btnNewButton_1.setBackground(new Color(70, 132, 195));
        mainPanel.add(btnNewButton_1, "cell 11 0,growx");
        btnNewButton_1.addActionListener(e1 -> {
            int Student_graduate = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 6));
            if (Student_graduate >= 5) {
                String Student_no = (String) table.getValueAt(table.getSelectedRow(), 0);
                String publish = "学业警示";
                Timestamp timestamp = new Timestamp(new Date().getTime());

                int count = studentDaoImpl.getRPCount(Student_no);
                String sql = "insert into rewardandpublish " +
                        "values(" +
                        (count + 1) + ",'" +
                        Student_no + "','" +
                        AID + "','" +
                        publish + "','" +
                        timestamp + "')";
                studentDaoImpl.addPublish(sql);
                JLabel Massage = new JLabel("已添加学业警示");
                Massage.setFont(font);
                JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            if (table.getValueAt(table.getSelectedRow(), 7).toString().contains("大一")) {
                String sql = "update student set Student_graduate = '大二本科在读' where Student_no = '" + table.getValueAt(table.getSelectedRow(), 0) + "'";
                studentDaoImpl.updateStudent(sql);
            } else if (table.getValueAt(table.getSelectedRow(), 7).toString().contains("大二")) {
                String sql = "update student set Student_graduate = '大三本科在读' where Student_no = '" + table.getValueAt(table.getSelectedRow(), 0) + "'";
                studentDaoImpl.updateStudent(sql);
            } else {
                String sql = "update student set Student_graduate = '大四本科在读' where Student_no = '" + table.getValueAt(table.getSelectedRow(), 0) + "'";
                studentDaoImpl.updateStudent(sql);
            }
            Vector<Vector<String>> rows2 = administratorDao.selectStudentCourse(sqlSelect);
            defaultTableModel.setDataVector(rows2, cols2);
            table.setModel(defaultTableModel);

        });

        JButton btnNewButton_2 = new JButton("审核毕业信息");
        btnNewButton_2.setFont(font);
        btnNewButton_2.setForeground(new Color(255, 255, 255));
        btnNewButton_2.setBackground(new Color(70, 132, 195));
        btnNewButton_2.setEnabled(false);
        mainPanel.add(btnNewButton_2, "cell 13 0,growx");
        btnNewButton_2.addActionListener(e2 -> {
            int Studeng_graduate = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 6));
            int Studeng_credit = (int) Double.parseDouble((String) table.getValueAt(table.getSelectedRow(), 5));
            String Student_no = (String) table.getValueAt(table.getSelectedRow(), 0);

            if (Studeng_graduate > 0) {
                JLabel Massage = new JLabel("存在未及格课程，是否延迟毕业？");
                Massage.setFont(font);
                if (JOptionPane.showConfirmDialog(this, Massage, "提示", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
                    String sql = "update student " +
                            "set Student_graduate = '大四本科在读(延毕)' " +
                            "where Student_no = '" + Student_no + "'";
                    studentDaoImpl.updateStudent(sql);
                    JLabel Massage1 = new JLabel("延迟毕业成功");
                    Massage1.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage1, "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (Studeng_credit < 30) {
                JLabel Massage = new JLabel("未修满学分，是否延迟毕业？");
                Massage.setFont(font);
                if (JOptionPane.showConfirmDialog(this, Massage, "提示", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
                    String sql = "update student " +
                            "set Student_graduate = '大四本科在读(延毕)' " +
                            "where Student_no = '" + Student_no + "'";
                    studentDaoImpl.updateStudent(sql);
                    JLabel Massage1 = new JLabel("延迟毕业成功");
                    Massage1.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage1, "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                String sql = "update student " +
                        "set Student_graduate = '本科毕业' " +
                        "where Student_no = '" + Student_no + "'";
                studentDaoImpl.updateStudent(sql);
                JLabel Massage1 = new JLabel("毕业审核通过");
                Massage1.setFont(font);
                JOptionPane.showMessageDialog(this, Massage1, "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            Vector<Vector<String>> rows2 = administratorDao.selectStudentCourse(sqlSelect);
            defaultTableModel.setDataVector(rows2, cols2);
            table.setModel(defaultTableModel);
            setVisible(true);
        });


        JButton buttonSelect = new JButton("查询");
        buttonSelect.setFont(font);
        buttonSelect.setEnabled(true);
        buttonSelect.setForeground(new Color(255, 255, 255));
        buttonSelect.setBackground(new Color(70, 132, 195));
        mainPanel.add(buttonSelect, "cell 9 0,growx");
        buttonSelect.addActionListener(e -> {
            Vector<Vector<String>> rows2;
            String College = (String) comboBoxSchool.getSelectedItem();
            String Major = (String) comboBoxMajor.getSelectedItem();
            String Class = (String) comboBoxClass.getSelectedItem();
            String Select = (String) comboBoxSelectStudent2.getSelectedItem();
            studentDaoImpl = new StudentDaoImpl();
            //选择学院、专业、班级
            if (!"".equals(College) && !"".equals(Major) && !"".equals(Class)) {
                if (Objects.equals(Select, "无未及格课程学生信息")) {
                    sqlSelect = "select * from graduate " +
                            "where Student_grades = 0 " +
                            "and College_name = '" + College + "' " +
                            "and Major_name = '" + Major + "' " +
                            "and Class_name = '" + Class + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                } else if (Objects.equals(Select, "有未及格课程学生信息")) {
                    sqlSelect = "select * from graduate " +
                            "where Student_grades <> 0 " +
                            "and College_name = '" + College + "' " +
                            "and Major_name = '" + Major + "' " +
                            "and Class_name = '" + Class + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                } else {
                    sqlSelect = "select * from graduate " +
                            "where College_name = '" + College + "' " +
                            "and Major_name = '" + Major + "' " +
                            "and Class_name = '" + Class + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                }
            } else if (!"".equals(College) && "".equals(Major) && "".equals(Class)) {
                if (Objects.equals(Select, "无未及格课程学生信息")) {
                    sqlSelect = "select * from graduate " +
                            "where Student_grades = 0 " +
                            "and College_name = '" + College + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                } else if (Objects.equals(Select, "有未及格课程学生信息")) {
                    sqlSelect = "select * from graduate " +
                            "where Student_grades <> 0 " +
                            "and College_name = '" + College + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                } else {
                    sqlSelect = "select * from graduate " +
                            "where College_name = '" + College + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                }
            } else if (!"".equals(College) && !"".equals(Major)) {
                if (Objects.equals(Select, "无未及格课程学生信息")) {
                    sqlSelect = "select * from graduate " +
                            "where Student_grades = 0 " +
                            "and College_name = '" + College + "' " +
                            "and Major_name = '" + Major + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                } else if (Objects.equals(Select, "有未及格课程学生信息")) {
                    sqlSelect = "select * from graduate " +
                            "where Student_grades <> 0 " +
                            "and College_name = '" + College + "' " +
                            "and Major_name = '" + Major + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                } else {
                    sqlSelect = "select * from graduate " +
                            "where College_name = '" + College + "' " +
                            "and Major_name = '" + Major + "'";
                    rows2 = administratorDao.selectStudentCourse(sqlSelect);
                    defaultTableModel.setDataVector(rows2, cols2);
                    table.setModel(defaultTableModel);
                    setVisible(true);
                }
            } else {
                JLabel Massage = new JLabel("未选中查询信息！");
                Massage.setFont(font);
                JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
            }
            btnNewButton_1.setEnabled(false);
            btnNewButton_2.setEnabled(false);
            labelCount2.setText(String.valueOf(table.getRowCount()));

        });

        JButton buttonSelectGrades = new JButton("查询课程成绩信息");
        buttonSelectGrades.setFont(font);
        buttonSelectGrades.setEnabled(true);
        buttonSelectGrades.setForeground(new Color(255, 255, 255));
        buttonSelectGrades.setBackground(new Color(70, 132, 195));
        buttonSelectGrades.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JLabel Massage = new JLabel("未选中查询信息！");
                Massage.setFont(font);
                JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JFrame gradesFrame = new JFrame();
            gradesFrame.setTitle("课程成绩信息");
            gradesFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            gradesFrame.setSize(new Dimension(900, 600));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int x = (int) (toolkit.getScreenSize().getWidth() - gradesFrame.getWidth()) / 2;
            int y = (int) (toolkit.getScreenSize().getHeight() - gradesFrame.getHeight()) / 2;
            gradesFrame.setLocation(x, y);

            JPanel panel = new JPanel();
            gradesFrame.getContentPane().add(panel, BorderLayout.CENTER);

            UpPanel upPanel1 = new UpPanel(" 课程成绩信息");
            upPanel1.buttonBack.setVisible(false);
            gradesFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);

            panel.setLayout(new MigLayout("", "[grow][grow][grow][::120px,grow][grow][grow][grow]", "[40px][grow][grow][grow][grow][grow][grow][grow][grow][grow][::40px,grow]"));

            JLabel lblNewLabel = new JLabel("学生学号： ");
            lblNewLabel.setFont(font);
            panel.add(lblNewLabel, "flowx,cell 0 0");

            JLabel lblNewLabel_2 = new JLabel("");
            lblNewLabel_2.setFont(font);
            lblNewLabel_2.setText((String) table.getValueAt(table.getSelectedRow(), 0));
            panel.add(lblNewLabel_2, "flowx,cell 0 0");

            JScrollPane scrollPane = new JScrollPane();
            panel.add(scrollPane, "cell 0 1 7 10,grow");


            JLabel lblNewLabel_4 = new JLabel("学生姓名：");
            lblNewLabel_4.setFont(font);
            panel.add(lblNewLabel_4, "cell 2 0");

            JLabel lblNewLabel_5 = new JLabel("");
            lblNewLabel_5.setFont(font);
            lblNewLabel_5.setText((String) table.getValueAt(table.getSelectedRow(), 1));
            panel.add(lblNewLabel_5, "flowx,cell 2 0");

            List<String> cols3 = new ArrayList<>();
            cols3.add("课程编号");
            cols3.add("课程名称");
            cols3.add("课程学分");
            cols3.add("任课教师");
            cols3.add("开课学年");
            cols3.add("课程分数");
            JTable table1 = new JTable();
            table1.setFont(font);
            table1.setRowHeight(40);
            table1.getTableHeader().setFont(font);
            SimpleTableModel<Course> simpleTableModel = new SimpleTableModel<>(cols3, new ArrayList<>(), "Course");
            table1.setModel(simpleTableModel);
            scrollPane.setViewportView(table1);

            JComboBox<String> comboBox = new JComboBox<>(new String[]{"全部课程信息", "及格课程信息", "未及格课程信息"});
            comboBox.setFont(font);

            comboBox.addActionListener(e1 -> {
                String SID = (String) table.getValueAt(table.getSelectedRow(), 0);
                if (Objects.equals(comboBox.getSelectedItem(), "全部课程信息")) {
                    String sql2 = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_time, Course_grades " +
                            "FROM course,teachercourse,teacher,studentcourse " +
                            "WHERE studentcourse.Student_no=" + SID + " AND " +
                            "course.Course_no=teachercourse.Course_no " +
                            "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                            "AND course.Course_no=studentcourse.Course_no " +
                            "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                            "AND Course_grades>0";
                    SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<Course>(cols3, studentDaoImpl.selectCourse(sql2), "Course");
                    table1.setModel(simpleTableModel2);
                    setVisible(true);
                } else if (Objects.equals(comboBox.getSelectedItem(), "未及格课程信息")) {
                    /**查询所有已考试但未及格课程的sql语句*/
                    String sql2 = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_time,Course_grades " +
                            "FROM course,teachercourse,teacher,studentcourse " +
                            "WHERE studentcourse.Student_no=" + SID + " AND " +
                            "course.Course_no=teachercourse.Course_no " +
                            "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                            "AND course.Course_no=studentcourse.Course_no " +
                            "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                            "AND Course_grades<60 " +
                            "AND Course_grades>0";
                    SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<>(cols3, studentDaoImpl.selectCourse(sql2), "Course");
                    table1.setModel(simpleTableModel2);
                    setVisible(true);
                } else {
                    String sql2 = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_time,Course_grades " +
                            "FROM course,teachercourse,teacher,studentcourse " +
                            "WHERE studentcourse.Student_no=" + SID + " AND " +
                            "course.Course_no=teachercourse.Course_no " +
                            "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                            "AND course.Course_no=studentcourse.Course_no " +
                            "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                            "AND Course_grades >= 60 ";
                    SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<>(cols3, studentDaoImpl.selectCourse(sql2), "Course");
                    table1.setModel(simpleTableModel2);
                    setVisible(true);
                }
            });
            comboBox.setSelectedItem("全部课程信息");
            panel.add(comboBox, "cell 4 0");


            gradesFrame.setVisible(true);
        });
        mainPanel.add(buttonSelectGrades, "cell 15 0,growx");


        JScrollPane jScrollPane = new JScrollPane(table);
        mainPanel.add(jScrollPane, "cell 0 1 17 8,grow");

        comboBoxClass = new JComboBox<>();
        comboBoxClass.setFont(font);
        comboBoxClass.setSelectedItem(null);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (table.getValueAt(table.getSelectedRow(), 7).toString().contains("大四")) {
                    btnNewButton_1.setEnabled(false);
                    btnNewButton_2.setEnabled(true);
                } else if (table.getValueAt(table.getSelectedRow(), 7).toString().contains("毕业")) {
                    btnNewButton_1.setEnabled(false);
                    btnNewButton_2.setEnabled(false);
                } else {
                    int Student_grades = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 6));
                    btnNewButton_1.setEnabled(true);
                    btnNewButton_2.setEnabled(false);
                }

                if (e.getClickCount() == 2) {
                    Font font = new Font("黑体", Font.PLAIN, 20);

                    JFrame gradesFrame = new JFrame();
                    gradesFrame.setTitle("课程成绩信息");
                    gradesFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                    gradesFrame.setSize(new Dimension(900, 600));
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    int x = (int) (toolkit.getScreenSize().getWidth() - gradesFrame.getWidth()) / 2;
                    int y = (int) (toolkit.getScreenSize().getHeight() - gradesFrame.getHeight()) / 2;
                    gradesFrame.setLocation(x, y);

                    JPanel panel = new JPanel();
                    gradesFrame.getContentPane().add(panel, BorderLayout.CENTER);

                    UpPanel upPanel1 = new UpPanel(" 课程成绩信息");
                    upPanel1.buttonBack.setVisible(false);
                    gradesFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);

                    panel.setLayout(new MigLayout("", "[grow][grow][grow][::120px,grow][grow][grow][grow]", "[40px][grow][grow][grow][grow][grow][grow][grow][grow][grow][::40px,grow]"));

                    JLabel lblNewLabel = new JLabel("学生学号： ");
                    lblNewLabel.setFont(font);
                    panel.add(lblNewLabel, "flowx,cell 0 0");

                    JLabel lblNewLabel_2 = new JLabel("");
                    lblNewLabel_2.setFont(font);
                    lblNewLabel_2.setText((String) table.getValueAt(table.getSelectedRow(), 0));
                    panel.add(lblNewLabel_2, "flowx,cell 0 0");

                    JScrollPane scrollPane = new JScrollPane();
                    panel.add(scrollPane, "cell 0 1 7 10,grow");


                    JLabel lblNewLabel_4 = new JLabel("学生姓名：");
                    lblNewLabel_4.setFont(font);
                    panel.add(lblNewLabel_4, "cell 2 0");

                    JLabel lblNewLabel_5 = new JLabel("");
                    lblNewLabel_5.setFont(font);
                    lblNewLabel_5.setText((String) table.getValueAt(table.getSelectedRow(), 1));
                    panel.add(lblNewLabel_5, "flowx,cell 2 0");

                    List<String> cols2 = new ArrayList<>();
                    cols2.add("课程编号");
                    cols2.add("课程名称");
                    cols2.add("课程学分");
                    cols2.add("任课教师");
                    cols2.add("开课学年");
                    cols2.add("课程分数");
                    JTable table1 = new JTable();
                    table1.setFont(font);
                    table1.setRowHeight(40);
                    table1.getTableHeader().setFont(font);
                    SimpleTableModel<Course> simpleTableModel = new SimpleTableModel<Course>(cols2, new ArrayList<>(), "Course");
                    table1.setModel(simpleTableModel);
                    scrollPane.setViewportView(table1);

                    JComboBox<String> comboBox = new JComboBox<>(new String[]{"全部课程信息", "及格课程信息", "未及格课程信息"});
                    comboBox.setFont(font);

                    comboBox.addActionListener(e1 -> {
                        String SID = (String) table.getValueAt(table.getSelectedRow(), 0);
                        if (Objects.equals(comboBox.getSelectedItem(), "全部课程信息")) {
                            String sql2 = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_time, Course_grades " +
                                    "FROM course,teachercourse,teacher,studentcourse " +
                                    "WHERE studentcourse.Student_no=" + SID + " AND " +
                                    "course.Course_no=teachercourse.Course_no " +
                                    "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                                    "AND course.Course_no=studentcourse.Course_no " +
                                    "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                                    "AND Course_grades>0";
                            SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<>(cols2, studentDaoImpl.selectCourse(sql2), "Course");
                            table1.setModel(simpleTableModel2);
                            setVisible(true);
                        } else if (Objects.equals(comboBox.getSelectedItem(), "未及格课程信息")) {
                            /**查询所有已考试但未及格课程的sql语句*/
                            String sql2 = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_time,Course_grades " +
                                    "FROM course,teachercourse,teacher,studentcourse " +
                                    "WHERE studentcourse.Student_no=" + SID + " AND " +
                                    "course.Course_no=teachercourse.Course_no " +
                                    "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                                    "AND course.Course_no=studentcourse.Course_no " +
                                    "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                                    "AND Course_grades<60 " +
                                    "AND Course_grades>0";
                            SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<>(cols2, studentDaoImpl.selectCourse(sql2), "Course");
                            table1.setModel(simpleTableModel2);
                            setVisible(true);
                        } else {
                            String sql2 = "SELECT course.Course_no,Course_name,Course_credit,Teacher_name,Course_time,Course_grades " +
                                    "FROM course,teachercourse,teacher,studentcourse " +
                                    "WHERE studentcourse.Student_no=" + SID + " AND " +
                                    "course.Course_no=teachercourse.Course_no " +
                                    "AND teachercourse.Teacher_no=teacher.Teacher_no " +
                                    "AND course.Course_no=studentcourse.Course_no " +
                                    "AND teachercourse.Teacher_no=studentcourse.Teacher_no " +
                                    "AND Course_grades >= 60 ";
                            SimpleTableModel<Course> simpleTableModel2 = new SimpleTableModel<>(cols2, studentDaoImpl.selectCourse(sql2), "Course");
                            table1.setModel(simpleTableModel2);
                            setVisible(true);
                        }
                    });
                    comboBox.setSelectedItem("全部课程信息");
                    panel.add(comboBox, "cell 4 0");


                    gradesFrame.setVisible(true);
                }
            }
        });


        mainPanel.add(comboBoxClass, "cell 5 0,growx");
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("审核学业要求");
        upPanel.buttonBack.addActionListener(e -> AdministratorFrame.card.show(AdministratorFrame.contentPanelCenter, "bgPanel"));
        this.add(upPanel, BorderLayout.NORTH);
    }
}
