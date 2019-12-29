package gui.TeacherFrame;

import dal.daoimpl.TeacherDaoImpl;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Vector;

public class UpGradePanel extends JPanel {
    private TeacherDaoImpl teacherDao = new TeacherDaoImpl();
    private String TID;

    public UpGradePanel(String TID) {
        super();
        this.TID = TID;
        this.setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();

    }

    private void addMainPanel() {
        JPanel mainPanel = new JPanel();
        Dimension preferSize = new Dimension(1610, 860);
        mainPanel.setPreferredSize(preferSize);
        mainPanel.setMaximumSize(preferSize);
        mainPanel.setMinimumSize(preferSize);
        mainPanel.setLayout(new MigLayout("",
                "[grow,center][grow,center][grow][grow,center][grow][grow][grow][grow][100px,fill][grow]",
                "[40px][][grow][grow][grow][grow][grow][grow][grow][grow][15][40px][15]"));

        Font font = new Font("黑体", Font.PLAIN, 20);


        JLabel lblNewLabel = new JLabel("开设课程");
        lblNewLabel.setFont(font);
        mainPanel.add(lblNewLabel, "cell 0 0 3 1");

        JLabel lblNewLabel_1 = new JLabel("选课学生信息");
        lblNewLabel_1.setFont(font);
        mainPanel.add(lblNewLabel_1, "cell 3 0 7 1");


        Vector<String> colsCourse = new Vector<>();
        colsCourse.add("课程编号");
        colsCourse.add("课程名称");
        colsCourse.add("课程学分");
        colsCourse.add("选课人数");

        Vector<Vector<Object>> rowsCourse = teacherDao.selectCourse(TID);
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.setDataVector(rowsCourse, colsCourse);
        JTable table_1 = new JTable();
//        table_1.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table_1.setModel(defaultTableModel);
        table_1.setFont(font);
        table_1.getTableHeader().setFont(font);
        table_1.setRowHeight(40);

        JScrollPane jScrollPane = new JScrollPane(table_1);
        mainPanel.add(jScrollPane, "cell 0 1 3 9,grow");

        Vector<Vector<Object>> rowsStudent;
        Vector<String> colsStudent = new Vector<>();
        colsStudent.add("学生学号");
        colsStudent.add("学生姓名");
        colsStudent.add("学生班级");
        colsStudent.add("成绩");
        rowsStudent = new Vector<>();
        JTable table = new JTable();
        DefaultTableModel defaultTableModelStudent = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };


        defaultTableModelStudent.setDataVector(rowsStudent, colsStudent);
        table.setModel(defaultTableModelStudent);
        table.setFont(font);
        table.getTableHeader().setFont(font);
        table.setRowHeight(40);
        JScrollPane jScrollPaneStudent = new JScrollPane(table);
        mainPanel.add(jScrollPaneStudent, "cell 3 1 7 9,grow");

        table_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int row = table_1.rowAtPoint(e.getPoint());
                int col = table_1.columnAtPoint(e.getPoint());
                table_1.setRowSelectionInterval(row, row);
                table_1.setColumnSelectionInterval(0, table_1.getColumnCount() - 1);

                Vector<Vector<Object>> rowsStudent = teacherDao.selectCourseGrades((String) table_1.getValueAt(table_1.getSelectedRow(), 0));
                defaultTableModelStudent.setDataVector(rowsStudent, colsStudent);
                defaultTableModelStudent.fireTableDataChanged();
                table.setModel(defaultTableModelStudent);

            }
        });

        JButton btnNewButton = new JButton("提交成绩");
        btnNewButton.setFont(font);
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBackground(new Color(70, 132, 195));
        btnNewButton.setFont(font);
        btnNewButton.addActionListener(e -> {
            for (int i = 0; i < table.getRowCount(); i++) {
                String Sgrade;
                if (Objects.equals((String) table.getValueAt(i, 3), "")) {
                    Sgrade = "null";
                } else
                    Sgrade = (String) table.getValueAt(i, 3);

                String Sno = (String) table.getValueAt(i, 0);
                String Cno = (String) table_1.getValueAt(table_1.getSelectedRow(), 0);
                String sql = "update studentcourse set Course_grades = " + Sgrade + " " +
                        "where Student_no = '" + Sno + "' " +
                        "and studentcourse.Course_no = " + Cno;
                if (!teacherDao.updateGrades(sql)) {
                    JLabel Massage = new JLabel("提交失败！");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            JLabel Massage = new JLabel("提交成功！");
            Massage.setFont(font);
            JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
        });
        mainPanel.add(btnNewButton, "cell 8 11,grow");


        this.add(mainPanel, BorderLayout.CENTER);
    }


    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("登录分数");
        upPanel.buttonBack.addActionListener(e -> TeacherFrame.card.show(TeacherFrame.contentPanelCenter, "bgPanel"));
        this.add(upPanel, BorderLayout.NORTH);
    }
}
