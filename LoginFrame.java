/*
This is the login frame for the users to login
Nov.18.2019
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
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font welcomeFont = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
    private final Font instructionFont = new Font("Times New Roman", Font.PLAIN, 25);
    private JButton loginButton;
    private JButton changePasswordButton;
    private JPanel buttonPanel;
    private JPanel instructionPanel;
    private JLabel welcomeLabel;
    private JLabel instructionLabel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JPanel inputPanel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JPanel userNamePanel;
    private JPanel passwordPanel;
    private JTextField passwordField2;
    private JLabel alertLabel;

    public LoginFrame()
    {
        super("login Frame");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        DataBase tepo = new DataBase("StudentInfo");
        if (tepo.isTableEmpty("loginInfo", "userName") == false)
            // set up the frame that will set the first username and password
        {
            this.setUpApplication();
        }
        else// normal login frame
        {
            this.Initialize();
        }
        this.setVisible(true);
    }

    public void Initialize()
    {
        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel(new BorderLayout());
        passwordPanel = new JPanel(new FlowLayout());
        userNamePanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BKGROUND_COLOR);
        passwordPanel.setBackground(BKGROUND_COLOR);
        userNamePanel.setBackground(BKGROUND_COLOR);

        //Constuct Label
        welcomeLabel = new JLabel("Welcome to use this application", SwingConstants.CENTER);
        instructionLabel = new JLabel("Please enter username and password", SwingConstants.CENTER);
        userNameLabel = new JLabel("Username", SwingConstants.CENTER);
        passwordLabel = new JLabel("Password", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeFont);
        instructionLabel.setFont(instructionFont);

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.add(instructionLabel, BorderLayout.SOUTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        loginButton = new JButton("login");
        loginButton.addActionListener(this);
        changePasswordButton = new JButton("change password");
        changePasswordButton.addActionListener(this);

        //Add Button
        buttonPanel.add(loginButton);;
        buttonPanel.add(changePasswordButton);

        //Construct textField
        userNameField = new JTextField(10);
        passwordField = new JPasswordField(10);

        //Add panel
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        inputPanel.add(userNamePanel, BorderLayout.NORTH);
        inputPanel.add(passwordPanel, BorderLayout.CENTER);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setUpApplication()
    {
        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel(new BorderLayout());
        passwordPanel = new JPanel(new FlowLayout());
        userNamePanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BKGROUND_COLOR);
        passwordPanel.setBackground(BKGROUND_COLOR);
        userNamePanel.setBackground(BKGROUND_COLOR);

        //Constuct Label
        welcomeLabel = new JLabel("Welcome to use this application", SwingConstants.CENTER);
        instructionLabel = new JLabel("Please enter your username and your password, ", SwingConstants.CENTER);
        userNameLabel = new JLabel("Username", SwingConstants.CENTER);
        passwordLabel = new JLabel("Password", SwingConstants.CENTER);
        alertLabel = new JLabel("username stays permantely,please be careful", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeFont);
        instructionLabel.setFont(instructionFont);
        alertLabel.setFont(instructionFont);

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.add(instructionLabel, BorderLayout.CENTER);
        instructionPanel.add(alertLabel, BorderLayout.SOUTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        loginButton = new JButton("start the set up");
        loginButton.addActionListener(this);

        //Add Button
        buttonPanel.add(loginButton);;

        //Construct textField
        userNameField = new JTextField(10);
        passwordField2 = new JTextField(10);

        //Add panel
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField2);
        inputPanel.add(userNamePanel, BorderLayout.NORTH);
        inputPanel.add(passwordPanel, BorderLayout.CENTER);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String tepoUserName;
        String tepoPassword;
        ArrayList<ArrayList<String>> tepo2;
        String command = e.getActionCommand();
        if (command.equals("login"))
        {
            DataBase tepo = new DataBase("StudentInfo");
            tepo2 = tepo.getData("loginInfo", new String[]
            {
                "userName", "password"
            });
            tepoUserName = userNameField.getText();
            tepoPassword = passwordField.getText();
            // if the input is match with the database
            if (tepo2.get(0).get(0).equals(tepoUserName) && tepo2.get(0).get(1).equals(tepoPassword))
            {
                //System.out.println(tepoUserName+" "+tepoPassword);
                //System.out.println("login pressed, database accessed");
                new MainFrame();
                this.dispose();
            }
            else
            {
                new ErrorFrame("UserName or password wrong");

            }

        }
        else if (command.equals("change password"))
        {
            new ChangePasswordFrame();
            this.dispose();
            System.out.println("change password");
        }
        else if (command.equals("start the set up"))
        {
            // for the setup frame
            tepoUserName = userNameField.getText();
            tepoPassword = passwordField2.getText();
            if (tepoUserName.equals("") || tepoPassword.equals(""))
            {
                new ErrorFrame("Please enter something in the field");
            }
            else
            {
                DataBase tepo = new DataBase("StudentInfo");
                tepo.insertIntoTable("loginInfo", new Object[]
                {
                    tepoUserName, tepoPassword
                });
                this.getContentPane().removeAll();
                this.Initialize();
                this.revalidate();
                this.repaint();
            }
        }

    }

    public static void main(String[] args)
    {
        new LoginFrame();
    }

}
