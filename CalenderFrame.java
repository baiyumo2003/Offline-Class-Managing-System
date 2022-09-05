/*
This Frame will displayed a calender for user to make appointment
Nov.9th.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class CalenderFrame extends JFrame implements ActionListener
{

    //Initilize and construct 
    private JLabel YearLabel = new JLabel("Year:");
    private JComboBox<String> YearBox = new JComboBox<>();
    private JLabel MonthLabel = new JLabel("Month:");
    private JComboBox<String> MonthBox = new JComboBox<>();
    private JButton updateButton = new JButton("Update");
    private JButton todayButton = new JButton("today");
    private LocalDateTime dateOfToday = LocalDateTime.now();
    private int currentYear = dateOfToday.getYear();
    private int currentMonth = dateOfToday.getMonthValue();
    private boolean todayFlag = false;
    private JButton[] dayButton = new JButton[42];
    private final String[] week =
    {
        "Sunday", "Monday", "Thuesday", "Wendesday", "Thursday", "Friday", "Saturday"
    };
    private JButton[] weekButton = new JButton[7];
    private String yearInt = null;
    private int monthInt;
    private JPanel headerPanel = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JPanel finalPanel = new JPanel(new BorderLayout());
    private JButton addButton = new JButton("Add an appointment");
    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private JPanel dayPanel = new JPanel();
    private JLabel time = new JLabel("", SwingConstants.CENTER);
    private JButton returnButton = new JButton("Return");
    private JPanel buttonPanel = new JPanel(new FlowLayout());
    private String dataPressed;
    private AddAppointmentFrame appointmentFrame;
    private boolean whichConstructor;

    public CalenderFrame()
    {
        super("calander");
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setBounds(300, 200, 900, 420);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.initialize();
        this.setLocation(500, 300);
        whichConstructor = true;
        //this.setResizable(false);
        this.setVisible(true);
    }

    public CalenderFrame(AddAppointmentFrame tepo)
    {
        super("calander");
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setBounds(300, 200, 900, 420);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.initilizeWhenMakeAppointment();
        this.setLocation(500, 300);
        whichConstructor = false;
        appointmentFrame = tepo;
        this.setResizable(false);
        this.setVisible(true);

    }

    //initialize 
    private void initialize()
    {
        //Construct and set font
        Font font = new Font("Times New Roman", Font.BOLD, 14);
        YearLabel.setFont(font);
        MonthLabel.setFont(font);
        updateButton.setFont(font);
        todayButton.setFont(font);

        //set the year box from -10 to +10
        for (int i = currentYear - 10; i <= currentYear + 10; i++)
        {
            YearBox.addItem(i + "");
        }
        // set initial is the current year
        YearBox.setSelectedIndex(10);

        //set the month box
        for (int i = 1; i < 13; i++)
        {
            MonthBox.addItem(i + "");
        }
        // System.out.println(currentMonth);
        MonthBox.setSelectedIndex(currentMonth - 1);

        //add everything into panel
        headerPanel.add(YearLabel);
        headerPanel.add(YearBox);
        headerPanel.add(MonthLabel);
        headerPanel.add(MonthBox);
        headerPanel.add(updateButton);
        headerPanel.add(todayButton);
        updateButton.addActionListener(this);
        todayButton.addActionListener(this);

        //construct week header
        dayPanel.setLayout(new GridLayout(7, 7, 4, 3));
        for (int i = 0; i < 7; i++)
        {
            weekButton[i] = new JButton(week[i]);
            weekButton[i].setForeground(Color.black);
            dayPanel.add(weekButton[i]);
        }
        weekButton[0].setForeground(Color.red);
        weekButton[6].setForeground(Color.red);

        //add day into Panel
        for (int i = 0; i < 42; i++)
        {
            dayButton[i] = new JButton(" ");
            dayPanel.add(dayButton[i]);
        }
        // Add ActionListernr
        for (int i = 0; i < 42; i++)
        {
            dayButton[i].addActionListener(this);
        }

        // calculate the start of the month
        printDay();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(dayPanel, BorderLayout.CENTER);
        finalPanel.add(mainPanel, BorderLayout.NORTH);
        //
        finalPanel.add(time, BorderLayout.CENTER);
        time.setFont(new Font("Times New Roman", Font.BOLD, 30));
        this.setTimer(time);
        buttonPanel.add(addButton);
        buttonPanel.add(returnButton);
        finalPanel.add(buttonPanel, BorderLayout.SOUTH);
        addButton.addActionListener(this);
        returnButton.addActionListener(this);
        this.setColor();
        this.getContentPane().add(finalPanel, BorderLayout.CENTER);
    }

    private void setColor()
    {
        finalPanel.setBackground(BKGROUND_COLOR);
        mainPanel.setBackground(BKGROUND_COLOR);
        headerPanel.setBackground(BKGROUND_COLOR);
        dayPanel.setBackground(BKGROUND_COLOR);

    }

    public void initilizeWhenMakeAppointment()
    {

        //Construct and set font
        Font font = new Font("Times New Roman", Font.BOLD, 14);
        YearLabel.setFont(font);
        MonthLabel.setFont(font);
        updateButton.setFont(font);
        todayButton.setFont(font);

        //set the year box from -10 to +10
        for (int i = currentYear - 10; i <= currentYear + 10; i++)
        {
            YearBox.addItem(i + "");
        }
        // set initial is the current year
        YearBox.setSelectedIndex(10);

        //set the month box
        for (int i = 1; i < 13; i++)
        {
            MonthBox.addItem(i + "");
        }
        // System.out.println(currentMonth);
        MonthBox.setSelectedIndex(currentMonth - 1);

        //add everything into panel
        headerPanel.add(YearLabel);
        headerPanel.add(YearBox);
        headerPanel.add(MonthLabel);
        headerPanel.add(MonthBox);
        headerPanel.add(updateButton);
        headerPanel.add(todayButton);
        updateButton.addActionListener(this);
        todayButton.addActionListener(this);

        //construct week header
        dayPanel.setLayout(new GridLayout(7, 7, 4, 3));
        for (int i = 0; i < 7; i++)
        {
            weekButton[i] = new JButton(week[i]);
            weekButton[i].setForeground(Color.black);
            dayPanel.add(weekButton[i]);
        }
        weekButton[0].setForeground(Color.red);
        weekButton[6].setForeground(Color.red);

        //add day into Panel
        for (int i = 0; i < 42; i++)
        {
            dayButton[i] = new JButton(" ");
            dayPanel.add(dayButton[i]);
        }
        // Add ActionListernr
        for (int i = 0; i < 42; i++)
        {
            dayButton[i].addActionListener(this);
        }

        // calculate the start of the month
        printDay();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(dayPanel, BorderLayout.CENTER);
        this.setColor();
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void setTimer(JLabel time)
    {
        final JLabel varTime = time;
        Timer timeAction = new Timer(100, new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                long timemillis = System.currentTimeMillis();
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                varTime.setText(timeFormat.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    }

    //display current date
    private void printDay()
    {
        // for update
        clearButtons();

        //check if the today button is click
        if (todayFlag)
        {
            yearInt = currentYear + "";
            monthInt = currentMonth - 1;
        }
        // if update button click
        else
        {
            yearInt = YearBox.getSelectedItem().toString();
            monthInt = MonthBox.getSelectedIndex();
        }

        //get the chosen year
        int selectedYear = Integer.parseInt(yearInt);
        //Set the first day of the month selected
        Date firstDay = new Date(selectedYear - 1900, monthInt, 1);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(firstDay);
        int days = 0;
        // System.out.println(firstDay);
        int dayOfWeek = firstDay.getDay();
//        System.out.println(selectedYear);
//        System.out.println(dayOfWeek);
//        System.out.println(monthInt+1);
        if (monthInt == 0 || monthInt == 2 || monthInt == 4 || monthInt == 6
                || monthInt == 7 || monthInt == 9 || monthInt == 11)
        {
            days = 31;
        }
        else if (monthInt == 3 || monthInt == 5 || monthInt == 8 || monthInt == 10)
        {
            days = 30;
        }
        else
        {
            if (cal.isLeapYear(selectedYear - 1900))
            {
                days = 29;
            }
            else
            {
                days = 28;
            }
        }

        int count = 1;
        // System.out.println(dayOfWeek);
        for (int i = dayOfWeek; i < dayOfWeek + days; count += 1, i++)
        {
            if (i % 7 == 0 || i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 41)
            {
                if (i == dayOfWeek + dateOfToday.getDayOfMonth() - 1)
                {
                    // set the today's date as color blue
                    dayButton[i].setForeground(Color.blue);
                    dayButton[i].setText(count + "");
                }
                else
                {
                    // rest sunday and saturday as red to remind break
                    dayButton[i].setForeground(Color.red);
                    dayButton[i].setText(count + "");
                }
            }
            else
            {
                // set the today's date as color blue
                if (i == dayOfWeek + dateOfToday.getDayOfMonth() - 1)
                {
                    dayButton[i].setForeground(Color.blue);
                    dayButton[i].setText(count + "");
                }
                else
                {
                    // set all other day as black
                    dayButton[i].setForeground(Color.black);
                    dayButton[i].setText(count + "");
                }
            }

        }

        // Incase 
        if (dayOfWeek == 0)
        {
            for (int i = days; i < 42; i++)
            {
                dayButton[i].setText("");
            }
        }
        else
        {
            // set everything front of the starting day blank
            for (int i = 0; i < dayOfWeek; i++)
            {
                dayButton[i].setText("");
            }
            // set everthing after the last day of month blank
            for (int i = dayOfWeek + days; i < 42; i++)
            {
                dayButton[i].setText("");
            }
        }
    }
//Clear buttons

    private void clearButtons()
    {
        for (int i = 0; i < 42; i++)
        {
            dayButton[i].setText("");
        }
    }

    public String[][] convertData(ArrayList<ArrayList<String>> tepo2)
    {
        //System.out.println(tepo2.size());
        String[][] dataList;
        if (tepo2.isEmpty() == false)
        {
            dataList = new String[tepo2.size()][tepo2.get(0).size()];
            //System.out.println(dataList.length);
            // System.out.println(dataList[0].length);
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
            dataList = new String[0][0];
        }
        return dataList;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == updateButton)
        {
            todayFlag = false;
            printDay();
        }
        else if (e.getSource() == todayButton)
        {
            todayFlag = true;
            YearBox.setSelectedIndex(10);
            MonthBox.setSelectedIndex(currentMonth - 1);
            printDay();
        }
        else if (e.getSource() == addButton)
        {
            appointmentFrame = new AddAppointmentFrame();
        }
        else if (e.getSource() == returnButton)
        {
            this.dispose();
        }
        else if (!e.getActionCommand().equals("") && whichConstructor == true)
        {
            //set appointment into database
            ArrayList<ArrayList<String>> dayPressedData;
            dayPressedData = new ArrayList<>();
            DataBase tepo;
            tepo = new DataBase("StudentInfo");
            String[][] appointmentData = this.convertData(tepo.
                    getData("Appointment", new String[]
                    {
                        "time", "date", "people", "location"
            }));

//            if (appointmentData.length == 0)
//            {
//                new ErrorFrame("No appointment for today");
//            }
            String pressedDate = (monthInt + 1) + "/" + e.getActionCommand() + "/" + yearInt;
//            for(int i=0;i<appointmentData.length;i++)
//            {
//                for(int j=0;j<appointmentData[0].length;j++)
//                {
//                    System.out.print(appointmentData[i][j]+"  ");
//                }
//                System.out.println(" ");
//            }
//            System.out.println(pressedDate + "    " + appointmentData[0][1]);
            for (int i = 0; i < appointmentData.length; i++)
            {
                if (appointmentData[i][1].equals(pressedDate))
                {
                    ArrayList<String> oneLineData;
                    oneLineData = new ArrayList<>();
                    for (int j = 0; j < appointmentData[0].length; j++)
                    {
                        oneLineData.add(appointmentData[i][j]);
                    }
                    dayPressedData.add(oneLineData);

                }
            }
//            for(int i=0;i<dayPressedData.size();i++)
//            {
//                for(int j=0;j<dayPressedData.get(0).size();j++)
//                {
//                    System.out.print(dayPressedData.get(i).get(j)+"  ");
//                }
//                System.out.println(" ");
//            }
            new AppointmentDisplayFrame(dayPressedData);

        }
        else if (!e.getActionCommand().equals("") && whichConstructor == false)
        {
            //appointmentFrame.setFocus();
            appointmentFrame.setDate(MonthBox.getSelectedItem() + "/" + e.getActionCommand() + "/"
                    + YearBox.getSelectedItem());
            this.dispose();
        }
    }

    public static void main(String[] args)
    {
        CalenderFrame test = new CalenderFrame();
    }
}
