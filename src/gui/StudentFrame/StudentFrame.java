package gui.StudentFrame;

import gui.JIconButton;
import gui.ToolBar;

import javax.swing.*;
import java.awt.*;

public class StudentFrame extends JFrame {
    private String SID;
    private ToolBar toolBar;

    protected static JPanel contentPanel;
    protected static UserPanel userPanel;
    protected static JPanel contentPanelCenter;
    protected static UpActivityPanel upActivityPanel;
    protected static SelectPanel selectPanel;
    protected static CoursePanel coursePanel;
    protected static CardLayout card = new CardLayout();

    public StudentFrame(String SID) {
        super();
        this.SID = SID;
        initTeacherFrame();
        card.show(contentPanelCenter, "bgPanel");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initTeacherFrame() {
        this.setTitle("学生端");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1680, 960);

        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 254, 249));
        contentPanel.setLayout(new BorderLayout());
        this.setContentPane(contentPanel);

        userPanel = new UserPanel(SID);
        upActivityPanel = new UpActivityPanel(SID);
        selectPanel = new SelectPanel(SID);
        coursePanel = new CoursePanel(SID);
        toolBar = new ToolBar();

        contentPanelCenter = new JPanel(card);
        contentPanelCenter.add(userPanel, "userPanel");
        contentPanelCenter.add(upActivityPanel, "upActivityPanel");
        contentPanelCenter.add(selectPanel, "selectPanel");
        contentPanelCenter.add(coursePanel, "classPanel");

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

        ImageIcon iconSelect = new ImageIcon("src/Picture/search.png");
        iconSelect.setImage(iconSelect.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonSelect = new JIconButton(iconSelect, iconSelect, "查询成绩");
        buttonSelect.addActionListener(e -> card.show(contentPanelCenter, "selectPanel"));

        ImageIcon iconClass = new ImageIcon("src/Picture/list.png");
        iconClass.setImage(iconClass.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonClass = new JIconButton(iconClass, iconClass, "选课");
        buttonClass.addActionListener(e -> card.show(contentPanelCenter, "classPanel"));

        ImageIcon iconUpActivity = new ImageIcon("src/Picture/edit.png");
        iconUpActivity.setImage(iconUpActivity.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonUpActivity = new JIconButton(iconUpActivity, iconUpActivity, "填写社会实践活动");
        buttonUpActivity.addActionListener(e -> card.show(contentPanelCenter, "upActivityPanel")
        );

        toolBar.add(buttonUser);
        toolBar.add(buttonSelect);

        toolBar.add(buttonClass);
        toolBar.add(buttonUpActivity);
        this.setVisible(true);

    }
}
