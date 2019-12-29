package gui.StudentFrame;

import dal.Entity.Student;
import dal.daoimpl.StudentDaoImpl;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class UserPanel extends JPanel {
    private String SID;
    StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
    private JTable table;

    public UserPanel(String SID) {
        super();
        this.SID = SID;
        setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();
    }

    private void addMainPanel() {
        String sqlPersonalInformation;
        sqlPersonalInformation = "SELECT Student_no,Student_name,Student_sex," +
                "Student_location,Student_birth,Student_join," +
                "Class_name,Major_name,College_name, Student_graduate, Student_password " +
                "FROM student,class,major,college " +
                "WHERE student.Student_no = " + SID + " " +
                "AND student.Class_no=class.Class_no " +
                "AND class.Major_no=major.Major_no " +
                "AND major.College_no=college.College_no";
        List<Student> students = studentDaoImpl.selectStudent1(sqlPersonalInformation);

        JPanel mainPanel = new JPanel(null);


        Font font = new Font("黑体", Font.PLAIN, 20);

        JLabel labelPic = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/Picture/男同学.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        mainPanel.setLayout(new MigLayout("", "[grow][120px][60px:n][120px][grow][120px][grow][grow][grow][grow][grow][grow][20px]", "[20px][80px][80px][80px][80px][80px][grow][grow][20px]"));
        labelPic.setIcon(imageIcon);
        mainPanel.add(labelPic, "cell 0 1,grow");

        JLabel labelSno = new JLabel("学号");
        labelSno.setFont(font);
        mainPanel.add(labelSno, "cell 1 1,alignx left,aligny center");

        JTextField textFieldSno = new JTextField();
        textFieldSno.setEditable(false);
        textFieldSno.setFont(font);

        textFieldSno.setText(students.get(0).getSno());
        mainPanel.add(textFieldSno, "cell 2 1 2 1,growx,aligny center");


        JLabel lblNewLabel_5 = new JLabel("奖惩信息");
        lblNewLabel_5.setFont(font);
        mainPanel.add(lblNewLabel_5, "cell 8 0 1 2,alignx left,aligny center");

        JLabel labelSsex = new JLabel("性别");
        labelSsex.setFont(font);
        mainPanel.add(labelSsex, "cell 1 2,grow");

        String[] sex = {"男", "女"};
        JComboBox<String> comboBoxSsex = new JComboBox<>(sex);
        comboBoxSsex.setFont(font);
        //从数据库读取要登录该账号学生的性别
        comboBoxSsex.setSelectedItem(students.get(0).getSsex());
        mainPanel.add(comboBoxSsex, "cell 2 2 2 1,growx,aligny center");

        JLabel labelSlocation = new JLabel("籍贯");
        labelSlocation.setFont(font);
        mainPanel.add(labelSlocation, "cell 1 3,grow");

        JTextField textFieldSlocation = new JTextField();
        textFieldSlocation.setColumns(10);
        textFieldSlocation.setFont(font);
        //从数据库读取要登录该账号学生的籍贯
        textFieldSlocation.setText(students.get(0).getSlocation());
        mainPanel.add(textFieldSlocation, "cell 2 3 2 1,growx,aligny center");

        JLabel labelSname = new JLabel("姓名");
        labelSname.setFont(font);
        mainPanel.add(labelSname, "cell 5 1,growx,aligny center");

        JTextField textFieldSname = new JTextField();
        textFieldSname.setColumns(10);
        textFieldSname.setFont(font);
        //从数据库读取要登录该账号学生的姓名
        textFieldSname.setText(students.get(0).getSname());
        mainPanel.add(textFieldSname, "cell 6 1,growx,aligny center");

        JLabel labelSbirth = new JLabel("出生年月");
        labelSbirth.setFont(font);
        mainPanel.add(labelSbirth, "cell 5 2,grow");

        JTextField textFieldSbirth = new JTextField();
        textFieldSbirth.setFont(font);
        textFieldSbirth.setColumns(10);
        textFieldSbirth.setText(students.get(0).getSbirth());
        mainPanel.add(textFieldSbirth, "cell 6 2,growx,aligny center");

        JLabel labelSjoin = new JLabel("入学年份");
        labelSjoin.setFont(font);
        mainPanel.add(labelSjoin, "cell 5 3,grow");

        JTextField textFieldSjoin = new JTextField();
        textFieldSjoin.setColumns(10);
        textFieldSjoin.setEditable(false);
        textFieldSjoin.setFont(font);
        textFieldSjoin.setText(students.get(0).getSjoin());
        mainPanel.add(textFieldSjoin, "cell 6 3,growx,aligny center");

        JScrollPane scrollPane = new JScrollPane();
        mainPanel.add(scrollPane, "cell 8 2 4 6,grow");

        Vector<String> cols = new Vector<>();
        cols.add("奖惩编号");
        cols.add("奖惩内容");
        cols.add("操作人");
        cols.add("获得时间");
        table = new JTable();
        table.setFont(font);
        table.setRowHeight(40);
        table.getTableHeader().setFont(font);
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Vector<Vector<String>> rows = studentDaoImpl.selectRewardandpublish(SID);
        defaultTableModel.setDataVector(rows, cols);
        table.setModel(defaultTableModel);


        scrollPane.setViewportView(table);

        JLabel labelSschool = new JLabel("学院");
        labelSschool.setFont(font);
        mainPanel.add(labelSschool, "cell 1 4,alignx left,growy");

        JTextField textFieldSschool = new JTextField();
        textFieldSschool.setColumns(10);
        textFieldSschool.setFont(font);
        textFieldSschool.setText(students.get(0).getSCollege());
        textFieldSschool.setEditable(false);
        mainPanel.add(textFieldSschool, "cell 2 4 2 1,growx,aligny center");

        JLabel labelSmajor = new JLabel("专业");
        labelSmajor.setFont(font);
        mainPanel.add(labelSmajor, "cell 5 4,grow");

        JTextField textFieldSmajor = new JTextField();
        textFieldSmajor.setEditable(false);
        textFieldSmajor.setFont(font);
        textFieldSmajor.setColumns(10);
        //从数据库读取要登录该账号学生的专业*/
        textFieldSmajor.setText(students.get(0).getSMajor());
        mainPanel.add(textFieldSmajor, "cell 6 4,growx,aligny baseline");

        JLabel labelSclass = new JLabel("班级");
        labelSclass.setFont(font);
        mainPanel.add(labelSclass, "cell 1 5,alignx left,growy");
        //从数据库读取要登录该账号学生的班级*/

        JTextField textFieldSclass = new JTextField();
        textFieldSclass.setColumns(10);
        textFieldSclass.setEditable(false);
        textFieldSclass.setFont(font);
        textFieldSclass.setText(students.get(0).getSclass());
        mainPanel.add(textFieldSclass, "cell 2 5 2 1,growx,aligny center");

        JLabel labelSpassword = new JLabel("密码");
        labelSpassword.setFont(font);
        mainPanel.add(labelSpassword, "cell 5 5,alignx left,growy");
        //从数据库读取要登录该账号学生的班级*/

        JTextField textFieldSpassword = new JTextField();
        textFieldSpassword.setColumns(10);
        textFieldSpassword.setFont(font);
        textFieldSpassword.setText(students.get(0).getSpassword());
        mainPanel.add(textFieldSpassword, "cell 6 5,growx,aligny center");

        JButton buttonUpdate = new JButton("修改");
        buttonUpdate.setForeground(new Color(255, 255, 255));
        buttonUpdate.setBackground(new Color(70, 132, 195));
        buttonUpdate.setFont(font);
        buttonUpdate.addActionListener(e -> {
            String Sno = textFieldSno.getText();
            String Sname = textFieldSname.getText();
            String Ssex = (String) comboBoxSsex.getSelectedItem();
            String Sbirth = textFieldSbirth.getText();
            String Slocation = textFieldSlocation.getText();
            String Spassword = textFieldSpassword.getText();

            JLabel Massage = new JLabel("是否修改个人信息？");
            Massage.setFont(font);
            if (JOptionPane.showConfirmDialog(this, Massage, "提示", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
                String sql = "update student " +
                        "set Student_name = '" + Sname + "'," +
                        "Student_sex = '" + Ssex + "'," +
                        "Student_birth = '" + Sbirth + "'," +
                        "Student_location = '" + Slocation + "'," +
                        "Student_password = '" + Spassword + "' " +
                        "where Student_no = '" + Sno + "'";
                if (studentDaoImpl.updateStudent(sql)) {
                    JLabel Massage1 = new JLabel("修改成功！");
                    Massage1.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage1, "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JLabel Massage1 = new JLabel("修改失败！");
                    Massage1.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage1, "提示", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        mainPanel.add(buttonUpdate, "cell 3 6,growx");

        JButton buttonConcel = new JButton("取消");
        buttonConcel.setFont(font);
        buttonConcel.setForeground(new Color(255, 255, 255));
        buttonConcel.setBackground(new Color(70, 132, 195));
        buttonConcel.addActionListener(e -> {
            textFieldSname.setText(students.get(0).getSname());
            textFieldSlocation.setText(students.get(0).getSlocation());
            textFieldSbirth.setText(students.get(0).getSbirth());
            comboBoxSsex.setSelectedItem(students.get(0).getSsex());
            textFieldSpassword.setText(students.get(0).getSpassword());
        });
        mainPanel.add(buttonConcel, "cell 5 6,growx");

        JButton buttonRefresh = new JButton("刷新");
        buttonRefresh.setFont(font);
        buttonRefresh.setForeground(new Color(255, 255, 255));
        buttonRefresh.setBackground(new Color(70, 132, 195));
        buttonRefresh.addActionListener(e -> {
            Vector<Vector<String>> rows2 = studentDaoImpl.selectRewardandpublish(SID);
            defaultTableModel.setDataVector(rows2, cols);
            table.setModel(defaultTableModel);
        });
        mainPanel.add(buttonRefresh, "cell 11 1,alignx right");

        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("用户信息");
        upPanel.buttonBack.addActionListener(e -> StudentFrame.card.show(StudentFrame.contentPanelCenter, "bgPanel"));
        this.add(upPanel, BorderLayout.NORTH);
    }
}
