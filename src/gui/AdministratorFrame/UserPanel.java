package gui.AdministratorFrame;

import dal.Entity.Administrator;
import dal.daoimpl.AdministratorDaoImpl;
import gui.UpPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {
    private String AID;

    public UserPanel(String AID) {
        super();
        this.AID = AID;
        setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();
    }

    private void addMainPanel() {
        String sql = "SELECT * " +
                "FROM administrator " +
                "WHERE Admin_no = " + AID;
        AdministratorDaoImpl administratorDao = new AdministratorDaoImpl();
        List<Administrator> administratorList = administratorDao.selectAdministrator(sql);
        JPanel mainPanel = new JPanel(null);
        this.add(mainPanel, BorderLayout.CENTER);

        Font font = new Font("黑体", Font.PLAIN, 20);

        JLabel labelPic = new JLabel();
        labelPic.setBounds(100, 80, 50, 50);
        ImageIcon imageIcon = new ImageIcon("src/Picture/男同学.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING));
        labelPic.setIcon(imageIcon);
        mainPanel.add(labelPic);

        JLabel labelTno = new JLabel("职工号");
        labelTno.setFont(font);
        labelTno.setBounds(200, 90, 100, 30);
        mainPanel.add(labelTno);

        JTextField textFieldTno = new JTextField();
        textFieldTno.setText(administratorList.get(0).getAdministrator_no());
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
        textFieldTsex.setText(administratorList.get(0).getAdministrator_sex());
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
        textFieldTlocation.setText(administratorList.get(0).getAdministrator_location());
        textFieldTlocation.setFont(font);
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
        textFieldTname.setText(administratorList.get(0).getAdministrator_name());
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
        textFieldTbirth.setText(administratorList.get(0).getAdministrator_birth());
        textFieldTbirth.setFont(font);
        textFieldTbirth.setBounds(600, 160, 160, 30);
        mainPanel.add(textFieldTbirth);

        JLabel labelTjoin = new JLabel("入职年份");
        labelTjoin.setFont(font);
        labelTjoin.setBounds(500, 230, 100, 30);
        mainPanel.add(labelTjoin);

        JTextField textFieldTjoin = new JTextField();
        textFieldTjoin.setColumns(10);
        textFieldTjoin.setText(administratorList.get(0).getAdministrator_join());
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
//
//        JLabel lblNewLabel_9 = new JLabel("New label");
//        lblNewLabel_9.setBounds(970, 90, 45, 13);
//        mainPanel.add(lblNewLabel_9);
//
//        JTable table_2 = new JTable();
//        table_2.setBounds(900, 198, 544, 197);
//        mainPanel.add(table_2);

    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("用户信息");
        upPanel.buttonBack.addActionListener(e -> {
            AdministratorFrame.card.show(AdministratorFrame.contentPanelCenter, "bgPanel");
        });
        this.add(upPanel, BorderLayout.NORTH);


    }

}
