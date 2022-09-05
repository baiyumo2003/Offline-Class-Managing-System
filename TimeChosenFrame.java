/*
Choose the time for the appointment
Dec.19th.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

public class TimeChosenFrame extends JFrame implements ActionListener
{

    private JComboBox <Object> hourBox = new JComboBox<>();
    private JComboBox <Object>minuteBox = new JComboBox<>();
    private JLabel hourLabel = new JLabel("Please selete the hour:  ");
    private JLabel minuteLabel = new JLabel("Please selete the minute:");
    private JPanel hourPanel = new JPanel(new FlowLayout());
    private JPanel minutePanel = new JPanel(new FlowLayout());
    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private JPanel finalPanel = new JPanel(new BorderLayout());
    private JButton confirmButton = new JButton("Confirm the time");
    private JPanel buttonPanel = new JPanel(new FlowLayout());
    private AddAppointmentFrame tepoFrame;

    public TimeChosenFrame(AddAppointmentFrame tepo)
    {
        super("Time chosen Frame");
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setBounds(300, 200, 900, 420);
        this.setColor();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.initialize();
        this.setLocation(500, 300);
        tepoFrame = tepo;
        //this.setResizable(false);
        this.setVisible(true);
    }

    public void initialize()
    {
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        minuteLabel.setFont(font);
        hourLabel.setFont(font);
        //set combo box 
        for (int i = 0; i < 24; i++)
        {
            if (i < 10)

            {
                // make the number smaller than 10 also has two places
                hourBox.addItem("0" + i);
            }
            else
            {
                hourBox.addItem(i);
            }
        }
        for (int i = 0; i < 60; i++)
        {
            if (i < 10)

            {
                // make the number smaller than 10 also has two places
                minuteBox.addItem("0" + i);
            }
            else
            {
                minuteBox.addItem(i);
            }
        }
        //add into together
        hourPanel.add(hourLabel);
        hourPanel.add(hourBox);
        minutePanel.add(minuteLabel);
        minutePanel.add(minuteBox);
        finalPanel.add(hourPanel, BorderLayout.NORTH);
        finalPanel.add(minutePanel, BorderLayout.CENTER);
        buttonPanel.add(confirmButton);
        confirmButton.addActionListener(this);
        this.add(finalPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setColor()
    {
        // set Back ground color
        finalPanel.setBackground(BKGROUND_COLOR);
        minutePanel.setBackground(BKGROUND_COLOR);
        hourPanel.setBackground(BKGROUND_COLOR);
    }

    public String getData()
    {
        //get the informtion
        String[] list = new String[2];
        list[0] = hourBox.getSelectedItem().toString();
        list[1] = minuteBox.getSelectedItem().toString();
        String time = list[0] + ":" + list[1];
        return time;
    }

    public static void main(String[] args)
    {
        new TimeChosenFrame(new AddAppointmentFrame());
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == confirmButton)
        {
            tepoFrame.setTime(getData());
            this.dispose();
        }

    }

}
