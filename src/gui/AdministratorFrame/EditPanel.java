package gui.AdministratorFrame;

import dal.Entity.Student;
import dal.daoimpl.StudentDaoImpl;
import gui.SimpleTableModel;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class EditPanel extends JPanel {
    //    private static List<Student> StudentList;
    private String SID;
    private StudentDaoImpl studentDao = new StudentDaoImpl();
    private static JTable table = new JTable();
    private static SimpleTableModel<Student> tableModel;
    private static JScrollPane jScrollPane = new JScrollPane();
    private JComboBox<String> comboBoxMajor = new JComboBox<>();
    private JComboBox<String> comboBoxClass = new JComboBox<>();
    private JComboBox<String> comboBoxCollege = new JComboBox<>();
    private String sqlAll;

    public EditPanel() {
        super();
//        StudentList = new ArrayList<>();
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
        mainPanel.setLayout(new MigLayout("", "[20px][][120px,grow,fill][20px][20px][120px,grow,fill][20px][20px][120px,grow,fill][20px][grow][20px][120px:n,grow][20px][120px:n,grow,fill][20px][120px:n,grow][20px]", "[20px][40px][20px][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));

        Font font = new Font("黑体", Font.PLAIN, 20);

        List<String> cols = new ArrayList<>();
        cols.add("学号");
        cols.add("姓名");
        cols.add("性别");
        cols.add("籍贯");
        cols.add("出生年月");
        cols.add("入学年份");
        cols.add("专业名称");
        cols.add("班级名称");
        cols.add("学院名称");
        cols.add("学籍情况");
        sqlAll = "";
//        studentDao = new StudentDaoImpl();
//        sqlAll ="SELECT Student_no,Student_name,Student_sex," +
//                "Student_location,Student_birth,Student_join," +
//                "Major_name,Class_name,College_name,Student_graduate " +
//                "FROM student,class,major,college " +
//                "WHERE student.Class_no=class.Class_no " +
//                "AND class.Major_no=major.Major_no " +
//                "AND major.College_no=college.College_no ";

        List<Student> rows = new ArrayList<>();
        tableModel = new SimpleTableModel<>(cols, rows, "Student");
        table.setModel(tableModel);
        table.setRowHeight(40);
        table.getTableHeader().setFont(font);
        table.setFont(font);
        jScrollPane = new JScrollPane(table);
        jScrollPane.setViewportView(table);


        JLabel labelSchool = new JLabel("学院");
        labelSchool.setFont(font);
        mainPanel.add(labelSchool, "cell 1 1,alignx trailing");

        String sqlCollegeName = "select College_name from college";
        Vector<String> colleges = studentDao.selectCollegeName(sqlCollegeName);
        colleges.add(0, "");
        comboBoxCollege = new JComboBox<>(colleges);
        comboBoxCollege.setFont(font);
        comboBoxCollege.setBounds(180, 60, 160, 30);
        comboBoxCollege.addItemListener(e -> {
            String sqlMajorName = "select Major_name from major where major.College_no = " +
                    "(select college.College_no from college where College_name = '" + comboBoxCollege.getSelectedItem()
                    + "')";
            Vector<String> majors = studentDao.selectMajorName(sqlMajorName);
            majors.add(0, "");

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(majors);
            comboBoxMajor.setModel(model);
            String sqlClassName = "select Class_name from class where class.Major_no = " +
                    "(select major.Major_no from major where Major_name = '" + comboBoxMajor.getSelectedItem()
                    + "')";
            Vector<String> Classes = studentDao.selectClassName(sqlClassName);
            Classes.add(0, "");
            model = new DefaultComboBoxModel<>(Classes);
            comboBoxClass.setModel(model);
        });
        mainPanel.add(comboBoxCollege, "cell 2 1,growx");

        JLabel labelMajor = new JLabel("专业");
        labelMajor.setFont(font);
        mainPanel.add(labelMajor, "cell 4 1,alignx trailing");

        comboBoxMajor = new JComboBox<>();
        comboBoxMajor.setFont(font);
        comboBoxMajor.setBounds(450, 60, 160, 30);
        comboBoxMajor.addItemListener(e -> {
            String sqlClassName = "select Class_name from class where class.Major_no = " +
                    "(select major.Major_no from major where Major_name = '" + comboBoxMajor.getSelectedItem()
                    + "')";
            Vector<String> Classes = studentDao.selectClassName(sqlClassName);
            Classes.add(0, "");
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(Classes);
            comboBoxClass.setModel(model);
        });
        mainPanel.add(comboBoxMajor, "cell 5 1,growx");

        JLabel labelClass = new JLabel("班级");
        labelClass.setFont(font);
        mainPanel.add(labelClass, "cell 7 1,alignx trailing");

        comboBoxClass = new JComboBox<>();
        comboBoxClass.setBounds(720, 60, 160, 30);
        comboBoxClass.setFont(font);
        mainPanel.add(comboBoxClass, "cell 8 1,growx");


        JButton buttonSelect = new JButton("查询");
        buttonSelect.setFont(font);
        buttonSelect.setForeground(new Color(255, 255, 255));
        buttonSelect.setBackground(new Color(70, 132, 195));
        buttonSelect.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonSelect.addActionListener(e -> {
            List<Student> rows2;
            String College = (String) comboBoxCollege.getSelectedItem();
            String Major = (String) comboBoxMajor.getSelectedItem();
            String Class = (String) comboBoxClass.getSelectedItem();

            studentDao = new StudentDaoImpl();
            //选择学院、专业、班级
            if (!"".equals(College) && !"".equals(Major) && !"".equals(Class)) {

                sqlAll = "SELECT Student_no,Student_name,Student_sex, " +
                        "Student_location,Student_birth,Student_join, " +
                        "Major_name,Class_name,College_name ,Student_graduate  " +
                        "FROM student,class,major,college " +
                        "WHERE student.Class_no=class.Class_no " +
                        "AND class.Major_no=major.Major_no " +
                        "AND major.College_no=college.College_no " +
                        "AND major.Major_name=" + "'" + Major + "' " +
                        "AND class.Class_name =" + "'" + Class + "' " +
                        "AND college.College_name =" + "'" + College + "' ";
                rows2 = studentDao.selectStudent(sqlAll);
                SimpleTableModel<Student> simpleTableModel2 = new SimpleTableModel<>(cols, rows2, "Student");
                table.setModel(simpleTableModel2);
                setVisible(true);
            }
            //选择学院
            else if (!"".equals(College) && "".equals(Major) && "".equals(Class)) {
                sqlAll = "SELECT Student_no,Student_name,Student_sex, " +
                        "Student_location,Student_birth,Student_join, " +
                        "Major_name,Class_name,College_name ,Student_graduate  " +
                        "FROM student,class,major,college " +
                        "WHERE student.Class_no=class.Class_no " +
                        "AND class.Major_no=major.Major_no " +
                        "AND major.College_no=college.College_no " +
                        "AND college.College_name =" + "'" + College + "' ";
                rows2 = studentDao.selectStudent(sqlAll);
                SimpleTableModel<Student> simpleTableModel2 = new SimpleTableModel<Student>(cols, rows2, "Student");
                table.setModel(simpleTableModel2);
                setVisible(true);
            } else if (!"".equals(College) && !"".equals(Major)) {
                sqlAll = "SELECT Student_no,Student_name,Student_sex, " +
                        "Student_location,Student_birth,Student_join, " +
                        "Major_name,Class_name,College_name,Student_graduate " +
                        "FROM student,class,major,college " +
                        "WHERE student.Class_no=class.Class_no " +
                        "AND class.Major_no=major.Major_no " +
                        "AND major.College_no=college.College_no " +
                        "AND college.College_name =" + "'" + College + "' " +
                        "AND major.Major_name=" + "'" + Major + "' ";
                rows2 = studentDao.selectStudent(sqlAll);
                SimpleTableModel<Student> simpleTableModel2 = new SimpleTableModel<Student>(cols, rows2, "Student");
                table.setModel(simpleTableModel2);
                setVisible(true);
            } else {
//                sqlAll ="SELECT Student_no,Student_name,Student_sex," +
//                        "Student_location,Student_birth,Student_join," +
//                        "Major_name,Class_name,College_name ,Student_graduate  " +
//                        "FROM student,class,major,college " +
//                        "WHERE student.Class_no=class.Class_no " +
//                        "AND class.Major_no=major.Major_no " +
//                        "AND major.College_no=college.College_no ";
//                List<Student> rows1 = studentDao.selectStudent(sqlAll);
//
//                table.setModel(new SimpleTableModel<>(cols, rows1, "Student"));
                JLabel Massage = new JLabel("未选中查询信息！");
                Massage.setFont(font);
                JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton buttonUpdate = new JButton("修改学籍信息");
        buttonUpdate.setFont(font);
        buttonUpdate.setForeground(new Color(255, 255, 255));
        buttonUpdate.setBackground(new Color(70, 132, 195));
        buttonUpdate.addActionListener(e -> {
            JComboBox<String> comboBoxMajorUpdate = new JComboBox<>();
            JComboBox<String> comboBoxClassUpdate = new JComboBox<>();
            JComboBox<String> comboBoxCollegeUpdate;
            try {
                JFrame updateFrame = new JFrame();
                updateFrame.setTitle("修改学籍信息");
                updateFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                updateFrame.setSize(1600, 800);

                updateFrame.setSize(new Dimension(900, 600));
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                int x = (int) (toolkit.getScreenSize().getWidth() - updateFrame.getWidth()) / 2;
                int y = (int) (toolkit.getScreenSize().getHeight() - updateFrame.getHeight()) / 2;
                updateFrame.setLocation(x, y);

                JPanel panel = new JPanel();
                updateFrame.getContentPane().add(panel, BorderLayout.CENTER);

                UpPanel upPanel1 = new UpPanel(" 修改学籍信息");
                upPanel1.buttonBack.setVisible(false);
                updateFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);

                panel.setLayout(new MigLayout("", "[grow][:80px:120px,grow,right][grow][:120px:120px,center][grow][:120px:120px][center][160px,grow,left][grow]", "[grow,fill][grow,center][grow,center][grow,center][grow,center][grow,center][grow][grow][grow][grow][grow]"));

                String Sno = (String) table.getValueAt(table.getSelectedRow(), 0);
                JLabel labelSno = new JLabel("学号");
                labelSno.setFont(font);
                panel.add(labelSno, "cell 1 1,alignx center");

                JTextField textFieldSno = new JTextField();
                textFieldSno.setFont(font);
                textFieldSno.setText(Sno);
                panel.add(textFieldSno, "cell 2 1 2 1,growx");
                textFieldSno.setColumns(10);

                JLabel labelSname = new JLabel("姓名");
                labelSname.setFont(font);
                panel.add(labelSname, "cell 5 1 2 1,alignx center");

                JTextField textFieldSname = new JTextField();
                textFieldSname.setFont(font);
                textFieldSname.setText((String) table.getValueAt(table.getSelectedRow(), 1));
                textFieldSname.setColumns(10);
                panel.add(textFieldSname, "cell 7 1,growx");

                JLabel labelSsex = new JLabel("性别");
                labelSsex.setFont(font);
                panel.add(labelSsex, "cell 1 2,alignx center");

                JComboBox<String> comboBoxSsex = new JComboBox<>();
                comboBoxSsex.setModel(new DefaultComboBoxModel<>(new String[]{"", "男", "女"}));
                comboBoxSsex.setSelectedItem(table.getValueAt(table.getSelectedRow(), 2));
                comboBoxSsex.setFont(new Font("黑体", Font.PLAIN, 20));
                panel.add(comboBoxSsex, "cell 2 2 2 1,growx");

                JLabel labelSlocation = new JLabel("籍贯");
                labelSlocation.setFont(font);
                panel.add(labelSlocation, "cell 5 2 2 1,alignx center");

                JTextField textFieldSlocation = new JTextField();
                textFieldSlocation.setFont(font);
                textFieldSlocation.setColumns(10);
                textFieldSlocation.setText((String) table.getValueAt(table.getSelectedRow(), 3));
                panel.add(textFieldSlocation, "cell 7 2,growx");

                JLabel labelSbirth = new JLabel("出生日期");
                labelSbirth.setFont(font);
                panel.add(labelSbirth, "cell 1 3,alignx center");

                JTextField textFieldSbirth = new JTextField();
                textFieldSbirth.setFont(font);
                textFieldSbirth.setText((String) table.getValueAt(table.getSelectedRow(), 4));
                panel.add(textFieldSbirth, "cell 2 3 2 1,growx");
                textFieldSbirth.setColumns(10);

                JLabel labelSjoin = new JLabel("入学年份");
                labelSjoin.setFont(font);
                panel.add(labelSjoin, "cell 5 3 2 1,alignx center");

                JTextField textFieldSjoin = new JTextField();
                textFieldSjoin.setFont(font);
                textFieldSjoin.setEditable(false);
                textFieldSjoin.setText((String) table.getValueAt(table.getSelectedRow(), 5));
                panel.add(textFieldSjoin, "cell 7 3,growx");
                textFieldSjoin.setColumns(10);

                JLabel labelCollege = new JLabel("学院");
                labelCollege.setFont(font);
                panel.add(labelCollege, "cell 1 4,alignx center");

                comboBoxCollegeUpdate = new JComboBox<>();
                String sqlCollegeName1 = "select College_name from college";
                Vector<String> colleges1 = studentDao.selectCollegeName(sqlCollegeName1);
                colleges1.add(0, "");
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(colleges1);
                comboBoxCollegeUpdate.setModel(model);
                comboBoxCollegeUpdate.setFont(font);
                comboBoxCollegeUpdate.addItemListener(e1 -> {
                    String sqlMajorName = "select Major_name from major where major.College_no = " +
                            "(select college.College_no from college where College_name = '" + comboBoxCollegeUpdate.getSelectedItem()
                            + "')";
                    Vector<String> majors1 = studentDao.selectMajorName(sqlMajorName);
                    majors1.add(0, "");
                    DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(majors1);
                    comboBoxMajorUpdate.setModel(model1);
                    String sqlClassName = "select Class_name from class where class.Major_no = " +
                            "(select major.Major_no from major where Major_name = '" + comboBoxMajorUpdate.getSelectedItem()
                            + "')";

                    Vector<String> Classes = studentDao.selectClassName(sqlClassName);
                    Classes.add(0, "");
                    model1 = new DefaultComboBoxModel<>(Classes);
                    comboBoxClassUpdate.setModel(model1);
                });
                panel.add(comboBoxCollegeUpdate, "cell 2 4 2 1,growx");


                JLabel lblNewLabel_2 = new JLabel("专业");
                lblNewLabel_2.setFont(font);
                panel.add(lblNewLabel_2, "cell 5 4 2 1,alignx center");
                comboBoxMajorUpdate.setFont(font);
                comboBoxMajorUpdate.addItemListener(e1 -> {
                    int join = Integer.parseInt(Objects.requireNonNull(textFieldSjoin.getText()).toString().replaceAll("年", "")) - 2000;
                    String sqlClassName = "select Class_name from class " +
                            "where Class_name like '%" + join + "%' " +
                            "and class.Major_no = " +
                            "(select major.Major_no from major where Major_name = '" + comboBoxMajorUpdate.getSelectedItem()
                            + "')";
                    Vector<String> Classes = studentDao.selectClassName(sqlClassName);
                    Classes.add(0, "");
                    DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(Classes);
                    comboBoxClassUpdate.setModel(model2);
                });
                panel.add(comboBoxMajorUpdate, "cell 7 4,growx");

                JLabel labelClassUpdate = new JLabel();
                labelClassUpdate.setFont(font);
                panel.add(labelClassUpdate, "cell 1 5,alignx center");

                comboBoxClassUpdate.setFont(font);
                panel.add(comboBoxClassUpdate, "cell 2 5 2 1,growx");

                comboBoxCollegeUpdate.setSelectedItem(table.getValueAt(table.getSelectedRow(), 8));
                comboBoxMajorUpdate.setSelectedItem(table.getValueAt(table.getSelectedRow(), 6));
                comboBoxClassUpdate.setSelectedItem(table.getValueAt(table.getSelectedRow(), 7));

                JButton buttonUpdateStudent = new JButton("修改");
                buttonUpdateStudent.setFont(font);
                buttonUpdateStudent.addActionListener(e1 -> {
                    String class_no = studentDao.getClassByName((String) comboBoxClassUpdate.getSelectedItem());
                    String sql = "update student " +
                            "set Student_no = '" + textFieldSno.getText() + "'," +
                            "Student_name = '" + textFieldSname.getText() + "'," +
                            "Student_sex = '" + comboBoxSsex.getSelectedItem() + "'," +
                            "Student_location = '" + textFieldSlocation.getText() + "'," +
                            "Student_birth = '" + textFieldSbirth.getText() + "'," +
                            "Student_join = '" + textFieldSjoin.getText() + "'," +
                            "Class_no = '" + class_no + "' " +
                            "where Student_no = '" + Sno + "' ";

                    if (studentDao.updateStudent(sql)) {
////                        StudentList.remove(table.getSelectedRow());
//                        Student student = new Student();
//                        student.setSname(textFieldSname.getText());
//                        student.setSno(Sno);
//                        student.setSbirth(textFieldSbirth.getText());
//                        student.setSlocation(textFieldSlocation.getText());
//                        student.setSsex((String) comboBoxSsex.getSelectedItem());
//                        student.setSjoin(textFieldSjoin.getText());
//                        student.setSclass((String) comboBoxClassUpdate.getSelectedItem());
//                        student.setSMajor((String) comboBoxMajorUpdate.getSelectedItem());
//                        student.setSCollege((String) comboBoxCollegeUpdate.getSelectedItem());
////                        StudentList.add(student);
                        tableModel.setRows(studentDao.selectStudent(sqlAll));
                        tableModel.fireTableDataChanged();
                        table.setModel(tableModel);
                        JLabel Massage = new JLabel("修改成功！");
                        Massage.setFont(font);
                        JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
                        updateFrame.setVisible(false);
                    } else {
                        JLabel Massage = new JLabel("修改失败");
                        Massage.setFont(font);
                        JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
                    }
                });
                panel.add(buttonUpdateStudent, "cell 3 9,growx");

                JButton buttonConcel = new JButton("取消");
                buttonConcel.setFont(font);
                panel.add(buttonConcel, "cell 5 9,growx");
                buttonConcel.addActionListener(e1 -> {
                    updateFrame.setVisible(false);
                });


                updateFrame.setVisible(true);
            } catch (ArrayIndexOutOfBoundsException ex) {
                JLabel Massage = new JLabel("未选中学生信息！");
                Massage.setFont(font);
                JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);

            }
        });

        JButton buttonDeleteStudent = new JButton("删除学生信息");
        buttonDeleteStudent.setFont(font);
        buttonDeleteStudent.setForeground(new Color(255, 255, 255));
        buttonDeleteStudent.setBackground(new Color(70, 132, 195));
        buttonDeleteStudent.addActionListener(e -> {
            try {
                String Sno = (String) table.getValueAt(table.getSelectedRow(), 0);

                JLabel Massage = new JLabel("是否删除该学生信息？");
                Massage.setFont(font);
                if (JOptionPane.showConfirmDialog(this, Massage, "提示",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    if (studentDao.deleteStudentBySno(Sno)) {

                        tableModel.setRows(studentDao.selectStudent(sqlAll));
                        tableModel.fireTableDataChanged();
                        table.setModel(tableModel);
                        JLabel deleteMassage = new JLabel("删除成功");
                        Massage.setFont(font);
                        JOptionPane.showMessageDialog(this, deleteMassage, "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JLabel deleteMassage = new JLabel("删除失败");
                        Massage.setFont(font);
                        JOptionPane.showMessageDialog(this, deleteMassage, "提示", JOptionPane.ERROR_MESSAGE);
                    }

                }
            } catch (ArrayIndexOutOfBoundsException e1) {
                JLabel Massage = new JLabel("未选中学生信息！");
                Massage.setFont(font);
                JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton buttonAddStudent = new JButton("新增学生信息");
        buttonAddStudent.setFont(font);
        buttonAddStudent.setForeground(new Color(255, 255, 255));
        buttonAddStudent.setBackground(new Color(70, 132, 195));
        buttonAddStudent.addActionListener(e -> {
            JComboBox<String> comboBoxMajorAdd;
            JComboBox<String> comboBoxClassAdd;
            JComboBox<String> comboBoxCollegeAdd;

            JFrame addStudentFrame = new JFrame();
            addStudentFrame.setTitle("新增学生信息");
            addStudentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            addStudentFrame.setSize(1600, 800);

            addStudentFrame.setSize(new Dimension(900, 600));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int x = (int) (toolkit.getScreenSize().getWidth() - addStudentFrame.getWidth()) / 2;
            int y = (int) (toolkit.getScreenSize().getHeight() - addStudentFrame.getHeight()) / 2;
            addStudentFrame.setLocation(x, y);

            JPanel panel = new JPanel();
            addStudentFrame.getContentPane().add(panel, BorderLayout.CENTER);

            UpPanel upPanel1 = new UpPanel(" 新增学生信息");
            upPanel1.buttonBack.setVisible(false);
            addStudentFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);


            panel.setLayout(new MigLayout("", "[grow][:80px:120px,grow,right][grow][:120px:120px,center][grow][:120px:120px][center][160px,grow,left][grow]", "[grow,fill][grow,center][grow,center][grow,center][grow,center][grow,center][grow][grow][grow][grow][grow]"));

            JLabel labelSno = new JLabel("学号");
            labelSno.setFont(font);
            panel.add(labelSno, "cell 1 1,alignx center");

            JTextField textFieldSno = new JTextField();
            textFieldSno.setFont(font);

            panel.add(textFieldSno, "cell 2 1 2 1,growx");

            JLabel labelSname = new JLabel("姓名");
            labelSname.setFont(font);
            panel.add(labelSname, "cell 5 1 2 1,alignx center");

            JTextField textFieldSname = new JTextField();
            textFieldSname.setFont(font);
            panel.add(textFieldSname, "cell 7 1,growx");

            JLabel labelSsex = new JLabel("性别");
            labelSsex.setFont(font);
            panel.add(labelSsex, "cell 1 2,alignx center");

            JComboBox<String> comboBoxSsex = new JComboBox<>();
            comboBoxSsex.setModel(new DefaultComboBoxModel<>(new String[]{"", "男", "女"}));
            comboBoxSsex.setFont(new Font("黑体", Font.PLAIN, 20));
            panel.add(comboBoxSsex, "cell 2 2 2 1,growx");

            JLabel labelSlocation = new JLabel("籍贯");
            labelSlocation.setFont(font);
            panel.add(labelSlocation, "cell 5 2 2 1,alignx center");

            JTextField textFieldSlocation = new JTextField();
            textFieldSlocation.setFont(font);
            textFieldSlocation.setColumns(10);
            panel.add(textFieldSlocation, "cell 7 2,growx");

            JLabel labelSbirth = new JLabel("出生日期");
            labelSbirth.setFont(font);
            panel.add(labelSbirth, "cell 1 3,alignx center");

            JTextField textFieldSbirth = new JTextField();
            textFieldSbirth.setFont(font);
            panel.add(textFieldSbirth, "cell 2 3 2 1,growx");
            textFieldSbirth.setColumns(10);

            JLabel labelSjoin = new JLabel("入学年份");
            labelSjoin.setFont(font);
            panel.add(labelSjoin, "cell 5 3 2 1,alignx center");

            String[] years = {"", Calendar.getInstance().get(Calendar.YEAR) - 3 + "年", Calendar.getInstance().get(Calendar.YEAR) - 2 + "年", Calendar.getInstance().get(Calendar.YEAR) - 1 + "年", Calendar.getInstance().get(Calendar.YEAR) + "年"};
            JComboBox<String> comboBoxSjoin = new JComboBox<>(years);
            comboBoxSjoin.setFont(font);

            panel.add(comboBoxSjoin, "cell 7 3,growx");


            JLabel labelClassAdd = new JLabel("班级");
            labelClassAdd.setFont(font);
            panel.add(labelClassAdd, "cell 1 5,alignx center");

            comboBoxClassAdd = new JComboBox<>();
            comboBoxClassAdd.setFont(font);
            panel.add(comboBoxClassAdd, "cell 2 5 2 1,growx");

            JLabel labelMajorAdd = new JLabel("专业");
            labelMajorAdd.setFont(font);
            panel.add(labelMajorAdd, "cell 5 4 2 1,alignx center");

            comboBoxMajorAdd = new JComboBox<>();

            comboBoxMajorAdd.setFont(font);
            comboBoxMajorAdd.addItemListener(e1 -> {
                String sqlClassName;
                if (!Objects.equals(comboBoxSjoin.getSelectedItem(), "")) {
                    int join = Integer.parseInt(Objects.requireNonNull(comboBoxSjoin.getSelectedItem()).toString().replaceAll("年", "")) - 2000;
                    sqlClassName = "select Class_name from class " +
                            "where Class_name like '%" + join + "%' " +
                            "and class.Major_no = " +
                            "(select major.Major_no from major where Major_name = '" + comboBoxMajorAdd.getSelectedItem()
                            + "')";
                } else {
                    sqlClassName = "select Class_name from class " +
                            "where class.Major_no = " +
                            "(select major.Major_no from major where Major_name = '" + comboBoxMajorAdd.getSelectedItem()
                            + "')";
                }

                Vector<String> Classes = studentDao.selectClassName(sqlClassName);
                Classes.add(0, "");
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(Classes);
                comboBoxClassAdd.setModel(model);

            });
            panel.add(comboBoxMajorAdd, "cell 7 4,growx");

            JLabel labelCollege = new JLabel("学院");
            labelCollege.setFont(font);
            panel.add(labelCollege, "cell 1 4,alignx center");

            comboBoxCollegeAdd = new JComboBox<>();

            comboBoxCollegeAdd.setFont(font);
//            String sqlCollegeName1 = "select College_name from college";

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(colleges);
            comboBoxCollegeAdd.setModel(model);
            comboBoxCollegeAdd.addItemListener(e1 -> {
                String sqlMajorName = "select Major_name from major where major.College_no = " +
                        "(select college.College_no from college where College_name = '" + comboBoxCollegeAdd.getSelectedItem()
                        + "')";
                Vector<String> majors = studentDao.selectMajorName(sqlMajorName);
                majors.add(0, "");

                DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(majors);
                comboBoxMajorAdd.setModel(model1);
                String sqlClassName = "select Class_name from class where class.Major_no = " +
                        "(select major.Major_no from major where Major_name = '" + comboBoxMajorAdd.getSelectedItem()
                        + "')";
                Vector<String> Classes = studentDao.selectClassName(sqlClassName);
                Classes.add(0, "");
                model1 = new DefaultComboBoxModel<>(Classes);
                comboBoxClassAdd.setModel(model1);
            });

            panel.add(comboBoxCollegeAdd, "cell 2 4 2 1,growx");

            comboBoxSjoin.addItemListener(e1 -> {
                int join = Integer.parseInt(Objects.requireNonNull(comboBoxSjoin.getSelectedItem()).toString().replaceAll("年", "")) - 2000;
                String sqlClassName = "select Class_name from class " +
                        "where Class_name like '%" + join + "%' " +
                        "and class.Major_no = " +
                        "(select major.Major_no from major where Major_name = '" + comboBoxMajorAdd.getSelectedItem()
                        + "')";
                Vector<String> Classes = studentDao.selectClassName(sqlClassName);
                Classes.add(0, "");
                DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(Classes);
                comboBoxClassAdd.setModel(model1);
            });
            comboBoxCollegeAdd.setSelectedItem(comboBoxCollege.getSelectedItem());
            comboBoxMajorAdd.setSelectedItem(comboBoxMajor.getSelectedItem());
            comboBoxClassAdd.setSelectedItem(comboBoxClass.getSelectedItem());

            JButton buttonAdd = new JButton("添加");
            buttonAdd.setFont(font);
            buttonAdd.addActionListener(e1 -> {
                String Sno = textFieldSno.getText();
                String Sname = textFieldSname.getText();
                String Ssex = (String) comboBoxSsex.getSelectedItem();
                String Sbirth = textFieldSbirth.getText();
                String Slocation = textFieldSlocation.getText();
                String Sjoin = (String) comboBoxSjoin.getSelectedItem();
                String Sgraduate;
                switch (Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(Sjoin.toString().replace("年", ""))) {
                    case 0:
                        Sgraduate = "大一本科在读";
                        break;
                    case 1:
                        Sgraduate = "大二本科在读";
                        break;
                    case 2:
                        Sgraduate = "大三本科在读";
                        break;
                    case 3:
                        Sgraduate = "大四本科在读";
                        break;
                    default:
                        Sgraduate = "大四本科在读（延迟）";
                        break;
                }
                ;
                String Major_no = studentDao.getMajorByName((String) comboBoxMajorAdd.getSelectedItem());
                String Class_no = studentDao.getClassByName((String) comboBoxClassAdd.getSelectedItem());
                String sql = "insert into student " +
                        "values ('" +
                        Sno + "','" +
                        Sname + "','" +
                        Ssex + "','" +
                        Slocation + "','" +
                        Sbirth + "','" +
                        Sjoin + "','" +
                        Sno + "','" +
                        Class_no + "','" +
                        Sgraduate + "'" +
                        ")";
                if (studentDao.insertStudent(sql)) {
//                    Student student = new Student();
//                    student.setSname(Sname);
//                    student.setSno(Sno);
//                    student.setSbirth(Sbirth);
//                    student.setSlocation(Slocation);
//                    student.setSsex(Ssex);
//                    student.setSjoin(Sjoin);
//                    student.setSclass((String) comboBoxClassAdd.getSelectedItem());
//                    student.setSMajor((String) comboBoxMajorAdd.getSelectedItem());
//                    student.setSCollege((String) comboBoxCollegeAdd.getSelectedItem());
//                    StudentList.add(student);
                    tableModel.setRows(studentDao.selectStudent(sqlAll));

                    tableModel.fireTableDataChanged();
                    table.setModel(tableModel);
                    JLabel Massage = new JLabel("添加成功！");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JLabel Massage = new JLabel("添加失败");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
                }
            });
            panel.add(buttonAdd, "cell 3 9,growx");

            JButton buttonConcel = new JButton("取消");
            buttonConcel.setFont(font);
            buttonConcel.addActionListener(e1 -> {
                textFieldSno.setText("");
                textFieldSname.setText("");
                textFieldSlocation.setText("");
                textFieldSbirth.setText("");
                comboBoxSsex.setSelectedItem("");
            });
            panel.add(buttonConcel, "cell 5 9,growx");

            addStudentFrame.setVisible(true);
        });


        mainPanel.add(buttonSelect, "cell 10 1,grow");
        mainPanel.add(buttonAddStudent, "cell 12 1,grow");
        mainPanel.add(buttonDeleteStudent, "cell 14 1,growy");
        mainPanel.add(buttonUpdate, "cell 16 1,grow");
        mainPanel.add(jScrollPane, "cell 0 3 18 9,grow");

        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("编辑学籍信息");
        upPanel.buttonBack.addActionListener(e -> AdministratorFrame.card.show(AdministratorFrame.contentPanelCenter, "bgPanel"));
        this.add(upPanel, BorderLayout.NORTH);
    }
}
