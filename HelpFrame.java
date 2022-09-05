/*
This Frame will provide help information to the user
Nov.19.2019
 */
//package comsci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class HelpFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font instructionFont = new Font("Times New Roman", Font.BOLD, 30);
    private JButton returnButton;
    private JTextArea helpLabel;
    private final java.net.URL help
            = getClass().getResource("helpImage.gif");
    private final ImageIcon helpImage
            = new ImageIcon(new ImageIcon(help).getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));

    private JLabel imageLabel = new JLabel(helpImage);
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel helpPanel = new JPanel(new BorderLayout());
    private final String helpFile = "\n \n \n \n*****Important: Before using the grade book, please add all students of your class in studentList. ***** \n"
            + "\n"
            + "How do I add an assignment?\n"
            + "-Click “GradeBook” on the mainframe, then click “show assignment list. Finally, click “Add an assignment”.\n"
            + "     \n"
            + "How do I grade an assignment?\n"
            + "-Click “GradeBook” on the mainframe, then click “show assignment list. Select one assignment you want to grade.\n Input grades for each student and click “Add”. When the combo box only left with “...”, "
            + "\n click “set entered grade into database”.Then when you back to the grade book frame, it will appear.\n"
            + "    \n"
            + "How do I change the grade(multiple and individuals)? \n"
            + "-You have to regrade everyone according to the “How do I grade an assignment” procedure.\n"
            + "\n"
            + "How do I delete an assignment?\n"
            + "-Just select the assignment you want to delete and click “Delete assignment”.\n"
            + "\n"
            + "What is assignment id?\n"
            + "-It is just an auto-generated id for each assignment you created. Teachers don’t have to worry about it.\n"
            + "\n"
            + "How does the arranging seat work?\n"
            + "-It will sort the student in the student list by their height. And the empty places in the classroom will be blank.\n"
            + "\n"
            + "How does the “During exam” arranging seat work?\n"
            + "-It will output a possible solution to make sure all suspectors won’t seat next to each other.\n"
            + "\n"
            + "What is quantified integrity?\n"
            + "-It indicates the ratio of total ways of arranging suspectors not sit next to each other and the \n total ways of arranging student\n"
            + "\n"
            + "How to use quantified integrity?\n"
            + "-If it is between 0.7-1.0, the class is in good shape.\n"
            + " If it is between 0.2-0.7 teachers need to start worrying about the class\n"
            + " If it is below 0.2 teachers need to take some actions \n"
            + " If it is 0, the class needs to be dismissed.\n"
            + "\n"
            + "How to make an appointment?\n"
            + "-Go to appointment frame and click add an appointment, fill the information in add appointment frame\n"
            + "\n"
            + "How to find an appointment?\n"
            + "-Click the specific date in the calendar frame, and all the appointments on that day will be displayed by time order.\n"
            + "\n"
            + "Why Error Frame shows “Please correct/confirm your input”?\n"
            + "-This means you put a letter or a word at the place which only accepts the number.\n"
            + "\n"
            + "How to change your password?\n"
            + "-At login Frame click “change your password” \n"
            + "*****username can’t be changed.*****\n"
            + "\n"
            + "How to input student height information?\n"
            + "-Please use the decimal form like 5.4.\n"
            + "\n"
            + "How are the final grades work?\n"
            + "-The final grade is the average grade of all assignments, and it will be rounded up.\n"
            + "\n"
            + "How to use the graph function?\n"
            + "-After grading at least one assignment, the program will able to graph the line chart of"
            + " the student grade, which will \n provide a visual for teachers about students’ current status."
            + "\n"
            + "";

    public HelpFrame()
    {
        super("Help Frame");
        this.setBounds(100, 500, 900, 560);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Coustruct Button
        returnButton = new JButton("back");
        returnButton.addActionListener(this);

        //Construct Label
        helpLabel = new JTextArea(helpFile);
        helpLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        helpPanel.add(imageLabel, BorderLayout.NORTH);
        helpPanel.add(helpLabel, BorderLayout.CENTER);
        helpPanel.setBackground(BKGROUND_COLOR);
        helpLabel.setBackground(BKGROUND_COLOR);

        //scroll pane
        scrollPane.getViewport().add(helpPanel);
        scrollPane.setOpaque(false);

        //add Label
        this.add(scrollPane, BorderLayout.CENTER);
        // this.add(imageLabel,BorderLayout.NORTH);
        this.add(returnButton, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    // find if the button pressed or not
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    public static void main(String[] args)
    {
        new HelpFrame();
    }

}
