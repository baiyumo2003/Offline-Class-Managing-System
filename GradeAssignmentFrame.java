/*
This Frame is designed to grade the assignment 
Dec. 25th 2019
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GradeAssignmentFrame extends JFrame implements ActionListener
{

    private JComboBox <String>studentBox;
    private JButton gradeButton;
    private JTextField gradeField;
    private JLabel outOfLabel;
    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private String[] assignmentInfo;
    private JLabel headerLabel = new JLabel("Please enter the grade for each student",
            SwingConstants.CENTER);
    private JLabel gradeLabel = new JLabel("Grades: ");
    private JLabel studentLabel = new JLabel("Student: ");
    private JPanel inputPanel;
    private JLabel blankLabel = new JLabel("      ");
    private JButton returnButton = new JButton("Return");
    private JPanel buttonPanel;
    private ArrayList<ArrayList<String>> values = new ArrayList<>(10);
    private JButton setAllGradesButton = new JButton("Set entered grade into database");
    private ArrayList<ArrayList<String>> studentList;
    private int counter = 0;

    public GradeAssignmentFrame(String[] tepo)
    {
        super("Grading Frame");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        assignmentInfo = tepo;
        this.setLayout(new BorderLayout());
        this.setUp();
        this.setVisible(true);
    }

    public void setUp()
    {
        //set up the combo box and frame
        studentBox = new JComboBox<>();
        DataBase tepo = new DataBase("StudentInfo");
        // get all the student
        studentList = tepo.getData("studentTable",
                new String[]
                {
                    "name", "id"
                });
        //System.out.println(assignmentInfo[0]+"dgasgdiugasiudagduiagsiud");
        for (int i = 0; i < studentList.size(); i++)
        {
            studentBox.addItem(studentList.get(i).get(0));
        }
        outOfLabel = new JLabel("Out of: " + assignmentInfo[2]);
        gradeField = new JTextField(5);
        gradeButton = new JButton("Add");
        gradeButton.addActionListener(this);
        inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(studentLabel);
        inputPanel.add(studentBox);
        inputPanel.add(blankLabel);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);
        inputPanel.add(blankLabel);
        inputPanel.add(outOfLabel);
        inputPanel.add(blankLabel);
        inputPanel.add(gradeButton);
        inputPanel.setBackground(BKGROUND_COLOR);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(returnButton);
        buttonPanel.add(setAllGradesButton);
        returnButton.addActionListener(this);
        setAllGradesButton.addActionListener(this);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(headerLabel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    //Concert arrayList to Array
    public Object[] toObjectArray(ArrayList<String> tepo)
    {
        Object[] result = new Object[tepo.size()];
        result[0] = Integer.parseInt(tepo.get(0));
        for (int i = 1; i < tepo.size() - 1; i++)
        {
            result[i] = tepo.get(i);
        }
        //System.out.println(Double.parseDouble(tepo.get(tepo.size()-1)));//???????????????????
        result[tepo.size() - 1] = Double.parseDouble(tepo.get(tepo.size() - 1));
        return result;
    }

    private boolean isDouble(String tepo)
    {
        //Check whetehr input is double or not
        try
        {
            Double.parseDouble(tepo);
        }
        catch (NumberFormatException q)
        {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gradeButton)
        {

            ArrayList<String> tepo = new ArrayList<>();
            String grades = gradeField.getText();
            if (isDouble(grades) == true)
            {
                try
                {
                    tepo.add(assignmentInfo[0]);
                    // System.out.println(studentBox.getSelectedIndex());
                    //System.out.println(studentList.get(studentBox.getSelectedIndex()).get(1));
                    tepo.add(studentList.get(counter).get(1));
                    tepo.add(grades);
                    studentBox.removeItemAt(studentBox.getSelectedIndex());
                    // System.out.println(studentBox.getMaximumRowCount());
                    if (studentBox.getSelectedIndex() + 1 < studentList.size() && studentBox.getItemCount() > 1)
                    {
                        studentBox.setSelectedIndex(studentBox.getSelectedIndex() + 1);
                    }
                    gradeField.setText("");
                    counter++;
                    values.add(tepo);
                }
                catch (Exception w)
                {
                    gradeField.setText("");
                    new ErrorFrame("you already grade everthing");
                }
            }
            else
            {
                new ErrorFrame("Please confirm your input");
            }
        }
        else if (e.getSource() == returnButton)
        {
            this.dispose();
        }
        else if (e.getSource() == setAllGradesButton)
        {
            // set the grades into the database
            DataBase tepo = new DataBase("StudentInfo");
            // if the arrayList size is the same
            if (values.size() != studentList.size())
            {
                new ErrorFrame("You have to input grades for all student");
            }
            else
            {
                //If this assignment was gradeed before.
                tepo.deleteFromTable("gradeTable", "assignmentId", assignmentInfo[0]);
                for (int i = 0; i < values.size(); i++)
                {
                    tepo.insertIntoTable("gradeTable", this.toObjectArray(values.get(i)));
                }
                this.dispose();
            }

        }

    }

    public static void main(String args[])
    {
        new GradeAssignmentFrame(new String[]
        {
            "1", "2", "3"
        });
    }

}
