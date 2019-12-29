package gui.TeacherFrame;

import dal.Entity.Teacher;
import dal.daoimpl.TeacherDaoImpl;
import gui.UpPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {
    private String TID;

    public UserPanel(String TID) {
        super();
        this.TID = TID;
        setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();
    }

    private void addMainPanel() {
        String sql = "SELECT * " +
                "FROM teacher " +
                "WHERE Teacher_no= " + TID;
        TeacherDaoImpl teacherDao = new TeacherDaoImpl();
        List<Teacher> teacherList = teacherDao.selectTeacher(sql);

        JPanel mainPanel = new JPanel(null);
        this.add(mainPanel, BorderLayout.CENTER);
        Font font = new Font("黑体", Font.PLAIN, 20);

        JLabel labelPic = new JLabel();
        labelPic.setBounds(100, 80, 50, 50);
        ImageIcon imageIcon = new ImageIcon("src/Picture/男同学.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        labelPic.setIcon(imageIcon);
        mainPanel.add(labelPic);

        JLabel labelTno = new JLabel("教师号");
        labelTno.setFont(font);
        labelTno.setBounds(200, 90, 100, 30);
        mainPanel.add(labelTno);

        JTextField textFieldTno = new JTextField();
        /**教师编号*/
        textFieldTno.setText(teacherList.get(0).getTeacher_no());
        textFieldTno.setFont(font);
        textFieldTno.setBounds(280, 90, 160, 30);
        textFieldTno.setEditable(false);
        textFieldTno.setColumns(10);
        mainPanel.add(textFieldTno);

        JLabel labelTsex = new JLabel("性别");
        labelTsex.setFont(font);
        labelTsex.setBounds(200, 160, 45, 30);
        mainPanel.add(labelTsex);

        JTextField textFieldTsex = new JTextField();
        textFieldTsex.setFont(font);
        /**教师性别*/
        textFieldTsex.setText(teacherList.get(0).getTeacher_sex());
        textFieldTsex.setEditable(false);
        textFieldTsex.setColumns(10);
        textFieldTsex.setBounds(280, 160, 160, 30);
        mainPanel.add(textFieldTsex);

        JLabel labelTlocation = new JLabel("籍贯");
        labelTlocation.setFont(font);
        labelTlocation.setBounds(200, 230, 45, 30);
        mainPanel.add(labelTlocation);

        JTextField textFieldTlocation = new JTextField();
        textFieldTlocation.setColumns(10);
        /**教师籍贯*/
        textFieldTlocation.setFont(font);
        textFieldTlocation.setText(teacherList.get(0).getTeacher_location());
        textFieldTlocation.setEditable(false);
        textFieldTlocation.setBounds(280, 230, 160, 30);
        mainPanel.add(textFieldTlocation);

        JLabel labelTname = new JLabel("姓名");
        labelTname.setFont(font);
        labelTname.setBounds(500, 90, 100, 30);
        mainPanel.add(labelTname);

        JTextField textFieldTname = new JTextField();
        textFieldTname.setColumns(10);
        textFieldTname.setFont(font);
        /**教师姓名*/
        textFieldTname.setText(teacherList.get(0).getTeacher_name());
        textFieldTname.setEditable(false);
        textFieldTname.setBounds(600, 90, 160, 30);
        mainPanel.add(textFieldTname);

        JLabel labelTbirth = new JLabel("出生年月");
        labelTbirth.setFont(font);
        labelTbirth.setBounds(500, 160, 100, 30);
        mainPanel.add(labelTbirth);

        JTextField textFieldTbirth = new JTextField();
        textFieldTbirth.setEditable(false);
        textFieldTbirth.setColumns(10);
        /**教师的出生年月*/
        textFieldTbirth.setFont(font);
        textFieldTbirth.setText(teacherList.get(0).getTeacher_birth());
        textFieldTbirth.setBounds(600, 160, 160, 30);
        mainPanel.add(textFieldTbirth);

        JLabel labelTjoin = new JLabel("入职年份");
        labelTjoin.setFont(font);
        labelTjoin.setBounds(500, 230, 100, 30);
        mainPanel.add(labelTjoin);

        JTextField textFieldTjoin = new JTextField();
        textFieldTjoin.setColumns(10);
        /**教师的入职年份*/
        textFieldTjoin.setText(teacherList.get(0).getTeacher_join());
        textFieldTjoin.setEditable(false);
        textFieldTjoin.setFont(font);
        textFieldTjoin.setBounds(600, 230, 160, 30);
        mainPanel.add(textFieldTjoin);

//        JLabel lblNewLabel_5 = new JLabel("New label");
//        lblNewLabel_5.setBounds(158, 582, 45, 13);
//        mainPanel.add(lblNewLabel_5);
//
//        JTable table_1 = new JTable();
//        table_1.setBounds(213, 451, 625, 234);
//        mainPanel.add(table_1);

    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("用户信息");
        upPanel.buttonBack.addActionListener(e -> {
            TeacherFrame.card.show(TeacherFrame.contentPanelCenter, "bgPanel");
        });
        this.add(upPanel, BorderLayout.NORTH);


    }

}
