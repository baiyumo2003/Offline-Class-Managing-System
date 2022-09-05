/*
This is the main frame of the whole application
Nov.6th.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MainFrame extends JFrame implements ActionListener
{
//initialise and constructor
    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font welcomeFont = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
    private final Font instructionFont = new Font("Times New Roman", Font.BOLD, 30);
    private JButton gradeBookButton;
    private JButton studentListButton;
    private JButton seatsArrangeButton;
    private JButton appointmentButton;
    private JButton helpButton;
    private JPanel buttonPanel;
    private JPanel instructionPanel;
    private JLabel welcomeLabel;
    private JLabel instructionLabel;
    private final java.net.URL welcome
            = getClass().getResource("mainImage.png");
    private final ImageIcon welcomeImage
            = new ImageIcon(new ImageIcon(welcome)
                    .getImage().getScaledInstance(900, 350, Image.SCALE_DEFAULT));
    private JLabel imageLabel = new JLabel(welcomeImage, SwingConstants.CENTER);
    private JLabel time = new JLabel("", SwingConstants.CENTER);
    private JPanel middlePanel;
    private JLabel blankLabel;
    private JPanel timePanel;

    public MainFrame()
    {
        super("Main Frame");
        this.setBounds(300, 600, 1000, 635);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        timePanel = new JPanel(new BorderLayout());

        //Constuct Label
        welcomeLabel = new JLabel("Welcome to use Class managing system", SwingConstants.CENTER);
        instructionLabel = new JLabel("Please use buttons to guide", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeFont);
        instructionLabel.setFont(instructionFont);
        blankLabel = new JLabel(" "
                + " ");

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.add(instructionLabel, BorderLayout.SOUTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        seatsArrangeButton = new JButton("To arrnge seats");
        seatsArrangeButton.addActionListener(this);
        gradeBookButton = new JButton("GradeBook");
        gradeBookButton.addActionListener(this);
        appointmentButton = new JButton("Appointment");
        appointmentButton.addActionListener(this);
        studentListButton = new JButton("Student List");
        studentListButton.addActionListener(this);
        helpButton = new JButton("Help");
        helpButton.addActionListener(this);

        //set update time
        time.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        this.setTimer(time);

        //set middle Panel
        timePanel.add(blankLabel, BorderLayout.NORTH);
        timePanel.add(imageLabel, BorderLayout.CENTER);
        middlePanel.add(timePanel, BorderLayout.NORTH);
        middlePanel.add(time, BorderLayout.CENTER);
        timePanel.setBackground(BKGROUND_COLOR);
        middlePanel.setBackground(BKGROUND_COLOR);

        //Add Button
        buttonPanel.add(seatsArrangeButton);
        buttonPanel.add(gradeBookButton);
        buttonPanel.add(appointmentButton);
        buttonPanel.add(studentListButton);
        buttonPanel.add(helpButton);

        //Add panel
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    private void setTimer(JLabel time)
    {
        // update the JLabel by 0.1s
        JLabel varTime = time;
        Timer timeAction = new Timer(100, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                long timemillis = System.currentTimeMillis();
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                varTime.setText(timeFormat.format(new Date(timemillis)));
            }
        }
        );
        timeAction.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("To arrnge seats"))
        {
            new SeatArrangementFrame();
            // System.out.println("Arrange seats pressed");
        }
        if (command.equals("GradeBook"))
        {
            new GradeBookFrame();
            System.out.println("GradeBook and database accessed");
        }
        if (command.equals("Appointment"))
        {
            // System.out.println("Appointment");
            new CalenderFrame();
        }
        if (command.equals("Student List"))
        {
            new StudentListFrame();
            //System.out.println("StudentList");
        }
        if (command.equals("Help"))
        {
            new HelpFrame();
            //System.out.println("Help");
        }

    }

    public static void main(String[] args)
    {
        new MainFrame();
    }

}
