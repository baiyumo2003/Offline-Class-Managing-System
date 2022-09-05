/*
 This Frame will pop up when there is error occured
Nov.15,2019
 */
//package comsci;

//initialise and constructor
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
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ErrorFrame extends JFrame implements ActionListener
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private final Font instructionFont = new Font("Times New Roman", Font.BOLD, 30);
    private JButton returnButton;
    private JLabel errorLabel;
    private final java.net.URL warning
            = getClass().getResource("image.jpeg");
    private final ImageIcon warningImage
            = new ImageIcon(new ImageIcon(warning).getImage().getScaledInstance(350, 180, Image.SCALE_DEFAULT));

    private JLabel imageLabel = new JLabel(warningImage);

    public ErrorFrame(String error)
    {
        super("Error Frame");
        this.setBounds(100, 500, 700, 380);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //Coustruct Button
        returnButton = new JButton("back");
        returnButton.addActionListener(this);

        //Construct Label
        errorLabel = new JLabel(error, SwingConstants.CENTER);
        errorLabel.setFont(instructionFont);

        //add Label
        this.add(errorLabel, BorderLayout.NORTH);
        this.add(imageLabel, BorderLayout.CENTER);
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
        new ErrorFrame("124567898765678");
    }

}
