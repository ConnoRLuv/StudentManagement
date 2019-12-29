package gui;

import dal.daoimpl.AdministratorDaoImpl;
import dal.daoimpl.StudentDaoImpl;
import dal.daoimpl.TeacherDaoImpl;
import gui.AdministratorFrame.AdministratorFrame;
import gui.StudentFrame.StudentFrame;
import gui.TeacherFrame.TeacherFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;


public class LoginFrame extends JFrame {
    private JPanel contentPanel;
    private JTextField UserId;
    private JTextField UserPassword;
    private JPanel loginPanel;                                                      //登录面板
    private ButtonGroup buttonGroup;                                                //单选按钮组
    private JRadioButton teacherRadioButton, studentRadioButton, adminRadioButton;   //单选按钮


    public LoginFrame() {
        this.setTitle("学生信息管理系统登陆首页");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1600, 900);
        this.setLocationRelativeTo(null);
//        this.setResizable(false);
        contentPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon lib = new ImageIcon("src/Picture/lib.png");
                g.drawImage(lib.getImage(), 0, 0,
                        contentPanel.getWidth(),
                        contentPanel.getHeight(),
                        contentPanel);

                ImageIcon logo_w = new ImageIcon("src/Picture/logo-w.png");
                g.drawImage(logo_w.getImage(), 10, 15,
                        logo_w.getIconWidth(),
                        logo_w.getIconHeight(),
                        contentPanel);
            }
        };
        setContentPane(contentPanel);
        contentPanel.setLayout(new MigLayout("", "[grow][grow,center][grow]", "[grow][87.00,grow,center][grow]"));

        buttonGroup = new ButtonGroup();
        loginPanel = new JPanel();

        loginPanel.setBackground(new Color(255, 255, 255, 230));
        loginPanel.setPreferredSize(new Dimension(800, 600));
        loginPanel.setMaximumSize(new Dimension(800, 600));
        loginPanel.setMinimumSize(new Dimension(800, 600));

        loginPanel.setBounds(this.getWidth() / 2 - 400, this.getHeight() / 2 - 300, 800, 600);
        loginPanel.setLayout(new MigLayout("", "[100px:n,grow][][grow][grow][][grow][grow][][100px:n,grow]", "[grow][][30px:n][::100px][30px:n][30px,center][30px:n][30px][30px:n][50px:n][grow]"));

        Font font = new Font("黑体", Font.PLAIN, 20);
        JLabel label = new JLabel("选择身份");
        label.setFont(font);
        loginPanel.add(label, "cell 2 3,alignx center,aligny center");


        studentRadioButton = new JRadioButton("学生");
        studentRadioButton.setSelected(true);
        studentRadioButton.setFont(font);
        buttonGroup.add(studentRadioButton);
        loginPanel.add(studentRadioButton, "cell 4 3,alignx center,aligny center");


        adminRadioButton = new JRadioButton("其他");
        adminRadioButton.setFont(font);
        buttonGroup.add(adminRadioButton);
        loginPanel.add(adminRadioButton, "cell 5 3,alignx center,aligny center");

        JLabel InputID = new JLabel("账号");
        InputID.setFont(font);
        loginPanel.add(InputID, "cell 2 5,alignx center,aligny center");

        UserId = new JTextField();
        UserId.setFont(font);
        loginPanel.add(UserId, "cell 3 5 3 1,grow");

        JLabel InputPassword = new JLabel("密码");
        InputPassword.setFont(font);
        loginPanel.add(InputPassword, "cell 2 7,alignx center,aligny center");

        UserPassword = new JPasswordField();

        loginPanel.add(UserPassword, "cell 3 7 3 1,grow");


        JLabel Hello = new JLabel();
        Hello.setText("欢迎使用学籍管理系统");
        Hello.setFont(new Font("黑体", Font.PLAIN, 50));
        loginPanel.add(Hello, "cell 2 1 6 1,alignx center,aligny bottom");


        JButton buttonLogin = new JButton("登  陆");

        buttonLogin.setFont(font);
        buttonLogin.addActionListener(e -> {
            String id = UserId.getText();
            String password = UserPassword.getText();
            if (id.equals("") || password.equals("")) {
                JLabel Massage = new JLabel("用户名或密码不允许为空");
                Massage.setFont(font);
                JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (teacherRadioButton.isSelected()) {
                TeacherDaoImpl teacherDao = new TeacherDaoImpl();
                if (teacherDao.certifyTeacher(id, password)) {
                    this.setVisible(false);
                    TeacherFrame teacherFrame = new TeacherFrame(id);
                } else {
                    JLabel Massage = new JLabel("输入用户名或密码有误，请重新输入");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.WARNING_MESSAGE);
                }
            } else if (studentRadioButton.isSelected()) {
                StudentDaoImpl studentDao = new StudentDaoImpl();
                if (studentDao.certifyStudent(id, password)) {
                    this.setVisible(false);
                    StudentFrame studentFrame = new StudentFrame(id);
                } else {
                    JLabel Massage = new JLabel("输入用户名或密码有误，请重新输入");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.WARNING_MESSAGE);
                }
            } else if (adminRadioButton.isSelected()) {
                AdministratorDaoImpl administratorDao = new AdministratorDaoImpl();
                if (administratorDao.certifyAdministrator(id, password)) {
                    this.setVisible(false);
                    AdministratorFrame administratorFrame = new AdministratorFrame(id);
                } else {
                    JLabel Massage = new JLabel("输入用户名或密码有误，请重新输入");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                //                JOptionPane;
            }
        });
        loginPanel.add(buttonLogin, "cell 3 9,growx");


        contentPanel.add(loginPanel, "cell 1 1,alignx center,aligny center");

        teacherRadioButton = new JRadioButton("教师");
        teacherRadioButton.setFont(font);
        buttonGroup.add(teacherRadioButton);

        loginPanel.add(teacherRadioButton, "cell 3 3,alignx center,aligny center");

        JButton button = new JButton("取  消");
        button.setFont(font);
        button.addActionListener(e -> {
            buttonGroup.clearSelection();
            buttonGroup.setSelected(studentRadioButton.getModel(), true);
            UserId.setText("");
            UserPassword.setText("");
        });
        loginPanel.add(button, "cell 5 9,growx");

        setVisible(true);

    }

    //进行LoginFrame的测试
    public static void main(String[] args) {
        new LoginFrame();
    }
}
