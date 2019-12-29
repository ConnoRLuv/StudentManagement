package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JIconButton extends JButton {
    private ImageIcon iconEnable, iconDisable;
    private String tips;

    /**
     * 构造方法（不带按钮内显文字）
     *
     * @param iconEnable
     * @param iconDisable
     * @param tips
     */
    public JIconButton(ImageIcon iconEnable, ImageIcon iconDisable, String tips) {
        this.iconEnable = iconEnable;
        this.iconDisable = iconDisable;
        this.tips = "<html><body style=\"font-style:SimSun;font-size:20px;\">" + tips + "</body></html>";

        initIconButton();
        setUp();
    }

    /**
     * 构造方法（带按钮内显文字）
     *
     * @param iconEnable 图标
     * @param tips       提示
     */
    public JIconButton(ImageIcon iconEnable, ImageIcon iconDisable, String tips, String text) {
        this.iconEnable = iconEnable;
        this.iconDisable = iconDisable;
        this.tips = tips;
        initIconButton();
        setUp();
    }

    private void initIconButton() {
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusable(true);
        this.setMargin(new Insets(0, 0, 0, 0));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBorderPainted(false);
            }
        });

    }

    private void setUp() {
        this.setIcon(iconEnable);
//        this.setRolloverIcon(iconEnable);
//        this.setPressedIcon(iconEnable);
//        this.setDisabledIcon(iconDisable);
        if (!tips.equals("")) {
            this.setToolTipText("<html><body style=\"font-style:SimSun;font-size:20px;\">" + tips + "</body></html>");
        }

    }
}
