/*
This Frame is for user to add student to student List
Nov.4th.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddStudentFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font welcomeFont = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
    private final Font instructionFont = new Font("Times New Roman", Font.BOLD, 30);
    private JButton addButton;
    private JButton backButton;
    private JPanel buttonPanel;
    private JPanel instructionPanel;
    private JLabel welcomeLabel;
    private JTextField nameField;
    private JTextField idField;
    private JTextField gradeLevelField;
    private JTextField heightField;
    private JPanel inputPanel;
    private JLabel nameLabel;
    private JLabel idLabel;
    private JPanel namePanle;
    private JPanel idPanel;
    private JLabel gradeLevelLabel;
    private JLabel heightLabel;
    private JPanel gradeLevelPanel;
    private JPanel heightPanel;
    private StudentListFrame tepoFrame;

    public AddStudentFrame(StudentListFrame listFrame)
    {
        super("Add Student Frame");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        namePanle = new JPanel(new FlowLayout());
        gradeLevelPanel = new JPanel(new FlowLayout());
        heightPanel = new JPanel(new FlowLayout());
        idPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BKGROUND_COLOR);
        idPanel.setBackground(BKGROUND_COLOR);
        namePanle.setBackground(BKGROUND_COLOR);
        gradeLevelPanel.setBackground(BKGROUND_COLOR);
        heightPanel.setBackground(BKGROUND_COLOR);

        //Constuct Label
        welcomeLabel = new JLabel("Please enter the student information", SwingConstants.CENTER);
        nameLabel = new JLabel("Name        ");
        idLabel = new JLabel("ID             ");
        gradeLevelLabel = new JLabel("Grade Level");
        heightLabel = new JLabel("Height      ");
        welcomeLabel.setFont(welcomeFont);

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        backButton = new JButton("Back to Student List");
        backButton.addActionListener(this);

        //Add Button
        buttonPanel.add(addButton);;
        buttonPanel.add(backButton);

        //Construct textField
        nameField = new JTextField(10);
        idField = new JTextField(10);
        gradeLevelField = new JTextField(10);
        heightField = new JTextField(10);

        //passing value
        tepoFrame = listFrame;
        //Add panel
        namePanle.add(nameLabel);
        namePanle.add(nameField);
        idPanel.add(idLabel);
        idPanel.add(idField);
        gradeLevelPanel.add(gradeLevelLabel);
        gradeLevelPanel.add(gradeLevelField);
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);
        inputPanel.add(namePanle);
        inputPanel.add(idPanel);
        inputPanel.add(gradeLevelPanel);
        inputPanel.add(heightPanel);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);

    }
//change  StudentListFrame
    public void setTable()
    {
        tepoFrame.removeTable();
        this.tepoFrame.setTable();
        tepoFrame.revalidate();
        tepoFrame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Back to Student List"))
        {
            this.dispose();
        }
        else if (command.equals("Add"))
        {
            try// set info into database
            {
                DataBase tepo = new DataBase("StudentInfo");
                tepo.insertIntoTable("studentTable", new Object[]
                {
                    nameField.getText(), idField.getText(),
                    Integer.parseInt(gradeLevelField.getText()), Double.parseDouble(heightField.getText())
                });
                nameField.setText("");
                idField.setText("");
                gradeLevelField.setText("");
                heightField.setText("");
                // after input successful update the frame
                setTable();
            }
            catch (NumberFormatException a)
            {
                new ErrorFrame("Input information wrong");
            }
        }

    }

    public static void main(String[] args)
    {
        new AddStudentFrame(new StudentListFrame());
    }
}
