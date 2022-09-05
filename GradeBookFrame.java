/*
This Frame will display the gradebook for the user
Nov.11.2019
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

public class GradeBookFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private JTable gradeTable;
    private String[] tableHeader;
    private Object[][] tableObject;
    private JScrollPane scrollPane;
    private final Font headerFont = new Font("Times New Roman", Font.BOLD, 23);
    private JButton returnButton;
    private JButton addAssignmentButton;
    private JPanel buttonPanel;
    private JButton assignmentDisplayButton;
    private JButton gradeAssignmentButton;
    private JButton removeAssignmentButton;
    private JLabel noGradeLabel = new JLabel("No grades were entered", SwingConstants.CENTER);
    private JButton graphButton = new JButton("Graph");
    private boolean whetherExsist;
    private final int emptySpace=2;

    public GradeBookFrame()
    {
        super("Gradebook Frame");
        this.setBounds(300, 300, 900, 650);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        DataBase tepo = new DataBase("StudentInfo");
        // Join the normalized table to get the data
        ArrayList<ArrayList<String>> Object = tepo.joinAndGetData("gradeTable",
                "studentTable", "assignmentTable", "studentId", "assignmentId", "id",
                "assignmentId", new String[]
                {
                    "name", "id", "assignmentId", "assignmentName", "assignmentGrade"
                });
        this.setTable(this.convertArrayList(this.sortArrayList(Object)));
        this.setFrame();
        this.setVisible(true);
    }

    public void setTable(String[][] tepo)
    {
        if (tepo.length == 0)
        {
            noGradeLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
            this.add(noGradeLabel, BorderLayout.CENTER);
        }
        else
        {
            String firstName = tepo[0][0];
            int columnCounter = 0;
            for (int i = 0; i < tepo.length; i++)
            {
                //Calculate the total numebr of assignments
                if (tepo[i][0].equals(firstName))
                {
                    columnCounter++;
                }
                else
                {
                    break;
                }
            }
//        System.out.println(columnCounter);
//        for(int i=0;i<tepo.length;i++)
//        {
//            for(int j=0;j<tepo[0].length;j++)
//            {
//                System.out.print(tepo[i][j]+"     ");
//            }
//            System.out.println("");
//        }


            // leave space for the name and final grade
            tableHeader = new String[columnCounter + emptySpace];
            //set the first column
            tableHeader[0] = "Name";
            //add assignment name as a header
            for (int i = 1; i <= columnCounter; i++)
            {
                tableHeader[i] = tepo[i - 1][3];
            }
            //set the final column
            tableHeader[columnCounter + 1] = "Final Grade";
//        for (int i = 0; i < tableHeader.length; i++)
//        {
//            System.out.println(tableHeader[i] + "fyfuyfyu");
//        }

            DataBase assignment = new DataBase("StudentInfo");
            ArrayList<ArrayList<String>> assignmentList = assignment.getData("assignmentTable",
                    new String[]
                    {
                        "assignmentId", "assignmentGrade"
                    });
            double totalGrade = 0;
            for (int i = 0; i < columnCounter; i++)
            {
                String tepoId = tepo[i][2];
                // System.out.println(tepoId+" n");
                for (int j = 0; j < assignmentList.size(); j++)
                {
                    // find the sum of the assignment grade
                    if (assignmentList.get(j).get(0).equals(tepoId))
                    {
                        totalGrade += Double.parseDouble(assignmentList.get(j).get(1));
                        break;
                    }
                }

            }
            // System.out.println(totalGrade);
            
            //put student grade into the table 
            int dataLine = 0;
            tableObject = new Object[tepo.length / columnCounter][tableHeader.length];
            for (int i = 0; i < tepo.length / columnCounter; i++)
            {
                for (int j = 0; j < tableHeader.length - 2; j++)
                {
                    // System.out.println(tableHeader.length-1);
                    //System.out.println(tepo[j][4]);
                    tableObject[i][j + 1] = tepo[dataLine][4];
                    dataLine++;
                }
            }
            DataBase tepoBase = new DataBase("StudentInfo");
            double average = 0;
            dataLine = 0;
            // calculate the average final for each student
            for (int i = 0; i < tepo.length / columnCounter; i++)
            {
                average = 0;
                for (int j = 0; j < tableHeader.length - 2; j++)
                {
                    average += Double.parseDouble(tepo[dataLine][4]);
                    //System.out.println(Double.parseDouble(tepo[j][4])+"sadasdasdasd");
                    dataLine++;
                }
                tableObject[i][tableHeader.length - 1] = (int) Math.ceil((average / totalGrade) * 100);
            }
            int counter = 0;
            
            // Add the information of student
            for (int i = 0; i < tepo.length; i += columnCounter)
            {

                tableObject[counter][0] = tepo[i][0];
                counter++;
            }
            // set the information into the Jtable
            gradeTable = new JTable(tableObject, tableHeader);
            gradeTable.setFillsViewportHeight(rootPaneCheckingEnabled);
            gradeTable.setBackground(BKGROUND_COLOR);
            gradeTable.setGridColor(Color.BLACK);
            gradeTable.setForeground(Color.BLACK);
            gradeTable.getSelectionModel().addListSelectionListener(gradeTable);
            scrollPane = new JScrollPane();
            //Add the table into scroll pane
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getViewport().add(gradeTable);
            gradeTable.getTableHeader().setFont(headerFont);
            gradeTable.getTableHeader().setBackground(BKGROUND_COLOR);
            gradeTable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            whetherExsist = true;
            this.add(scrollPane, BorderLayout.CENTER);

        }
    }

    // set the table for the update
    public void setAssignmentTable(ArrayList<ArrayList<String>> tepo)
    {
        Object[][] dataList = convertArrayList(tepo);
        tableHeader = new String[3];
        tableHeader[0] = "AssignmentId";
        tableHeader[1] = "AssignmentName";
        tableHeader[2] = "Out of";
        tableObject = dataList;
        gradeTable = new JTable(tableObject, tableHeader);
        gradeTable.setFillsViewportHeight(rootPaneCheckingEnabled);
        gradeTable.setBackground(BKGROUND_COLOR);
        gradeTable.setGridColor(Color.BLACK);
        gradeTable.setForeground(Color.BLACK);
        gradeTable.getSelectionModel().addListSelectionListener(gradeTable);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().add(gradeTable);
        gradeTable.getTableHeader().setFont(headerFont);
        gradeTable.getTableHeader().setBackground(BKGROUND_COLOR);
        gradeTable.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        this.add(scrollPane, BorderLayout.CENTER);

    }
    
    //Sort the joined data into lexicographical order

    public ArrayList<ArrayList<String>> sortArrayList(ArrayList<ArrayList<String>> Object)
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
            }
        }
        return Object;
    }

    // Convert ArrayList into array
    public String[][] convertArrayList(ArrayList<ArrayList<String>> tepo)
    {
        //System.out.println(tepo.size());
        String[][] dataList;
        if (tepo.isEmpty() == false)
        {
            dataList = new String[tepo.size()][tepo.get(0).size()];
            //System.out.println(dataList.length);
            // System.out.println(dataList[0].length);
            for (int i = 0; i < tepo.size(); i++)
            {
                for (int j = 0; j < tepo.get(0).size(); j++)
                {
                    dataList[i][j] = tepo.get(i).get(j);
                }
            }
        }
        //If the arraylist is empty
        else
        {
            dataList = new String[0][0];
        }
        return dataList;
    }

    // set the frame
    public void setFrame()
    {
        buttonPanel = new JPanel(new FlowLayout());
        returnButton = new JButton("Back to Main Frame");
        assignmentDisplayButton = new JButton("Show assignment list");
        returnButton.addActionListener(this);
        graphButton.addActionListener(this);
        assignmentDisplayButton.addActionListener(this);
        buttonPanel.add(returnButton);
        buttonPanel.add(assignmentDisplayButton);
        buttonPanel.add(graphButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }
    
    // for the assignment display

    public void setAssignmentFrame()
    {
        buttonPanel = new JPanel(new FlowLayout());
        returnButton = new JButton("Back to gradeBook");
        addAssignmentButton = new JButton("Add an assignment");
        gradeAssignmentButton = new JButton("Grade the assignment");
        removeAssignmentButton = new JButton("Delete Assignment");
        returnButton.addActionListener(this);
        addAssignmentButton.addActionListener(this);
        gradeAssignmentButton.addActionListener(this);
        removeAssignmentButton.addActionListener(this);
        buttonPanel.add(returnButton);
        buttonPanel.add(addAssignmentButton);
        buttonPanel.add(gradeAssignmentButton);
        buttonPanel.add(removeAssignmentButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    public Object[][] getTableObject()
    {
        return tableObject;
    }

    @Override
    public void removeAll()
    {
        //remove everything on the frame
        this.getContentPane().removeAll();
    }

    public void removeTable()
    {
        // Just remove the table 
        this.remove(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Back to Main Frame"))
        {
            this.dispose();
        }
        else if (command.equals("Back to gradeBook"))
        {
            this.dispose();
            new GradeBookFrame();
        }
        else if (e.getSource() == addAssignmentButton)
        {
            //System.out.println("123123123123");
            new CreateAssignmentFrame(this);
        }
        else if (e.getSource() == assignmentDisplayButton)
        {
            DataBase tepo = new DataBase("StudentInfo");
            
            //Get assignment information
            ArrayList<ArrayList<String>> dataList = tepo.getData("assignmentTable",
                    new String[]
                    {
                        "assignmentId", "assignmentName", "assignmentGrade"
                    });
            this.removeAll();
            this.setAssignmentTable(dataList);
            this.setAssignmentFrame();
            this.revalidate();
            this.repaint();
        }
        else if (command.equals("Delete Assignment"))
        {
            int selectCount;
            selectCount = gradeTable.getSelectedRowCount();
            DataBase tepo = new DataBase("StudentInfo");
            if (selectCount > 0)
            {
                //delete all the rows seleted
                int[] rows = gradeTable.getSelectedRows();
                for (int i = 0; i < rows.length; i++)
                {
                    //System.out.println(tableObject[rows[i]][1].toString()+"qqqqqqqqqqq");
                    tepo.deleteFromTable("assignmentTable", "assignmentId",
                            tableObject[rows[i]][0].toString());
                }
            }
            else
            {
                new ErrorFrame("Please select the assignment(s) you want to delete.");
            }
            this.removeTable();
            //Update the table
            this.setAssignmentTable(tepo.getData("assignmentTable",
                    new String[]
                    {
                        "assignmentId", "assignmentName", "assignmentGrade"
                    }));
            this.revalidate();
            this.repaint();
        }
        else if (command.equals("Grade the assignment"))
        {
            int selectCount;
            selectCount = gradeTable.getSelectedRowCount();
            if (selectCount > 1)
            {
                // make sure only one assignment is being selected
                new ErrorFrame("Only select one assignment to grade");
            }
            else if (selectCount == 0)
            {
                new ErrorFrame("You have to choose one assignment to grade");
            }
            else
            {
                String[] tepo = new String[3];
                int[] selectRow = gradeTable.getSelectedRows();
                System.out.println(selectRow[0]);
                for (int i = 0; i < tepo.length; i++)
                {
                    //System.out.println(tableObject[selectRow[0]][i].toString());
                    tepo[i] = tableObject[selectRow[0]][i].toString();
                }
                
                new GradeAssignmentFrame(tepo);
            }
        }

        else if (command.equals("Graph") && whetherExsist == true)
        {
            //graph the student grade information
            System.out.println(this.getTableObject().length);
            new GraphFrame(this.getTableObject());

        }

    }

    public static void main(String[] args)
    {
        GradeBookFrame tepo = new GradeBookFrame();
        Object[][] max = tepo.getTableObject();
        //System.out.println(max[0][max[0].length-1]);
    }

}
