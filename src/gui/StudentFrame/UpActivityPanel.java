package gui.StudentFrame;

import dal.Entity.Activity;
import dal.daoimpl.StudentDaoImpl;
import gui.UpPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.Objects;
import java.util.Vector;

public class UpActivityPanel extends JPanel {
    private String SID;
    private StudentDaoImpl studentDaoImpl = new StudentDaoImpl();

    public UpActivityPanel(String SID) {
        super();
        this.SID = SID;
        setLayout(new BorderLayout());
        addUpPanel();
        addMainPanel();
    }

    private void addMainPanel() {
        Font font = new Font("黑体", Font.PLAIN, 20);
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new MigLayout("", "[20px][100][20][grow,left][grow][grow][grow][grow][grow][grow][grow][20px]", "[grow 50][grow][grow][grow][grow][grow][grow][grow][20px]"));

        JLabel labelPic = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/Picture/实践活动.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_AREA_AVERAGING));
        labelPic.setIcon(imageIcon);
        labelPic.setBorder(new EmptyBorder(0, 0, 0, 10));
        mainpanel.add(labelPic, "flowx,cell 1 0");

        Vector<Vector<String>> rows;
        Vector<String> cols = new Vector<>();
        cols.add("社会实践活动编号");
        cols.add("社会实践活动时间");
        cols.add("社会实践活动内容");

        String sql = "SELECT activity.Activity_no,Activity_time,Activity_content " +
                "FROM activity,studentactivity " +
                "WHERE activity.Activity_no=studentactivity.Activity_no " +
                "AND Student_no= '" + SID + "'";
        StudentDaoImpl studentDao = new StudentDaoImpl();

        rows = studentDao.selectActivity(sql);
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTableModel.setDataVector(rows, cols);
        JTable table = new JTable();
        table.setModel(defaultTableModel);
        table.getTableHeader().setFont(font);

        table.setFont(font);
        table.setRowHeight(40);
        JScrollPane jScrollPane = new JScrollPane(table);
        mainpanel.add(jScrollPane, "cell 1 1 10 7,grow");

        JButton buttonActivity2 = new JButton("添加社会实践活动");
        buttonActivity2.setFont(font);
        buttonActivity2.setForeground(new Color(255, 255, 255));
        buttonActivity2.setBackground(new Color(70, 132, 195));
        buttonActivity2.addActionListener(e -> {
            JFrame addActivityFrame = new JFrame();
            addActivityFrame.setTitle("添加社会实践活动");
            addActivityFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            addActivityFrame.setSize(new Dimension(900, 600));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            int x = (int) (toolkit.getScreenSize().getWidth() - addActivityFrame.getWidth()) / 2;
            int y = (int) (toolkit.getScreenSize().getHeight() - addActivityFrame.getHeight()) / 2;
            addActivityFrame.setLocation(x, y);

            JPanel panel = new JPanel();
            addActivityFrame.getContentPane().add(panel, BorderLayout.CENTER);

            UpPanel upPanel1 = new UpPanel(" 添加社会实践活动");
            upPanel1.buttonBack.setVisible(false);
            addActivityFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);

            panel.setLayout(new MigLayout("", "[grow][grow][grow,200px][][right][][left][][grow,200px][grow][grow]", "[grow,fill][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));
            //活动编号
            JLabel label = new JLabel("活动编号");
            label.setFont(font);
            panel.add(label, "cell 1 1,alignx left");

            String sql1 = "select * from activity";
            Vector<Vector<String>> activities = studentDaoImpl.selectActivity(sql1);
            Vector<String> activityNo = new Vector<>();
            activityNo.add(0, "新增活动");
            for (Vector<String> activity :
                    activities) {
                activityNo.add(activity.get(0));
            }

            JComboBox<String> comboBox = new JComboBox<>(activityNo);
            comboBox.setFont(font);

            panel.add(comboBox, "cell 2 1 7 1,growx");


            //活动时间
            JLabel labelTime = new JLabel("活动时间");
            labelTime.setFont(font);
            panel.add(labelTime, "cell 1 2,alignx left");

            JTextField textFieldTime = new JTextField();
            textFieldTime.setFont(font);
            panel.add(textFieldTime, "cell 2 2 7 1,growx");


            //活动内容
            JLabel lblNewLabel = new JLabel("活动内容");
            lblNewLabel.setFont(font);
            panel.add(lblNewLabel, "cell 1 3");

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            panel.add(scrollPane, "cell 2 3 7 6,grow");

            JTextArea textArea = new JTextArea();
            textArea.setLineWrap(true);        //激活自动换行功能
            textArea.setWrapStyleWord(true);
            scrollPane.setViewportView(textArea);
            textArea.setFont(font);

            comboBox.addActionListener(e1 -> {
                if (!Objects.equals(comboBox.getSelectedItem(), "新增活动")) {
                    Activity activity = new Activity();
                    for (Vector<String> temp : activities) {
                        if (temp.get(0).equals(comboBox.getSelectedItem())) {
                            activity.setActivity_no(temp.get(0));
                            activity.setActivity_time(temp.get(1));
                            activity.setActivity_content(temp.get(2));
                            break;
                        }
                    }
                    textFieldTime.setText(activity.getActivity_time());
                    textArea.setText(activity.getActivity_content());
                    textFieldTime.setEditable(false);
                    textArea.setEditable(false);
                } else {
                    textFieldTime.setText("");
                    textArea.setText("");
                    textFieldTime.setEditable(true);
                    textArea.setEditable(true);
                }
            });

            JButton btnNewButton = new JButton("添加");
            btnNewButton.setFont(font);
            panel.add(btnNewButton, "cell 4 10");
            btnNewButton.addActionListener(ex -> {

                if (Objects.equals(comboBox.getSelectedItem(), "新增活动")) {
                    String Activity_time = textFieldTime.getText();
                    String Activity_content = textArea.getText();
                    if (Activity_time.equals("") || Activity_content.equals("")) {
                        JOptionPane.showMessageDialog(this, "错误!请先添加完整的信息");
                    } else {
                        int activityCount = studentDaoImpl.getActivityCount();
                        String Activity_no = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) * 100 + activityCount + 1);
                        String sqlActivity_table = "INSERT INTO activity(Activity_no,Activity_time,Activity_content) " +
                                "VALUES (" + Activity_no + "," + "'" + Activity_time + "'" + "," + "'" + Activity_content + "'" + ")";
                        String sqlStudentActivity_table = "INSERT INTO studentactivity(Student_no,Activity_no) " +
                                "VALUES (" + "'" + SID + "'" + "," + Activity_no + ")";
                        studentDaoImpl = new StudentDaoImpl();
                        int num1 = studentDaoImpl.AddCourse(sqlActivity_table);
                        int num2 = studentDaoImpl.AddCourse(sqlStudentActivity_table);
                        if (num1 == 1 && num2 == 1) {
                            defaultTableModel.setDataVector(studentDaoImpl.selectActivity(sql), cols);
                            defaultTableModel.fireTableDataChanged();
                            table.setModel(defaultTableModel);

                            JLabel Massage = new JLabel("社会实践活动添加成功！");
                            Massage.setFont(font);
                            JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JLabel Massage = new JLabel("社会实践活动添加失败！");
                            Massage.setFont(font);
                            JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    String Activity_no = (String) comboBox.getSelectedItem();
                    String sqlStudentActivity_table = "INSERT INTO studentactivity(Student_no,Activity_no) " +
                            "VALUES (" + "'" + SID + "'" + "," + "'" + Activity_no + "'" + ")";
                    studentDaoImpl = new StudentDaoImpl();
                    int num2 = studentDaoImpl.AddCourse(sqlStudentActivity_table);
                    defaultTableModel.setDataVector(studentDaoImpl.selectActivity(sql), cols);
                    defaultTableModel.fireTableDataChanged();
                    table.setModel(defaultTableModel);
                    JLabel Massage = new JLabel("社会实践活动添加成功！");
                    Massage.setFont(font);
                    JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
                }

            });

            JButton btnNewButton_1 = new JButton("撤销");
            btnNewButton_1.setFont(font);
            btnNewButton_1.addActionListener(ex1 -> {
                comboBox.setSelectedItem("新增活动");
                textFieldTime.setText(null);
                textArea.setText(null);
            });
            panel.add(btnNewButton_1, "cell 6 10");

            addActivityFrame.setVisible(true);
        });
        mainpanel.add(buttonActivity2, "cell 1 0,alignx center");


        JButton buttonActivity1 = new JButton("修改社会实践活动信息");
        buttonActivity1.setFont(font);
        buttonActivity1.setForeground(new Color(255, 255, 255));
        buttonActivity1.setBackground(new Color(70, 132, 195));
        buttonActivity1.addActionListener(e -> {
            int Index = table.getSelectedRow();
            if (Index == -1) {
                JOptionPane.showMessageDialog(this, "未选中信息！");
            } else {
                JFrame addActivityFrame = new JFrame();
                addActivityFrame.setTitle("修改社会实践活动信息");
                addActivityFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                addActivityFrame.setSize(new Dimension(900, 600));
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                int x = (int) (toolkit.getScreenSize().getWidth() - addActivityFrame.getWidth()) / 2;
                int y = (int) (toolkit.getScreenSize().getHeight() - addActivityFrame.getHeight()) / 2;
                addActivityFrame.setLocation(x, y);

                JPanel panel = new JPanel();
                addActivityFrame.getContentPane().add(panel, BorderLayout.CENTER);

                UpPanel upPanel1 = new UpPanel(" 修改社会实践活动信息");
                upPanel1.buttonBack.setVisible(false);
                addActivityFrame.getContentPane().add(upPanel1, BorderLayout.NORTH);

                panel.setLayout(new MigLayout("",
                        "[grow][grow][grow][grow][grow,right][grow][grow,left][grow][grow][grow][grow]",
                        "[grow,fill][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));

//活动编号
                JLabel label = new JLabel("活动编号");
                label.setFont(font);
                panel.add(label, "cell 1 1,alignx left");

                JTextField textField = new JTextField();
                textField.setFont(font);
                textField.setEditable(false);
                textField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                panel.add(textField, "cell 2 1 7 1,growx");
                textField.setColumns(10);
//活动时间
                JLabel labelTime = new JLabel("活动时间");
                labelTime.setFont(font);
                panel.add(labelTime, "cell 1 2,alignx left");

                JTextField textFieldTime = new JTextField();
                textFieldTime.setFont(font);

                textFieldTime.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                panel.add(textFieldTime, "cell 2 2 7 1,growx");
                textField.setColumns(10);
//活动内容
                JLabel lblNewLabel = new JLabel("活动内容");
                lblNewLabel.setFont(font);
                panel.add(lblNewLabel, "cell 1 3");

                JTextArea textArea = new JTextArea();
                textArea.setFont(font);
                textArea.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                panel.add(textArea, "cell 2 3 7 7,grow");

                JButton btnNewButton = new JButton("修改");
                btnNewButton.setFont(font);
                btnNewButton.addActionListener(e1 -> {
                    String Ano = textField.getText();
                    String Atime = textFieldTime.getText();
                    String Acontent = textArea.getText();

                    String sql2 = "update activity " +
                            "set Activity_time  = '" + Atime + "'," +
                            "Activity_content = '" + Acontent + "' " +
                            "where Activity_no = " + Ano;
                    if (studentDao.updateStudent(sql2)) {
                        defaultTableModel.setDataVector(studentDaoImpl.selectActivity(sql), cols);
                        defaultTableModel.fireTableDataChanged();
                        table.setModel(defaultTableModel);
                        JLabel Massage = new JLabel("修改活动信息成功！");
                        Massage.setFont(font);
                        JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JLabel Massage = new JLabel("修改活动信息出错！");
                        Massage.setFont(font);
                        JOptionPane.showMessageDialog(this, Massage, "提示", JOptionPane.ERROR_MESSAGE);
                    }
                });
                panel.add(btnNewButton, "cell 4 10");

                JButton btnNewButton_1 = new JButton("取消");
                btnNewButton_1.setFont(font);
                btnNewButton_1.addActionListener(e1 -> {
                    textField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                    textFieldTime.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                    textArea.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                });
                panel.add(btnNewButton_1, "cell 6 10");

                addActivityFrame.setVisible(true);
            }
        });
        mainpanel.add(buttonActivity1, "cell 3 0");
        this.add(mainpanel, BorderLayout.CENTER);
    }

    private void addUpPanel() {
        UpPanel upPanel = new UpPanel("填写社会实践活动");
        upPanel.buttonBack.addActionListener(e ->
                StudentFrame.card.show(StudentFrame.contentPanelCenter, "bgPanel"));
        this.add(upPanel, BorderLayout.NORTH);
    }
}
