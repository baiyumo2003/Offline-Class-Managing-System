/*
This Frame is for user who wants to change their login password
Nov.06.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ChangePasswordFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font welcomeFont = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
    private final Font instructionFont = new Font("Times New Roman", Font.BOLD, 30);
    private JButton loginButton;
    private JButton changePasswordButton;
    private JPanel buttonPanel;
    private JPanel instructionPanel;
    private JLabel welcomeLabel;
    private JTextField userNameField;
    private JTextField oldPasswordField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JPanel inputPanel;
    private JLabel userNameLabel;
    private JLabel oldPasswordLabel;
    private JPanel userNamePanel;
    private JPanel oldPasswordPanel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JPanel passwordPanel;
    private JPanel confirmPanel;

    public ChangePasswordFrame()
    {
        super("Change Password Frame");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        userNamePanel = new JPanel(new FlowLayout());
        passwordPanel = new JPanel(new FlowLayout());
        confirmPanel = new JPanel(new FlowLayout());
        oldPasswordPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BKGROUND_COLOR);
        oldPasswordPanel.setBackground(BKGROUND_COLOR);
        userNamePanel.setBackground(BKGROUND_COLOR);
        passwordPanel.setBackground(BKGROUND_COLOR);
        confirmPanel.setBackground(BKGROUND_COLOR);

        //Constuct Label
        welcomeLabel = new JLabel("Please enter username and old password", SwingConstants.CENTER);
        userNameLabel = new JLabel("Username            ", SwingConstants.CENTER);
        oldPasswordLabel = new JLabel("Old Password       ", SwingConstants.CENTER);
        passwordLabel = new JLabel("New password      ", SwingConstants.CENTER);
        confirmPasswordLabel = new JLabel("Confirm password", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeFont);

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        loginButton = new JButton("change");
        loginButton.addActionListener(this);
        changePasswordButton = new JButton("Back to login");
        changePasswordButton.addActionListener(this);

        //Add Button
        buttonPanel.add(loginButton);;
        buttonPanel.add(changePasswordButton);

        //Construct textField
        userNameField = new JTextField(10);
        oldPasswordField = new JTextField(10);
        passwordField = new JPasswordField(10);
        confirmField = new JPasswordField(10);

        //Add panel
        userNamePanel.add(userNameLabel);
        userNamePanel.add(userNameField);
        oldPasswordPanel.add(oldPasswordLabel);
        oldPasswordPanel.add(oldPasswordField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        confirmPanel.add(confirmPasswordLabel);
        confirmPanel.add(confirmField);
        inputPanel.add(userNamePanel);
        inputPanel.add(oldPasswordPanel);
        inputPanel.add(passwordPanel);
        inputPanel.add(confirmPanel);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String tepoUserName;
        String tepoPassword;
        String command = e.getActionCommand();
        if (command.equals("change"))
        {
            
            tepoUserName = userNameField.getText();
            tepoPassword = oldPasswordField.getText();
            DataBase tepo = new DataBase("StudentInfo");
            ArrayList<ArrayList<String>> data = tepo.getData("loginInfo", new String[]
            {
                "userName", "password"
            });
            //check if the enter username and password as recorede
            System.out.println(data.get(0).get(0)+"qweqwewqe"+data.get(0).get(1));
            if (tepoUserName.equals(data.get(0).get(0)) && tepoPassword.equals(data.get(0).get(1)))
            {
                String password = passwordField.getText();
                // check whether the confirm password and password are the same
                if (password.equals(confirmField.getText()))
                {
                    // set into database
                    tepo.deleteFromTable("loginInfo", "userName", "\'"+data.get(0).get(0)+"\'");
                    tepo.insertIntoTable("loginInfo", new Object[]
                            {
                                data.get(0).get(0), password
                            });
//                    tepo.updateInTable("loginInfo", "userName",  data.get(0).get(0),
//                            new Object[]
//                            {
//                                "userName", "password"
//                            },
//                            new Object[]
//                            {
//                                data.get(0).get(0), password
//                            });
                    this.dispose();
                    new MainFrame();
                }
                else
                {
                    new ErrorFrame("New password and confirm password are different");
                }
            }
            else
            {
                new ErrorFrame("User Name or old password wrong");
            }

        }
        if (command.equals("Back to login"))
        {
            this.dispose();
            System.out.println("Back to login");
            new LoginFrame();
        }

    }

    public static void main(String[] args)
    {
        new ChangePasswordFrame();
    }
}
