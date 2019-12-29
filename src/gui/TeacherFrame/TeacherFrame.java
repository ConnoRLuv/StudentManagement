package gui.TeacherFrame;

import gui.JIconButton;
import gui.ToolBar;

import javax.swing.*;
import java.awt.*;

public class TeacherFrame extends JFrame {

    private ToolBar toolBar;
    private String TID;

    protected static JPanel contentPanel;
    protected static UserPanel userPanel;
    protected static JPanel contentPanelCenter;
    protected static UpGradePanel upGradePanel;


    protected static CardLayout card = new CardLayout();

    public TeacherFrame(String TID) {
        super();
        this.TID = TID;
        initTeacherFrame();
        card.show(contentPanelCenter, "bgPanel");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initTeacherFrame() {
        this.setTitle("教师端");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1680, 960);

        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 254, 249));
        contentPanel.setLayout(new BorderLayout());
        this.setContentPane(contentPanel);

        userPanel = new UserPanel(TID);
        upGradePanel = new UpGradePanel(TID);
        toolBar = new ToolBar();

        contentPanelCenter = new JPanel(card);
        contentPanelCenter.add(userPanel, "userPanel");
        contentPanelCenter.add(upGradePanel, "upGradePanel");

        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("src/Picture/bj.jpg");
                g.drawImage(icon.getImage(), 0, 0,
                        contentPanelCenter.getWidth(),
                        contentPanelCenter.getHeight(),
                        contentPanelCenter);
            }
        };
        contentPanelCenter.add(bgPanel, "bgPanel");
        contentPanel.add(contentPanelCenter, BorderLayout.CENTER);
        contentPanel.add(toolBar, BorderLayout.WEST);


        ImageIcon iconUser = new ImageIcon("src/Picture/user.png");
        iconUser.setImage(iconUser.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonUser = new JIconButton(iconUser, iconUser, "查看与修改个人信息");
        buttonUser.addActionListener(e -> card.show(contentPanelCenter, "userPanel")
        );


        ImageIcon iconUpGrade = new ImageIcon("src/Picture/edit.png");
        iconUpGrade.setImage(iconUpGrade.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonUpGrade = new JIconButton(iconUpGrade, iconUpGrade, "登录分数");
        buttonUpGrade.addActionListener(e -> card.show(contentPanelCenter, "upGradePanel")
        );

        toolBar.add(buttonUser);

        toolBar.add(buttonUpGrade);

        this.setVisible(true);

    }

}
