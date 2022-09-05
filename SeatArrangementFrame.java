/*
This Frame will provide basic seating arrangement(Bt height)
Nov.13.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class SeatArrangementFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font welcomeFont = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
    private JButton addButton;
    private JButton backButton;
    private JPanel buttonPanel;
    private JPanel instructionPanel;
    private JLabel welcomeLabel;
    private JTextField rowField;
    private JTextField columnField;
    private JPanel inputPanel;
    private JLabel rowLabel;
    private JLabel columnLabel;
    private JPanel rowPanel;
    private JPanel columnPanel;
    private final java.net.URL seating
            = getClass().getResource("seatingChart.png");
    private final ImageIcon seatingImage
            = new ImageIcon(new ImageIcon(seating)
                    .getImage().getScaledInstance(380, 275, Image.SCALE_DEFAULT));
    private JLabel imageLabel = new JLabel(seatingImage);
    private JPanel finalPanel;
    private JButton returnButton;

    public SeatArrangementFrame()
    {
        super("Seat Arrangement Frame");
        this.setBounds(200, 200, 800, 500);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        rowPanel = new JPanel(new FlowLayout());
        columnPanel = new JPanel(new FlowLayout());
        finalPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(BKGROUND_COLOR);
        columnPanel.setBackground(BKGROUND_COLOR);
        rowPanel.setBackground(BKGROUND_COLOR);
        finalPanel.setBackground(BKGROUND_COLOR);

        //Constuct Label
        welcomeLabel = new JLabel("Please enter the information of the classroom", SwingConstants.CENTER);
        rowLabel = new JLabel("The comuln", SwingConstants.CENTER);
        columnLabel = new JLabel("The rows    ", SwingConstants.CENTER);  
        welcomeLabel.setFont(welcomeFont);

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        addButton = new JButton("During Exam?");
        addButton.addActionListener(this);
        backButton = new JButton("Arrangement");
        backButton.addActionListener(this);
        returnButton = new JButton("Return");
        returnButton.addActionListener(this);

        //Add Button
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        buttonPanel.add(returnButton);

        //Construct textField
        rowField = new JTextField(10);
        columnField = new JTextField(10);

        //Add panel
        rowPanel.add(rowLabel);
        rowPanel.add(rowField);
        columnPanel.add(columnLabel);
        columnPanel.add(columnField);
        inputPanel.add(rowPanel, BorderLayout.NORTH);
        inputPanel.add(columnPanel, BorderLayout.CENTER);
        finalPanel.add(inputPanel, BorderLayout.NORTH);
        finalPanel.add(imageLabel, BorderLayout.CENTER);
        this.add(finalPanel, BorderLayout.CENTER);
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("During Exam?"))
        {
            new ExamArrangeFrame();
            this.dispose();
        }
        else if (command.equals("Arrangement"))
        {
            try
            {
            int rows = Integer.parseInt(rowField.getText());
            int column = Integer.parseInt(columnField.getText());
            DataBase tepo = new DataBase("StudentInfo");
            ArrayList<ArrayList<String>> studentData = tepo.getData("studentTable", new String[]
            {
                "name", "height"
            });
           if (rows * column >= studentData.size())
               // check if the room is able to hold that many people
            {
                new ArrangeDisplayFrame(rows, column);
                this.dispose();
            }
            else
            {
                new ErrorFrame("The size of the classroom is too small for all studnets");
            }
            }
            catch(NumberFormatException w)
            {
                new ErrorFrame("Please check your input");
            }
        }
        else if (command.equals("Return"))
        {
            this.dispose();
        }

    }

    public static void main(String[] args)
    {
        new SeatArrangementFrame();
    }
}
