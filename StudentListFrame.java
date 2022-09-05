/*
This Frame will display all the students who are in this class
Nov.11.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StudentListFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private JTable studentTable;
    private String[] tableHeader = new String[4];
    private Object[][] tableObject;
    private JScrollPane scrollPane;
    private final Font headerFont = new Font("Times New Roman", Font.BOLD, 23);
    private JButton addStudentButton;
    private JButton returnButton;
    private JPanel buttonPanel = new JPanel(new FlowLayout());
    private JButton deleteButton;
    private AddAppointmentFrame tepoFrame;

    public StudentListFrame()
    {
        super("Student List Frame");
        this.setBounds(300, 300, 800, 650);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTable();
        this.setFrame();
        this.setVisible(true);
    }

    public StudentListFrame(AddAppointmentFrame tepo)
    {
        super("Student List Frame");
        this.setBounds(300, 300, 800, 650);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tepoFrame = tepo;
        this.setLayout(new BorderLayout());
        this.setTable();
        this.setAddingFrame();
        this.setVisible(true);
    }

    public void setTable()
    {
        // get the stuent info
        tableObject = this.getData("studentTable", new String[]
        {
            "name", "id", "gradeLevel", "height"
        });
        tableHeader[0] = "Name";
        tableHeader[1] = "ID";
        tableHeader[2] = "Grade Level";
        tableHeader[3] = "Height";
        studentTable = new JTable(tableObject, tableHeader);
        studentTable.setFillsViewportHeight(rootPaneCheckingEnabled);
        studentTable.setBackground(BKGROUND_COLOR);
        studentTable.setGridColor(Color.BLACK);
        studentTable.setForeground(Color.BLACK);
        studentTable.getSelectionModel().addListSelectionListener(studentTable);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().add(studentTable);
        studentTable.getTableHeader().setFont(headerFont);
        studentTable.getTableHeader().setBackground(BKGROUND_COLOR);
        studentTable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(scrollPane, BorderLayout.CENTER);

    }

    public void removeTable()
    {
        this.remove(scrollPane);
    }

    public Object[][] getData(String dbName, String[] headers)
    {//access db
        ArrayList<ArrayList<String>> tepo2;
        DataBase tepo = new DataBase("StudentInfo");
        tepo2 = tepo.getData(dbName, headers);
        System.out.println(tepo2.size());
        Object[][] dataList;
        if (tepo2.isEmpty() == false)
        {
            dataList = new Object[tepo2.size()][tepo2.get(0).size()];
            System.out.println(dataList.length);
            System.out.println(dataList[0].length);
            for (int i = 0; i < tepo2.size(); i++)
            {
                for (int j = 0; j < tepo2.get(0).size(); j++)
                {
                    dataList[i][j] = tepo2.get(i).get(j);
                }
            }
        }
        else
        {
            dataList = new Object[0][0];
        }
        return dataList;
    }

    public void setFrame()
    {
        // set the frame
        addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(this);
        returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        deleteButton = new JButton("Delete Student");
        deleteButton.addActionListener(this);
        buttonPanel.add(addStudentButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(deleteButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void setAddingFrame()
    {
        // for the adding appointemnt frame
        addStudentButton = new JButton("Making appointment with him/her");
        addStudentButton.addActionListener(this);
        buttonPanel.add(addStudentButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int selectCount;
        String command = e.getActionCommand();
        if (command.equals("Add Student"))
        {
            new AddStudentFrame(this);
        }
        else if (command.equals("Return"))
        {
            this.dispose();
        }
        else if (command.equals("Delete Student"))
        {
            selectCount = studentTable.getSelectedRowCount();
            if (selectCount > 0)
            {
                //delete all the rows seleted
                int[] rows = studentTable.getSelectedRows();
                DataBase tepo = new DataBase("StudentInfo");
                for (int i = 0; i < rows.length; i++)
                {
                    //System.out.println(tableObject[rows[i]][1].toString()+"qqqqqqqqqqq");
                    tepo.deleteFromTable("studentTable", "id", "\'"
                            + tableObject[rows[i]][1].toString() + "\'");
                }
            }
            else
            {
                new ErrorFrame("Please select the student(s) you want to delete.");
            }
            this.removeTable();
            this.setTable();
            this.revalidate();
            this.repaint();
        }
        else if (command.equals("Making appointment with him/her"))
        {
            // making appointment with student
            if (studentTable.getSelectedRowCount() > 1)
            {
                new ErrorFrame("You can only make appointment with one people");
            }
            else
            {
                int rows = studentTable.getSelectedRow();
                tepoFrame.setStudent(tableObject[rows][0].toString());
                this.dispose();
            }
        }

    }

    public static void main(String[] args)
    {
        new StudentListFrame();
    }

}
