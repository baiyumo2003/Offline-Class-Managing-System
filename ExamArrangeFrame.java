/*
This frame will let user input the information for the seats arrangement during exam.
Nov.01.2019
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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ExamArrangeFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font welcomeFont = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40);
    private JButton arrangeButton;
    private JButton quantifiedButton;
    private JPanel buttonPanel;
    private JPanel instructionPanel;
    private JLabel welcomeLabel;
    private JTextField rowField;
    private JTextField columnField;
    private JTextField suspectsField;
    private JPanel inputPanel;
    private JLabel rowLabel;
    private JLabel columnLabel;
    private JPanel rowPanel;
    private JPanel columnPanel;
    private JLabel suspectsLabel;
    private JPanel suspectsPanel;
    private JLabel answerLabel;
    private JButton returnButton;
    private JButton returnPerviousButton;

    public ExamArrangeFrame()
    {
        super("Exam Arranging  Frame");
        this.setBounds(200, 200, 800, 400);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setUp();

    }

    public void setUp()
    {
        //construct Panel
        buttonPanel = new JPanel(new FlowLayout());
        instructionPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        rowPanel = new JPanel(new FlowLayout());
        suspectsPanel = new JPanel(new FlowLayout());
        columnPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(BKGROUND_COLOR);
        columnPanel.setBackground(BKGROUND_COLOR);
        rowPanel.setBackground(BKGROUND_COLOR);
        suspectsPanel.setBackground(BKGROUND_COLOR);

        //Constuct Label
        welcomeLabel = new JLabel("Please enter the classroom information", SwingConstants.CENTER);
        rowLabel = new JLabel("The column             ", SwingConstants.CENTER);
        columnLabel = new JLabel("The rows                ", SwingConstants.CENTER);
        suspectsLabel = new JLabel("Number of suspects", SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeFont);

        //add Label
        instructionPanel.add(welcomeLabel, BorderLayout.NORTH);
        instructionPanel.setBackground(BKGROUND_COLOR);

        //Construct Button
        arrangeButton = new JButton("Arrange");
        arrangeButton.addActionListener(this);
        quantifiedButton = new JButton("Quantified Intergrity");
        quantifiedButton.addActionListener(this);
        returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        returnPerviousButton = new JButton("Return to pervious");
        returnPerviousButton.addActionListener(this);

        //Add Button
        buttonPanel.add(arrangeButton);;
        buttonPanel.add(quantifiedButton);
        buttonPanel.add(returnPerviousButton);

        //Construct textField
        rowField = new JTextField(10);
        columnField = new JTextField(10);
        suspectsField = new JTextField(10);

        //Add panel
        rowPanel.add(rowLabel);
        rowPanel.add(rowField);
        columnPanel.add(columnLabel);
        columnPanel.add(columnField);
        suspectsPanel.add(suspectsLabel);
        suspectsPanel.add(suspectsField);
        inputPanel.add(rowPanel);
        inputPanel.add(columnPanel);
        inputPanel.add(suspectsPanel);
        this.add(inputPanel, BorderLayout.CENTER);
        this.add(instructionPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

     public boolean canPut(boolean[][] isSuspector, int y, int x)
    {
        int xPointer, yPointer;
        for (int i = -1; i < 2; i += 2)
        {
            xPointer = x + i;
            yPointer = y + i;
            if (0 <= xPointer && xPointer < isSuspector[0].length)
            {//point exist
                if (isSuspector[y][xPointer])
                {//point near
                    return false;
                }
            }
            if (0 <= yPointer && yPointer < isSuspector.length)
            {//point exist
                if (isSuspector[yPointer][x])
                {//point near
                    return false;
                }
            }
        }

        return true;
    }

    public int getSuspectors(boolean[][] isSuspector)
    {
        // get the number of suspectors
        int rows = isSuspector[0].length;
        int columns = isSuspector.length;
        int counter = 0;
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (isSuspector[i][j])
                {
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean[][] arrangement(boolean[][] isSuspector, int index, int suspects)
    {//create an array of student that can weither be suspect or not
        int rows = isSuspector[0].length;
        int columns = isSuspector.length;
        int start = index % rows;
        boolean[][] temp;
        for (int i = index / rows; i < columns;)
        {
            for (int j = start; j < rows; j++)
            {
                if (canPut(isSuspector, i, j))
                {
                    isSuspector[i][j] = true;
                    System.out.println(index);
                    // output solution
                    for (int a = 0; a < columns; a++)
                    {
                        for (int b = 0; b < rows; b++)
                        {
                            System.out.print(isSuspector[a][b]);
                        }
                        System.out.println();
                    }
                    //check status
                    if (getSuspectors(isSuspector) == suspects)
                    {
                        return isSuspector;
                    }//recursion
                    temp = arrangement(isSuspector, index + 1, suspects);
                    if (getSuspectors(temp) == suspects)
                    {
                        return temp;
                    }
                    isSuspector[i][j] = false;
                }
            }
            start = 0;
        }
        return isSuspector;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("Quantified Intergrity"))
        {
            try // check the input
            {
                CalculateIntegrety quantifiedResult = new CalculateIntegrety(
                        Integer.parseInt(rowField.getText()),
                        Integer.parseInt(columnField.getText()),
                        Integer.parseInt(suspectsField.getText()));
                this.getContentPane().removeAll();
                if (quantifiedResult.getResult().equals("0"))
                {
                    // if the class is endanger
                    answerLabel = new JLabel("You need to do something, the integrity is 0",
                            SwingConstants.CENTER);
                }
                else
                {
                    //if the class is in normal status
                    answerLabel = new JLabel("The quantified Integrity of the class is "
                            + quantifiedResult.getResult(),
                            SwingConstants.CENTER);
                }
                answerLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
                this.add(answerLabel, BorderLayout.CENTER);
                this.add(returnButton, BorderLayout.SOUTH);
                this.revalidate();
                this.repaint();
            }
            catch (NumberFormatException qr)
            {
                new ErrorFrame("Please correct the input");
            }

        }
        else if (command.equals("Arrange"))
        {
            try// check input
            {
                int rows = Integer.parseInt(rowField.getText());
                int columns = Integer.parseInt(columnField.getText());
                int suspects = Integer.parseInt(suspectsField.getText());
                CalculateIntegrety quantifiedResult = new CalculateIntegrety(rows, columns, suspects);
                if (quantifiedResult.getResult().equals("0"))
                {
                    new ErrorFrame("This can't be arranged!! Too many dishonest students");
                }
                else
                {
                    boolean[][] isSuspector = new boolean[columns][rows];
                    for (int i = 0; i < columns; i++)
                    {
                        for (int j = 0; j < rows; j++)
                        {
                            isSuspector[i][j] = false;
                        }
                    }
                    if (suspects >= 1)
                    {
                        isSuspector = arrangement(isSuspector, 0, suspects);
                       // System.out.print("het");
                    }
                    new ArrangeDisplayFrame(isSuspector);
                    this.dispose();
                }

            }
            catch (NumberFormatException q)
            {
                new ErrorFrame("Please correct the input");
            }
        }
        else if (command.equals("Return"))
        {
            this.getContentPane().removeAll();
            this.setUp();
            this.revalidate();
            this.repaint();
        }
        else if (command.equals("Return to pervious"))
        {
            this.dispose();
            new SeatArrangementFrame();
        }

    }

    public static void main(String[] args)
    {
        new ExamArrangeFrame();
    }
}
