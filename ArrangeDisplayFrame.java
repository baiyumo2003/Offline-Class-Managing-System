/*
This Frame will display the results of arranging seats
Nov.15.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ArrangeDisplayFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private JButton[] buttonList;
    private JLabel headerLabel;
    private JPanel mainPanel;
    private JButton returnButton;
    private JButton returnToMainButton;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private ArrayList<ArrayList<String>> studentData;

    public ArrangeDisplayFrame(boolean[][] isSuspector)
    {
        super("Arranging displayed Frame");
        this.setBounds(200, 200, 1000, 1000);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //construct Label
        headerLabel = new JLabel("The solution is: ", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));

        //construct Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        buttonPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(BKGROUND_COLOR);

        //Construct studentList
        int rows = isSuspector[0].length;
        int columns = isSuspector.length;
        buttonList = new JButton[rows * columns];
        for (int i = 0; i < buttonList.length; i += 1)
        {
            buttonList[i] = new JButton("");
            buttonList[i].setPreferredSize(new Dimension(
                    this.getWidth() / (rows + 1), this.getHeight() / (columns) / 2));
        }
        // Get the student info
        DataBase tepo = new DataBase("StudentInfo");
        studentData = tepo.getData("studentTable", new String[]
        {
            "name", "height"
        });
        //arrange them by heights
        for (int i = 0; i < studentData.size(); i++)
        {
            for (int j = 0; j < studentData.size() - 1; j++)
            {
                if (Double.parseDouble(studentData.get(j).get(1))
                        > Double.parseDouble(studentData.get(j + 1).get(1)))
                {
                    ArrayList<String> tepoStudent = studentData.get(j);
                    studentData.set(j, studentData.get(j + 1));
                    studentData.set(j + 1, tepoStudent);
                }
            }
        }
        for (int i = 0; i < buttonList.length; i++)
        {
            if (isSuspector[i / rows][i % rows])
            {
                buttonList[i].setText("Suspector");
                buttonList[i].setForeground(Color.blue);
            } 
            else
            {
                buttonList[i].setText("Student");
            }
            mainPanel.add(buttonList[i]);
        }
        for (int i = studentData.size(); i < buttonList.length; i++)
        {
            mainPanel.add(buttonList[i]);
        }

        //Construct button
        returnButton = new JButton("Return to the arrangment frame");
        returnToMainButton = new JButton("Return to the main frame");
        returnButton.addActionListener(this);
        returnToMainButton.addActionListener(this);

        //add Panel
        buttonPanel.add(returnButton);
        buttonPanel.add(returnToMainButton);

        //construct scrollPane
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().add(mainPanel);

        //add to Frame
        this.add(headerLabel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);
        //this.pack();
        this.setVisible(true);

    }
    
  public ArrangeDisplayFrame(int rows, int column)
    {
        super("Arranging displayed Frame");
        this.setBounds(200, 200, 1000, 1000);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //construct Label
        headerLabel = new JLabel("The solution is: ", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));

        //construct Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        buttonPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(BKGROUND_COLOR);

        //Construct studentList
        buttonList = new JButton[rows * column];
        for (int i = 0; i < buttonList.length; i += 1)
        {
            buttonList[i] = new JButton(" ");
            buttonList[i].setPreferredSize(new Dimension(
                    this.getWidth()/(rows+1), this.getHeight()/(column)/2));
        }
        //get student info and arrange them by heights
        DataBase tepo = new DataBase("StudentInfo");
        studentData=tepo.getData("studentTable", new String[]
        {
            "name","height"
        });
        for(int i=0;i<studentData.size();i++)
        {
            for(int j=0;j<studentData.size()-1;j++)
            {
                if(Double.parseDouble(studentData.get(j).get(1))
                        >Double.parseDouble(studentData.get(j+1).get(1)))
                {
                    ArrayList<String> tepoStudent=studentData.get(j);
                    studentData.set(j, studentData.get(j+1));
                    studentData.set(j+1, tepoStudent);
                    
                }
            }
        }
        
        
        for(int i=0;i<studentData.size();i++)
        {
            buttonList[i].setText(studentData.get(i).get(0));
            mainPanel.add(buttonList[i]);
        }
        for(int i=studentData.size();i<buttonList.length;i++)
        {
            mainPanel.add(buttonList[i]);
        }
        

        //Construct button
        returnButton = new JButton("Return to arrangment frame");
        returnToMainButton = new JButton("Return to main frame");
        returnButton.addActionListener(this);
        returnToMainButton.addActionListener(this);

        //add Panel
        buttonPanel.add(returnButton);
        buttonPanel.add(returnToMainButton);

        //construct scrollPane
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().add(mainPanel);

        //add to Frame
        this.add(headerLabel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);
        //this.pack();
        this.setVisible(true);

    }
   
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Return to main frame"))
        {
            this.dispose();
        } 
        else if (command.equals("Return to arrangment frame"))
        {
            this.dispose();
            new SeatArrangementFrame();
        }
        else if(command.equals("Return to the main frame"))
        {
            new MainFrame();
            this.dispose();         
        }
        else if(command.equals("Return to the arrangment frame"))
        {
            new ExamArrangeFrame();
            this.dispose();
        }

    }

    public static void main(String args[])
    {
        boolean[][] sample = new boolean[4][4];
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                sample[i][j] = true;
            }
        }
        new ArrangeDisplayFrame(sample);
    }

}
