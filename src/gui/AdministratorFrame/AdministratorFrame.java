package gui.AdministratorFrame;

import gui.JIconButton;

import gui.ToolBar;

import javax.swing.*;
import java.awt.*;

public class AdministratorFrame extends JFrame {
    private String AID;

    protected static JPanel contentPanel;
    protected static JPanel contentPanelCenter;
    protected static SelectPanel selectPanel;
    protected static UserPanel userPanel;
    protected static EditPanel editPanel;
    protected static GraduatePanel graduatePanel;

    protected static ToolBar toolBar;
    protected static CardLayout card = new CardLayout();

    public AdministratorFrame(String AID) {
        super();
        this.AID = AID;
        initTeacherFrame();
        card.show(contentPanelCenter, "bgPanel");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initTeacherFrame() {
        this.setTitle("教务处端");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1680, 960);

        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 254, 249));
        contentPanel.setLayout(new BorderLayout());
        this.setContentPane(contentPanel);

        selectPanel = new SelectPanel();
        userPanel = new UserPanel(AID);
        editPanel = new EditPanel();
        graduatePanel = new GraduatePanel(AID);
        toolBar = new ToolBar();

        contentPanelCenter = new JPanel(card);
        contentPanelCenter.add(selectPanel, "selectPanel");
        contentPanelCenter.add(userPanel, "userPanel");
        contentPanelCenter.add(editPanel, "addPanel");
        contentPanelCenter.add(graduatePanel, "graduatePanel");


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
        buttonUser.addActionListener(e -> {
                    card.show(contentPanelCenter, "userPanel");
                }
        );

        ImageIcon iconSelect = new ImageIcon("src/Picture/search.png");
        iconSelect.setImage(iconSelect.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonSelect = new JIconButton(iconSelect, iconSelect, "查询学籍信息");
        buttonSelect.addActionListener(e -> {
            card.show(contentPanelCenter, "selectPanel");
        });

        ImageIcon iconAdd = new ImageIcon("src/Picture/编辑学籍.png");
        iconAdd.setImage(iconAdd.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonAdd = new JIconButton(iconAdd, iconAdd, "编辑学籍信息");
        buttonAdd.addActionListener(e -> {
            card.show(contentPanelCenter, "addPanel");
        });

        ImageIcon iconGraduate = new ImageIcon("src/Picture/毕业.png");
        iconGraduate.setImage(iconGraduate.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        JIconButton buttonGraduate = new JIconButton(iconGraduate, iconGraduate, "审核学业要求");
        buttonGraduate.addActionListener(e -> {
            card.show(contentPanelCenter, "graduatePanel");
        });

        toolBar.add(buttonUser);
        toolBar.add(buttonSelect);
        toolBar.add(buttonAdd);
        toolBar.add(buttonGraduate);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new AdministratorFrame("123");
    }
}
