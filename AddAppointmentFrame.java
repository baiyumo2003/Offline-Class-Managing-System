/*
This Frame is for users to add appointment with their students
Nov.12.2019
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

public class AddAppointmentFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font welcomeFont = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
    private final Font instructionFont = new Font("Times New Roman", Font.BOLD, 30);
    private JButton addButton;
    private JButton backButton;
    private JPanel buttonPanel;
    private JPanel instructionPanel;
    private JLabel welcomeLabel;
    //private JTextField timeField;
   // private JTextField dateField;
   // private JTextField peopleField;
    private JTextField locationField;
    private JPanel inputPanel;
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JPanel timePanel;
    private JPanel datePanel;
    private JLabel peopleLabel;
    private JLabel locationLabel;
    private JPanel peoplePanel;
    private JPanel locationPanel;
    private JButton returnButton;
    private JButton timeButton;
    private JButton dateButton;
    private JButton studentButton;
    private int counter = 0;

    public AddAppointmentFrame()
    {
        super("Add Appointment Frame");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        timePanel = new JPanel(new FlowLayout());
        peoplePanel = new JPanel(new FlowLayout());
        locationPanel = new JPanel(new FlowLayout());
        datePanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BKGROUND_COLOR);
        datePanel.setBackground(BKGROUND_COLOR);
        timePanel.setBackground(BKGROUND_COLOR);
        peoplePanel.setBackground(BKGROUND_COLOR);
        locationPanel.setBackground(BKGROUND_COLOR);

        //Constuct Label
        welcomeLabel = new JLabel("Please enter the appointment information", SwingConstants.CENTER);
        timeLabel = new JLabel("The time", SwingConstants.CENTER);
        dateLabel = new JLabel("The date", SwingConstants.CENTER);
        peopleLabel = new JLabel("The people", SwingConstants.CENTER);
        locationLabel = new JLabel("The location", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeFont);

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        backButton = new JButton("Back to canlender");
        backButton.addActionListener(this);
        returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        timeButton=new JButton("Select time");
        timeButton.addActionListener(this);
        dateButton=new JButton("Select date");
        dateButton.addActionListener(this);
        studentButton=new JButton("Select student");
        studentButton.addActionListener(this);

        //Add Button
        buttonPanel.add(addButton);;
        buttonPanel.add(backButton);

        //Construct textField
//        timeField = new JTextField(10);
//        dateField = new JTextField(10);
//        peopleField = new JTextField(10);
        locationField = new JTextField(10);

        //Add panel
        timePanel.add(timeLabel);
        timePanel.add(timeButton);
        datePanel.add(dateLabel);
        datePanel.add(dateButton);
        peoplePanel.add(peopleLabel);
        peoplePanel.add(studentButton);
        locationPanel.add(locationLabel);
        locationPanel.add(locationField);
        //timeField.addActionListener(this);
//        dateField.addFocusListener(this);
//        timeField.addFocusListener(this);
//        peopleField.addFocusListener(this);
        inputPanel.add(timePanel);
        inputPanel.add(datePanel);
        inputPanel.add(peoplePanel);
        inputPanel.add(locationPanel);
        //this.setAutoRequestFocus(false);
        //addButton.requestFocusInWindow();
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    // set method for buttons
    public void setDate(String tepo)
    {
        this.dateButton.setText(tepo);
    }

    public void setTime(String tepo)
    {
        this.timeButton.setText(tepo);
    }

    public void setStudent(String tepo)
    {
        this.studentButton.setText(tepo);
    }
//
//    public void setFocus()
//    {
//        this.addButton.requestFocus();
//    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Back to canlender"))
        {
            this.dispose();
            // new CalenderFrame();
        }
        else if (command.equals("Add"))
        {
           // System.out.println("123");
            // check whether the field is empty or not
            DataBase tepo = new DataBase("StudentInfo");
            if (timeButton.getText().equals("Select Time")
                    || dateButton.getText().equals("Select date")
                    || studentButton.getText().equals("Select student")
                    || locationField.getText().trim().isEmpty() == true)
            {
                new ErrorFrame("Please fill out all the information");
            }
            else
            {
                try
                {
                     //Check whether the input info is correct
                    tepo.insertIntoTable("Appointment", new Object[]
                    {
                        timeButton.getText(), dateButton.getText(), studentButton.getText(),
                        locationField.getText()
                    });
                    this.dispose();
                }
                catch (NumberFormatException a)
                {
                    new ErrorFrame("input wrong");
                }

            }
        }

        else if (command.equals("Return"))
        {
            this.dispose();
        }
        
        else if(e.getSource().equals(timeButton))
        {
            new TimeChosenFrame(this);
           // System.out.println(timeButton.getText());
        }
        
        else if(e.getSource().equals(dateButton))
        {
            new CalenderFrame(this);
        }
        
        else if(e.getSource().equals(studentButton))
        {
            new StudentListFrame(this);
        }

    }

//    @Override
//    public void focusGained(FocusEvent e)
//    {
//
//        // Check where is the mouse pointer
//        if (counter % 2 == 0)
//        {
//            JComponent component = (JComponent) e.getSource();
//            //System.out.println(component);
//            if (component == dateField && dateField.getText().trim().isEmpty())
//            {
//                new CalenderFrame(this);
//            }
//            else if (component.equals(timeField) && timeField.getText().trim().isEmpty())
//            {
//                new TimeChosenFrame(this);
//                //System.out.println("12345676521784678gwuiw");
//            }
//            else if (component == peopleField && peopleField.getText().trim().isEmpty())
//            {
//                new StudentListFrame(this);
//            }
//        }
//        counter += 1;
//    }

//    @Override
//    public void focusLost(FocusEvent e)
//    {
//        System.out.println("focus lost");
//        this.getRootPane().setDefaultButton(addButton);
//        addButton.requestFocus();
//    }

    public static void main(String[] args)
    {
        new AddAppointmentFrame();
    }

}
