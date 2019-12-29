package gui.AdministratorFrame;

import dal.Entity.Student;
import dal.daoimpl.StudentDaoImpl;
import gui.SimpleTableModel;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SelectPanel extends JPanel {
    private StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
    private JComboBox<String> comboBoxMajor;
    private JComboBox<String> comboBoxClass;

    public SelectPanel() {
        super();
        this.setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();

    }

    private void addMainPanel() {
        JPanel mainPanel = new JPanel();
        Dimension preferSize = new Dimension(1610, 860);
        mainPanel.setPreferredSize(preferSize);
        mainPanel.setMaximumSize(preferSize);
        mainPanel.setMinimumSize(preferSize);
        mainPanel.setLayout(new MigLayout("",
                "[::100px,grow,center][::50px,grow,right][160px,grow][::50px,grow,right][160px,grow][::50px,grow,right][160px,grow][::50px,grow,right][160px,grow][::50px,grow,right][160px,grow][::20px,grow][120px:n:120px,grow,fill][::20px,grow][120px:n:120px,grow,center][::20px,grow]",
                "[:80px:80px,grow][::60px,grow][::60px,grow,center][::60px,grow][grow][grow]"));


        Font font = new Font("黑体", Font.PLAIN, 20);
        JTable table = new JTable();
        JScrollPane jScrollPane = new JScrollPane(table);
        table.setRowHeight(40);
        table.setFont(font);
        table.getTableHeader().setFont(font);
        JLabel labelPic = new JLabel();
        labelPic.setBounds(40, 50, 50, 50);
        ImageIcon imageIcon = new ImageIcon("src/Picture/分析.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        labelPic.setIcon(imageIcon);
        mainPanel.add(labelPic, "cell 0 0");

        JLabel labelSchool = new JLabel("学院");
        labelSchool.setFont(font);
        mainPanel.add(labelSchool, "cell 1 0");

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
        mainPanel.add(comboBoxSchool, "cell 2 0,growx");

        JLabel labelMajor = new JLabel("专业");
        labelMajor.setFont(font);

        mainPanel.add(labelMajor, "cell 3 0");


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
        mainPanel.add(comboBoxMajor, "cell 4 0,growx");

        JLabel labelClass = new JLabel("班级");
        labelClass.setFont(font);
        mainPanel.add(labelClass, "cell 5 0");

        comboBoxClass = new JComboBox<>();
        comboBoxClass.setFont(font);
        mainPanel.add(comboBoxClass, "cell 6 0,growx");


        JLabel labelSno = new JLabel("学号");
        labelSno.setFont(font);
        mainPanel.add(labelSno, "cell 7 0,alignx center");

        JTextField textFieldSno = new JTextField();
        textFieldSno.setFont(font);
        mainPanel.add(textFieldSno, "cell 8 0,growx");
        textFieldSno.setColumns(13);

        JLabel labelSname = new JLabel("姓名");
        labelSname.setFont(font);
        mainPanel.add(labelSname, "cell 9 0");


        JTextField textFieldSname = new JTextField();
        textFieldSname.setFont(font);
        textFieldSname.setColumns(10);
        mainPanel.add(textFieldSname, "cell 10 0,growx");


        JButton buttonSelect = new JButton("查询");
        buttonSelect.setFont(font);
        buttonSelect.setForeground(new Color(255, 255, 255));
        buttonSelect.setBackground(new Color(70, 132, 195));
        buttonSelect.setHorizontalTextPosition(SwingConstants.CENTER);
        mainPanel.add(buttonSelect, "cell 12 0");

        JButton buttonSelectMore = new JButton("活动与奖惩");
        buttonSelectMore.setFont(font);
        buttonSelectMore.setForeground(new Color(255, 255, 255));
        buttonSelectMore.setBackground(new Color(70, 132, 195));
        buttonSelectMore.setHorizontalTextPosition(SwingConstants.CENTER);
        mainPanel.add(buttonSelectMore, "cell 14 0");

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
        studentDaoImpl = new StudentDaoImpl();
//        String sql="SELECT Student_no,Student_name,Student_sex," +
//                "Student_location,Student_birth,Student_join," +
//                "Major_name,Class_name,College_name,Student_graduate " +
//                "FROM student,class,major,college " +
//                "WHERE student.Class_no=class.Class_no " +
//                "AND class.Major_no=major.Major_no " +
//                "AND major.College_no=college.College_no ";
        List<Student> rows = new ArrayList<>();
        SimpleTableModel<Student> simpleTableModel = new SimpleTableModel<>(cols, rows, "Student");
        table.setModel(simpleTableModel);
        table.setRowHeight(40);
        table.setFont(font);
        jScrollPane.setViewportView(table);
        setVisible(true);
        mainPanel.add(jScrollPane, "cell 0 1 16 5,grow");
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    JFrame selecetRPAFrame = new JFrame();
                    selecetRPAFrame.setTitle("查询奖惩与活动信息");
                    selecetRPAFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                    selecetRPAFrame.setSize(new Dimension(900, 900));
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    int x = (int) (toolkit.getScreenSize().getWidth() - selecetRPAFrame.getWidth()) / 2;
                    int y = (int) (toolkit.getScreenSize().getHeight() - selecetRPAFrame.getHeight()) / 2;
                    selecetRPAFrame.setLocation(x, y);

                    UpPanel upPanel1 = new UpPanel(" 查询奖惩与活动信息");
                    upPanel1.buttonBack.setVisible(false);
                    selecetRPAFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);

                    JPanel panel = new JPanel();
                    selecetRPAFrame.getContentPane().add(panel, BorderLayout.CENTER);
                    panel.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[50px:50px][grow][grow][50px:50px][grow][grow]"));

                    JLabel lblNewLabel = new JLabel("奖惩信息");
                    lblNewLabel.setFont(font);
                    panel.add(lblNewLabel, "cell 2 0,alignx center,aligny bottom");


                    JLabel labelSno2 = new JLabel();
                    labelSno2.setFont(font);
                    labelSno2.setText("学号： " + table.getValueAt(table.getSelectedRow(), 0));
                    panel.add(labelSno2, "cell 0 0");

                    JLabel labelSname2 = new JLabel();
                    labelSname2.setFont(font);
                    labelSname2.setText("姓名：" + table.getValueAt(table.getSelectedRow(), 1));
                    panel.add(labelSname2, "cell 1 0");

                    JScrollPane scrollPane_1 = new JScrollPane();
                    panel.add(scrollPane_1, "cell 0 1 5 2,grow");
                    Vector<String> cols2 = new Vector<>();
                    cols2.add("奖惩编号");
                    cols2.add("奖惩内容");
                    cols2.add("操作人");
                    cols2.add("获得时间");
                    JTable table_1 = new JTable();
                    table_1.setFont(font);
                    table_1.setRowHeight(40);
                    table_1.getTableHeader().setFont(font);
                    DefaultTableModel defaultTableModel = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    Vector<Vector<String>> rows2 = studentDaoImpl.selectRewardandpublish((String) table.getValueAt(table.getSelectedRow(), 0));
                    defaultTableModel.setDataVector(rows2, cols2);
                    table_1.setModel(defaultTableModel);
                    scrollPane_1.setViewportView(table_1);

                    JLabel lblNewLabel_1 = new JLabel("社会实践活动信息");
                    lblNewLabel_1.setFont(font);
                    panel.add(lblNewLabel_1, "cell 2 3,alignx center,aligny bottom");


                    Vector<Vector<String>> rows1;
                    Vector<String> cols1 = new Vector<>();
                    cols1.add("社会实践活动编号");
                    cols1.add("社会实践活动时间");
                    cols1.add("社会实践活动内容");

                    String sql = "SELECT activity.Activity_no,Activity_time,Activity_content " +
                            "FROM activity,studentactivity " +
                            "WHERE activity.Activity_no=studentactivity.Activity_no " +
                            "AND Student_no= '" + table.getValueAt(table.getSelectedRow(), 0) + "'";
                    StudentDaoImpl studentDao = new StudentDaoImpl();

                    rows1 = studentDao.selectActivity(sql);
                    DefaultTableModel defaultTableModel1 = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    defaultTableModel1.setDataVector(rows1, cols1);
                    JTable table_2 = new JTable();
                    table_2.setModel(defaultTableModel1);
                    table_2.getTableHeader().setFont(font);

                    table_2.setFont(font);
                    table_2.setRowHeight(40);
                    JScrollPane scrollPane = new JScrollPane();
                    scrollPane.setViewportView(table_2);
                    panel.add(scrollPane, "cell 0 4 5 2,grow");


                    selecetRPAFrame.setVisible(true);
                }
            }
        });

        //查询按钮: 按条件查询
        buttonSelect.addActionListener(exx -> {
            System.out.println("A");
            List<Student> rows2;
            String College = (String) comboBoxSchool.getSelectedItem();
            String Major = (String) comboBoxMajor.getSelectedItem();
            String Class = (String) comboBoxClass.getSelectedItem();
            String SID = textFieldSno.getText();
            String Sname = textFieldSname.getText();
            studentDaoImpl = new StudentDaoImpl();
            //选择学院、专业、班级
            if (!"".equals(College) && !"".equals(Major) && !"".equals(Class)) {
                String sql1;
                if (!"".equals(SID) && !"".equals(Sname)) {
                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join," +
                            "Major_name,Class_name,College_name,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND major.Major_name=" + "'" + Major + "' " +
                            "AND class.Class_name =" + "'" + Class + "' " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND student.Student_no = '" + SID + "' " +
                            "AND student.Student_name = '" + Sname + "'";
                } else if (!"".equals(SID)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND major.Major_name=" + "'" + Major + "' " +
                            "AND class.Class_name =" + "'" + Class + "' " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND student.Student_no = '" + SID + "' ";

                } else if (!"".equals(Sname)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND major.Major_name=" + "'" + Major + "' " +
                            "AND class.Class_name =" + "'" + Class + "' " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND student.Student_name = '" + Sname + "'";

                } else {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND major.Major_name=" + "'" + Major + "' " +
                            "AND class.Class_name =" + "'" + Class + "' " +
                            "AND college.College_name =" + "'" + College + "' ";

                }

                rows2 = studentDaoImpl.selectStudent(sql1);
                SimpleTableModel<Student> simpleTableModel2 = new SimpleTableModel<>(cols, rows2, "Student");
                table.setModel(simpleTableModel2);
                setVisible(true);
            }
            //选择学院
            else if (!"".equals(College) && "".equals(Major) && "".equals(Class)) {
                String sql1;
                if (!"".equals(SID) && !"".equals(Sname)) {
                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND student.Student_no = '" + SID + "' " +
                            "AND student.Student_name like '%" + Sname + "%'";
                } else if (!"".equals(SID)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND student.Student_no = '" + SID + "' ";

                } else if (!"".equals(Sname)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND student.Student_name like '%" + Sname + "%'";

                } else {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' ";

                }

                rows2 = studentDaoImpl.selectStudent(sql1);
                SimpleTableModel<Student> simpleTableModel2 = new SimpleTableModel<>(cols, rows2, "Student");
                table.setModel(simpleTableModel2);
                setVisible(true);
            } else if (!"".equals(College) && !"".equals(Major)) {
                String sql1;
                if (!"".equals(SID) && !"".equals(Sname)) {
                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND major.Major_name=" + "'" + Major + "' " +
                            "AND student.Student_no = '" + SID + "' " +
                            "AND student.Student_name like '%" + Sname + "%'";
                } else if (!"".equals(SID)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND major.Major_name=" + "'" + Major + "' " +
                            "AND student.Student_no = '" + SID + "' ";

                } else if (!"".equals(Sname)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND major.Major_name=" + "'" + Major + "' " +
                            "AND student.Student_name like '%" + Sname + "%'";

                } else {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND college.College_name =" + "'" + College + "' " +
                            "AND major.Major_name=" + "'" + Major + "' ";

                }
                rows2 = studentDaoImpl.selectStudent(sql1);
                SimpleTableModel<Student> simpleTableModel2 = new SimpleTableModel<>(cols, rows2, "Student");
                table.setModel(simpleTableModel2);
                setVisible(true);
            } else {
                String sql1;
                if (!"".equals(SID) && !"".equals(Sname)) {
                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND student.Student_no = '" + SID + "' " +
                            "AND student.Student_name like '%" + Sname + "%'";
                } else if (!"".equals(SID)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND student.Student_no = '" + SID + "' ";

                } else if (!"".equals(Sname)) {

                    sql1 = "SELECT Student_no,Student_name,Student_sex, " +
                            "Student_location,Student_birth,Student_join, " +
                            "Major_name,Class_name,College_name ,Student_graduate " +
                            "FROM student,class,major,college " +
                            "WHERE student.Class_no=class.Class_no " +
                            "AND class.Major_no=major.Major_no " +
                            "AND major.College_no=college.College_no " +
                            "AND student.Student_name like '%" + Sname + "%'";

                } else {
                    JLabel Massage = new JLabel("未选中查询信息！");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<Student> rows1 = studentDaoImpl.selectStudent(sql1);
                SimpleTableModel<Student> simpleTableModel2 = new SimpleTableModel<>(cols, rows1, "Student");
                table.setModel(simpleTableModel2);
            }
        });

        buttonSelectMore.addActionListener(e -> {
            JFrame selecetRPAFrame = new JFrame();
            selecetRPAFrame.setTitle("查询奖惩与活动信息");
            selecetRPAFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            selecetRPAFrame.setSize(new Dimension(900, 900));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int x = (int) (toolkit.getScreenSize().getWidth() - selecetRPAFrame.getWidth()) / 2;
            int y = (int) (toolkit.getScreenSize().getHeight() - selecetRPAFrame.getHeight()) / 2;
            selecetRPAFrame.setLocation(x, y);

            UpPanel upPanel1 = new UpPanel(" 查询奖惩与活动信息");
            upPanel1.buttonBack.setVisible(false);
            selecetRPAFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);

            JPanel panel = new JPanel();
            selecetRPAFrame.getContentPane().add(panel, BorderLayout.CENTER);
            panel.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[50px:50px][grow][grow][50px:50px][grow][grow]"));

            JLabel lblNewLabel = new JLabel("奖惩信息");
            lblNewLabel.setFont(font);
            panel.add(lblNewLabel, "cell 2 0,alignx center,aligny bottom");


            JLabel labelSno2 = new JLabel();
            labelSno2.setFont(font);
            labelSno2.setText("学号： " + table.getValueAt(table.getSelectedRow(), 0));
            panel.add(labelSno2, "cell 0 0");

            JLabel labelSname2 = new JLabel();
            labelSname2.setFont(font);
            labelSname2.setText("姓名：" + table.getValueAt(table.getSelectedRow(), 1));
            panel.add(labelSname2, "cell 1 0");

            JScrollPane scrollPane_1 = new JScrollPane();
            panel.add(scrollPane_1, "cell 0 1 5 2,grow");
            Vector<String> cols2 = new Vector<>();
            cols2.add("奖惩编号");
            cols2.add("奖惩内容");
            cols2.add("操作人");
            cols2.add("获得时间");
            JTable table_1 = new JTable();
            table_1.setFont(font);
            table_1.setRowHeight(40);
            table_1.getTableHeader().setFont(font);
            DefaultTableModel defaultTableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Vector<Vector<String>> rows2 = studentDaoImpl.selectRewardandpublish((String) table.getValueAt(table.getSelectedRow(), 0));
            defaultTableModel.setDataVector(rows2, cols2);
            table_1.setModel(defaultTableModel);
            scrollPane_1.setViewportView(table_1);

            JLabel lblNewLabel_1 = new JLabel("社会实践活动信息");
            lblNewLabel_1.setFont(font);
            panel.add(lblNewLabel_1, "cell 2 3,alignx center,aligny bottom");


            Vector<Vector<String>> rows1;
            Vector<String> cols1 = new Vector<>();
            cols1.add("社会实践活动编号");
            cols1.add("社会实践活动时间");
            cols1.add("社会实践活动内容");

            String sql = "SELECT activity.Activity_no,Activity_time,Activity_content " +
                    "FROM activity,studentactivity " +
                    "WHERE activity.Activity_no=studentactivity.Activity_no " +
                    "AND Student_no= '" + table.getValueAt(table.getSelectedRow(), 0) + "'";
            StudentDaoImpl studentDao = new StudentDaoImpl();

            rows1 = studentDao.selectActivity(sql);
            DefaultTableModel defaultTableModel1 = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            defaultTableModel1.setDataVector(rows1, cols1);
            JTable table_2 = new JTable();
            table_2.setModel(defaultTableModel1);
            table_2.getTableHeader().setFont(font);

            table_2.setFont(font);
            table_2.setRowHeight(40);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(table_2);
            panel.add(scrollPane, "cell 0 4 5 2,grow");


            selecetRPAFrame.setVisible(true);
        });
        this.add(mainPanel, BorderLayout.CENTER);
    }


    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("查询学生信息");
        upPanel.buttonBack.addActionListener(e -> AdministratorFrame.card.show(AdministratorFrame.contentPanelCenter, "bgPanel"));
        this.add(upPanel, BorderLayout.NORTH);
    }
}
