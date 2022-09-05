/*
This is the Frame used to display the appointments
Dec.23th.2019
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AppointmentDisplayFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private JTable appointmentTable;
    private String[] tableHeader = new String[4];
    private Object[][] tableObject;
    private JScrollPane scrollPane;
    private final Font headerFont = new Font("Times New Roman", Font.BOLD, 23);
    private JButton returnButton;
    private JPanel buttonPanel;
    private JButton deleteButton;
    private JLabel noAppointmentLabel = new JLabel("You have no appointment today!", SwingConstants.CENTER);

    public AppointmentDisplayFrame(ArrayList<ArrayList<String>> tepoList)
    {
        super("Student List Frame");
        this.setBounds(300, 300, 800, 650);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        if (this.checkTable(tepoList) == true)
        {
            setTable(tepoList);
            this.setFrame();
        }
        else
        {
            this.setFrameWithNoAppointment();

        }
        this.setVisible(true);

    }
    
    // sort the appointment based by the time order

    private ArrayList<ArrayList<String>> sortAppointment(ArrayList<ArrayList<String>> Object)
    {
        int tepo1;

        for (int i = 0; i < Object.size(); i++)
        {
            for (int j = 0; j < Object.size() - 1; j++)
            {
                tepo1 = Object.get(j).get(0).compareTo(Object.get(j + 1).get(0));
                if (tepo1 > 0)
                {
                    ArrayList<String> r = Object.get(j);
                    Object.set(j, Object.get(j + 1));
                    Object.set(j + 1, r);
                }
                //System.out.println(Object.get(j).get(0));
            }
        }
        return Object;

    }
//If appointment exsist, construct the table to display
    public void setTable(ArrayList<ArrayList<String>> list)
    {
        tableObject = convertData(sortAppointment(list));
        tableHeader[0] = "Time";
        tableHeader[1] = "Date";
        tableHeader[2] = "People";
        tableHeader[3] = "Location";
        appointmentTable = new JTable(tableObject, tableHeader);
        appointmentTable.setFillsViewportHeight(rootPaneCheckingEnabled);
        appointmentTable.setBackground(BKGROUND_COLOR);
        appointmentTable.setGridColor(Color.BLACK);
        appointmentTable.setForeground(Color.BLACK);
        appointmentTable.getSelectionModel().addListSelectionListener(appointmentTable);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().add(appointmentTable);
        appointmentTable.getTableHeader().setFont(headerFont);
        appointmentTable.getTableHeader().setBackground(BKGROUND_COLOR);
        appointmentTable.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        this.add(scrollPane, BorderLayout.CENTER);

    }
// After some appointments deleted, check whether the appointment list is empty or not
    public boolean checkTable(ArrayList<ArrayList<String>> list)
    {
        if (list.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
//If no appointment on that day, construct the frame in this way
    public void setFrameWithNoAppointment()
    {
        buttonPanel = new JPanel(new FlowLayout());
        noAppointmentLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        this.add(noAppointmentLabel, BorderLayout.CENTER);
        returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        buttonPanel.add(returnButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }
    //Convert ArrayList to Array

    public Object[][] convertData(ArrayList<ArrayList<String>> tepo2)
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
// set the basic frame
    public void setFrame()
    {
        buttonPanel = new JPanel(new FlowLayout());
        returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        deleteButton = new JButton("Delete this appointment");
        deleteButton.addActionListener(this);
        buttonPanel.add(returnButton);
        buttonPanel.add(deleteButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
//For the update
    public void removeTable()
    {
        this.remove(scrollPane);
    }

    public void removeButtonPanel()
    {
        this.remove(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int selectCount;
        // delete the appointment
        if (e.getSource() == deleteButton)
        {
            selectCount = appointmentTable.getSelectedRowCount();
            if (selectCount > 0)
            {
                //delete all the rows seleted
                int[] rows = appointmentTable.getSelectedRows();
                DataBase tepo = new DataBase("StudentInfo");
                for (int i = 0; i < rows.length; i++)
                {
                    tepo.deleteBaseOnTwoVariablesTable("Appointment",
                            "date", "\'" + tableObject[rows[i]][1].toString() + "\'",
                            "time", "\'" + tableObject[rows[i]][0].toString() + "\'");
                    tableObject[rows[i]][0] = "Delete234";
                }
            }
            else
            {
                new ErrorFrame("Please select the appointment(s) you want to delete.");
            }
            ArrayList<ArrayList<String>> changedList = new ArrayList<>(10);
            this.removeTable();
//            for (int i = 0; i < tableObject.length; i++)
//            {
//                for (int j = 0; j < tableObject[0].length; j++)
//                {
//                    System.out.print(tableObject[i][j]+"   ");
//                }
//                System.out.println("");
//            }
            // System.out.println(tableObject.length+"   "+tableObject[0].length);
            for (int i = 0; i < tableObject.length; i++)
            {
                ArrayList<String> oneLineData = new ArrayList<>(1);
                for (int j = 0; j < tableObject[0].length; j++)
                {
                    if (tableObject[i][0].equals("Delete234"))// Make something unique
                    {
                        break;
                    }
                    else
                    {
                        oneLineData.add(tableObject[i][j].toString());
//                            System.out.println(tableObject[i][j].toString()+"     qweqwe");
                    }
                }
                if (oneLineData.isEmpty() == false)
                {
                    changedList.add(oneLineData);
                }
            }
            //               System.out.println(changedList.size()+"    "+changedList.get(0).size());
//                for (int i = 0; i < changedList.size(); i++)
//                {
//                    for (int j = 0; j < changedList.get(0).size(); j++)
//                    {
//                        System.out.print(changedList.get(i).get(j) + "    ");
//                    }
//                    System.out.println(" ");
//                }

            // if there is still appointment
            if (changedList.size() > 0)
            {
                this.setTable(changedList);
                this.removeButtonPanel();
                this.setFrame();
            }
            // if no more appointment.
            else
            {
                this.removeButtonPanel();
                this.setFrameWithNoAppointment();
            }
            this.revalidate();
            this.repaint();
        }
        else if (e.getSource() == returnButton)
        {
            this.dispose();
        }

    }

    public static void main(String[] args)
    {
        //new AppointmentDisplayFrame();
    }

}
