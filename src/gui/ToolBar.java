package gui;

import java.awt.*;
import javax.swing.*;

public class ToolBar extends JPanel {

    public ToolBar() {
        Dimension preferredSize = new Dimension(70, 960);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        initToolBar();
    }


    private void initToolBar() {

        Color bgColor = new Color(70, 132, 195);
        this.setBackground(bgColor);
        this.setLayout(new FlowLayout(-2, -2, -4));

//        JPanel panelTool = new JPanel();
//        panelTool.setLayout();
//        panelTool.setBackground(bgColor);


//        this.add(panelTool);
        setVisible(true);

    }


}
