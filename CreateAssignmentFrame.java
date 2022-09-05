/*
This Frame is used to create assignment
Dec.23rd.2019
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class CreateAssignmentFrame extends JFrame implements ActionListener
{

    private JLabel assignmentNameLabel = new JLabel("Assignment Name: ", SwingConstants.CENTER);
    private JTextField assignmentNameField = new JTextField(10);
    private JLabel gradeLabel = new JLabel("Out of:                     ", SwingConstants.CENTER);
    private JTextField gradeField = new JTextField(10);
    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private JPanel assignmentPanel = new JPanel(new FlowLayout());
    private JPanel assignmentNamePanel = new JPanel(new FlowLayout());
    private JPanel gradePanel = new JPanel(new FlowLayout());
    private JButton addButton = new JButton("Add");
    private JButton returnButton = new JButton("Return");
    private JPanel buttonPanel = new JPanel(new FlowLayout());
    private JLabel headerLabel = new JLabel("Please input the name and grade to construct new assignment",
            SwingConstants.CENTER);
    private JPanel finalPanel = new JPanel(new BorderLayout());
    private GradeBookFrame gradeFrame;
    private boolean whichConstructor;

    public CreateAssignmentFrame()
    {
        super("Assignment Create");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setUp();
        this.setColor();
        whichConstructor = false;
        this.setVisible(true);
    }

    public CreateAssignmentFrame(GradeBookFrame tepo)
    {
        // construct the frame
        super("Assignment Create");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        gradeFrame = tepo;
        whichConstructor = true;
        this.setUp();
        this.setColor();
        this.setVisible(true);
    }

    public void setUp()
    {
        //set up the frame
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
        addButton.addActionListener(this);
        returnButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(returnButton);
        gradeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        assignmentNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        assignmentPanel.add(assignmentNameLabel);
        assignmentPanel.add(assignmentNameField);
        gradePanel.add(gradeLabel);
        gradePanel.add(gradeField);
        finalPanel.add(assignmentPanel, BorderLayout.NORTH);
        finalPanel.add(gradePanel, BorderLayout.CENTER);
        this.add(headerLabel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(finalPanel, BorderLayout.CENTER);

    }

    public void setColor()
    {
        finalPanel.setBackground(BKGROUND_COLOR);
        gradePanel.setBackground(BKGROUND_COLOR);
        assignmentPanel.setBackground(BKGROUND_COLOR);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // insert the created assignment into database
        if (e.getSource() == addButton && whichConstructor != true)
        {
            ArrayList<String> idList = new ArrayList<String>(1);
            DataBase tepo = new DataBase("StudentInfo");
            idList = tepo.getOneDimentionalData("assignmentId", "id");
            try
            {
            tepo.insertIntoTable("assignmentTable", new Object[]
            {
                Integer.parseInt(idList.get(0)), assignmentNameField.getText(),
                Double.parseDouble(gradeField.getText())
            });
            }
            catch(NumberFormatException w)
            {
                new ErrorFrame("Please comfirm the input");
            }
            //System.out.println("Insert Sucess");
            int updater = Integer.parseInt(idList.get(0)) + 1;
            this.assignmentNameField.setText("");
            this.gradeField.setText("");
            tepo.deleteFromTable("assignmentId", "id", idList.get(0));
            tepo.insertIntoTable("assignmentId", new Object[]
            {
                updater
            });
        }
        // insert the created assignment into database
        else if (e.getSource() == addButton && whichConstructor == true)
        {
            ArrayList<String> idList = new ArrayList<>();
            DataBase tepo = new DataBase("StudentInfo");
            idList = tepo.getOneDimentionalData("assignmentId", "id");
            try
            {
            tepo.insertIntoTable("assignmentTable", new Object[]
            {
                Integer.parseInt(idList.get(0)), assignmentNameField.getText(),
                Double.parseDouble(gradeField.getText())
            });
            }
            catch(NumberFormatException qw)
            {
                new ErrorFrame("Please confirm the input");
            }
            
            //System.out.println("Insert Sucess");
            this.assignmentNameField.setText("");
            this.gradeField.setText("");
            
            
            int updater = Integer.parseInt(idList.get(0)) + 1;
            tepo.deleteFromTable("assignmentId", "id", idList.get(0));
            tepo.insertIntoTable("assignmentId", new Object[]
            {
                updater
            });
            gradeFrame.removeAll();
            ArrayList<ArrayList<String>> dataList = tepo.getData("assignmentTable",
                    new String[]
                    {
                        "assignmentId", "assignmentName", "assignmentGrade"
                    });
            gradeFrame.setAssignmentTable(dataList);
            gradeFrame.setAssignmentFrame();
            gradeFrame.revalidate();
            gradeFrame.repaint();

        }
        else if (e.getSource() == returnButton)
        {
            this.dispose();
        }
    }

    public static void main(String[] args)
    {
        new CreateAssignmentFrame();
    }

}
