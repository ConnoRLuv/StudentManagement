package gui;

import gui.JIconButton;

import javax.swing.*;
import java.awt.*;

/**
 * 顶部提示栏
 */
public class UpPanel extends JPanel {

    public JIconButton buttonBack;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image logo_w = new ImageIcon("src/Picture/logo-w.png").getImage();
        g.drawImage(logo_w,
                this.getWidth() - 330, 15,
                300,
                66,
                this);
    }

    public UpPanel(String text) {
        this.setLayout(new BorderLayout());
        initPanel(text);
    }

    private void initPanel(String text) {
        this.setPreferredSize(new Dimension(1610, 100));
        this.setMaximumSize(new Dimension(1610, 100));
        this.setMinimumSize(new Dimension(1610, 100));
        this.setBackground(new Color(70, 132, 195));
        this.setBounds(0, 0, this.getPreferredSize().width, 100);

        ImageIcon iconBack = new ImageIcon("src/Picture/back.png");
        iconBack.setImage(iconBack.getImage().getScaledInstance(70, 70, Image.SCALE_AREA_AVERAGING));
        buttonBack = new JIconButton(iconBack, iconBack, "返回");

        this.add(buttonBack, BorderLayout.WEST);

        JLabel Hello = new JLabel();

        Hello.setText(text);
        Hello.setForeground(new Color(253, 253, 253));
        Hello.setFont(new Font("黑体", Font.PLAIN, 48));
        Hello.setBounds(0, 0, 450, 250);

        this.add(Hello, BorderLayout.CENTER);
    }


}
